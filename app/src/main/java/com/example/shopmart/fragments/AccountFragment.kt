package com.example.shopmart.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.shopmart.LoginSignUpFragment.PhoneLoginActivity
import com.example.shopmart.R
import com.example.shopmart.activity.AddressDetailActivity
import com.example.shopmart.activity.OrdersActivity
import com.example.shopmart.databinding.FragmentAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentAccountBinding.inflate(inflater, container, false)



        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        Firebase.firestore.collection("users").document(currentUser).get()
            .addOnSuccessListener {
                binding.userName.setText(it.getString("name"))
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
            }



        binding.accountDetail.setOnClickListener {
            val intent = Intent(requireContext(), AddressDetailActivity::class.java)
            intent.putExtra("totalAmount", "no")
            intent.putStringArrayListExtra("productIds", ArrayList())
            startActivity(intent)
        }

        binding.allorderDetail.setOnClickListener {
            startActivity(Intent(requireContext(), OrdersActivity::class.java))
        }



        /// log out btn
        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext() , PhoneLoginActivity::class.java))
            activity?.finish()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}