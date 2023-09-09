package com.example.shopmart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.shopmart.R
import com.example.shopmart.databinding.ActivityAddressDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressDetailBinding
    private lateinit var amount: String
    private lateinit var list: ArrayList<String>
    private val currentTime = System.currentTimeMillis()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        amount = intent.getStringExtra("totalAmount")!!
        list = intent.getStringArrayListExtra("productIds")!!

        if(amount == "no"){
            binding.checkoutBtn.text = "Update Profile"
        }

        binding.checkoutBtn.setOnClickListener {
            validateData()
        }

        loadData()

    }

    private fun validateData() {
        if (binding.pincodeEdt.text.toString().isEmpty()) {
            binding.pincodeEdt.error = "Empty"
        } else if (binding.addressEdt.text.toString().isEmpty()) {
            binding.addressEdt.error = "Empty"
        } else if (binding.localityEdt.text.toString().isEmpty()) {
            binding.localityEdt.error = "Empty"
        } else if (binding.cityEdt.text.toString().isEmpty()) {
            binding.cityEdt.error = "Empty"
        } else if (binding.stateEdt.text.toString().isEmpty()) {
            binding.stateEdt.error = "Empty"
        } else {
            uploadDataToUser()
        }
    }

    private fun uploadDataToUser() {
        val data = hashMapOf<String, Any>()
        data["name"] = binding.userName.text.toString()
        data["pinCode"] = binding.pincodeEdt.text.toString()
        data["address"] = binding.addressEdt.text.toString()
        data["locality"] = binding.localityEdt.text.toString()
        data["city"] = binding.cityEdt.text.toString()
        data["state"] = binding.stateEdt.text.toString()
        data["orderedAt"] = currentTime

        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update(data).addOnSuccessListener {
                Toast.makeText(this, "Information Updated!", Toast.LENGTH_SHORT).show()

                if (amount != "no") {
                    val intent = Intent(this, CheckOutActivity::class.java)
                    intent.putExtra("totalAmount", amount)
                    intent.putStringArrayListExtra("productIds", list)
                    startActivity(intent)
                    finish()
                }

            }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
    }


    private fun loadData() {

        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        Firebase.firestore.collection("users").document(currentUser).get()
            .addOnSuccessListener {
                binding.userName.setText(it.getString("name"))
                binding.userNumber.setText(it.getString("number"))
                binding.pincodeEdt.setText(it.getString("pinCode"))
                binding.addressEdt.setText(it.getString("address"))
                binding.cityEdt.setText(it.getString("city"))
                binding.stateEdt.setText(it.getString("state"))
                binding.localityEdt.setText(it.getString("locality"))
            }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
    }
}