package com.example.shopmart.activity

import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shopmart.R
import com.example.shopmart.databinding.ActivityProductDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    private var db = Firebase.firestore
    private var isInBag = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)


        val productId = intent.getStringExtra("id")
        getProductDetail(productId)

        binding.cartBtn.setOnClickListener {
            addOrRemoveFromBag(productId)
        }

        productAddToCart(productId)
    }


    private fun addOrRemoveFromBag(productId: String?) {

        val bag = db.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid)

        if (isInBag) {
            bag.update("shoppingBag", FieldValue.arrayRemove(productId))
        } else {
            bag.update("shoppingBag", FieldValue.arrayUnion(productId))
        }

        productAddToCart(productId)
    }


    private fun productAddToCart(productId: String?) {

        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("users").document(currentUser).get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val doc = task.result
                    if (doc.exists()) {
                        val shoppingBag = doc.get("shoppingBag") as List<*>

                        if (shoppingBag.contains(productId)) {
                            isInBag = true
                            binding.cartBtn.apply {
                                text = "REMOVE"
                            }
                        } else {
                            isInBag = false
                            binding.cartBtn.apply {
                                text = "ADD TO BAG"
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        this@ProductDetailActivity,
                                        R.color.red
                                    )
                                )
                                setTextColor(Color.WHITE)
                            }
                        }

                    }
                }

            }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }


    private fun getProductDetail(proId: String?) {

        db.collection("products").document(proId!!).get().addOnSuccessListener {

            val name = it.getString("productName")
            val brandName = it.getString("brandName")
            val desc = it.getString("productDescription")
            val specialPrice = ("₹${it.getString("productSp")}")
            val mrpStr = it.getString("productMrp")
            val spStr = it.getString("productSp")
            val coverImg = it.getString("productCoverImg")


            val list = it.get("productImages") as ArrayList<String>
            binding.productBrand.text = brandName
            binding.productName.text = name
            binding.productPrice.text = specialPrice
            binding.productDesc.text = desc

            val content = "MRP ₹${it.getString("productMrp")}"
            binding.productMrp.paintFlags =
                binding.productMrp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.productMrp.text = content


            val mrp = mrpStr?.toFloat()
            val sp = spStr?.toFloat()

            val offPercentage = ((mrp!! - sp!!) / mrp) * 100
            val percentageInInt = offPercentage.toInt()
            binding.productDiscount.text = ("-${percentageInInt}%")

            val slideList = ArrayList<SlideModel>()
            for (data in list) {
                slideList.add(SlideModel(data, ScaleTypes.CENTER_CROP))
            }
            binding.imageView.setImageList(slideList)


        }.addOnFailureListener {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }

    }

}