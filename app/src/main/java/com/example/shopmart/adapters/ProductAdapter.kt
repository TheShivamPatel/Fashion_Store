package com.example.shopmart.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopmart.R
import com.example.shopmart.activity.ProductDetailActivity
import com.example.shopmart.databinding.ProductItemLayoutBinding
import com.example.shopmart.model.AddProductModel

class ProductAdapter(private val context : Context , private val list : ArrayList<AddProductModel>) : RecyclerView.Adapter<ProductAdapter.productViewHolder>() {


    inner class productViewHolder(val binding : ProductItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productViewHolder {
        val binding = ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return productViewHolder(binding)
    }

    override fun onBindViewHolder(holder: productViewHolder, position: Int) {
        val data = list[position]
        Glide.with(context).load(data.productCoverImg).thumbnail(Glide.with(context).load(R.drawable.loading)).into(holder.binding.shapeableImageView2)
        holder.binding.textView2.text = data.productName
        holder.binding.brandName.text = data.brandName
        holder.binding.textView3.text = ("Best price ₹${data.productSp}")
        holder.binding.root.setOnClickListener {
            val intent = Intent(context , ProductDetailActivity::class.java)
            intent.putExtra("id" , list[position].productId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}