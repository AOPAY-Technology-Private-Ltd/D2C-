package com.example.twopayd2c.ui.view.activity.scanpay

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.twopayd2c.R
import com.example.twopayd2c.activity.DashBoard
import com.example.twopayd2c.databinding.ActivityScanAndPaySuccessFulPageBinding
import com.example.twopayd2c.utils.SharedPreference


class ScanAndPaySuccessFulPage : AppCompatActivity() {
    private lateinit var bin: ActivityScanAndPaySuccessFulPageBinding
    private var preference: SharedPreference? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bin = ActivityScanAndPaySuccessFulPageBinding.inflate(layoutInflater)
        setContentView(bin.root)
        
        initView()
        btnListener()
        
    }

    private fun btnListener() {

        bin.ivBack.setOnClickListener {
            startActivity(Intent(this, DashBoard::class.java))
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        preference = SharedPreference.getInstance(this)
        bin.repeatRecharge.visibility = View.GONE
        bin.totalAmount.text = "₹" + intent.getStringExtra("totalTransaction")
        bin.amount.text = "₹" + intent.getStringExtra("amount")
        bin.dateAndTime.text = intent.getStringExtra("dateAndTime")
        bin.referenceId.text = intent.getStringExtra("referenceId")
        bin.toUpiId.text = intent.getStringExtra("upiId")
        bin.toUpiIdName.text = "To: " + intent.getStringExtra("payeeName")
        bin.utrNumber.text = intent.getStringExtra("utrNo")
        bin.serviceChargeWithGSTText.text = "₹" + intent.getStringExtra("serviceChargeWithGST")
        bin.totalTransactionText.text = "₹" + intent.getStringExtra("totalTransaction")
        bin.status.text = intent.getStringExtra("message")
        bin.receiverNameFirstCharacter.text = intent.getStringExtra("payeeName")!!.take(2).uppercase()
        bin.receivername.text = "To ${intent.getStringExtra("payeeName")}"

        val isSuccess = (intent.getStringExtra("Status") == "true")
        val colorResId = if (isSuccess) R.drawable.green_circle else R.drawable.green_circle
        bin.circle.setBackgroundResource(colorResId)
        bin.status.text = if (isSuccess) "Transaction Successful" else "Transaction Successful"

    }


}