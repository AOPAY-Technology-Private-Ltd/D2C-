package com.example.twopayd2c.ui.view.activity.travel.AllBusModule

import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.twopayd2c.data.model.travelmodel.bus.busBooking.BusTempBookingReq
import com.example.twopayd2c.data.model.travelmodel.bus.busBooking.BusTempBookingRes
import com.example.twopayd2c.data.model.travelmodel.bus.busBooking.PaXDetails
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTempBookingRequest
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.SeattypeModel
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.PassengerSelectedSeatDataModel
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTampBookTicketResponseRequest
import com.example.twopayd2c.data.repository.GetAllAPIServiceRepository
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.GetAllApiServiceViewModelFactory
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.ActivityReviewBookingPageBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.PassengerDataAdapter
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.AddPassengerPage.Companion.seatDataList
import com.example.twopayd2c.ui.viewmodel.GetAllApiServiceViewModel
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.calculateDOBFromAge
import com.example.twopayd2c.utils.ConstantClass.Companion.getNextNumber
import com.example.twopayd2c.utils.SharedPreference
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson

class ReviewBookingPage : AppCompatActivity() {
    lateinit var binding: ActivityReviewBookingPageBinding
    lateinit var preference: SharedPreference
    var passengerDataList: MutableList<PassengerSelectedSeatDataModel> = mutableListOf()
    private lateinit var getAllApiServiceViewModel: GetAllApiServiceViewModel
    lateinit var passengerAdapter : PassengerDataAdapter
    private lateinit var viewModel: TravelViewModel
    var fromCityName : String?=""
    var toCityName : String?=""
    var travelCompanyName : String?=""
    var pickupName : String?=""
    var dropName : String?=""
    var dropTime : String?=""
    var boardTime : String?=""
    var totalTicketAmount : String?=""
    private lateinit var list: ArrayList<String>
    private var selectedIndex: Int? = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReviewBookingPageBinding.inflate(layoutInflater)
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
        list = ArrayList()

