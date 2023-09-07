package com.example.shopmart.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopmart.adapters.CartBagAdapter
import com.example.shopmart.databinding.FragmentCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var shoppingBag: ArrayList<*>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)


        getShoppingBagList()

        return binding.root
    }

    private fun getShoppingBagList() {

        val list: ArrayList<String> = arrayListOf()

        firebaseAuth = FirebaseAuth.getInstance()
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid).get()
            .addOnCompleteListener { task ->
                list.clear()
                if (task.isSuccessful) {
                    val doc = task.result
                    if (doc.exists()) {
                        shoppingBag = doc.get("shoppingBag") as ArrayList<*>
                    }
                    for (i in shoppingBag) {
                        list.add(i.toString())
                    }
                }
                calculateTotal(list)
                binding.bagRv.adapter = CartBagAdapter(requireContext(), list, 0)
                binding.bagRv.layoutManager = LinearLayoutManager(context)
            }

    }

    private fun calculateTotal(list: ArrayList<String>) {
        var amountTotal = 0
        var mrpTotal =0
        val db = Firebase.firestore.collection("products")

        for (user in list) {

            db.document(user).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val doc = task.result
                        if (doc.exists()) {
                            val productSp = doc.get("productSp").toString()
                            val productMrp = doc.get("productMrp").toString()
                            amountTotal += productSp.toInt()
                            mrpTotal += productMrp.toInt()
                        }
                    }

                    binding.totalMrp.text = "₹ $mrpTotal"
                    binding.totalAmount.text = "₹ $amountTotal /-"
                    binding.discountText.text = "-₹${mrpTotal-amountTotal}"
                }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}