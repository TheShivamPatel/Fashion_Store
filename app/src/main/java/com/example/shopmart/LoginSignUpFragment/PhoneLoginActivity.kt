package com.example.shopmart.LoginSignUpFragment

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shopmart.R
import com.example.shopmart.databinding.ActivityPhoneLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneLoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPhoneLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.materialButton2.setOnClickListener {
            if (binding.phoneNumberEdt.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show()
            } else {
                sendOtp(binding.phoneNumberEdt.text.toString())
            }
        }

    }


    private lateinit var builder : AlertDialog
    private fun sendOtp(phoneNumber: String) {

        builder = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please wait")
            .create()
        builder.show()


        val options = PhoneAuthOptions.newBuilder()
            .setPhoneNumber("+91$phoneNumber") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }


    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

        }

        override fun onVerificationFailed(e: FirebaseException) {

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            builder.dismiss()

            val intent = Intent(this@PhoneLoginActivity , OTPActivity::class.java)
            intent.putExtra("verificationId" , verificationId)
            intent.putExtra("number", binding.phoneNumberEdt.text.toString())
            startActivity(intent)
            finish()
        }
    }


}