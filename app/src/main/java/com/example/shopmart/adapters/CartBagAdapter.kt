package com.example.shopmart.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopmart.R
import com.example.shopmart.databinding.BagItemLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CartBagAdapter(
    private val context: Context,
    private val list: ArrayList<String>,
    private var i: Int
) :
    RecyclerView.Adapter<CartBagAdapter.bagViewHolder>() {

    inner class bagViewHolder(val binding: BagItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bagViewHolder {
        val view = BagItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return bagViewHolder(view)
    }

    override fun onBindViewHolder(holder: bagViewHolder, position: Int) {
        holder.binding.apply {

            val user = list[i]
            i++
            Firebase.firestore.collection("products").document(user).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val doc = task.result
                        if (doc.exists()) {
                            val brand_name = doc.getString("brandName")
                            val product_name = doc.getString("productName")
                            val price = doc.getString("productSp")
                            val productImage = doc.getString("productCoverImg")

                            brandName.text = brand_name
                            productNameTxt.text = product_name
                            productPrice.text = "₹$price"
                            Glide.with(context).load(productImage).into(productCoverImg)
                        }
                    }
                }

            deleteBtn.setOnClickListener {
                Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid)
                    .update("shoppingBag", FieldValue.arrayRemove(user))
                deleteBtn.setImageResource(R.drawable.baseline_maximize_24)
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}