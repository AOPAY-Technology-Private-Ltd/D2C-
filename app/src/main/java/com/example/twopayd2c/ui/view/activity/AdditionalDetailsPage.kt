package com.example.twopayd2c.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twopayd2c.R
import com.example.twopayd2c.databinding.ActivityAdditionalDetailsPageBinding
import com.example.twopayd2c.utils.ConstantClass.Companion.startFlow

class AdditionalDetailsPage : AppCompatActivity() {
    lateinit var binding: ActivityAdditionalDetailsPageBinding
    private lateinit var queue: ArrayList<String>

    companion object{
        var count: Int = 0
        var progress: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAdditionalDetailsPageBinding.inflate(layoutInflater)
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

        setOnClickListner()

    }


    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }


    fun setOnClickListner(){

        binding.backicon.setOnClickListener {
            finish()
        }

        binding.genderadd.setOnClickListener {
            //startActivity(Intent(this,GenderPage::class.java))
            startFlow("gender",this)
        }

        binding.maritalstaus.setOnClickListener {
            //startActivity(Intent(this,GenderPage::class.java))
            startFlow("marital",this)
        }

        binding.qualificationstaus.setOnClickListener {
            //startActivity(Intent(this,GenderPage::class.java))
            startFlow("education",this)
        }

        binding.fmlmemberstaus.setOnClickListener {
            //startActivity(Intent(this,GenderPage::class.java))
            startFlow("family",this)
        }

    }


}