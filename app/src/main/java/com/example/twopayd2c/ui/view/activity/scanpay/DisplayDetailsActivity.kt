package com.example.twopayd2c.ui.view.activity.scanpay

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.twopayd2c.data.model.travelmodel.transferAMountToAgent.TransferAmountToAgentsReq
import com.example.twopayd2c.data.model.travelmodel.transferAMountToAgent.TransferAmountToAgentsRes
import com.example.twopayd2c.data.model.travelmodel.walletBalance.merchantBal.GetMerchantBalanceReq
import com.example.twopayd2c.data.model.travelmodel.walletBalance.merchantBal.GetMerchantBalanceRes
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.merchant.apiServiceCharge.GetPayoutCommercialReq
import com.example.twopayd2c.data.model.merchant.apiServiceCharge.GetPayoutCommercialRes
import com.example.twopayd2c.data.model.recharge.payout.PayoutAmountReq
import com.example.twopayd2c.data.model.recharge.payout.PayoutAmountRes
import com.example.twopayd2c.data.repository.GetAllAPIServiceRepository
import com.example.twopayd2c.data.repository.PayoutRepository
import com.example.twopayd2c.data.viewModelFactory.GetAllApiServiceViewModelFactory
import com.example.twopayd2c.data.viewModelFactory.PayoutViewModelFactory
import com.example.twopayd2c.databinding.ActivityDisplayDetailsBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.PassengerDataForFlightAdapter
import com.example.twopayd2c.ui.view.activity.paymentgateway.PayemntGateWayPage
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.ConvenienceFee
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.OnwordFare
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.OperatorGST
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.SeaterAmount
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.SleeperAmount
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.grandTotal
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.promoDiscount
import com.example.twopayd2c.ui.view.activity.wallet.WalletTopUpPage
import com.example.twopayd2c.ui.viewmodel.GetAllApiServiceViewModel
import com.example.twopayd2c.ui.viewmodel.PayoutViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.hideKeyboard
import com.example.twopayd2c.utils.SharedPreference
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotlin.jvm.java
import kotlin.text.compareTo
import kotlin.text.get
import kotlin.times
import kotlin.toString

class DisplayDetailsActivity : AppCompatActivity() {
    lateinit var binding : ActivityDisplayDetailsBinding
    lateinit var preference : SharedPreference
    private lateinit var viewModel: PayoutViewModel
    private lateinit var getAllApiServiceViewModel: GetAllApiServiceViewModel
    private var isSlabMatched = false // Flag to check if any slab matches
    private var serviceCharge: Double = 0.0


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDisplayDetailsBinding.inflate(layoutInflater)
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

        getAllApiServiceViewModel = ViewModelProvider(this, GetAllApiServiceViewModelFactory(GetAllAPIServiceRepository(ApiClient.apiAllInterface)))[GetAllApiServiceViewModel::class.java]

        viewModel = ViewModelProvider(this, PayoutViewModelFactory(PayoutRepository(ApiClient.apiInterface)))[PayoutViewModel::class.java]

        preference= SharedPreference(this)
        setDataForView()

