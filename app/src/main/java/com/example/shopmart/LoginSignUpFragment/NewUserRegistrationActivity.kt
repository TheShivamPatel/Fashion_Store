package com.example.shopmart.LoginSignUpFragment

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shopmart.MainActivity
import com.example.shopmart.databinding.ActivityNewUserRegistrationBinding
import com.example.shopmart.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewUserRegistrationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewUserRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUserRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val number  = intent.getStringExtra("number")
        binding.textView6.text = number

        binding.materialButton2.setOnClickListener {
            validateUser()
        }
    }


    private fun validateUser() {

        if (binding.nameEdt.text!!.isEmpty()){
            Toast.makeText(this , "Please fill all details!", Toast.LENGTH_SHORT).show()
        }
        else{
            storeData()
        }

    }

    private fun storeData() {

        val builder = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please wait")
            .create()
        builder.show()

        val data = UserModel(name= binding.nameEdt.text.toString() , number= intent.getStringExtra("number").toString())


        Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid).set(data)
            .addOnSuccessListener {
                builder.dismiss()
                Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@NewUserRegistrationActivity , MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                builder.dismiss()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }



}