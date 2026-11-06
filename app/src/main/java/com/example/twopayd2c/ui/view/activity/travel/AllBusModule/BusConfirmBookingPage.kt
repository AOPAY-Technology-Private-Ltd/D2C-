package com.example.twopayd2c.ui.view.activity.travel.AllBusModule

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.example.twopayd2c.data.model.travelmodel.promocode.GetPromotionListReq
import com.example.twopayd2c.data.model.travelmodel.transferAMountToAgent.TransferAmountToAgentsReq
import com.example.twopayd2c.data.model.travelmodel.travel.bus.addMoney.BusAddMoneyReq
import com.example.twopayd2c.data.model.travelmodel.travel.bus.addMoney.BusAddMoneyRes
import com.example.twopayd2c.data.model.travelmodel.walletBalance.merchantBal.GetMerchantBalanceReq
import com.example.twopayd2c.data.model.travelmodel.walletBalance.merchantBal.GetMerchantBalanceRes
import com.bumptech.glide.Glide
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.bus.busRequery.BusRequeryReq
import com.example.twopayd2c.data.model.travelmodel.bus.busRequery.BusRequeryRes
import com.example.twopayd2c.data.model.travelmodel.bus.busRequery.PaXDetails
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.AddTicketReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.AddTicketResponseReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusPaxRequeryResponseReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketingReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketingRes
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.BusCommissionDataItem
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.BusCommissionReq
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.BusCommissionResp
import com.example.twopayd2c.data.model.travelmodel.bus.promocode.PromocodeDataItem
import com.example.twopayd2c.data.repository.GetAllAPIServiceRepository
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.GetAllApiServiceViewModelFactory
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.ActivityConfirmBookingPageBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.PromocodeAdapter
import com.example.twopayd2c.ui.adapter.UserPassengerDetailsAdapter
import com.example.twopayd2c.ui.view.activity.paymentgateway.PayemntGateWayPage
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.BusRequeryResponse
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.ConvenienceFee
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.OnwordFare
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.OperatorGST
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.SeaterAmount
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.SleeperAmount
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.forServiceChargeSeatTypeBus
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.grandTotal
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusBookedTicketActivity.Companion.promoDiscount
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.ReviewBookingPage.Companion.forServiceChargeSeatType
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.AddDetailsPassangerActivity.Companion.flightDetailsPassangerDetail
import com.example.twopayd2c.ui.view.activity.wallet.WalletTopUpPage
import com.example.twopayd2c.ui.viewmodel.GetAllApiServiceViewModel
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.GetAirlineLogo
import com.example.twopayd2c.utils.ConstantClass.Companion.serviceChargeWithGST
import com.example.twopayd2c.utils.SharedPreference
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Hashtable
import java.util.Locale
import java.util.TimeZone

class BusConfirmBookingPage : AppCompatActivity(),PromocodeAdapter.OnClickListner {
    lateinit var binding: ActivityConfirmBookingPageBinding
    var promocodeOfferList:MutableList<String> = mutableListOf()
    var promodataList: List<PromocodeDataItem?>? = arrayListOf()
    lateinit var promoAdapter : PromocodeAdapter
    lateinit var preference : SharedPreference
    private lateinit var viewModel: TravelViewModel
    var offerAmount: Double = 0.0
    var totalAmountWithoutOffer: Double = 0.0
    var travelCompanyName : String? = ""
    var fromCityName : String? = ""
    var toCityName : String ?= ""
    private var qrText: String? = null
    private var qrBitmap: Bitmap? = null
    private lateinit var getAllApiServiceViewModel: GetAllApiServiceViewModel
    private lateinit var passengerList: ArrayList<PaXDetails>
    private lateinit var userPassengerDetailsAdapter: UserPassengerDetailsAdapter


