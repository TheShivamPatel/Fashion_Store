package com.example.shopmart.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.shopmart.LoginSignUpFragment.PhoneLoginActivity
import com.example.shopmart.adapters.ProductAdapter
import com.example.shopmart.databinding.FragmentHomeBinding
import com.example.shopmart.model.AddProductModel
import com.example.shopmart.model.CategoryModel
import com.example.storeadmin.adapters.CategoryAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)



        getSliderImage()

        return binding.root
    }

    private fun getSliderImage() {

        Firebase.firestore.collection("slider").document("item")
            .get().addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.brandSliderImage)
                binding.brandPromoTxt.text = it.get("brand").toString()
            }
        getCategoryFromFirebase()
    }
    // ------------------------ on create ended ---------------------------//

    // ------------------------ getting category form firebase ----------------//
    private fun getCategoryFromFirebase() {
        val list = ArrayList<CategoryModel>()

        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                binding.categoryRv.adapter = CategoryAdapter(requireContext(), list)
            }
        getProductsFromFirebase()
    }

    // ------------------------- getting all products form firebase ------------//
    private fun getProductsFromFirebase() {

            val list = ArrayList<AddProductModel>()
            Firebase.firestore.collection("products").get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                binding.productRv.adapter = ProductAdapter(requireContext() , list)
                binding.productRv.layoutManager = GridLayoutManager(requireContext(), 2)
            }

    }


}