        binding.passengerMobileNumber.requestFocus()
        binding.passengerMobileNumber.postDelayed({
            binding.passengerMobileNumber.requestFocus()
            val imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.passengerMobileNumber, InputMethodManager.SHOW_FORCED)

            binding.main.viewTreeObserver.addOnGlobalLayoutListener {
                val rect = Rect()
                binding.main.getWindowVisibleDisplayFrame(rect)

                val screenHeight = binding.main.rootView.height
                val keypadHeight = screenHeight - rect.bottom

                Log.d("KeyboardCheck", "screenHeight=$screenHeight, rect.bottom=${rect.bottom}, keypadHeight=$keypadHeight")

                if (keypadHeight > screenHeight * 0.20) {
                    // ✅ Keyboard is open

                    binding.nestedscrollview.post {
                        binding.nestedscrollview.smoothScrollTo(0, binding.nestedscrollview.bottom)
                    }

                }
            }

        }, 300)

        setDataOnView()
        setOnClickListner()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(ApiClient.apiAllTravelAPI, ApiClient.apiBusAddRequestlAPI)))[TravelViewModel::class.java]
        getAllApiServiceViewModel = ViewModelProvider(this, GetAllApiServiceViewModelFactory(
            GetAllAPIServiceRepository(ApiClient.apiAllInterface)
        ))[GetAllApiServiceViewModel::class.java]
    }


    companion object{
        var forServiceChargeSeatType : MutableList<SeattypeModel> = mutableListOf()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setOnClickListner(){
        binding.backicon.setOnClickListener {
            finish()
        }


        binding.detailstxt.setOnClickListener {
            openBottomSheetForShowingDetailsPickupDropUp()
        }

        binding.farebreakup.setOnClickListener {
            openBottomSheetForFarebreakUp()
        }

        binding.proceedBtn.setOnClickListener {
            //tempbooking .................................
            validationPassenger()
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun validationPassenger() {
        val mailId = binding.passengerMailId.text.toString()
        val mobileNumber = binding.passengerMobileNumber.text.toString()

        when {
            mailId.isBlank() -> {
                binding.passengerMailId.requestFocus()
                Toast.makeText(this,"Please enter email ID",Toast.LENGTH_SHORT).show()
            }

            !Patterns.EMAIL_ADDRESS.matcher(mailId).matches() -> {
                binding.passengerMailId.requestFocus()
                Toast.makeText(this,"Please enter valid email ID",Toast.LENGTH_SHORT).show()
            }

            mobileNumber.isBlank() -> {
                binding.passengerMobileNumber.requestFocus()
                Toast.makeText(this,"Please enter mobile number",Toast.LENGTH_SHORT).show()
            }

            !mobileNumber.matches(Regex("^[6-9]\\d{9}$")) -> {
                binding.passengerMobileNumber.requestFocus()
                Toast.makeText(this,"Please enter valid mobile number",Toast.LENGTH_SHORT).show()
            }

            else -> {
                // All valid ✅  TEMP BOOKING
                getAllDetailsBusBooking()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getPassengerDetailsList(): List<PaXDetails> {

        val paxList = mutableListOf<PaXDetails>()

        passengerDataList.forEachIndexed { index, item ->
          when{
              item.gender.equals("Male")->{
                  selectedIndex=0
              }
              item.gender.equals(ConstantClass.FemaleGender)->{
                  selectedIndex=1
              }
          }
            val next = getNextNumber()
            val seatNumber = item.seatNumberForBooking
            var dateOfBirth =""

            if(item.age.isNotEmpty()){
                dateOfBirth = calculateDOBFromAge(item.age)
            }else{
                dateOfBirth = ""
            }

            paxList.add(
                PaXDetails(
                    Age = item.age.toIntOrNull(),
                    DOB = dateOfBirth,
                    Fare = list,
                    Gender = selectedIndex.toString(),
                    IdNumber = "",
                    IdType = 4,
                    LadiesSeat = false,
                    PAXId = next.toString(),
                    PAXName = item.passengerName,
                    PenaltyCharge = "",
                    Primary = true,
                    SeatNumber = seatNumber,
                    Status = "",
                    TicketNumber = "",
                    Title = /*item.type*/"Mr"
                )
            )


        }
        return paxList
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAllDetailsBusBooking() {
        val busTempBookingReq = BusTempBookingReq(
            boardingId = preference.getStringValue(ConstantClass.boarding_Id,""),
            corporatePaymentMode = 0,
            corporateStatus = "0",
            costCenterId = 0,
            customerMobile = binding.passengerMobileNumber.text.toString(),
            dealKey = "",
            droppingId = preference.getStringValue(ConstantClass.dropping_Id,""),
            gst = false,
            gstin = "",
            gstinHolderAddress = "",
            gstinHolderName = "",
            paXDetails = getPassengerDetailsList(),
            PassengerEmail = binding.passengerMailId.text.toString(),
            PassengerMobile = binding.passengerMobileNumber.text.toString(),
            ProjectId = 0,
            Remarks = "testing",
            BusKey = preference.getStringValue(ConstantClass.busKey,""),
            SearchKey = preference.getStringValue(ConstantClass.searchKey, ""),
            SeatMapKey = preference.getStringValue(ConstantClass.seatMap_Key, ""),
            sendEmail = false,
            sendSMS = false,
            iPAddress = preference.getStringValue(ConstantClass.deviceIPAddress, ""),
            requestId = preference.getStringValue(ConstantClass.requestId, ""),
            imeINumber = preference.getStringValue(ConstantClass.imeINumber, ""),
            registrationID = preference.getStringValue(ConstantClass.MerchantId, "")
        )

        Log.d("busTempBookingReq", Gson().toJson(busTempBookingReq))
        hitApiForGetTampBookingRequest( Gson().toJson(busTempBookingReq))

        viewModel.getAllBusTempBooking(busTempBookingReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                getAllDetailsBusBookingRes(response)
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        hitApiForGetTampBookingRequest( Gson().toJson(busTempBookingReq))

                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.loading(this)
                    }
                }
            }
        }

    }


    private fun getAllDetailsBusBookingRes(response: BusTempBookingRes?) {

        if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
            ConstantClass.dialog.dismiss()
        }


        if (response?.responseHeader?.errorCode == "0000") {
            hitApiForGetTampBookingResponse(Gson().toJson(response),response)

            if( !response.bookingRefNo.isNullOrEmpty()){
                preference.setStringValue(ConstantClass.BookingRefNo, response.bookingRefNo.toString())
                startActivity(Intent(this,BusConfirmBookingPage::class.java))
            }
            Log.d("bookingRefNo", response.bookingRefNo.toString())
            Toast.makeText(this,response.responseHeader?.errorCode.toString(),Toast.LENGTH_SHORT).show()

        }
        else {

            Toast.makeText(this, response?.responseHeader?.errorInnerException.toString(), Toast.LENGTH_SHORT).show()
        }

    }


    private fun hitApiForGetTampBookingRequest( request:String){
        val busTempBookingReq = BusTempBookingRequest(
            iPAddress = preference.getStringValue(ConstantClass.deviceIPAddress, ""),
            requestId = preference.getStringValue(ConstantClass.requestId, ""),
            imeINumber = preference.getStringValue(ConstantClass.imeINumber, ""),
            corporatePaymentMode = "0",
            corporateStatus = "0",
            costCenterId = "0",
            customer_Mobile = binding.passengerMobileNumber.text.toString(),
            dealKey = "",
            boardingId = preference.getStringValue(ConstantClass.boarding_Id, ""),
            droppingId =preference.getStringValue(ConstantClass.dropping_Id, ""),
            gst = "",
            gstin = "",
            gstinHolderAddress = "",
            gstinHolderName = "",
            busKey = preference.getStringValue(ConstantClass.busKey, ""),
            searchKey = preference.getStringValue(ConstantClass.searchKey, ""),
            seatMapKey = preference.getStringValue(ConstantClass.seatMap_Key, ""),
            sendEmail = "",
            sendSMS = "",
            createdBy = preference.getStringValue(ConstantClass.RegistrationId, ""),
            registrationID = preference.getStringValue(ConstantClass.MerchantId, ""),
            loginId = preference.getStringValue(ConstantClass.RegistrationId, ""),
            apiRequest =request
        )

        Log.d("TampBookRequest",busTempBookingReq.toString())
        viewModel.getBusTampBookRequest(busTempBookingReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {

                        it.data?.let { users ->
                            users.body()?.let { response ->
                               // ConstantClass.uploadDataOnFirebaseConsole(Gson().toJson(response),"BusSeatingBusTampBookRequest",this@BusSeating)
                                Log.d("TampBookReqResopnse",response.toString())
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


    private fun hitApiForGetTampBookingResponse( response:String, apiResponse:BusTempBookingRes){
        val busTempBookingReq = BusTampBookTicketResponseRequest(
            iPAddress = preference.getStringValue(ConstantClass.deviceIPAddress, ""),
            requestId = preference.getStringValue(ConstantClass.requestId, ""),
            imeINumber =  preference.getStringValue(ConstantClass.imeINumber, ""),
            bookingRefNo = apiResponse.bookingRefNo,
            errorCode = apiResponse.responseHeader!!.errorCode,
            errorDesc = apiResponse.responseHeader!!.errorDesc,
            errorInnerException = apiResponse.responseHeader!!.errorInnerException,
            createdBy = preference.getStringValue(ConstantClass.RegistrationId, ""),
            registrationID = preference.getStringValue(ConstantClass.MerchantId, ""),
            loginId = preference.getStringValue(ConstantClass.RegistrationId, ""),
            apiResponse =response
        )

        Log.d("TampBookResponseRequest",Gson().toJson(busTempBookingReq))

        viewModel.getTampBusTicketResponse(busTempBookingReq).observe(this)
        { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                //ConstantClass.uploadDataOnFirebaseConsole(Gson().toJson(response),"BusSeatingTampBusTicketResponse",this@BusSeating)
                                Log.d("TampBookResponseRes",response.toString())
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


    fun setDataOnView() {
        fromCityName = preference?.getStringValue(ConstantClass.fromDesignationName, "")
        toCityName = preference?.getStringValue(ConstantClass.toDesignationName, "")
        travelCompanyName = preference?.getStringValue(ConstantClass.travelCompanyName, "")
        pickupName = preference?.getStringValue(ConstantClass.boardingPoint, "")
        dropName = preference?.getStringValue(ConstantClass.droppingPoint, "")
        dropTime = preference?.getStringValue(ConstantClass.dropping_Time, "")
        boardTime = preference?.getStringValue(ConstantClass.boarding_Time, "")
        totalTicketAmount = preference?.getStringValue(ConstantClass.TotalSeatAmount, "")


        binding.companyName.text = travelCompanyName
        binding.fromcityname.text = fromCityName
        binding.tocityname.text = toCityName
        binding.pickupPoint.text = pickupName
        binding.dropPoint.text = dropName
        binding.pickupPointDatetime.text = boardTime
        binding.dropPointDatetime.text = dropTime
        binding.dropPoint.text = dropName
        binding.finalAmount.text = "₹ ${totalTicketAmount}"


        if (seatDataList.isNotEmpty()) {
            passengerDataList = seatDataList
            passengerAdapter = PassengerDataAdapter(this,passengerDataList)
            binding.passengerList.adapter = passengerAdapter
        }


    }


    fun openBottomSheetForShowingDetailsPickupDropUp(){
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.pickupdrop_bottomsheet_layout, null)
        bottomSheetDialog.setContentView(view)

        var companyName = view.findViewById<TextView>(R.id.companyName)
        var pickupPoint = view.findViewById<TextView>(R.id.pickupPoint)
        var fromcityname = view.findViewById<TextView>(R.id.fromcityname)
        var pickupPoint_datetime = view.findViewById<TextView>(R.id.pickupPoint_datetime)
        var dropPoint = view.findViewById<TextView>(R.id.dropPoint)
        var tocityname = view.findViewById<TextView>(R.id.tocityname)
        var dropPoint_datetime = view.findViewById<TextView>(R.id.dropPoint_datetime)
        var cancel = view.findViewById<ImageView>(R.id.cancelIcon)

        companyName.text = travelCompanyName
        fromcityname.text = fromCityName
        tocityname.text = toCityName
        pickupPoint.text = pickupName
        dropPoint.text = dropName
        pickupPoint_datetime.text = boardTime
        dropPoint_datetime.text = dropTime

        cancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }


    fun openBottomSheetForFarebreakUp(){
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.fare_breakup_layout, null)
        bottomSheetDialog.setContentView(view)

        var seateramount = view.findViewById<TextView>(R.id.seateramount)
        var sleeperamount = view.findViewById<TextView>(R.id.sleeperamount)
        var totalamnt = view.findViewById<TextView>(R.id.totalamount)
        var seattxt = view.findViewById<TextView>(R.id.seatetxt)
        var sleepertxt = view.findViewById<TextView>(R.id.sleepertxt)
        var seaterlayout = view.findViewById<LinearLayout>(R.id.seaterlayout)
        var sleeperlayout = view.findViewById<LinearLayout>(R.id.sleeperlayout)
        var cancel = view.findViewById<ImageView>(R.id.cancelIcon)


        if(forServiceChargeSeatType[0].seater>0) {
            seaterlayout.visibility= View.VISIBLE
            seattxt.text= "Seater Fare (${forServiceChargeSeatType[0].seater})"
        }
        else {
            seaterlayout.visibility= View.GONE
        }


        if(forServiceChargeSeatType[0].sleeper>0) {
            sleeperlayout.visibility= View.VISIBLE
            sleepertxt.text= "Sleeper Fare (${forServiceChargeSeatType[0].sleeper})"
        }
        else {
            sleeperlayout.visibility= View.GONE
        }


        seateramount.text = String.format("%.2f",
            forServiceChargeSeatType[0].busfare
                .filter { it.length == "1" }
                .sumOf { it.basicamount ?: 0.00 }
        )

        sleeperamount.text = String.format("%.2f",
            forServiceChargeSeatType[0].busfare
                .filter { it.length == "2" }
                .sumOf { it.basicamount ?: 0.00 }
        )

        totalamnt.text = "₹ ${String.format("%.2f",  totalTicketAmount?.toDoubleOrNull() ?: 0.0)}"

        cancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()

    }



}
