package com.example.twopayd2c.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twopayd2c.R
import com.example.twopayd2c.databinding.ActivityPersonalDetailsPageBinding

class PersonalDetailsPage : AppCompatActivity() {

    lateinit var binding : ActivityPersonalDetailsPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDetailsPageBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val secondaryBlue = ContextCompat.getColor(this, R.color.white)
        binding.main.setBackgroundColor(secondaryBlue)

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
        setclickListner()



    }
    fun setclickListner(){
        binding.backicon.setOnClickListener {
            finish()
        }
    }


}