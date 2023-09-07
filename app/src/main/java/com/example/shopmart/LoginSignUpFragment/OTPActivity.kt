package com.example.shopmart.LoginSignUpFragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shopmart.MainActivity
import com.example.shopmart.R
import com.example.shopmart.databinding.ActivityOtpactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding
    private lateinit var phoneNumber : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        phoneNumber = intent.getStringExtra("number").toString()

        binding.verifyBtn.setOnClickListener {
            if (binding.otpEdt.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show()
            } else {
                verifyOtp(binding.otpEdt.text.toString())
            }
        }


    }

    private fun verifyOtp(otp: String) {
        val credential =
            PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId")!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val isNewUser = task.result.additionalUserInfo?.isNewUser

                    if (isNewUser == true) {
                        Toast.makeText(this, "New User", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this , NewUserRegistrationActivity::class.java)
                        intent.putExtra("number", phoneNumber)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }

                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }

}