       setOnClickListner()


    }

    fun  setDataForView(){
        val upiId = intent.getStringExtra("upiId") ?: "N/A"
        val payeeName = intent.getStringExtra("payeeName") ?: "N/A"
        val bankingName = intent.getStringExtra("bankingName") ?: ""
        val amount = intent.getStringExtra("amount") ?: ""
        binding.firstCharacter.text = payeeName!!.take(2).uppercase()

        binding.tvUpiId.text = "UPI ID: $upiId"
        binding.tvAccountNumber.text = "Paying: $payeeName"

        if ("Banking name:$bankingName" != null) {
            binding.tvBankingName.text = "Banking name: $bankingName"
        } else {
            binding.tvBankingName.visibility = View.GONE
        }

        binding.amount.requestFocus()
        binding.amount.postDelayed({
            binding.amount.requestFocus()
            val imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.amount, InputMethodManager.SHOW_FORCED)
        }, 300)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun  setOnClickListner(){

        binding.crossBtn.setOnClickListener {
            finish()
        }

        binding.proceedtopaylayout.setOnClickListener {
            OpenForPaymentOption()
        }

        binding.enterBtn.setOnClickListener {
            if (binding.amount.text!!.toString() < "1") {
                //toast("Payment must be at least ₹1")
                binding.enterBtn.visibility = View.VISIBLE
                binding.proceedtopaylayout.visibility = View.GONE
                binding.amount.requestFocus()

            }
            else {
                val transferAmountText = binding.amount.text.toString().trim()
                getAllApiPayoutCommercialCharge(transferAmountText)
                Log.d("getAllServiceCharge", transferAmountText)
                goPaymentCalling()
            }
        }

        binding.amount.filters= arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
            val newInput = dest.toString().substring(0, dstart) +
                    source.subSequence(start, end) +
                    dest.toString().substring(dend)

            val regex = Regex("^\\d{0,7}(\\.\\d{0,2})?$") // up to 7 digits before decimal, 2 after
            if (newInput.matches(regex)) {
                null // accept input
            } else {
                ""   // reject invalid characters
            }
        })

        binding.main.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            binding.main.getWindowVisibleDisplayFrame(r)
            val screenHeight = binding.main.rootView.height
            val keypadHeight = screenHeight - r.bottom

            if (keypadHeight > screenHeight * 0.20) {
                // Keyboard is open
                if (binding.notes.hasFocus()) {
                    binding.scrollview.post {
                        binding.scrollview.smoothScrollTo(0, binding.scrollview.bottom)
                    }
                }
            }
        }

        binding.amount.addTextChangedListener(object : TextWatcher {
            private var editing = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {

                // Check for ZERO amount
                if (s.isNullOrEmpty()) {
                    binding.enterBtn.visibility = View.VISIBLE
                    binding.proceedtopaylayout.visibility = View.GONE
                }

            }
        })

    }


    private fun goPaymentCalling() {
        hideKeyboard(this)
        binding.proceedtopaylayout.visibility = View.VISIBLE
        binding.enterBtn.visibility = View.GONE
        binding.price.text = binding.amount.text.toString()
    }


    private fun getAllApiPayoutCommercialCharge(rechargeAmount: String) {
        val getPayoutCommercialReq = GetPayoutCommercialReq(
            merchant = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
            productId = "F0112",
            modeofPayment = "UPI"
        )
            Log.d(TAG, "getAllApiPayoutCommercialCharge: " + Gson().toJson(getPayoutCommercialReq))
            getAllApiServiceViewModel.getAllApiPayoutCommercialCharge(getPayoutCommercialReq)
                .observe(this) { resource ->
                    resource?.let {
                        when (it.apiStatus) {
                            ApiStatus.SUCCESS -> {
                                it.data?.let { users ->
                                    users.body()?.let { response ->
                                        ConstantClass.dialog.dismiss()
                                        Log.d("Commercialresponse", Gson().toJson(response))
                                        getAllApiPayoutCommercialChargeRes(response, rechargeAmount)
                                    }
                                }
                            }

                            ApiStatus.ERROR -> {
                                ConstantClass.dialog.dismiss()
                            }

                            ApiStatus.LOADING -> {
                                ConstantClass.loading(this)
                            }
                        }
                    }
                }

    }


    private fun getAllApiPayoutCommercialChargeRes(response: GetPayoutCommercialRes, rechargeAmount: String) {
        if (response.isSuccess == true) {
            try {
                val rechargeAmountValue = rechargeAmount.toDoubleOrNull() ?: 0.0

                for (i in response.data.indices) {
                    val gst = 18.0 // Fixed GST rate of 18%
                    // Get characters between ":" and "-" and convert to Double
                    // Extract the minimum value
                    val min = response.data[i].slabName
                        ?.substringAfter(": ") // Get the part after "With Slab: "
                        ?.substringBefore("-") // Get the part before "-"
                        ?.replace(",", "")     // Remove commas
                        ?.toDoubleOrNull()     // Convert to Double
                        ?: 0.0

                    // Extract the maximum value
                    val max = response.data[i].slabName
                        ?.substringAfter("-")  // Get the part after "-"
                        ?.replace(",", "")     // Remove commas
                        ?.toDoubleOrNull()     // Convert to Double
                        ?: 0.0

                    Log.d(TAG, "getAllApiPayoutCommercialChargeRes: $min, $max")

                    if (rechargeAmountValue in min..max) {
                        isSlabMatched = true   // Mark that a matching slab was found
                        serviceCharge = response.data[i].serviceCharge?.toDoubleOrNull() ?: 0.0
                        Log.d("serviceCharge", serviceCharge.toString())
                        serviceChargeCalculation(serviceCharge, gst, rechargeAmount, response, min, max)
                    }

                }

                //if no matching slab was found, show an error message
                if (!isSlabMatched) {
                    isSlabMatched = false
                    Toast.makeText(this, "No matching slab found for the amount: $rechargeAmountValue", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        else {
            ConstantClass.dialog.dismiss()

            val actualAmount = ("0.0".toDoubleOrNull() ?: 0.0) + (rechargeAmount.toString().toDoubleOrNull() ?: 0.0)

            binding.price.text =  String.format("%.2f", actualAmount)

            preference?. setStringValue(ConstantClass.actualAmountServiceChargeWithGST, String.format("%.2f", actualAmount))
            preference?. setStringValue(ConstantClass.serviceChargeWithGST, String.format("%.2f", 0.0))
            preference?.setStringValue(ConstantClass.totalTransaction, String.format("%.2f", actualAmount))

           // Toast.makeText(this, response.returnMessage.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun serviceChargeCalculation(serviceCharge: Double, gstRate: Double, rechargeAmount: String, response: GetPayoutCommercialRes, min: Double, max: Double): Double {
        val rechargeAmountValue = rechargeAmount.toDoubleOrNull() ?: 0.0
        var serviceChargeWithGst = 0.0
        var slabLimit: String? = null


        if (preference!!.getStringValue(ConstantClass.RegistrationId, "")!!.isNotEmpty()) {
            slabLimit = when {
                rechargeAmountValue <= max -> max.toString()
                else -> {
                    null
                }
            }
        }
        else {
            Toast.makeText(this, "No valid slab found for the amount: $rechargeAmountValue", Toast.LENGTH_SHORT).show()
        }


        for (i in response.data.indices) {
            if (slabLimit != null) {
                Log.d("ServiceType", "${response.data[i].serviceType} - Index $i")

                serviceChargeWithGst = when (response.data[i].serviceType) {
                    "Amount" -> {
                        val serviceChargeGst = serviceCharge * (gstRate / 100)
                        preference?.setStringValue(ConstantClass.serviceCharge, String.format("%.2f", serviceCharge))
                        serviceCharge + serviceChargeGst
                    }

                    "Percentage" -> {
                        val serviceInAmount = rechargeAmountValue * (serviceCharge / 100)
                        val serviceWithGst = serviceInAmount * (gstRate / 100)
                        preference?.setStringValue(ConstantClass.serviceCharge, String.format("%.2f", serviceInAmount))
                        serviceInAmount + serviceWithGst
                    }

                    else -> 0.0
                }
                break

            }
        }

        val actualAmount = serviceChargeWithGst + rechargeAmountValue

        binding.price.text =  String.format("%.2f", actualAmount)

        preference?. setStringValue(ConstantClass.actualAmountServiceChargeWithGST, String.format("%.2f", actualAmount))
        preference?. setStringValue(ConstantClass.serviceChargeWithGST, String.format("%.2f", serviceChargeWithGst))
        preference?.setStringValue(ConstantClass.totalTransaction, String.format("%.2f", actualAmount))

        Log.d("serviceChargeWithGST", String.format("%.2f", actualAmount) + " = " + String.format("%.2f", serviceChargeWithGst) + " = " + String.format("%.2f", actualAmount))
        Log.d("TotalRechargeAmount", String.format("%.2f", actualAmount))
        Log.d("totalAmountWithGst", String.format("%.2f", serviceChargeWithGst))


        if (serviceChargeWithGst == 0.0) {
            Toast.makeText(this, "No matching slab found for the entered amount", Toast.LENGTH_SHORT).show()
            return 0.0
        }


        return serviceChargeWithGst
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun OpenForPaymentOption(){
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.scanpaywalletpop, null)
        bottomSheetDialog.setContentView(view)

        var cancel = view.findViewById<ImageView>(R.id.cross)

        var walletbalanceamttxt = view.findViewById<TextView>(R.id.balanceamtbtxt)

        var insufficientbtxt = view.findViewById<TextView>(R.id.insufficientbtxt)

        var insufficientAmounttxt = view.findViewById<TextView>(R.id.insufficientAmounttxt)

        var wallettoplayout = view.findViewById<ConstraintLayout>(R.id.wallettopuplayout)

        var proceedBtn = view.findViewById<TextView>(R.id.proceedBtn)

        val walletLayout = view.findViewById<LinearLayout>(R.id.walletLayout)

        val walletButton = view.findViewById<RadioButton>(R.id.walletButton)


        var transferAmount = binding.price.text.toString().toDoubleOrNull()?:0.0

        var walletBalance = preference.getStringValue(ConstantClass.WalletAmount,"")
        walletbalanceamttxt.text  =  " ₹ %.2f  ".format(walletBalance?.toDouble())

        val payoutAmount = transferAmount ?: 0.0
        val walletAmount = walletBalance?.toDoubleOrNull() ?: 0.0


        if (walletAmount < payoutAmount) {
            val remainingAmount = payoutAmount - walletAmount
            Log.d("WalletCheck", "Insufficient balance. Need ₹$remainingAmount more")
            // Example UI
            insufficientAmounttxt.text = "₹${"%.2f".format(remainingAmount)}"
            insufficientbtxt.visibility= View.VISIBLE
            insufficientAmounttxt.visibility= View.VISIBLE
        }
        else {
            Log.d("WalletCheck", "Sufficient balance")
            insufficientbtxt.visibility= View.INVISIBLE
            insufficientAmounttxt.visibility= View.INVISIBLE
        }


        walletButton.isClickable = false


        if (walletAmount >= payoutAmount) {
            walletButton.isChecked = false
            proceedBtn.isEnabled = true
            proceedBtn.backgroundTintList = ContextCompat.getColorStateList(this,R.color.primary_blue)
        }
        else {
            walletButton.isChecked = true
            proceedBtn.isEnabled = false
            proceedBtn.backgroundTintList=  ContextCompat.getColorStateList(this,R.color.disablebuttoncolor)
        }


        walletLayout.setOnClickListener {
            walletButton.isChecked = true

            if (walletAmount < payoutAmount) {
                proceedBtn.isEnabled = false
                proceedBtn.backgroundTintList=  ContextCompat.getColorStateList(this,R.color.disablebuttoncolor)
            } else {
                proceedBtn.isEnabled = true
                proceedBtn.backgroundTintList = ContextCompat.getColorStateList(this,R.color.primary_blue)
            }

            Log.d("WalletChecked", "Yes")
        }


        proceedBtn.setOnClickListener {

        getMerchantBalance(payoutAmount)

        }

        wallettoplayout.setOnClickListener {
            var intent = Intent(this, WalletTopUpPage::class.java)
            intent.putExtra(ConstantClass.DebitAmount, " %.2f  ".format(payoutAmount))
            startActivity(intent)
        }

        cancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()

    }


    private fun getMerchantBalance(mainBalance: Double) {
        val getMerchantBalanceReq = GetMerchantBalanceReq(
            parmUser = preference!!.getStringValue(ConstantClass.MerchantId, ""),
            flag = "DebitBalance"
        )
        getAllApiServiceViewModel.getAllMerchantBalance(getMerchantBalanceReq)
            .observe(this) { resource ->
                resource?.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data?.let { users ->
                                users.body()?.let { response ->
                                    getAllMerchantBalanceRes(response, mainBalance)
                                }
                            }
                        }

                        ApiStatus.ERROR -> {
                           ConstantClass.dialog.dismiss()
                        }

                        ApiStatus.LOADING -> {
                            ConstantClass.loading(this)
                        }
                    }
                }
            }
    }


    private fun getAllMerchantBalanceRes(response: GetMerchantBalanceRes, mainBalance: Double) {
        if (response.isSuccess == true) {
            Log.d(TAG, "getAllMerchantBalanceRes: ${response.data[0].debitBalance}")

            val merchantBalance = response.data[0].debitBalance?.toDoubleOrNull() ?: 0.0

            if (mainBalance <= merchantBalance) {
                getTransferAmountToAgentWithCal(binding.amount.text.toString())
            }
            else {
                ConstantClass.dialog.dismiss()
                Toast.makeText(this, "Balance is insufficient,please top up your wallet", Toast.LENGTH_LONG).show()
            }
        } else {
            ConstantClass.dialog.dismiss()
            Toast.makeText(this, response.returnMessage.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun getTransferAmountToAgentWithCal(rechargeAmount: String) {
        try {
            val transferAmountToAgentsReq = TransferAmountToAgentsReq(
                transferFrom = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
                flag = "Y",
                serviceCode = "",
                transIpAddress = preference!!.getStringValue(ConstantClass.deviceIPAddress, ""),
                remark = "Payout",
                transferTo = "Admin",
                transferToMsg = "Your Account is credited by $rechargeAmount Rs. Due to Pay Out on number ${intent.getStringExtra("upiId")}.",
                transferFromMsg = "Your Account is debited by $rechargeAmount Rs. Due to Pay Out on number ${intent.getStringExtra("upiId")}.",
                operatorCode = "",
                subserviceCode = "",
                servicesChargeGSTAmt = preference?.getStringValue(ConstantClass.serviceChargeWithGST, "")?.toDoubleOrNull() ?: 0.0,
                servicesChargeWithoutGST = preference!!.getStringValue(ConstantClass.serviceCharge, "")?.toDoubleOrNull()?: 0.0,
                depositBankName = "",
                actualTransactionAmount = rechargeAmount,
                accountHolder = "",
                benfiid = "",
                apporvedStatus = "Approved",
                registrationId = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
                retailerCommissionAmt = preference!!.getStringValue(ConstantClass.retailerCommissionWithoutTDS, "")?.toDoubleOrNull()?: 0.0,
                customerVirtualAddress = "",
                transferAmt = preference!!.getStringValue(ConstantClass.totalTransaction, "")?.toDoubleOrNull()?: 0.0,
                merchantCode = preference!!.getStringValue(ConstantClass.MerchantId, ""),
                amountType = "Payout",
                paymentMode = "UPI",
                orderID = "" ,
                retailerId = "",
                parmUserName = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
                branchCodeChecqueNo = "",
                servicesChargeAmt = preference!!.getStringValue(ConstantClass.serviceCharge, "")?.toDoubleOrNull()?: 0.0,
                operatorname = "",
            )

            Log.d("moneyTransferReq",Gson().toJson(transferAmountToAgentsReq))

            getAllApiServiceViewModel.getTransferAmountToAgents(transferAmountToAgentsReq)
                .observe(this) { resource ->
                    resource?.let {
                        when (it.apiStatus) {
                            ApiStatus.SUCCESS -> {
                                it.data?.let { users ->
                                    users.body()?.let { response ->
                                        getTransferAmountToAgentWithCalRes(response)
                                    }
                                }
                            }

                            ApiStatus.ERROR -> {
                                ConstantClass.dialog.dismiss()
                                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }

                            ApiStatus.LOADING -> {
                            }
                        }
                    }
                }

        } catch (e: NumberFormatException) {
            e.printStackTrace()
            ConstantClass.dialog.dismiss()
            Toast.makeText(this, e.message.toString() + " " + e.localizedMessage?.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun getTransferAmountToAgentWithCalRes(response: TransferAmountToAgentsRes) {
        try {
            if (response.isSuccess == true) {
                sendingMoney()
            } else {
                ConstantClass.dialog.dismiss()
                Toast.makeText(this, response.returnMessage.toString(), Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            ConstantClass.dialog.dismiss()
            e.printStackTrace()
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun sendingMoney() {
        val payoutAmountReq = PayoutAmountReq(
            registrationID = preference!!.getStringValue(ConstantClass.MerchantId, ""),
            upiID = intent.getStringExtra("upiId"),
            amount = binding.amount.text.toString().trim())

            Log.d("payoutAmountReq", Gson().toJson(payoutAmountReq))

            viewModel.sendAmount(payoutAmountReq).observe(this) { resource ->
                resource?.let {
                    when (it.apiStatus) {

                        ApiStatus.SUCCESS -> {
                            it.data?.let { users ->
                                users.body()?.let { it1 ->
                                    sendAllAmount(it1)
                                }
                            }
                        }

                        ApiStatus.ERROR -> {
                            ConstantClass.dialog.dismiss()
                        }

                        ApiStatus.LOADING -> {

                        }

                    }
                }
            }

    }


    private fun sendAllAmount(response: PayoutAmountRes) {
        try {
            Log.d("payoutresp", Gson().toJson(response))
            if (response.statuss == true || response.statuss == false) {
                ConstantClass.dialog.dismiss()
                val intent = Intent(this, ScanAndPaySuccessFulPage::class.java).apply {
                    putExtra("amount", binding.amount.text.toString())
                    putExtra("upiId", intent.getStringExtra("upiId")) // Fix: Use correct Intent value
                    putExtra("referenceId", response.data!![0].payoutRef.toString())
                    putExtra("payeeName", intent.getStringExtra("payeeName")) // Fix: Use correct Intent value
                    putExtra("dateAndTime", response.data!![0].drLedgerPassedOnTimestamp.toString())
                    putExtra("Status", response.statuss.toString())
                    putExtra("message", response.message.toString())
                    putExtra("utrNo", response.data!![0].payoutRef.toString())
                    putExtra("serviceCharge", preference!!.getStringValue(ConstantClass.serviceCharge, "0.00"))
                    putExtra("serviceChargeWithGST", preference!!.getStringValue(ConstantClass.serviceChargeWithGST, "0.00"))
                    putExtra("totalTransaction", preference!!.getStringValue(ConstantClass.totalTransaction, "0.00"))
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, response.message.toString(), Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }

}