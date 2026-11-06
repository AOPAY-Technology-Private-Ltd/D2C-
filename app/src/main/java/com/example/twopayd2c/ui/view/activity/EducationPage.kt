package com.example.twopayd2c.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twopayd2c.R
import com.example.twopayd2c.activity.AdditionalDetailsPage.Companion.count
import com.example.twopayd2c.activity.AdditionalDetailsPage.Companion.progress
import com.example.twopayd2c.databinding.ActivityEducationPageBinding
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.startFlow

class EducationPage : AppCompatActivity() {
    lateinit var binding: ActivityEducationPageBinding
    private lateinit var queue: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEducationPageBinding.inflate(layoutInflater)
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
        queue = intent.getStringArrayListExtra("FLOW_QUEUE") ?: arrayListOf()
        progress +=25
        Log.d("progress","$progress")
        setOnClickListner()
        setUiProgress()
    }

    fun setUiProgress(){
        binding.progresstxt.text="Question ${count} of 4"
        binding.downloadProgressbar.setProgress(progress)
        if(count==4){
            binding.nextbutton.text=getString(R.string.completed)
        }else{
            binding.nextbutton.text=getString(R.string.next)
        }
    }

    fun  setOnClickListner(){
        binding.backicon.setOnClickListener {
            finish()
        }

        binding.nextBtn.setOnClickListener {
           //startActivity(Intent(this,FamilyMemberPage::class.java))
            saveMaritalStatus()
            goNext()
        }

    }

    private fun goNext() {
        if (queue.isEmpty()) {
            finish() // flow completed
            return
        }

        val next = queue.removeAt(0)

        val intent = when (next) {
            ConstantClass.Companion.FlowConfigration.GENDER -> Intent(this, GenderPage::class.java)
            ConstantClass.Companion.FlowConfigration.MARITAL -> Intent(this, MaritalStatusPage::class.java)
            ConstantClass.Companion.FlowConfigration.FAMILY -> Intent(this, FamilyMemberPage::class.java)
            else -> null
        }

        intent?.apply {
            putStringArrayListExtra("FLOW_QUEUE", queue)
            count++
            startActivity(this)
            finish()
        }
    }

    private fun saveMaritalStatus() {
        getSharedPreferences("UserPrefs", MODE_PRIVATE)
            .edit()
            .putString(ConstantClass.Companion.FlowConfigration.EDUCATION, "completed")
            .apply()
    }



}