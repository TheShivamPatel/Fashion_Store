package com.example.shopmart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.anupkumarpanwar.scratchview.ScratchView
import com.example.shopmart.R
import com.example.shopmart.databinding.ActivityCompleteOrderBinding

class CompleteOrderActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCompleteOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val random = (29..100).random()
        binding.numberTxt.text = "₹ $random"


        binding.scratchView.setRevealListener(object : ScratchView.IRevealListener{
            override fun onRevealed(scratchView: ScratchView?) {
                binding.scratchView.visibility = View.GONE
                binding.animationView2.playAnimation()
            }

            override fun onRevealPercentChangedListener(scratchView: ScratchView?, percent: Float) {
                if (percent >= .5f){
                }
            }

        })

        binding.textView9.setOnClickListener {
            startActivity(Intent(this, OrdersActivity::class.java))
            finish()
        }


    }
}