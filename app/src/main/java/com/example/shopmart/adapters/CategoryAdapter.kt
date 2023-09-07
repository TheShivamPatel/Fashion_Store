package com.example.storeadmin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopmart.R
import com.example.shopmart.databinding.LayoutCategoryItemBinding
import com.example.shopmart.model.CategoryModel

class CategoryAdapter(private var context: Context, private var list: ArrayList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.cateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cateViewHolder {
        return cateViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: cateViewHolder, position: Int) {

        holder.binding.categoryName.text = list[position].category
        Glide.with(context).load(list[position].img).into(holder.binding.categoryImage)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class cateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = LayoutCategoryItemBinding.bind(view)
    }


}