    companion object{
        var rechargeAmount : String =""

    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale", "SetTextI18n", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityConfirmBookingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAllApiServiceViewModel = ViewModelProvider(this, GetAllApiServiceViewModelFactory(
            GetAllAPIServiceRepository(ApiClient.apiAllInterface)
        )
        )[GetAllApiServiceViewModel::class.java]

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

        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI, ApiClient.apiBusAddRequestlAPI)))[TravelViewModel::class.java]
        preference = SharedPreference(this)

        fromCityName = preference?.getStringValue(ConstantClass.fromDesignationName, "")
        toCityName = preference?.getStringValue(ConstantClass.toDesignationName, "")
        travelCompanyName = preference?.getStringValue(ConstantClass.travelCompanyName, "")

        setOnClickListner()

        // checking  commission slab for ticket booking.........................................
        getCommissionRequestRetailer()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setOnClickListner(){
        binding.backicon.setOnClickListener {
            finish()
        }

        binding.cancellationPolicyLayout.setOnClickListener {
            openBottomSheetForCancellationPolicy()
        }


        binding.removePromocode.setOnClickListener {
            if(promoAdapter!=null){
                binding.promoCodeOfferlayout.visibility=View.GONE

                binding.showingAppliedCodeLayout.visibility=View.GONE
                promoAdapter.removeSelection()
                binding.transferamt.text = "₹ ${String.format(" % .2f", totalAmountWithoutOffer)}"
                binding.price.text = String.format("%.2f", totalAmountWithoutOffer)
            }
        }

        binding.proceedtopaylayout.setOnClickListener {
            OpenForPaymentOption()
        }
    }


    fun openBottomSheetForCancellationPolicy(){
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.cancellationpolicy, null)
        bottomSheetDialog.setContentView(view)

        var cancel = view.findViewById<ImageView>(R.id.cancelIcon)


        cancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()

    }


    fun hitApiForPromotionList(totalRechargeAmount: Double) {
        var userCode = preference!!.getStringValue(ConstantClass.RegistrationId, "").toString()
        var adminCode = preference!!.getStringValue(ConstantClass.AdminCode, "")
        var state = preference!!.getStringValue(ConstantClass.State, "").toString()

        runOnUiThread {
            val request = GetPromotionListReq(
                country = "India",
                applicationType = "Mobile App",
                applicableSubServices = "",
                applicationMode = "D2C",
                adminCode = adminCode,
                applicableOperators = "",
                promoCode = "",
                retailerCode = userCode,
                state = state,
                applicableServices = "F0133",
                status = "",
                promoionType = "Services"

            )

            Log.d("promolistreq", Gson().toJson(request))

            viewModel.getPromotionListRequest(request).observe(this) { resource ->
                resource?.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data?.let { users ->
                                users.body()?.let { response ->
                                    ConstantClass.dialog.dismiss()
                                    if (response.isSuccess!!) {
                                        promodataList = response!!.data!!
                                        Log.d("promolistresp", Gson().toJson(promodataList))
                                        if(promodataList!!.isNotEmpty()){
                                            promoAdapter = PromocodeAdapter(promodataList,this,this,totalRechargeAmount,this)
                                            binding.showingOfferlist.adapter = promoAdapter
                                            binding.offerLayout.visibility=View.VISIBLE
                                        }
                                        else{
                                            binding.offerLayout.visibility=View.GONE
                                        }
                                    }
                                    else {
                                        binding.offerLayout.visibility=View.GONE
                                        Toast.makeText(this, response.returnMessage, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }

                        ApiStatus.ERROR -> {

                        }

                        ApiStatus.LOADING -> {
                        }

                    }
                }
            }
        }
    }


    // for adding commission and service charge
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale", "SetTextI18n", "SuspiciousIndentation")
    private fun getCommissionRequestRetailer(){
        var adminCode =  preference.getStringValue(ConstantClass.AdminCode,"")
        var retailerId =  preference.getStringValue(ConstantClass.RegistrationId,"")
        var buscategory =  forServiceChargeSeatType.get(0).busType

        var seattype = getSeatTypes()

        if (seattype.size == 1) {
            // Only Sleeper or only Seater
            hitCommissionAPIRetailer(seattype[0].second, retailerId!!, adminCode!!, buscategory, rechargeAmount)
        }
        else {
            // Both Sleeper + Seater
            hitBothSeatTypeApiRetailer(seattype, retailerId!!, adminCode!!, buscategory, rechargeAmount)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale", "SetTextI18n", "SuspiciousIndentation")
    fun hitCommissionAPIRetailer(seatType: String, retailerId: String, adminCode: String, busCategory: String, rechargeAmount: String) {

        val req = BusCommissionReq(
            productSource = "F0133",
            seatType = seatType,
            retailerType = "NA",
            busCategory = busCategory,
            admincode = adminCode,
            userType = "D2C",
            retailerID = retailerId,
        )
        Log.d("RetailerCommissionReq", Gson().toJson(req))

        viewModel.getBusCommissionRequest(req).observe(this) { resource ->
            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    val response = resource.data?.body()
                    Log.d("RetailerCommissionResp", Gson().toJson(response))
                    if (response != null && response.isSuccess!!) {
                        getAllServiceChargeApiResRetailer(response, rechargeAmount)
                    }
                    else {
                        // Save commission types in shared preferences
                        with(preference!!) {
                            setStringValue(ConstantClass.retailer_CommissionType, "")
                            setStringValue(ConstantClass.serviceType, "")
                        }

                        preference!!.setStringValue(ConstantClass.retailerCommissionWithoutTDS, String.format("%.2f", 0.0))
                        preference!!.setStringValue(ConstantClass.retailerCommission, String.format("%.2f", 0.0))
                        preference!!.setStringValue(ConstantClass.tds, String.format("%.2f", 0.0))

                        val totalRechargeAmount = (rechargeAmount.toDoubleOrNull() ?: 0.0) + 0.0
                        preference!!.setStringValue(ConstantClass.totalTransaction, String.format("%.2f", totalRechargeAmount))

                        preference!!.getStringValue(ConstantClass.serviceChargeWithGST, "")!!
                        preference!!.setStringValue(ConstantClass.gst, String.format("%.2f", 0.0))
                        preference!!.setStringValue(ConstantClass.serviceCharge, String.format("%.2f", 0.0))
                        preference!!.setStringValue(ConstantClass.retailerCommission, String.format("%.2f", 0.0))
                        preference!!.setStringValue(ConstantClass.tds, String.format("%.2f", 0.0))

                        openDialogForPayout(rechargeAmount.toDoubleOrNull() ?: 0.0, 0.0, totalRechargeAmount, 0.0)

                    }
                }
                ApiStatus.ERROR -> if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                    ConstantClass.dialog.dismiss()
                }
                ApiStatus.LOADING ->  ConstantClass.loading(this)
            }
        }

    }



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale", "SetTextI18n", "SuspiciousIndentation")
    fun hitCommissionAPIRetailerForBothCondition(seatType: String, retailerId: String, adminCode: String, busCategory: String, rechargeAmount: String  , callback: ApiCallback<BusCommissionResp>) {

        val req = BusCommissionReq(
            productSource = "F0133",
            seatType = seatType,
            retailerType = "SPECIFIC",
            busCategory = busCategory,
            admincode = adminCode,
            userType = "B2B",
            retailerID = retailerId,
        )
        Log.d("RetailerCommissionReq", Gson().toJson(req))

        viewModel.getBusCommissionRequest(req).observe(this) { resource ->
            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    val response = resource.data?.body()
                    if (response != null && response.isSuccess == true) {
                        callback.onSuccess(response)   // 🔥 return data
                    }
                    else {
                        callback.onError("Slab not found") // 🔥 return error
                        if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                            ConstantClass.dialog.dismiss()
                        }
                    }
                }
                ApiStatus.ERROR ->  if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                    ConstantClass.dialog.dismiss()
                }
                ApiStatus.LOADING ->    ConstantClass.loading(this)
            }
        }

    }



    interface ApiCallback<T> {
        fun onSuccess(response: T)
        fun onError(message: String)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale", "SetTextI18n", "SuspiciousIndentation")
    fun hitBothSeatTypeApiRetailer(seatTypes: List<Pair<String,String>>, retailerId: String, adminCode: String, busCategory: String, rechargeAmount: String) {

        var commissionResp1: BusCommissionResp? = null
        var commissionResp2: BusCommissionResp? = null


        fun tryMerge() {
            if (commissionResp1 != null && commissionResp2 != null) {
                mergeAndCalculateRetailer(commissionResp1!!, commissionResp2!!, rechargeAmount)
            }
        }

        hitCommissionAPIRetailerForBothCondition(seatTypes[0].second, retailerId, adminCode, busCategory, rechargeAmount,object : ApiCallback<BusCommissionResp>{
            override fun onSuccess(response: BusCommissionResp) {
                commissionResp1 = response
                Log.d("RetailerBothCommissionResp", Gson().toJson(response))
                tryMerge()
            }

            override fun onError(message: String) {
                if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                    ConstantClass.dialog.dismiss()
                }
            }

        })


        hitCommissionAPIRetailerForBothCondition(seatTypes[1].second, retailerId, adminCode, busCategory, rechargeAmount,object :ApiCallback<BusCommissionResp>{
            override fun onSuccess(response: BusCommissionResp) {
                commissionResp2 = response
                Log.d("RetailerBothCommissionResp", Gson().toJson(response))
                tryMerge()
            }

            override fun onError(message: String) {
                if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                    ConstantClass.dialog.dismiss()
                }
            }

        })

    }



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale", "SetTextI18n", "SuspiciousIndentation")
    private fun mergeAndCalculateRetailer(resp1: BusCommissionResp, resp2: BusCommissionResp, rechargeAmount: String) {
        val combined = BusCommissionResp(
            isSuccess = true,
            data = listOf(
                BusCommissionDataItem(
                    commissionValue = (resp1.data!![0].commissionValue ?: 0.0) + (resp2.data[0].commissionValue ?: 0.0),
                    commissionType = resp1.data!![0].commissionType, // assume same type
                    servicesValue = (resp1.data!![0].servicesValue ?: 0.0) + (resp2.data[0].servicesValue ?: 0.0),
                    servicesType = resp1.data!![0].servicesType // assume same
                )
            )
        )

        getAllServiceChargeApiResRetailer(combined, rechargeAmount)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale", "SetTextI18n", "SuspiciousIndentation")
    private fun getAllServiceChargeApiResRetailer(response: BusCommissionResp, rechargeAmount: String) {
        if (response.isSuccess!!) {
            if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                ConstantClass.dialog.dismiss()
            }
            // Parse values safely
            val rechargeAmountValue = rechargeAmount.toDoubleOrNull() ?: 0.0
            val retailerCommission = response!!.data!![0]?.commissionValue ?: 0.0

            val TDSTax = 5.0 // Fixed TDS rate

            // Function to calculate commission with TDS
            fun calculateCommission(amount: Double, type: String?, tdsRate: Double): Double {
                return when {
                    "percentage".equals(type, ignoreCase = true) -> {
                        val commissionAmount = rechargeAmountValue * (amount / 100)
                        val tdsAmount = commissionAmount * (tdsRate / 100)

                        preference!!.setStringValue(ConstantClass.retailerCommission, String.format("%.2f", commissionAmount))
                        preference!!.setStringValue(ConstantClass.tds, String.format("%.2f", tdsAmount))

                        commissionAmount - tdsAmount
                    }

                    "amount".equals(type, ignoreCase = true) -> {
                        val tdsAmount = amount * (tdsRate / 100)

                        preference!!.setStringValue(ConstantClass.retailerCommission, String.format("%.2f", amount))
                        preference!!.setStringValue(ConstantClass.tds, String.format("%.2f", tdsAmount))

                        amount - tdsAmount
                    }

                    else -> {
                        preference!!.setStringValue(ConstantClass.retailerCommission, "0.0")
                        preference!!.setStringValue(ConstantClass.tds, "0.0")
                        0.0
                    }
                }
            }

            // Calculate retailer commission
            var type =  response.data[0].commissionType!!.trim()?.lowercase() ?: ""
            val finalRetailerCommission = calculateCommission(retailerCommission, type, TDSTax)

            // Log results
            Log.d("FinalRetailerCommission", String.format("%.2f", finalRetailerCommission))


            // Service charge calculation
            val gst = 18.0 // Fixed GST rate of 18%
            val serviceCharge = response!!.data!![0]?.servicesValue ?: 0.0

            val totalServiceChargeWithGst = serviceChargeCalculation(serviceCharge, gst, rechargeAmount, response)
            Log.d("servicechargewithgst", String.format("%.2f", totalServiceChargeWithGst))

//              Calculating the total recharge amount
            val totalRechargeAmount = (rechargeAmount.toDoubleOrNull() ?: 0.0) + totalServiceChargeWithGst
            Log.d("rechargeAmount", String.format("%.2f", totalRechargeAmount))

            // Save commission types in shared preferences
            with(preference!!) {
                setStringValue(ConstantClass.retailer_CommissionType, response.data!![0]?.commissionType.toString())
                setStringValue(ConstantClass.serviceType, response.data!![0]?.servicesType.toString())
            }

            preference!!.setStringValue(ConstantClass.retailerCommissionWithoutTDS, String.format("%.2f", finalRetailerCommission))

            preference!!.setStringValue(ConstantClass.totalTransaction, String.format("%.2f", totalRechargeAmount))

            openDialogForPayout(rechargeAmount.toDoubleOrNull() ?: 0.0, totalServiceChargeWithGst, totalRechargeAmount, preference!!.getStringValue(ConstantClass.retailerCommission, "")!!.toDoubleOrNull()!!)

        }
        else {

            if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                ConstantClass.dialog.dismiss()
            }

            // Save commission types in shared preferences
            with(preference!!) {
                setStringValue(ConstantClass.retailer_CommissionType, "")
                setStringValue(ConstantClass.serviceType, "")
            }

            preference!!.setStringValue(ConstantClass.retailerCommissionWithoutTDS, String.format("%.2f", 0.0))
            preference!!.setStringValue(ConstantClass.retailerCommission, String.format("%.2f", 0.0))
            preference!!.setStringValue(ConstantClass.tds, String.format("%.2f", 0.0))

            val totalRechargeAmount = (rechargeAmount.toDoubleOrNull() ?: 0.0) + 0.0
            preference!!.setStringValue(ConstantClass.totalTransaction, String.format("%.2f", totalRechargeAmount))

            serviceChargeWithGST = preference!!.getStringValue(ConstantClass.serviceChargeWithGST, "")!!
            preference!!.setStringValue(ConstantClass.gst, String.format("%.2f", 0.0))
            preference!!.setStringValue(ConstantClass.serviceCharge, String.format("%.2f", 0.0))
            preference!!.setStringValue(ConstantClass.retailerCommission, String.format("%.2f", 0.0))
            preference!!.setStringValue(ConstantClass.tds, String.format("%.2f", 0.0))

            openDialogForPayout(rechargeAmount.toDoubleOrNull() ?: 0.0, 0.0, totalRechargeAmount, 0.0)

        }

    }


    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun serviceChargeCalculation(serviceCharge: Double, gstRate: Double, rechargeAmount: String, response: BusCommissionResp): Double {
        val rechargeAmountValue = rechargeAmount.toDoubleOrNull() ?: 0.0
        var type =  response.data!![0]?.servicesType!!.trim()?.lowercase() ?: ""
        val totalAmountWithGst = when (type) {
            "amount" -> {
                // Service charge is a fixed amount
                val serviceChargeWithGst = serviceCharge * (gstRate / 100)
                preference!!.setStringValue(ConstantClass.gst, String.format("%.2f", serviceChargeWithGst))
                preference!!.setStringValue(ConstantClass.serviceCharge, String.format("%.2f", serviceCharge))
                serviceCharge + serviceChargeWithGst
            }

            "percentage" -> {
                // Service charge is a percentage of the recharge amount
                val serviceInAmount = rechargeAmountValue * (serviceCharge / 100)
                val serviceWithGst = serviceInAmount * (gstRate / 100)
                preference!!.setStringValue(ConstantClass.gst, String.format("%.2f", serviceWithGst))
                preference!!.setStringValue(ConstantClass.serviceCharge, String.format("%.2f", serviceInAmount))
                serviceInAmount + serviceWithGst

            }
            else -> {
                preference!!.setStringValue(ConstantClass.gst, String.format("%.2f", 0.0))
                preference!!.setStringValue(ConstantClass.serviceCharge, String.format("%.2f", 0.0))
                0.0
            }
        }

        preference!!.setStringValue(ConstantClass.serviceChargeWithGST, String.format("%.2f", totalAmountWithGst))

        Log.d("gstamount", preference!!.getStringValue(ConstantClass.gst, "").toString())
        Log.d("servicecharge", preference!!.getStringValue(ConstantClass.serviceCharge, "").toString())
        Log.d("totalAmountWithGst", preference!!.getStringValue(ConstantClass.serviceChargeWithGST, "").toString())

        // Return the total service charge (with GST) to include in the final transaction
        return totalAmountWithGst
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun openDialogForPayout(transferAmount: Double, servicechargeGst: Double, totalRechargeAmount: Double, retailerCommission: Double) {
        forServiceChargeSeatTypeBus.clear()
        forServiceChargeSeatTypeBus.addAll(forServiceChargeSeatType)

        if(forServiceChargeSeatType[0].seater>0) {
           binding.seaterlayout.visibility= View.VISIBLE
            binding.seatetxt.text= "Seater Fare (${forServiceChargeSeatType[0].seater})"
        }
        else {
            binding.seaterlayout.visibility= View.GONE
        }

        if(forServiceChargeSeatType[0].sleeper>0) {
            binding.sleeperlayout.visibility= View.VISIBLE
            binding.sleepertxt.text= "Sleeper Fare (${forServiceChargeSeatType[0].sleeper})"
        }
        else {
            binding.sleeperlayout.visibility= View.GONE
        }

        binding.seateramount.text = forServiceChargeSeatType[0].busfare
            .filter { it.length.equals("1")  }
            .sumOf { it.basicamount ?: 0.0 }
            .toString()

        binding.sleeperamount.text = forServiceChargeSeatType[0].busfare
            .filter { it.length.equals("2") }
            .sumOf { it.basicamount ?: 0.0 }
            .toString()

        binding.operatorgst.text = "${forServiceChargeSeatType[0].busfare.sumOf { it.otheramount ?: 0.0  }}"

        val gst = preference!!.getStringValue(ConstantClass.gst, "")

        preference!!.setStringValue(ConstantClass.serviceChargewithgst, String.format("%.2f", servicechargeGst)).toString()
        preference!!.setStringValue(ConstantClass.actualbusticketamt, String.format("%.2f", transferAmount)).toString()


        binding.actualamt.text = "$transferAmount"
        binding.servicechargewithgst.text = String.format("%.2f", servicechargeGst)


        binding.servicescharge.text = preference!!.getStringValue(ConstantClass.serviceCharge, "").toString()
        binding.gstamount.text = "$gst"

        var totalcount = forServiceChargeSeatType[0].seater + forServiceChargeSeatType[0].sleeper

        totalAmountWithoutOffer = (transferAmount + servicechargeGst)

        binding.basefaretxt.text = "Service Charge (${totalcount})"



        binding.transferamt.text = String.format("%.2f", totalAmountWithoutOffer)
        binding.price.text = String.format("%.2f", totalAmountWithoutOffer)
        binding.promoCodeOfferlayout.visibility=View.GONE


        if(servicechargeGst==0.0){
            binding.servicechargelayout.visibility=View.GONE
        }else{
            binding.servicechargelayout.visibility=View.VISIBLE
        }

        var checkView: Boolean = false

        binding.viewbreaklayout.setOnClickListener {
            if (checkView) {
                binding.chargesdetailslayout.visibility = View.GONE
                checkView = false
            } else {
                binding.chargesdetailslayout.visibility = View.VISIBLE
                checkView = true
            }
            if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                ConstantClass.dialog.dismiss()
            }
        }


        hitApiForPromotionList(totalAmountWithoutOffer)


    }


    private fun getSeatTypes(): List<Pair<String,String>> {
        val types = mutableListOf<Pair<String,String>>()

        val sleeper = forServiceChargeSeatType[0].sleeper
        val seater = forServiceChargeSeatType[0].seater

        if (sleeper != 0) types.add( Pair("2","SLEEPER"))
        if (seater != 0) types.add(Pair("1","SEATER"))

        return types // size 1 (single) or 2 (both)
    }


    override fun promoClickListner(saveamount: Int,promocode:String?) {
        offerAmount = saveamount.toDouble()
        binding.offercode.text= promocode
        binding.savedamount.text = "You saved ₹ ${saveamount}"
        binding.promoCodeOfferlayout.visibility=View.VISIBLE
        binding.showingAppliedCodeLayout.visibility=View.VISIBLE
        binding.promodiscountamount.text="- ₹${saveamount}"
        var totalRechargeAmount =(totalAmountWithoutOffer)?.minus(saveamount.toDouble())
        binding.transferamt.text = "₹ ${String.format(" % .2f", totalRechargeAmount)}"
        binding.price.text = String.format("%.2f", totalRechargeAmount)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun OpenForPaymentOption(){
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.payment_option_layout, null)
        bottomSheetDialog.setContentView(view)

        var cancel = view.findViewById<ImageView>(R.id.cross)

        var walletbalanceamttxt = view.findViewById<TextView>(R.id.balanceamtbtxt)
        var insufficientbtxt = view.findViewById<TextView>(R.id.insufficientbtxt)
        var insufficientAmounttxt = view.findViewById<TextView>(R.id.insufficientAmounttxt)

        var companyName = view.findViewById<TextView>(R.id.companyName)
        var fromcityname = view.findViewById<TextView>(R.id.fromcityname)
        var tocityname = view.findViewById<TextView>(R.id.tocityname)
        var ticketAmounttxt = view.findViewById<TextView>(R.id.busticketAmounttxt)
        var travelDate = view.findViewById<TextView>(R.id.traveldate)

        var wallettoplayout = view.findViewById<ConstraintLayout>(R.id.wallettopuplayout)
        var pickupdropFlightlayout = view.findViewById<ConstraintLayout>(R.id.pickupdropFlightlayout)
        var pickupdroplayout = view.findViewById<ConstraintLayout>(R.id.pickupdroplayout)

        pickupdropFlightlayout.visibility = View.GONE
        pickupdroplayout.visibility = View.VISIBLE

        var proceedBtn = view.findViewById<TextView>(R.id.proceedBtn)

        val walletLayout = view.findViewById<LinearLayout>(R.id.walletLayout)
        val gatewayLayout = view.findViewById<LinearLayout>(R.id.paymentgatewaylayout)

        val walletButton = view.findViewById<RadioButton>(R.id.walletButton)
        val gatewayButton = view.findViewById<RadioButton>(R.id.gatewayButton)

        fromcityname.text = fromCityName
        tocityname.text = toCityName
        companyName.text = travelCompanyName

        var transferAmount = binding.price.text.toString().toDoubleOrNull()?:0.0
        ticketAmounttxt.text  =  " ₹ %.2f  ".format(transferAmount.toDouble())
        var walletBalance = preference.getStringValue(ConstantClass.WalletAmount,"")
        walletbalanceamttxt.text  =  " ₹ %.2f  ".format(walletBalance?.toDouble())

        val ticketAmount = transferAmount ?: 0.0
        val walletAmount = walletBalance?.toDoubleOrNull() ?: 0.0

        grandTotal = "%.2f  ".format(transferAmount.toDouble())
        promoDiscount = binding.promodiscountamount.text.toString()
        SeaterAmount = binding.seateramount.text.toString()
        SleeperAmount = binding.sleeperamount.text.toString()
        OperatorGST = binding.operatorgst.text.toString()
        OnwordFare = binding.actualamt.text.toString()
        ConvenienceFee = binding.servicechargewithgst.text.toString()


        var bussearchdate =  preference?.getStringValue(ConstantClass.dateAndTime, "")
        travelDate.text = bussearchdate


        if (walletAmount < ticketAmount) {
            val remainingAmount = ticketAmount - walletAmount
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
        gatewayButton.isClickable = false

        if (walletAmount >= ticketAmount) {
            gatewayButton.isChecked = true
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
            gatewayButton.isChecked = false

            if (walletAmount < ticketAmount) {
                proceedBtn.isEnabled = false
                proceedBtn.backgroundTintList=  ContextCompat.getColorStateList(this,R.color.disablebuttoncolor)
            } else {
                proceedBtn.isEnabled = true
                proceedBtn.backgroundTintList = ContextCompat.getColorStateList(this,R.color.primary_blue)
            }

            Log.d("WalletChecked", "Yes")
        }

        gatewayLayout.setOnClickListener {
            walletButton.isChecked = false
            gatewayButton.isChecked = true
            proceedBtn.isEnabled = true
            proceedBtn.backgroundTintList = ContextCompat.getColorStateList(this,R.color.primary_blue)

            Log.d("GateWayChecked", "Yes")
        }

        proceedBtn.setOnClickListener {
            if(gatewayButton.isChecked){
                var intent = Intent(this, PayemntGateWayPage::class.java)
                intent.putExtra(ConstantClass.DebitAmount, " %.2f  ".format(ticketAmount))
                startActivity(intent)
                bottomSheetDialog.dismiss()

            }
            else {
                getMerchantBalance(ticketAmount)
            }

        }

        wallettoplayout.setOnClickListener {
            var intent = Intent(this, WalletTopUpPage::class.java)
            intent.putExtra(ConstantClass.DebitAmount, " %.2f  ".format(ticketAmount))
            startActivity(intent)
        }

        cancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getMerchantBalance(ticketAmount: Double) {
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
                                    getAllMerchantBalanceRes(response, ticketAmount)
                                }
                            }
                        }

                        ApiStatus.ERROR -> {

                            if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                ConstantClass.dialog.dismiss()
                            }
                        }

                        ApiStatus.LOADING -> {
                         ConstantClass.loading(this)
                        }
                    }
                }
            }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAllMerchantBalanceRes(response: GetMerchantBalanceRes, ticketAmount: Double) {
        if (response.isSuccess == true) {
            Log.d(TAG, "getAllMerchantBalanceRes: ${response.data[0].debitBalance}")
            preference!!.setStringValue(ConstantClass.merchantBalance, response.data[0].debitBalance)

            val merchantBalance = response.data[0].debitBalance?.toDoubleOrNull() ?: 0.0

            if (ticketAmount <= merchantBalance) {
                getAllBusAddMoney(preference.getStringValue(ConstantClass.BookingRefNo, "").toString())
            }
            else {
                if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                    ConstantClass.dialog.dismiss()
                }
                Toast.makeText(this, "Your merchant balance is low. Please contact the administrator", Toast.LENGTH_LONG).show()
            }
        } else {
            if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                ConstantClass.dialog.dismiss()
            }
            Toast.makeText(this, response.returnMessage.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun getAllBusAddMoney(referenceId: String) {
        val busAddMoneyReq = BusAddMoneyReq(
            clientRefNo = referenceId,
            refNo = referenceId,
            transactionType = 0,
            productId = "0",
            iPAddress = preference.getStringValue(ConstantClass.deviceIPAddress, ""),
            requestId = preference.getStringValue(ConstantClass.requestId, ""),
            imeINumber = preference.getStringValue(ConstantClass.imeINumber, ""),
            registrationID = preference.getStringValue(ConstantClass.MerchantId, "")
        )
        Log.d("BusAddMoneyReq", Gson().toJson(busAddMoneyReq))


        viewModel.getAllAddMoney(busAddMoneyReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("BusAddMoneyResp", Gson().toJson(response))
                                getAllBusAddMoneyRes(response)
                            }
                        }
                    }

                    ApiStatus.ERROR -> {

                    }

                    ApiStatus.LOADING -> {

                    }
                }
            }
        }

    }



    private fun getAllBusAddMoneyRes(response: BusAddMoneyRes?) {

        if (response?.responseHeader?.errorCode == "0000") {
            getAllBusTicketing(preference.getStringValue(ConstantClass.BookingRefNo, "").toString())
        }
        else {
            Toast.makeText(this, response?.responseHeader?.errorInnerException.plus("Amount: ").plus(response!!.amount), Toast.LENGTH_SHORT).show()
        }
    }



    private fun getAllBusTicketing(referenceId: String) {
        val busTicketingReq = BusTicketingReq(
            bookingRefNo = referenceId,
            iPAddress = preference.getStringValue(ConstantClass.deviceIPAddress, ""),
            requestId = preference.getStringValue(ConstantClass.requestId, ""),
            imeINumber = preference.getStringValue(ConstantClass.imeINumber, ""),
            registrationID = preference.getStringValue(ConstantClass.MerchantId, "")
        )

        Log.d("BusTicketReq", Gson().toJson(busTicketingReq))

        getAddBusTicketRequest(preference.getStringValue(ConstantClass.BookingRefNo, "").toString(),busTicketingReq)

    }


    // hit api for add bus ticket request.............................................................................................
    private fun getAddBusTicketRequest(referenceId: String,busTicketingReq:BusTicketingReq) {
        var  apirequest = Gson().toJson(busTicketingReq)

        val addTicketReq = AddTicketReq(
            requestId =preference.getStringValue(ConstantClass.requestId, "") ,
            iPAddress = preference.getStringValue(ConstantClass.deviceIPAddress, ""),
            imeINumber = preference.getStringValue(ConstantClass.imeINumber, ""),
            bookingRefNo = referenceId,
            createdBy = preference.getStringValue(ConstantClass.RegistrationId, ""),
            registrationID = preference.getStringValue(ConstantClass.MerchantId, ""),
            loginID = preference.getStringValue(ConstantClass.RegistrationId, ""),
            apiRequest = apirequest)

        Log.d("busAddTicketRequest", Gson().toJson(addTicketReq))

        viewModel.getAddBusTicketRequest(addTicketReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {

                        viewModel.getAllBusTicketing(busTicketingReq).observe(this) { resource ->
                            resource?.let {
                                when (it.apiStatus) {
                                    ApiStatus.SUCCESS -> {
                                        it.data?.let { users ->
                                            users.body()?.let { response ->

                                                Log.d("bookingRefNo",response.bookingRefNo.toString())

                                                if(!response.bookingRefNo.toString().isNullOrEmpty()){
                                                    preference.setStringValue(ConstantClass.ForPayoutBookingRefId, response.bookingRefNo.toString())
                                                    getAllBusTicketingRes(response)
                                                }else{
                                                    Toast.makeText(this,response.responseHeader?.errorInnerException.toString(),Toast.LENGTH_SHORT).show()
                                                }

                                            }
                                        }
                                    }

                                    ApiStatus.ERROR -> {
                                        if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                            ConstantClass.dialog.dismiss()
                                        }
                                    }

                                    ApiStatus.LOADING -> {

                                    }
                                }
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                    }

                    ApiStatus.LOADING -> {
                    }
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun getAllBusTicketingRes(response: BusTicketingRes) {
        Log.d("apiResponse", Gson().toJson(response))

        if (response.responseHeader?.errorCode == "0000") {
            hitApiForBusTicketResponse(response)

        }
        else {
            Toast.makeText(this, response.responseHeader?.errorInnerException.toString(), Toast.LENGTH_SHORT).show()
        }

    }


    private fun getAllBusRequaryTicket() {
        val busRequery = BusRequeryReq(
            bookingRefNo = preference.getStringValue(ConstantClass.BookingRefNo, ""),
            iPAddress = preference.getStringValue(ConstantClass.deviceIPAddress, ""),
            requestId = preference.getStringValue(ConstantClass.requestId, ""),
            imeINumber = preference.getStringValue(ConstantClass.imeINumber, ""),
            registrationID = preference.getStringValue(ConstantClass.MerchantId, ""))

        Log.d("BusRequeryReq",Gson().toJson(busRequery))

        viewModel.getAllBusRequary(busRequery).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("BusRequeryResp",Gson().toJson(response))
                                getAllBusRequaryTicketRes(response)
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                            ConstantClass.dialog.dismiss()
                        }
                    }

                    ApiStatus.LOADING -> {

                    }
                }
            }
        }

    }


    private fun getAllBusRequaryTicketRes(response: BusRequeryRes?) {
        val ticketStatus = when {

            response!!.paXDetails.all { it.status.equals("Confirmed", true) } ->
                "Confirmed"

            response.paXDetails.any { it.status.equals("Confirmed", true) } ||
                    response.paXDetails.any { it.status.equals("Payment Pending", true) } ->
                "Partial Cancelled"

            response.paXDetails.all { it.status.equals("Cancelled", true) } ->
                "Cancelled"

            response.paXDetails.any { it.status.equals("Cancelled", true) } &&
                    response.paXDetails.any { it.status.equals("Confirmed", true) } ->
                "Partial Cancelled"

            else ->
                "Unknown"
        }

        var PaxRequeryResponseReq = BusPaxRequeryResponseReq(
            loginId = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
            bookingRefNo = response.bookingRefNo,
            ipAddress =preference?.getStringValue(ConstantClass.deviceIPAddress, "") ,
            requestId = preference!!.getStringValue(ConstantClass.requestId, ""),
            imeINumber = preference.getStringValue(ConstantClass.imeINumber,""),
            registrationId = preference?.getStringValue(ConstantClass.MerchantId, ""),
            transportPNR =response.transportPNR ,
            ticketStatusId =response.ticketStatusId ,
            ticketStatusDesc = response.ticketStatusDesc ,
            apiResponse = Gson().toJson(response),
            paramUser =preference!!.getStringValue(ConstantClass.RegistrationId, ""),
            ticketcancelstatus= "Confirmed"
        )

        hitApiforPassDetailsListResponse(PaxRequeryResponseReq,response)

    }


    fun hitApiforPassDetailsListResponse(PaxRequeryResponseReq: BusPaxRequeryResponseReq,requeryResponse: BusRequeryRes?){
        Log.d("BusRequeryRequest", Gson().toJson(PaxRequeryResponseReq))

        viewModel.getPassangerDetailsRequest(PaxRequeryResponseReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {

                        it.data?.let { users ->
                            users.body()?.let { response ->

                                Log.d("PassDetailsListResp",Gson().toJson(response))

                                if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                    ConstantClass.dialog.dismiss()
                                }

                                if (requeryResponse?.responseHeader?.errorCode == "0000") {
                                    BusRequeryResponse = requeryResponse
                                    startActivity(Intent(this,BusBookedTicketActivity::class.java))
                                }

                                else {
                                    if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                        ConstantClass.dialog.dismiss()
                                    }
                                    Toast.makeText(this, requeryResponse?.responseHeader?.errorInnerException.toString(), Toast.LENGTH_SHORT).show()
                                }


                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                            ConstantClass.dialog.dismiss()
                        }
                    }

                    ApiStatus.LOADING -> {

                    }
                }
            }
        }


    }


    private fun hitApiForBusTicketResponse(response: BusTicketingRes){
        val busTicketingReq = AddTicketResponseReq(
            requestId =preference.getStringValue(ConstantClass.requestId, "") ,
            iPAddress = preference.getStringValue(ConstantClass.deviceIPAddress, ""),
            imeINumber = preference.getStringValue(ConstantClass.imeINumber, ""),
            bookingRefNo = preference.getStringValue(ConstantClass.BookingRefNo, ""),
            errorCode    = response.responseHeader!!.errorCode,
            errorDesc     = response.responseHeader!!.errorDesc,
            errorInnerException  = response.responseHeader!!.errorInnerException,
            createdBy = preference.getStringValue(ConstantClass.RegistrationId, ""),
            statusId      = response.responseHeader!!.statusId,
            supplierRefNo      = response.supplierRefno,
            transportPNR      = response.transportPNR,
            registrationID = preference.getStringValue(ConstantClass.MerchantId, ""),
            loginID = preference.getStringValue(ConstantClass.RegistrationId, ""),
            apiResponse  =  Gson().toJson(response)
        )

        Log.d("BusticketResponseRequest", Gson().toJson(busTicketingReq))


        viewModel.getAddBusTicketResponse(busTicketingReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        Log.d("BusticketResponseResponse", resource.data.toString())
                        getTransferAmountToAgentWithCal()

                    }

                    ApiStatus.ERROR -> {
                        if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                            ConstantClass.dialog.dismiss()
                        }
                    }

                    ApiStatus.LOADING -> {

                    }
                }
            }
        }

    }


    private fun getTransferAmountToAgentWithCal() {
        try {
            var transferamt =   preference!!.getStringValue(ConstantClass.totalTransaction,"")
            var actualamt =    preference!!.getStringValue(ConstantClass.actualbusticketamt,"").toString()
            var bookingRefId =   preference.getStringValue(ConstantClass.ForPayoutBookingRefId, "")

            val transferAmountToAgentsReq = TransferAmountToAgentsReq(
               transferFrom = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
                flag = "Y",
                serviceCode = "",
                transIpAddress = preference!!.getStringValue(ConstantClass.deviceIPAddress, ""),
                remark = "Bus Booking  Api",
                transferTo = "Admin",
                transferToMsg = "Your account has been credited by ₹ $transferamt for a bus booking Reference Number: ${bookingRefId}.",
                transferFromMsg = "Your account has been debited by ₹ $transferamt for a bus booking Reference Number: ${bookingRefId}.",
                operatorCode = "",
                subserviceCode = "",
                servicesChargeGSTAmt = preference!!.getStringValue(ConstantClass.gst, "")?.toDoubleOrNull()?:0.0,
                servicesChargeWithoutGST = preference!!.getStringValue(ConstantClass.serviceCharge, "")?.toDoubleOrNull()?:0.0,
                depositBankName = "",
                actualTransactionAmount = actualamt,
                accountHolder = "",
                benfiid = "",
                apporvedStatus = "Approved",
                registrationId = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
                retailerCommissionAmt = "0.00"?.toDoubleOrNull(),
                customerVirtualAddress = "",
                transferAmt = transferamt?.toDoubleOrNull(),
                merchantCode = preference!!.getStringValue(ConstantClass.MerchantId, ""),
                amountType = "Payout",
                paymentMode = "",
                orderID = "" ,
                retailerId = "",
                parmUserName = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
                branchCodeChecqueNo = "",
                servicesChargeAmt = preference!!.getStringValue(ConstantClass.serviceChargewithgst, "")?.toDoubleOrNull()?:0.0,
                operatorname = "",
            )

            Log.d("TransferAmountToAgentReq", Gson().toJson(transferAmountToAgentsReq))
            getAllApiServiceViewModel.getTransferAmountToAgents(transferAmountToAgentsReq)
                .observe(this) { resource ->
                    resource?.let {
                        when (it.apiStatus) {
                            ApiStatus.SUCCESS -> {
                                it.data?.let { users ->
                                    users.body()?.let { response ->

                                        Log.d("TransferAmountToAgentReqResp", response.toString())
                                        val commission = preference!!.getStringValue(ConstantClass.retailerCommission, "0.00")?.trim()?.toDoubleOrNull() ?: 0.0

                                        Log.d("retailercommissionforpayout", commission.toString())

                                        getAllBusRequaryTicket()



                                    }
                                }
                            }

                            ApiStatus.ERROR -> {
                                if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                    ConstantClass.dialog.dismiss()
                                }
                                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }

                            ApiStatus.LOADING -> {

                            }
                        }
                    }
                }
        }  catch (e: NumberFormatException) {
            e.printStackTrace()
            if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                ConstantClass.dialog.dismiss()
            }
            Toast.makeText(this, e.message.toString() + " " + e.localizedMessage?.toString(), Toast.LENGTH_SHORT).show()
        }
    }





}