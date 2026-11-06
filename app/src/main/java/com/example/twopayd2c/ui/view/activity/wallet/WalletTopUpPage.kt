package com.example.twopayd2c.ui.view.activity.wallet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.example.twopayd2c.R
import com.example.twopayd2c.databinding.ActivityWalletTopUpPageBinding
import com.example.twopayd2c.ui.adapter.WalletTopUpAdapter
import com.example.twopayd2c.ui.view.activity.paymentgateway.PayemntGateWayPage
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.roundToNextHundred
import com.example.twopayd2c.utils.SharedPreference

// if kyc 2 lpa and dont have 10k
class WalletTopUpPage : AppCompatActivity(), WalletTopUpAdapter.ClickListner {
    lateinit var binding: ActivityWalletTopUpPageBinding
    lateinit var preference: SharedPreference
    var totalRechargeAmount: Double = 0.0
    var walletTopUpAmountList : MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWalletTopUpPageBinding.inflate(layoutInflater)
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

        preference = SharedPreference(this)

        if(intent.hasExtra(ConstantClass.DebitAmount)){
            this.totalRechargeAmount = intent.getStringExtra(ConstantClass.DebitAmount).toString().toDouble()
        }

        binding.recommendedlayout.visibility =View.VISIBLE

        var walletBalance = preference.getStringValue(ConstantClass.WalletAmount,"")
        binding.walletAmount.text  =  " ₹ %.2f  ".format(walletBalance?.toDouble())

        val ticketAmount = totalRechargeAmount ?: 0.0
        val walletAmount = walletBalance?.toDoubleOrNull() ?: 0.0

        walletTopUpAmountList.clear()

        if (walletAmount < ticketAmount) {
            val remainingAmount = ticketAmount - walletAmount
            walletTopUpAmountList.add(" %.2f  ".format(remainingAmount))
            val amount1 = roundToNextHundred(remainingAmount)
            walletTopUpAmountList.add(amount1.toString())
            var amount : Int = 0
            for(i in 1 until 3){
                amount = amount + 100
                walletTopUpAmountList.add(amount.toString())
            }
            binding.enterAmount.setText(amount1.toString())

            if(amount1<10000){
                binding.proceedBtn.isEnabled = true
                binding.proceedBtn.backgroundTintList = ContextCompat.getColorStateList(this,R.color.primary_blue)
                binding.errormsgforamount.visibility=View.GONE
            }else{
                binding.proceedBtn.isEnabled = false
                binding. proceedBtn.backgroundTintList=  ContextCompat.getColorStateList(this,R.color.disablebuttoncolor)
                binding.errormsgforamount.visibility=View.VISIBLE
            }
            binding.recommendamount.setText("₹ ${amount1}")
            binding.recommendedtxtmsg.text= "₹ ${amount1} ${resources.getString(R.string.is_the_recommended)}"

        }
        preference.setBooleanValue(ConstantClass.WalletAmountClick,false)
        val adapter = WalletTopUpAdapter(this,walletTopUpAmountList,this,preference)
        binding.recommendedAmountShow.adapter=adapter

        setOnClickListner()

    }


    fun setOnClickListner(){
        binding.backicon.setOnClickListener {
            preference.setBooleanValue(ConstantClass.WalletAmountClick,false)
            finish()
        }

        binding.enterAmount.doAfterTextChanged {

            val amount = it.toString().toDoubleOrNull() ?: return@doAfterTextChanged

            when {
                amount < totalRechargeAmount -> {
                    binding.errormsgforamount.visibility=View.VISIBLE
                    binding.errormsgforamount.text = "Minimum ₹${"%.2f".format(totalRechargeAmount)}"
                    binding.proceedBtn.isEnabled = false
                    binding. proceedBtn.backgroundTintList=  ContextCompat.getColorStateList(this,R.color.disablebuttoncolor)

                }
                amount > 10000 -> {
                    binding.errormsgforamount.visibility=View.VISIBLE
                    binding.errormsgforamount.text = "Amount exceeds limit. Please enter ₹10,000 or less."
                    binding.proceedBtn.isEnabled = false
                    binding. proceedBtn.backgroundTintList=  ContextCompat.getColorStateList(this,R.color.disablebuttoncolor)
                }
                else -> {
                    binding.enterAmount.error = null
                    binding.errormsgforamount.visibility=View.GONE
                    binding.proceedBtn.isEnabled = true
                    binding.proceedBtn.backgroundTintList = ContextCompat.getColorStateList(this,R.color.primary_blue)
                }
            }
        }

        binding.proceedBtn.setOnClickListener {
            var totalRechargeAmount = binding.enterAmount.text.toString().toDouble()
            var intent = Intent(this, PayemntGateWayPage::class.java)
            intent.putExtra(ConstantClass.DebitAmount, " %.2f  ".format(totalRechargeAmount))
            startActivity(intent)
        }

    }



    override fun itemclickListner(position: Int, amount: String) {
      preference.setBooleanValue(ConstantClass.WalletAmountClick,true)
      binding.recommendedlayout.visibility =View.GONE

      when(position)  {
          0, 1 -> {
              binding.enterAmount.setText(amount)
          }
          else -> {
              val enteredAmount = binding.enterAmount.text.toString().trim()

              val currentAmount = enteredAmount.toDoubleOrNull() ?: 0.0
              val clickAmount = amount.toDoubleOrNull() ?: 0.0
              val totalAmount = currentAmount + clickAmount
              if (totalAmount <=10000) {
                  binding.enterAmount.setText("%.2f".format(totalAmount))
                  binding.errormsgforamount.visibility=View.GONE
                  binding.proceedBtn.isEnabled = true
                  binding.proceedBtn.backgroundTintList = ContextCompat.getColorStateList(this,R.color.primary_blue)
              }
              else{
                  binding.errormsgforamount.visibility=View.VISIBLE
                  binding.proceedBtn.isEnabled = false
                  binding. proceedBtn.backgroundTintList=  ContextCompat.getColorStateList(this,R.color.disablebuttoncolor)
              }
          }

         }

    }



}