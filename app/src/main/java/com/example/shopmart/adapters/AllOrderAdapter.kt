package com.example.shopmart.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopmart.databinding.MyordersItemLayoutBinding
import com.example.shopmart.model.AllOrderModel

class AllOrderAdapter(private val context : Context, private val list : ArrayList<AllOrderModel>) : RecyclerView.Adapter<AllOrderAdapter.allOrderViewHolder>() {

    inner class allOrderViewHolder(val binding : MyordersItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): allOrderViewHolder {
        val binding = MyordersItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return allOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: allOrderViewHolder, position: Int) {
        val post = list[position]



        holder.binding.apply {
            Glide.with(context).load(post.productCoverImg).into(productCoverImg)
            brandName.text = post.brandName
            productNameTxt.text = post.productName
            statusText.text = post.status
//            orderOn.text = dateString
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}