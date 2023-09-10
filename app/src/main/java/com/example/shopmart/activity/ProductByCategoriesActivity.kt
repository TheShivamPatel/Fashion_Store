package com.example.shopmart.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopmart.R
import com.example.shopmart.adapters.ProductAdapter
import com.example.shopmart.databinding.ActivityProductByCategoriesBinding
import com.example.shopmart.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductByCategoriesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductByCategoriesBinding
    private lateinit var category : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductByCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra("category")!!

        binding.categoryText.text = category

        getProductsFromFirebase()


    }


    private fun getProductsFromFirebase() {

        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory", category).get().addOnSuccessListener {
            list.clear()
            for (doc in it.documents) {
                val data = doc.toObject(AddProductModel::class.java)
                list.add(data!!)
            }
            binding.productRv.adapter = ProductAdapter(this , list)
            binding.productRv.layoutManager = GridLayoutManager(this, 2)
        }

    }
}