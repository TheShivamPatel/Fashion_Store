package com.example.shopmart.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shopmart.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject


class CheckOutActivity : AppCompatActivity() , PaymentResultListener {

    private lateinit var amount : String
    private lateinit var list : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        amount = intent.getStringExtra("totalAmount")!!
        list = intent.getStringArrayListExtra("productIds")!!

        val totalAmount = amount.toInt()*100

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_eCsOEuoGbKkwYp")

        try {
            val options = JSONObject()
            options.put("name", "Fashion Store")
            options.put("description", "Make your own fashion.")
            options.put("image", "https://i.pinimg.com/originals/36/ad/26/36ad261133c7a54ca92abd693e3d236c.jpg")
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amount", "$totalAmount") //pass amount in currency subunits
            options.put("prefill.email", "shivampatel0700@gmail.com")
            options.put("prefill.contact", "7879908886")
            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString() , Toast.LENGTH_SHORT).show()
        }

    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Successful." , Toast.LENGTH_SHORT).show()
        uploadDataToOrderSection()
    }

    private fun uploadDataToOrderSection() {
        for (currentId in list){
            fetchData(currentId)
        }
        Toast.makeText(this, "Order details saved." , Toast.LENGTH_SHORT).show()
        startActivity(Intent(this , CompleteOrderActivity::class.java))
        finish()
    }

    private fun fetchData(productId: String?) {
        Firebase.firestore.collection("products").document(productId!!).get()
            .addOnSuccessListener {
                saveData(
                    productId,
                    it.getString("brandName"),
                    it.getString("productName"),
                    it.getString("productCoverImg"),
                    it.getString("productSp")
                )
            }
    }

    private fun saveData(
        productId: String,
        brandName: String?,
        productName: String?,
        productCoverImg: String?,
        productSp: String?
    ) {
        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        val data = hashMapOf<String , Any>()
        data["productId"] = productId
        data["brandName"] = brandName!!
        data["productName"] = productName!!
        data["productCoverImg"] = productCoverImg!!
        data["productSp"] = productSp!!
        data["userId"] = currentUser
        data["status"] = "In Process"

        val db = Firebase.firestore.collection("allOrders")
        val key = db.document().id
        data["orderId"] = key

        db.document(key).set(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Order details saved." , Toast.LENGTH_SHORT).show()

                Firebase.firestore.collection("users").document(currentUser).update("shoppingBag", FieldValue.arrayRemove(productId))

            }
            .addOnFailureListener {
                Toast.makeText(this, "Error in saving order details." , Toast.LENGTH_SHORT).show()
            }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed." , Toast.LENGTH_SHORT).show()
    }
}