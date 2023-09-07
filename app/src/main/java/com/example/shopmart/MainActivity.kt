package com.example.shopmart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.shopmart.LoginSignUpFragment.PhoneLoginActivity
import com.example.shopmart.databinding.ActivityMainBinding
import com.example.shopmart.fragments.AccountFragment
import com.example.shopmart.fragments.CartFragment
import com.example.shopmart.fragments.HomeFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main)


        if (FirebaseAuth.getInstance().currentUser == null){
            startActivity(Intent(this , PhoneLoginActivity::class.java))
            finish()
        }


        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener{

            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.search -> replaceFragment(CartFragment())
                R.id.save -> replaceFragment(AccountFragment())
            }
            true

        }
    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_layout, fragment)
        fragmentTransaction.commit()
    }
}