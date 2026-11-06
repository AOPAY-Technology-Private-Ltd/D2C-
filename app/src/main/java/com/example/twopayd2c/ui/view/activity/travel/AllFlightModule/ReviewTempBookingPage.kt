package com.example.twopayd2c.ui.view.activity.travel.AllFlightModule

import android.content.ContentValues
import android.content.Intent
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
import com.example.twopayd2c.data.model.travelmodel.travel.flight.AirTicketBookingRequest
import com.example.twopayd2c.data.model.travelmodel.walletBalance.merchantBal.GetMerchantBalanceReq
import com.example.twopayd2c.data.model.travelmodel.walletBalance.merchantBal.GetMerchantBalanceRes
import com.example.twopayd2c.data.model.travel.flight.AirTicketingReq
import com.example.twopayd2c.data.model.travel.flight.AirTicketingResponse
import com.example.twopayd2c.data.model.travel.flight.FlightRequeryReq
import com.bumptech.glide.Glide
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.bus.promocode.PromocodeDataItem
import com.example.twopayd2c.data.model.travelmodel.flight.AirCommissionReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirCommissionResp
import com.example.twopayd2c.data.model.travelmodel.flight.AirReprintReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirTicketBookingResponseRequest
import com.example.twopayd2c.data.model.travelmodel.flight.FlightAddPaymentReq
import com.example.twopayd2c.data.model.travelmodel.flight.PaXDetailsFlight
import com.example.twopayd2c.data.repository.GetAllAPIServiceRepository
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.GetAllApiServiceViewModelFactory
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.ActivityReviewTempBookingPageBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.PassengerDetailsForReviewTripAdapter
import com.example.twopayd2c.ui.adapter.PromocodeAdapter
import com.example.twopayd2c.ui.view.activity.paymentgateway.PayemntGateWayPage
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.AddDetailsPassangerActivity.Companion.flightDetailsPassangerDetail
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.AddDetailsPassangerActivity.Companion.segmentListPassangerDetail
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.FlightBookedTicketActivity.Companion.BookedTicketList
import com.example.twopayd2c.ui.view.activity.wallet.WalletTopUpPage
import com.example.twopayd2c.ui.viewmodel.GetAllApiServiceViewModel
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.GetAirlineLogo
import com.example.twopayd2c.utils.ConstantClass.Companion.cabinBag
import com.example.twopayd2c.utils.ConstantClass.Companion.calculateTotalFlightDuration
import com.example.twopayd2c.utils.ConstantClass.Companion.checkinBag
import com.example.twopayd2c.utils.ConstantClass.Companion.className
import com.example.twopayd2c.utils.ConstantClass.Companion.formatDate1
import com.example.twopayd2c.utils.ConstantClass.Companion.formatDate2
import com.example.twopayd2c.utils.ConstantClass.Companion.generateRandomString
import com.example.twopayd2c.utils.ConstantClass.Companion.serviceChargeWithGST
import com.example.twopayd2c.utils.ConstantClass.Companion.splitDateTime
import com.example.twopayd2c.utils.ConstantClass.Companion.stops
import com.example.twopayd2c.utils.ConstantClass.Companion.totalDurationTime
import com.example.twopayd2c.utils.ConstantClass.Companion.travelType
import com.example.twopayd2c.utils.SharedPreference
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ReviewTempBookingPage : AppCompatActivity() ,PromocodeAdapter.OnClickListner{
    lateinit var binding : ActivityReviewTempBookingPageBinding
    private lateinit var viewModel: TravelViewModel
    var promodataList: List<PromocodeDataItem?>? = arrayListOf()
    lateinit var promoAdapter: PromocodeAdapter
    lateinit var preference : SharedPreference
    private lateinit var getAllApiServiceViewModel: GetAllApiServiceViewModel
    var selectedPromoCodePosition= -1
    var offerAmount: Double = 0.0
    var totalAmountWithoutOffer: Double = 0.0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReviewTempBookingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        preference = SharedPreference(this)
        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI, ApiClient.apiBusAddRequestlAPI)))[TravelViewModel::class.java]
        getAllApiServiceViewModel = ViewModelProvider(this, GetAllApiServiceViewModelFactory(
            GetAllAPIServiceRepository(ApiClient.apiAllInterface)
        )
        )[GetAllApiServiceViewModel::class.java]

        // check commission slab ....................................
        getCommissionRequestRetailer()
        setDataOnView()

       setOnClickListner()

    }

    companion object{
        var passengerTicketList : MutableList<PaXDetailsFlight> = mutableListOf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDataOnView(){

        val segments = listOf(
            mapOf(
                "departure_DateTime" to segmentListPassangerDetail[0]!!.departureDateTime,
                "arrival_DateTime" to segmentListPassangerDetail[0]!!.arrivalDateTime
            ),
            mapOf(
                "departure_DateTime" to segmentListPassangerDetail[segmentListPassangerDetail.size - 1]!!.departureDateTime,
                "arrival_DateTime" to segmentListPassangerDetail[segmentListPassangerDetail.size - 1]!!.arrivalDateTime
            )
        )

        totalDurationTime = calculateTotalFlightDuration(segments)
        var originDateTime = splitDateTime(segmentListPassangerDetail[0]!!.departureDateTime)

        var originDate = formatDate1(originDateTime.first).plus(",").plus(formatDate2(originDateTime.first))

        var originTime = originDateTime.second

        var destinationDateTime = splitDateTime(segmentListPassangerDetail[segmentListPassangerDetail.size - 1]!!.arrivalDateTime)
        var DestinationTime = destinationDateTime.second


        if (segmentListPassangerDetail.size == 1) {
            stops = "Non stops"
        }
        else {
            stops = (segmentListPassangerDetail.size - 1).toString().plus(" stop")
        }


        // "Fri,4 Jul| 19:10 - 01:05|5h 55m |1 Stop|Economy Class"
        var traveldetails = originDate.plus(" | ").plus(originTime).plus(" - ").plus(DestinationTime).plus(" | ")
            .plus(totalDurationTime).plus(" | ").plus(stops).plus(" | ").plus(className)


        binding.fromAirportCode.text = flightDetailsPassangerDetail[0]!!.origin
        binding.toAirportCode.text = flightDetailsPassangerDetail[0]!!.destination
        binding.travelprimedetails.text = traveldetails
        binding.checkinbagweight.text = checkinBag
        binding.cabinbagweight.text = cabinBag

        var airportIcon = GetAirlineLogo(flightDetailsPassangerDetail[0]!!.airlineCode)
        Glide.with(this).load(airportIcon).error(R.drawable.airplaceholder).into(binding.airlineicon)

        if(passengerTicketList.isNotEmpty()){
            var adapter = PassengerDetailsForReviewTripAdapter(this, passengerTicketList)
            binding.savedPassengerListShown.adapter = adapter
            adapter.notifyDataSetChanged()
        }


    }


    fun setOnClickListner(){
        binding.backicon.setOnClickListener {
            finish()
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


    fun hitApiForPromotionList(totalRechargeAmount: Double) {
        var userCode = /*preference!!.getStringValue(ConstantClass.RegistrationId, "").toString()*/
            "BOS-1055"
        var adminCode = /*preference!!.getStringValue(ConstantClass.AdminCode, "").toString()*/
            "BOS-1002"
        var state = /*preference!!.getStringValue(ConstantClass.State, "").toString()*/ "Delhi"
        var currentDate = getCurrentDateUtc() // 2025-12-29T07:15:24.611Z

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
                applicableServices = "F0134",
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
                                        if (promodataList!!.isNotEmpty()) {
                                            promoAdapter = PromocodeAdapter(promodataList, this, this, totalRechargeAmount,this)
                                            binding.showingOfferlist.adapter = promoAdapter
                                            binding.offerLayout.visibility = View.VISIBLE
                                        }
                                        else {
                                            binding.offerLayout.visibility = View.GONE
                                        }

                                    }
                                    else {
                                        binding.offerLayout.visibility = View.GONE
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


    fun getCurrentDateUtc(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date())
    }


    private fun getCommissionRequestRetailer(){
        var adminCode =  preference!!.getStringValue(ConstantClass.AdminCode,"")
        var retailerId =  preference!!.getStringValue(ConstantClass.RegistrationId,"")
        var airCategory = ""
        if(travelType==0){
            airCategory = "DOMESTIC"
        }
        else{
            airCategory = "INTERNATIONAL"
        }
        var operatorId =  preference!!.getStringValue(ConstantClass.airlinecode,"")

        var rechargeAmount = preference!!.getStringValue(ConstantClass.AirTotalTicketPrice, "")

        hitCommissionAPIRetailer(operatorId!!, retailerId!!, adminCode!!, airCategory, rechargeAmount!!)

    }


    fun hitCommissionAPIRetailer(operatorID: String, retailerId: String, adminCode: String, airCategory: String, rechargeAmount: String) {

        val req = AirCommissionReq(
            productSource = "F0134",
            retailerType = "NA",
            airCategory = airCategory,
            adminCode = adminCode,
            userType = "D2C",
            retailerID = retailerId,
            operatorID = operatorID,
        )

        Log.d("RetailerFlightCommissionReq",Gson().toJson(req))

        getAllApiServiceViewModel.getFlightCommissionRequest(req).observe(this) { resource ->
            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    val response = resource.data?.body()

                    Log.d("RetailerCommissionResp",Gson().toJson(response))
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

                        serviceChargeWithGST = preference!!.getStringValue(ConstantClass.serviceChargeWithGST, "")!!
                        preference!!.setStringValue(ConstantClass.gst, String.format("%.2f", 0.0))
                        preference!!.setStringValue(ConstantClass.serviceCharge, String.format("%.2f", 0.0))
                        preference!!.setStringValue(ConstantClass.retailerCommission, String.format("%.2f", 0.0))
                        preference!!.setStringValue(ConstantClass.tds, String.format("%.2f", 0.0))

                        var msg = "Warning : Slab structure not found. This transaction will proceed without any commission being credited."
                        openDialogForPayout(rechargeAmount.toDoubleOrNull() ?: 0.0, 0.0, totalRechargeAmount, 0.0, msg)

                    }
                }
                ApiStatus.ERROR -> if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                    ConstantClass.dialog.dismiss()
                }
                ApiStatus.LOADING -> {
                    ConstantClass.loading(this)
                }
            }
        }

    }


    private fun getAllServiceChargeApiResRetailer(response: AirCommissionResp, rechargeAmount: String) {
        if (response.isSuccess!!) {
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
            var type =  response.data!![0]?.commissionType!!.trim()?.lowercase() ?: ""
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

            openDialogForPayout(rechargeAmount.toDoubleOrNull() ?: 0.0, totalServiceChargeWithGst, totalRechargeAmount, preference!!.getStringValue(ConstantClass.retailerCommission, "")!!.toDoubleOrNull()!!, "")

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

            serviceChargeWithGST = preference!!.getStringValue(ConstantClass.serviceChargeWithGST, "")!!
            preference!!.setStringValue(ConstantClass.gst, String.format("%.2f", 0.0))
            preference!!.setStringValue(ConstantClass.serviceCharge, String.format("%.2f", 0.0))
            preference!!.setStringValue(ConstantClass.retailerCommission, String.format("%.2f", 0.0))
            preference!!.setStringValue(ConstantClass.tds, String.format("%.2f", 0.0))

            var msg = "Warning : Slab structure not found. This transaction will proceed without any commission being credited."
            openDialogForPayout(rechargeAmount.toDoubleOrNull() ?: 0.0, 0.0, totalRechargeAmount, 0.0, msg)

        }

    }


    private fun serviceChargeCalculation(serviceCharge: Double, gstRate: Double, rechargeAmount: String, response: AirCommissionResp): Double {

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


    fun openDialogForPayout(transferAmount: Double, servicechargeGst: Double, totalRechargeAmount: Double, retailerCommission: Double, msg: String) {

        val gst = preference!!.getStringValue(ConstantClass.gst, "")

        preference!!.setStringValue(ConstantClass.serviceChargewithgst, String.format("%.2f", servicechargeGst)).toString()
        preference!!.setStringValue(ConstantClass.actualbusticketamt, String.format("%.2f", transferAmount)).toString()


        binding.actualamt.text = String.format("%.2f", transferAmount)
        binding.servicechargewithgst.text = String.format("%.2f", servicechargeGst)

        binding.servicescharge.text = preference!!.getStringValue(ConstantClass.serviceCharge, "").toString()
        binding.gstamount.text = "$gst"

        totalAmountWithoutOffer = (transferAmount + servicechargeGst)

        binding.transferamt.text = String.format("%.2f", totalAmountWithoutOffer)
        binding.price.text = String.format("%.2f", totalAmountWithoutOffer)

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
        binding.promoCodeOfferlayout.visibility=View.GONE
        binding.showingAppliedCodeLayout.visibility=View.GONE

        hitApiForPromotionList(totalRechargeAmount)


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


    fun OpenForPaymentOption(){
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.payment_option_layout, null)
        bottomSheetDialog.setContentView(view)

        var cancel = view.findViewById<ImageView>(R.id.cross)
        var airlineIcon = view.findViewById<ImageView>(R.id.airlineicon)
        var fromAirportCode = view.findViewById<TextView>(R.id.fromAirportCode)
        var toAirportCode = view.findViewById<TextView>(R.id.toAirportCode)
        var ticketAmounttxt = view.findViewById<TextView>(R.id.ticketAmounttxt)
        var walletbalanceamttxt = view.findViewById<TextView>(R.id.balanceamtbtxt)
        var insufficientbtxt = view.findViewById<TextView>(R.id.insufficientbtxt)
        var insufficientAmounttxt = view.findViewById<TextView>(R.id.insufficientAmounttxt)
        var wallettoplayout = view.findViewById<ConstraintLayout>(R.id.wallettopuplayout)

        var proceedBtn = view.findViewById<TextView>(R.id.proceedBtn)

        val walletLayout = view.findViewById<LinearLayout>(R.id.walletLayout)
        val gatewayLayout = view.findViewById<LinearLayout>(R.id.paymentgatewaylayout)

        val walletButton = view.findViewById<RadioButton>(R.id.walletButton)
        val gatewayButton = view.findViewById<RadioButton>(R.id.gatewayButton)

        var airportIcon = GetAirlineLogo(flightDetailsPassangerDetail[0]!!.airlineCode)
        Glide.with(this).load(airportIcon).error(R.drawable.airplaceholder).into(airlineIcon)

        fromAirportCode.text = flightDetailsPassangerDetail[0]!!.origin
        toAirportCode.text = flightDetailsPassangerDetail[0]!!.destination
        var transferAmount = binding.price.text.toString().toDoubleOrNull()?:0.0
        ticketAmounttxt.text  =  " ₹ %.2f  ".format(transferAmount.toDouble())
        var walletBalance = preference.getStringValue(ConstantClass.WalletAmount,"")
        walletbalanceamttxt.text  =  " ₹ %.2f  ".format(walletBalance?.toDouble())

        val ticketAmount = transferAmount ?: 0.0
        val walletAmount = walletBalance?.toDoubleOrNull() ?: 0.0

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


        if (walletAmount < ticketAmount) {
            gatewayButton.isChecked = true
            proceedBtn.isEnabled = true
            proceedBtn.backgroundTintList = ContextCompat.getColorStateList(this,R.color.primary_blue)
        } else {
            walletButton.isChecked = true
            proceedBtn.isEnabled = false
            proceedBtn.backgroundTintList=  ContextCompat.getColorStateList(this,R.color.disablebuttoncolor)
        }


        walletButton.isClickable = false
        gatewayButton.isClickable = false


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
                var totalTransactionAmount = String.format("%.2f", ticketAmount)
                preference!!.setStringValue(ConstantClass.totalTransaction,totalTransactionAmount)
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

    private fun getMerchantBalance(flightBalance: Double) {
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
                                    getAllMerchantBalanceRes(response, flightBalance)
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



    private fun getAllMerchantBalanceRes(response: GetMerchantBalanceRes,flightBalance: Double) {
        if (response.isSuccess == true) {
            Log.d(ContentValues.TAG, "getAllMerchantBalanceRes: ${response.data[0].debitBalance}")
            preference!!.setStringValue(ConstantClass.merchantBalance, response.data[0].debitBalance)

            val merchantBalance = response.data[0].debitBalance?.toDoubleOrNull() ?: 0.0
            
            if (flightBalance <= merchantBalance) {
                HitApiForFlightAddPayment()
            }
            else {
                ConstantClass.dialog.dismiss()
                Toast.makeText(this, "Booking is currently unavailable due to a technical issue. Please try again shortly.", Toast.LENGTH_LONG).show()
            }
        }
        else {
            ConstantClass.dialog.dismiss()
            Toast.makeText(this, response.returnMessage.toString(), Toast.LENGTH_SHORT).show()
        }

    }


    fun HitApiForFlightAddPayment(){
        val randomString = generateRandomString()
        val flightaddpaymentreq = FlightAddPaymentReq(
            clientRefNo = randomString,
            refNo =  preference!!.getStringValue(ConstantClass.BookingRefNo,""),
            transactionType = 0,
            productId = "1",
            iPAddress = preference?.getStringValue(ConstantClass.deviceIPAddress, ""),
            requestId =  preference?.getStringValue(ConstantClass.requestId, ""),
            imeINumber = preference?.getStringValue(ConstantClass.imeINumber,""),
            registrationID = preference?.getStringValue(ConstantClass.MerchantId, "")
        )

        Log.d("AddPaymentReq", Gson().toJson(flightaddpaymentreq))

        viewModel.getFlightAddPaymentRequest(flightaddpaymentreq)
            .observe(this) { resource ->
                resource?.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data?.let { users ->
                                users.body()?.let { response ->

                                    if(response.responseHeader!!.errorCode.equals("204")){
                                        Toast.makeText(this,response.responseHeader.errorInnerException,Toast.LENGTH_SHORT).show()
                                    }

                                    if(response.responseHeader.errorCode.equals("0000")) {
                                        Log.d("AddPaymentResponse",response.paymentID)
                                        hitApiForAirTicketing()
                                    }else{
                                        ConstantClass.dialog.dismiss()
                                        Toast.makeText(this,response.responseHeader.errorInnerException,Toast.LENGTH_SHORT).show()
                                    }
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


    fun hitApiForAirTicketing(){
        val airTicketingreq = AirTicketingReq(
            BookingRefNo = preference!!.getStringValue(ConstantClass.BookingRefNo,""),
            TicketingType =  "1",
            iPAddress = preference?.getStringValue(ConstantClass.deviceIPAddress, ""),
            requestId =  preference?.getStringValue(ConstantClass.requestId, ""),
            imeINumber = preference?.getStringValue(ConstantClass.imeINumber,""),
            registrationID = preference?.getStringValue(ConstantClass.MerchantId, "")
        )

        Log.d("airticketreq", Gson().toJson(airTicketingreq))

        Log.d("airticketreq", Gson().toJson(airTicketingreq) )

        hitApiForUploadAirTicketingRequest(airTicketingreq)
        
    }

    fun hitApiForUploadAirTicketingRequest(apiresponse:AirTicketingReq){
        val req = AirTicketBookingRequest(
            bookingRefNo = preference!!.getStringValue(ConstantClass.BookingRefNo,""),
            ticketingType = "1",
            loginID = preference?.getStringValue(ConstantClass.requestId, ""),
            imeINumber = preference?.getStringValue(ConstantClass.imeINumber, ""),
            createdBy = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
            apiResponse = Gson().toJson(apiresponse),
            registrationID = preference?.getStringValue(ConstantClass.MerchantId, ""),
            requestId = preference?.getStringValue(ConstantClass.requestId, ""),
            iPAddress = preference?.getStringValue(ConstantClass.deviceIPAddress, ""),
        )

        Log.d("UploadAirTicketReq",Gson().toJson(req))

        getAllApiServiceViewModel.uploadAirBookingTicketRequest(req).observe(this) { resource ->
            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    val response = resource.data?.body()
                    Log.d("UploadAirTicketResp",Gson().toJson(response))
                    Log.d("AirTicketingReq",Gson().toJson(apiresponse))
                    uploadDataForAirTicketing(apiresponse)
                }
                ApiStatus.ERROR -> if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                    ConstantClass.dialog.dismiss()
                }
                ApiStatus.LOADING -> {}
            }
        }

    }

    fun uploadDataForAirTicketing(apiresponse:AirTicketingReq){

        viewModel.getAirTicketingRequest(apiresponse).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("airticketingresponse",response.toString())

                                if(response.responseHeader.statusId.equals("22")){
                                    Toast.makeText(this,response.responseHeader.errorDesc,Toast.LENGTH_LONG).show()
                                    if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                        ConstantClass.dialog.dismiss()
                                    }
                                }
                                else{
                                    hitApiForUploadAirTicketingResponseRequest(response)
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

    fun hitApiForUploadAirTicketingResponseRequest(apiresponse: AirTicketingResponse){

        var apiresponsevalue = Gson().toJson(apiresponse)

        val req = AirTicketBookingResponseRequest(
            bookingRefNo = apiresponse.bookingRefNo,
            supplierRefno =apiresponse.airlinePNRDetails!![0].airlinePNRs!![0].supplierRefNo ,
            airlineCode=apiresponse.airlinePNRDetails!![0].airlinePNRs!![0].airlineCode,
            loginID = preference?.getStringValue(ConstantClass.requestId, ""),
            holdValidity = apiresponse.airlinePNRDetails!![0].holdValidity,
            failureRemark = apiresponse.airlinePNRDetails!![0].failureRemark,
            airlinePNR = apiresponse.airlinePNRDetails!![0].airlinePNRs!![0].airlinePNR,
            flightId = apiresponse.airlinePNRDetails!![0].flightId,
            statusId = apiresponse.responseHeader.statusId,
            errorCode = apiresponse.responseHeader.errorCode,
            createdBy = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
            crSPNR = apiresponse.airlinePNRDetails!![0].airlinePNRs!![0].crSPNR,
            apiResponse = apiresponsevalue,
            errorDesc = apiresponse.responseHeader.errorDesc,
            registrationID = preference?.getStringValue(ConstantClass.MerchantId, ""),
            errorInnerException = apiresponse.responseHeader.errorInnerException,
            requestId = preference?.getStringValue(ConstantClass.requestId, ""),
            iPAddress = preference?.getStringValue(ConstantClass.deviceIPAddress, ""),
            recordLocator = apiresponse.airlinePNRDetails!![0].airlinePNRs!![0].recordLocator,
            crSCode = apiresponse.airlinePNRDetails!![0].airlinePNRs!![0].crSCode,
        )

        Log.d("UploadAirTicketResponseReq",Gson().toJson(req))

        getAllApiServiceViewModel.uploadAirBookingTicketResponseRequest(req).observe(this) { resource ->
            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    val response = resource.data?.body()
                    Log.d("UploadAirTicketResponseResp",Gson().toJson(response))
                    if (response != null && response.isSuccess!!) {
                        if(apiresponse.responseHeader.errorCode.equals("0000")){
                            preference!!.setStringValue(ConstantClass.ForPayoutBookingRefId, apiresponse.bookingRefNo)
                            getTransferAmountToAgentWithCal()

                            val airTicketReprintreq = AirReprintReq(
                                BookingRefNo = apiresponse.bookingRefNo,
                                airlinePNR =  apiresponse.airlinePNRDetails!![0].airlinePNRs!![0].airlinePNR,
                                ipAddress = preference?.getStringValue(ConstantClass.deviceIPAddress, ""),
                                requestId =  preference?.getStringValue(ConstantClass.requestId, ""),
                                imeNumber =  preference?.getStringValue(ConstantClass.imeINumber, ""),
                                registerId = preference?.getStringValue(ConstantClass.MerchantId, "")
                            )

                            hitApiForTicketReprint(airTicketReprintreq)
                        }

                        else{
                            if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                ConstantClass.dialog.dismiss()
                            }
                            Toast.makeText(this,apiresponse.responseHeader.errorInnerException,Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {

                    }
                }
                ApiStatus.ERROR -> if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                    ConstantClass.dialog.dismiss()
                }
                ApiStatus.LOADING -> {}
            }
        }

    }

    private fun getTransferAmountToAgentWithCal() {
        try {
            var transferamt =   preference!!.getStringValue(ConstantClass.totalTransaction,"")
            var actualamt =  preference!!.getStringValue(ConstantClass.actualbusticketamt,"").toString()
            var bookingRefId =   preference!!.getStringValue(ConstantClass.ForPayoutBookingRefId, "")

            val transferAmountToAgentsReq = TransferAmountToAgentsReq(
                transferFrom = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
                flag = "Y",
                serviceCode = "",
                transIpAddress = preference!!.getStringValue(ConstantClass.deviceIPAddress, ""),
                remark = "Flight Booking  Api",
                transferTo = "Admin",
                transferToMsg = "Your account has been credited by ₹ $transferamt for a flight booking Reference Number: ${bookingRefId}.",
                transferFromMsg = "Your account has been debited by ₹ $transferamt for a flight booking Reference Number: ${bookingRefId}.",
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

            Log.d("getAllGsonFromAPI", Gson().toJson(transferAmountToAgentsReq))
            getAllApiServiceViewModel.getTransferAmountToAgents(transferAmountToAgentsReq)
                .observe(this) { resource ->
                    resource?.let {
                        when (it.apiStatus) {
                            ApiStatus.SUCCESS -> {
                                it.data?.let { users ->
                                    users.body()?.let { response ->

                                        Log.d("BosPayoutTransaction", response.toString())

                                        val commission = preference!!.getStringValue(ConstantClass.retailerCommission, "0.00")?.trim()?.toDoubleOrNull() ?: 0.0

                                        Log.d("retailercommissionforpayout", commission.toString())


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
        } catch (e: NumberFormatException) {
            e.printStackTrace()

            Toast.makeText(this, e.message.toString() + " " + e.localizedMessage?.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    fun hitApiForTicketReprint(airReprintTicketReq:AirReprintReq){

        Log.d("ticketreprintreq", Gson().toJson(airReprintTicketReq))

        viewModel.getAirTicketReprintRequest(airReprintTicketReq)
            .observe(this) { resource ->
                resource?.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data?.let { users ->
                                users.body()?.let { response ->
                                    BookedTicketList=response

                                    var requeryReq = FlightRequeryReq(
                                        loginId = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
                                        bookingRefNo = response.bookingRefNo,
                                        ipAddress = preference?.getStringValue(ConstantClass.deviceIPAddress, ""),
                                        requestId = preference?.getStringValue(ConstantClass.requestId, ""),
                                        imeinumber = preference?.getStringValue(ConstantClass.imeINumber,""),
                                        registrationID = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
                                        airPnr = response.airPNRDetails!![0]?.airlinePNR,
                                        flightNumber = response.airPNRDetails[0]!!.flights!![0].flightId,
                                        travelDate = response.airPNRDetails[0]!!.flights!![0].travelDate,
                                        ticketStatusId = response.airPNRDetails[0]!!.ticketStatusId,
                                        ticketStatusDesc = response.airPNRDetails[0]!!.ticketStatusDesc,
                                        apiResponse = Gson().toJson(response),
                                        createdBy = preference!!.getStringValue(ConstantClass.RegistrationId, ""),
                                        destination = response.airPNRDetails[0]!!.flights!![0].destination,
                                        origin = response.airPNRDetails[0]!!.flights!![0].origin
                                    )

                                    Log.d("ticketrequeryreq",Gson().toJson(requeryReq))
                                    hitApiForRequeryRequest(requeryReq)
                                    Log.d("ticketreprintresponse",response.bookingRefNo)

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

    fun hitApiForRequeryRequest(requeryReq : FlightRequeryReq){
        getAllApiServiceViewModel.getAirRequeryRequest(requeryReq)
            .observe(this) { resource ->
                resource?.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data?.let { users ->
                                users.body()?.let { response ->
                                    ConstantClass.dialog.dismiss()
                                    Log.d("ticketrequeryresp",Gson().toJson(requeryReq))
                                    startActivity(Intent(this,FlightBookedTicketActivity::class.java))
                                    Toast.makeText(this,"Reprint Success",Toast.LENGTH_SHORT).show()
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


}