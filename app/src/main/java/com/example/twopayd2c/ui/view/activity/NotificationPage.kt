package com.example.twopayd2c.ui.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twopayd2c.R
import com.example.twopayd2c.databinding.ActivityNotificationPageBinding

class NotificationPage : AppCompatActivity() {
    lateinit var binding: ActivityNotificationPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotificationPageBinding.inflate(layoutInflater)
        val secondaryBlue = ContextCompat.getColor(this, R.color.white)
        binding.main.setBackgroundColor(secondaryBlue)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val bottomPadding = Math.max(systemBars.bottom, imeInsets.bottom)

            // APPLY TOP PADDING: This prevents the overlap with the status bar
            view.setPadding(
                systemBars.left,
                systemBars.top,   // This fixes the overlap
                systemBars.right,
                bottomPadding
            )
            insets
        }
        setOnClickListner()
    }

    fun setOnClickListner(){

        binding.backicon.setOnClickListener {
           finish()
        }

    }


}