package com.example.shopmart.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopmart.R
import com.example.shopmart.adapters.AllOrderAdapter
import com.example.shopmart.adapters.ProductAdapter
import com.example.shopmart.databinding.ActivityOrdersBinding
import com.example.shopmart.model.AddProductModel
import com.example.shopmart.model.AllOrderModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class OrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrdersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAllOrders()

    }


    private fun getAllOrders() {

        val list = ArrayList<AllOrderModel>()
        Firebase.firestore.collection("allOrders")
            .whereEqualTo("userId", FirebaseAuth.getInstance().currentUser!!.uid).get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    if (doc.exists()) {
                        val data = doc.toObject(AllOrderModel::class.java)
                        list.add(data!!)
                    }

                }
                binding.rv.adapter = AllOrderAdapter(this, list)
                binding.rv.layoutManager = LinearLayoutManager(this)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
            }

    }


}