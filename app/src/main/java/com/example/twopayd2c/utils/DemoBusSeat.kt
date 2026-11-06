/*
package com.example.twopayd2c.utils

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bos.payment.appName.R
import com.example.twopayd2c.data.model.transferAMountToAgent.TransferAmountToAgentsReq
import com.example.twopayd2c.data.model.travel.bus.addMoney.BusAddMoneyReq
import com.example.twopayd2c.data.model.travel.bus.addMoney.BusAddMoneyRes
import com.example.twopayd2c.data.model.travel.bus.busBooking.BusTempBookingReq
import com.example.twopayd2c.data.model.travel.bus.busBooking.BusTempBookingRes
import com.example.twopayd2c.data.model.travel.bus.busBooking.PaXDetails
import com.example.twopayd2c.data.model.travel.bus.busRequery.BusRequeryReq
import com.example.twopayd2c.data.model.travel.bus.busRequery.BusRequeryRes
import com.example.twopayd2c.data.model.travel.bus.busRequery.TicketDetailsForGenerateTicket
import com.example.twopayd2c.data.model.travel.bus.busSeatMap.BusSeatMapReq
import com.example.twopayd2c.data.model.travel.bus.busSeatMap.BusSeatMapRes
import com.example.twopayd2c.data.model.travel.bus.busSeatMap.SeatMap
import com.example.twopayd2c.data.model.travel.bus.busTicket.AddTicketReq
import com.example.twopayd2c.data.model.travel.bus.busTicket.AddTicketResponseReq
import com.example.twopayd2c.data.model.travel.bus.busTicket.BusPaxRequeryResponseReq
import com.example.twopayd2c.data.model.travel.bus.busTicket.BusTampBookTicketResponseRequest
import com.example.twopayd2c.data.model.travel.bus.busTicket.BusTampBookingResp
import com.example.twopayd2c.data.model.travel.bus.busTicket.BusTempBookingRequest
import com.example.twopayd2c.data.model.travel.bus.busTicket.BusTicketingReq
import com.example.twopayd2c.data.model.travel.bus.busTicket.BusTicketingRes
import com.example.twopayd2c.data.repository.GetAllAPIServiceRepository
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.GetAllApiServiceViewModelFactory
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.ActivityBusSeatingBinding
import com.bos.payment.appName.localdb.AppLog
import com.bos.payment.appName.localdb.AppLog.d
import com.bos.payment.appName.network.RetrofitClient
import com.bos.payment.appName.ui.view.travel.adapter.UserPassengerDetailsAdapter
import com.bos.payment.appName.ui.viewmodel.GetAllApiServiceViewModel
import com.bos.payment.appName.ui.viewmodel.TravelViewModel
import com.bos.payment.appName.utils.ApiStatus
import com.bos.payment.appName.utils.Constants
import com.bos.payment.appName.utils.MStash
import com.bos.payment.appName.utils.QRCodeGenerator.generateQRPdf
import com.bos.payment.appName.utils.Utils
import com.bos.payment.appName.utils.Utils.calculateAgeFromDOB
import com.bos.payment.appName.utils.Utils.getChangeDateFormat
import com.bos.payment.appName.utils.Utils.getNextNumber
import com.bos.payment.appName.utils.Utils.showLoadingDialog
import com.bos.payment.appName.utils.Utils.toast
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Hashtable
import java.util.Locale

class DemoBusSeat : AppCompatActivity() {

    private lateinit var mStash: MStash
    private lateinit var pd: ProgressDialog
    private lateinit var viewModel: TravelViewModel
    private lateinit var getAllApiServiceViewModel: GetAllApiServiceViewModel
    private lateinit var bin: ActivityBusSeatingBinding
    private val selectedSeats = mutableListOf<SeatMap>()
    private var boardingPointText: String? = null
    private val myCalender = Calendar.getInstance()
    val calendar = Calendar.getInstance()
    private var droppingPointText: String? = null
    private var titleText: String? = null
    private var genderText: String? = null
    private var qrText: String? = null
    private var selectedIndex: Int? = -1
    private var age: Int? = 0
    private var qrBitmap: Bitmap? = null
    private lateinit var list: ArrayList<String>
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private lateinit var userPassengerDetailsAdapter: UserPassengerDetailsAdapter
    private lateinit var passengerList: ArrayList<com.example.twopayd2c.data.model.travel.bus.busRequery.PaXDetails>
    var headers: Array<String> = arrayOf("Name", "Gender", "Seat No","Ticket Number")
    var data = mutableListOf<MutableList<String?>>()
    var ticketDetails = mutableListOf<TicketDetailsForGenerateTicket>()


        private val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        view.maxDate = System.currentTimeMillis()
        myCalender.add(Calendar.YEAR, -18)
        myCalender[Calendar.YEAR] = year
        myCalender[Calendar.MONTH] = monthOfYear
        myCalender[Calendar.DAY_OF_MONTH] = dayOfMonth
        val minAdultAge: Calendar = GregorianCalendar()
        val minAdultAge1: Calendar = GregorianCalendar()
        minAdultAge.add(Calendar.YEAR, -18)
        minAdultAge1.add(Calendar.YEAR, -61)
        when {
            minAdultAge.before(myCalender) -> {
                Toast.makeText(applicationContext, resources.getString(R.string.min_age_person), Toast.LENGTH_LONG).show()
                bin.passengerDetails.passengerDob.setText("")
            }

            minAdultAge1.after(myCalender) -> {
                Toast.makeText(applicationContext, resources.getString(R.string.max_age_person), Toast.LENGTH_LONG).show()
                bin.passengerDetails.passengerDob.setText("")
            }

            else -> {
                bin.passengerDetails.passengerDob.let {
                    Utils.updateLabel(it, myCalender, "Enter Date of Birth")
                }
            }
        }
    }

//    @SuppressLint("DefaultLocale")
//    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
//        val selectedDate = String.format("%02d/%02d/%04d", day, month + 1, year)
//        bin.passengerDob.setText(selectedDate)
//    }

//    private val date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
//        myCalender.set(Calendar.YEAR, year)
//        myCalender.set(Calendar.MONTH, monthOfYear)
//        myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//
//        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()) // you can format it as needed
//        val selectedDate = sdf.format(myCalender.time)
//
//        bin.passengerDob.setText(selectedDate) // set selected date in EditText
//        mStash?.setStringValue(Constants.dateAndTime, selectedDate) // save date in shared prefs
//        Log.d("SelectedDate", selectedDate) // for debug
//    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bin = ActivityBusSeatingBinding.inflate(layoutInflater)
        setContentView(bin.root)

        setupUI()
        setupViewModel()
        getAllMappingSeat()
        setDropDown()
        setClickListeners()

    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, TravelViewModelFactory(TravelRepository(RetrofitClient.apiAllTravelAPI,RetrofitClient.apiBusAddRequestlAPI)))[TravelViewModel::class.java]
        getAllApiServiceViewModel = ViewModelProvider(this, GetAllApiServiceViewModelFactory(GetAllAPIServiceRepository(RetrofitClient.apiAllInterface)))[GetAllApiServiceViewModel::class.java]

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setClickListeners() {
        bin.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        bin.NextBtn.setOnClickListener {
            validationSeat()
        }

        bin.proceedBtn.setOnClickListener {
            validationBoarding()
        }

        bin.proceedToBookBtn.setOnClickListener {
            validationPassenger()
        }


        bin.passengerDetails.passengerDob.parent.requestDisallowInterceptTouchEvent(true)
        bin.passengerDetails.passengerDob.setOnClickListener {
            Toast.makeText(this@DemoBusSeat,"Hii",Toast.LENGTH_SHORT).show()
            Utils.hideKeyboard(this)
            DatePickerDialog(
                this,
                date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }



        bin.seatSelectBtn.setOnClickListener {
            getAllMappingSeat()

        }
        bin.reviewBookingInclude.backBtn.setOnClickListener {
            onBackPressed()
        }

        bin.reviewBookingInclude.proceedToPay.setOnClickListener {
            // checking for ticket booking.........................................
            getAllBusAddMoney(mStash.getStringValue(Constants.booking_RefNo, "").toString())

        }

        bin.reviewBookingInclude.showTicketBtn.setOnClickListener {
            getAllBusRequaryTicket()
        }


        bin.printBtn.setOnClickListener {
            toast("Show Toast")
            qrBitmap?.let {
               // createTicketPdf(context = this, fileName = "MyBusTicket_${System.currentTimeMillis()}", qrBitmap = it, ticketData = qrText.toString())
                openDialogForGenerateTicket(context = this,fileName = "MyBusTicket_${System.currentTimeMillis()}",qrBitmap!!,data,ticketDetails)
            }
        }


        bin.confirmBookingLayout.backBtn.setOnClickListener {
            onBackPressed()
        }

    }


    private fun getAllBusRequaryTicket() {
        val busRequery = BusRequeryReq(
            bookingRefNo = mStash.getStringValue(Constants.booking_RefNo, ""),
            iPAddress = mStash.getStringValue(Constants.deviceIPAddress, ""),
            requestId = mStash.getStringValue(Constants.requestId, ""),
            imeINumber = "215237488",
            registrationID = mStash.getStringValue(Constants.MerchantId, ""))

        viewModel.getAllBusRequary(busRequery).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        pd.dismiss()
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                getAllBusRequaryTicketRes(response)
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        pd.dismiss()
                    }

                    ApiStatus.LOADING -> {
                        pd.show()
                    }
                }
            }
        }

    }


    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    private fun getAllBusRequaryTicketRes(response: BusRequeryRes?) {
        if (response?.responseHeader?.errorCode == "0000") {
            bin.fullLayout.visibility = View.GONE
            bin.reviewLayout.visibility = View.GONE
            bin.confirmBookingLayoutList.visibility = View.VISIBLE

            passengerList.clear()
            data.clear()
            ticketDetails.clear()

            response.paXDetails.forEach { passengerData ->
                passengerList.add(passengerData)
            }

            userPassengerDetailsAdapter.notifyDataSetChanged()


            // for ticket generation in pdf format....................................
            for(passenger in passengerList){
                if(passenger.gender==0){
                    data.add(mutableListOf(passenger.paXName,"Male",passenger.seatNumber,passenger.ticketNumber))
                }else{
                    data.add(mutableListOf(passenger.paXName,"Female",passenger.seatNumber,passenger.ticketNumber))
                }

            }


            qrText=  buildQrDataWithPassengers(response.transportPNR,response.bookingRefNo ,response.busDetail?.fromCity,response.busDetail?.toCity,response.busDetail?.travelDate, response.busDetail?.busType,passengerList)

            Log.d("qrData", qrText.toString())

            qrBitmap = generateQrCode(qrText!!)

            qrBitmap?.let { bin.confirmBookingLayout.ticketQRCode.setImageBitmap(it) }

            bin.confirmBookingLayout.fromLocation.text = response.busDetail?.fromCity.toString()
            bin.confirmBookingLayout.toLocation.text = response.busDetail?.toCity.toString() + " at " + response.busDetail?.droppingDetails?.droppingTime.toString()
            bin.confirmBookingLayout.departureTime.text = response.busDetail?.departureTime.toString() + " " + response.busDetail?.travelDate.toString()

            bin.confirmBookingLayout.ticketStatus.text = response.ticketStatusDesc.toString()
            bin.confirmBookingLayout.referenceNumber.text = response.bookingRefNo.toString()
            bin.confirmBookingLayout.supplierRefNo.text = response.busDetail?.supplierRefNo.toString()
            bin.confirmBookingLayout.transportPNR.text = response.transportPNR.toString()
            bin.confirmBookingLayout.busOperatorName.text = response.busDetail?.operatorName.toString()
            bin.confirmBookingLayout.boardingAddress.text = response.busDetail?.boardingDetails?.boardingAddress.toString()
            bin.confirmBookingLayout.droppingAddress.text = response.busDetail?.droppingDetails?.droppingAddress.toString()
            bin.confirmBookingLayout.boardingTime.text = response.busDetail?.boardingDetails?.boardingTime.toString()
            bin.confirmBookingLayout.droppingingTime.text = response.busDetail?.droppingDetails?.droppingTime.toString()
            bin.confirmBookingLayout.busType.text = response.busDetail?.busType.toString()

            response.paXDetails.forEach { fareDetails ->
                // Extract and parse individual amounts safely
                val basicAmount = fareDetails.fare?.basicAmount ?: 0
                val gst = fareDetails.fare?.gst ?: 0
                val otherAmount = fareDetails.fare?.otherAmount ?: 0

// Extract convenience fee from the TextView (e.g., "₹ 50"), remove non-digits
                val convenienceFeeText = bin.confirmBookingLayout.conveniencesFees.text.toString().trim()
                val convenienceFee = convenienceFeeText.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0

// Add all values
                val totalAmount = basicAmount.toDouble() + gst.toDouble() + otherAmount.toDouble() + convenienceFee.toDouble()

// Set values to TextViews
                bin.confirmBookingLayout.baseFare.text = "₹ $basicAmount"
                bin.confirmBookingLayout.gstCharge.text = "₹ $gst"
                bin.confirmBookingLayout.otherCharge.text = "₹ $otherAmount"
                bin.confirmBookingLayout.conveniencesFees.text = bin.confirmBookingLayout.conveniencesFees.text.toString().trim()
                bin.confirmBookingLayout.totalAmount.text = "₹ $totalAmount"



                ticketDetails.add(TicketDetailsForGenerateTicket(response.cancellationPolicy,response.transportPNR.toString(), response.bookingRefNo.toString(),response.busDetail?.fromCity.toString(),response.busDetail?.toCity.toString(),
                                                    response.busDetail?.departureTime.toString() , response.busDetail?.travelDate.toString() , response.busDetail?.arrivalTime.toString(),"",response.busDetail?.operatorName.toString(),
                    "You will get the driver contact number and vehicle number 30 mins to 1 hours before departure",response.busDetail?.boardingDetails?.boardingAddress.toString(),response.busDetail?.droppingDetails?.droppingAddress.toString() ,"",response.busDetail?.boardingDetails?.boardingTime.toString(),
                    response.busDetail?.busType.toString(),"₹ $basicAmount", "₹ $gst","₹ $otherAmount","₹ " + bin.confirmBookingLayout.conveniencesFees.text.toString().trim(),"₹ $totalAmount"))
           }


            var PaxRequeryResponseReq = BusPaxRequeryResponseReq(
                loginId = mStash!!.getStringValue(Constants.RegistrationId, ""),
                bookingRefNo = response.bookingRefNo,
                ipAddress =mStash?.getStringValue(Constants.deviceIPAddress, "") ,
                requestId = mStash!!.getStringValue(Constants.requestId, ""),
                imeINumber = "0054748569",
                registrationId = mStash?.getStringValue(Constants.MerchantId, ""),
                transportPNR =response.transportPNR ,
                ticketStatusId =response.ticketStatusId ,
                ticketStatusDesc = response.ticketStatusDesc ,
                apiResponse = Gson().toJson(response),
                paramUser =mStash!!.getStringValue(Constants.RegistrationId, "")
            )

            hitApiforPassDetailsListResponse(PaxRequeryResponseReq)




        } else {
            Toast.makeText(
                this,
                response?.responseHeader?.errorInnerException.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    fun hitApiforPassDetailsListResponse(PaxRequeryResponseReq: BusPaxRequeryResponseReq){
        Log.d("BusRequeryRequest", Gson().toJson(PaxRequeryResponseReq))

        viewModel.getPassangerDetailsRequest(PaxRequeryResponseReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        pd.dismiss()
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Constants.uploadDataOnFirebaseConsole(Gson().toJson(response),"BusSeatingPassangerDetailsRequest",this@DemoBusSeat)
                                Log.d("RequeryResponse", Gson().toJson(response))
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        pd.dismiss()
                    }

                    ApiStatus.LOADING -> {
                        pd.show()
                    }
                }
            }
        }


    }


    fun generateQrCode(content: String, size: Int = 512): Bitmap {
        val hints = Hashtable<EncodeHintType, String>().apply {
            put(EncodeHintType.CHARACTER_SET, "UTF-8")
        }

        val bitMatrix: BitMatrix = MultiFormatWriter().encode(
            content,
            BarcodeFormat.QR_CODE,
            size,
            size,
            hints
        )

        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }

        return bitmap
    }


    private fun buildQrData(
        transportPNR: String?,
        bookingRefNo: String?,
        fromCity: String?,
        toCity: String?,
        travelDate: String?,
        busType: String?
    ): String? {
        return """
            bookingRefNo: $bookingRefNo
            PNR No: $transportPNR
            fromCity: $fromCity
            toCity: $toCity
            travelDate: $travelDate
            busType: $busType
        """.trimIndent()
    }


    private fun buildQrDataWithPassengers(pnr: String?, bookingRefNo: String?, fromCity: String?, toCity: String?, travelDate: String?,
        busType: String?,
        passengerList: ArrayList<com.example.twopayd2c.data.model.travel.bus.busRequery.PaXDetails>
        ): String {
        val builder = StringBuilder()

        builder.appendLine("🚌 Bus Ticket")
        builder.appendLine("PNR: ${pnr ?: "N/A"}")
        builder.appendLine("Booking Ref No: ${bookingRefNo ?: "N/A"}")
        builder.appendLine("From: ${fromCity ?: "N/A"}")
        builder.appendLine("To: ${toCity ?: "N/A"}")
        builder.appendLine("Date: ${travelDate ?: "N/A"}")
        builder.appendLine("Bus Type: ${busType ?: "N/A"}")
        builder.appendLine()
        builder.appendLine("👤 Passengers:")

        passengerList.forEachIndexed { index, passenger ->
            builder.appendLine("${index + 1}. Name: ${passenger.paXName ?: "N/A"}")
            builder.appendLine("   Age: ${passenger.age ?: "N/A"}")
            builder.appendLine("   Gender: ${if (passenger.gender == 0) "Male" else "Female"}")
            builder.appendLine("   Seat: ${passenger.seatNumber ?: "N/A"}")
            builder.appendLine()
        }

        return builder.toString().trim()
    }


    fun createTicketPdf(context: Context, fileName: String, qrBitmap: Bitmap, ticketData: String) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        val paint = Paint()
        paint.textSize = 14f
        paint.color = Color.BLACK

        // Draw ticket text
        val lines = ticketData.split("\n")
        var yPosition = 50
        for (line in lines) {
            canvas.drawText(line, 50f, yPosition.toFloat(), paint)
            yPosition += 25
        }

        // Draw QR Code below the text
        val resizedQr = Bitmap.createScaledBitmap(qrBitmap, 200, 200, false)
        canvas.drawBitmap(resizedQr, 50f, (yPosition + 20).toFloat(), null)

        pdfDocument.finishPage(page)

        // Save to Downloads folder
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        if (!downloadsDir.exists()) downloadsDir.mkdirs()

        val file = File(downloadsDir, "$fileName.pdf")
        try {
            val outputStream = FileOutputStream(file)
            pdfDocument.writeTo(outputStream)
            outputStream.flush()
            outputStream.close()
            val uri = Uri.fromFile(file)
            val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
            context.sendBroadcast(scanIntent)
            Toast.makeText(context, "PDF saved to Downloads", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to save PDF", Toast.LENGTH_SHORT).show()
        }

        pdfDocument.close()
    }


    fun openDialogForGenerateTicket(context: Context,fileName: String,bitmap:Bitmap,data:MutableList<MutableList<String?>>, ticketDetails :MutableList<TicketDetailsForGenerateTicket>){
        if(!ticketDetails.isEmpty()&&ticketDetails.size>0) {
            var ticketDetails = ticketDetails.get(0)
            generateQRPdf(context,bitmap,  fileName,data, headers,ticketDetails){it.second.let {
                Toast.makeText(context, "PDF saved to Downloads", Toast.LENGTH_SHORT).show()
            }

            }

        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun validationPassenger() {
        val titlePos = bin.passengerDetails.titleSp.selectedItemPosition
        val genderPos = bin.passengerDetails.passengerGender.selectedItemPosition

        when {
            bin.userEmailId.text.isNullOrBlank() -> {
                bin.userEmailId.requestFocus()
                toast("Please enter email ID")
            }

            bin.userMobileNo.text.isNullOrBlank() -> {
                bin.userMobileNo.requestFocus()
                toast("Please enter mobile number")
            }

            else -> {
                getAllDetailsBusBooking()
            }
        }
    }


    private fun validationBoarding() {
        val boardingTextPos = bin.boardingPointSp.selectedItemPosition
        val droppingTextPos = bin.droppingPointSp.selectedItemPosition

        if (boardingTextPos == 0) {
            Toast.makeText(this, "Please select your boarding point", Toast.LENGTH_SHORT).show()
        } else if (droppingTextPos == 0) {
            Toast.makeText(this, "Please select your dropping point", Toast.LENGTH_SHORT).show()
        }
        else {
            bin.seatBookingLayout.visibility = View.GONE
            bin.boardingPointLayout.visibility = View.GONE
            bin.NextBtn.visibility = View.GONE
            bin.proceedBtn.visibility = View.GONE
            bin.proceedToBookBtn.visibility = View.VISIBLE
            bin.passengerDetails.passengerDetailsLayout.visibility = View.VISIBLE
            setSpinnerForGender()
        }
    }


    private fun validationSeat() {
        if (selectedSeats.isEmpty()) {
            toast("Choose your seat")
        } else {
            bin.seatBookingLayout.visibility = View.GONE
            bin.boardingPointLayout.visibility = View.VISIBLE
            bin.NextBtn.visibility = View.GONE
            bin.proceedBtn.visibility = View.VISIBLE
            bin.proceedToBookBtn.visibility = View.GONE
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAllDetailsBusBooking() {
        val busTempBookingReq = BusTempBookingReq(
            boardingId = intent.getStringExtra(Constants.boarding_Id),
            corporatePaymentMode = 0,
            corporateStatus = "0",
            costCenterId = 0,
            customerMobile = bin.userMobileNo.text.toString().trim(),
            dealKey = "",
            droppingId = intent.getStringExtra(Constants.dropping_Id),
            gst = false,
            gstin = "",
            gstinHolderAddress = "",
            gstinHolderName = "",
            paXDetails = getPassengerDetailsList(),
            PassengerEmail = bin.userEmailId.text.toString().trim(),
            PassengerMobile = bin.userMobileNo.text.toString().trim(),
            ProjectId = 0,
            Remarks = "testing",
            BusKey = intent.getStringExtra(Constants.busKey),
            SearchKey = mStash.getStringValue(Constants.searchKey, ""),
            SeatMapKey = mStash.getStringValue(Constants.seatMap_Key, ""),
            sendEmail = false,
            sendSMS = false,
            iPAddress = mStash.getStringValue(Constants.deviceIPAddress, ""),
            requestId = mStash.getStringValue(Constants.requestId, ""),
            imeINumber = "1234567890",
            registrationID = mStash.getStringValue(Constants.MerchantId, "")
        )

        Log.d("busTempBookingReq", Gson().toJson(busTempBookingReq))

        viewModel.getAllBusTempBooking(busTempBookingReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        pd.dismiss()
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                hitApiForGetTampBookingRequest( Gson().toJson(busTempBookingReq))
                                getAllDetailsBusBookingRes(response)
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        hitApiForGetTampBookingRequest( Gson().toJson(busTempBookingReq))
                        pd.dismiss()
                    }

                    ApiStatus.LOADING -> {
                        pd.show()
                    }
                }
            }
        }
    }


    private fun hitApiForGetTampBookingRequest( request:String){
        val busTempBookingReq = BusTempBookingRequest(
            iPAddress = mStash.getStringValue(Constants.deviceIPAddress, ""),
            requestId = mStash.getStringValue(Constants.requestId, ""),
            imeINumber = "1234567890",
            corporatePaymentMode = "0",
            corporateStatus = "0",
            costCenterId = "0",
            customer_Mobile = bin.userMobileNo.text.toString().trim(),
            dealKey = "",
            boardingId = intent.getStringExtra(Constants.boarding_Id),
            droppingId = intent.getStringExtra(Constants.dropping_Id),
            gst = "",
            gstin = "",
            gstinHolderAddress = "",
            gstinHolderName = "",
            busKey = intent.getStringExtra(Constants.busKey),
            searchKey = mStash.getStringValue(Constants.searchKey, ""),
            seatMapKey = mStash.getStringValue(Constants.seatMap_Key, ""),
            sendEmail = "",
            sendSMS = "",
            createdBy = mStash.getStringValue(Constants.RegistrationId, ""),
            registrationID = mStash.getStringValue(Constants.MerchantId, ""),
            loginId = mStash.getStringValue(Constants.RegistrationId, ""),
            apiRequest =request
        )

        Log.d("TampBookRequest",busTempBookingReq.toString())
        viewModel.getBusTampBookRequest(busTempBookingReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        pd.dismiss()
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Constants.uploadDataOnFirebaseConsole(Gson().toJson(response),"BusSeatingBusTampBookRequest",this@DemoBusSeat)
                                Log.d("TampBookReqResopnse",response.toString())
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        pd.dismiss()
                    }

                    ApiStatus.LOADING -> {
                        pd.show()
                    }

                }
            }
        }

    }


    private fun hitApiForGetTampBookingResponse( response:String, apiResponse:BusTempBookingRes){
        val busTempBookingReq = BusTampBookTicketResponseRequest(
            iPAddress = mStash.getStringValue(Constants.deviceIPAddress, ""),
            requestId = mStash.getStringValue(Constants.requestId, ""),
            imeINumber = "1234567890",
            bookingRefNo = apiResponse.bookingRefNo,
            errorCode = apiResponse.responseHeader!!.errorCode,
            errorDesc = apiResponse.responseHeader!!.errorDesc,
            errorInnerException = apiResponse.responseHeader!!.errorInnerException,
            createdBy = mStash.getStringValue(Constants.RegistrationId, ""),
            registrationID = mStash.getStringValue(Constants.MerchantId, ""),
            loginId = mStash.getStringValue(Constants.RegistrationId, ""),
            apiResponse =response
        )

        Log.d("TampBookResponseRequest",Gson().toJson(busTempBookingReq))

        viewModel.getTampBusTicketResponse(busTempBookingReq).observe(this)
        {
            resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        pd.dismiss()
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Constants.uploadDataOnFirebaseConsole(Gson().toJson(response),"BusSeatingTampBusTicketResponse",this@DemoBusSeat)
                                Log.d("TampBookResponseRes",response.toString())
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        pd.dismiss()
                    }

                    ApiStatus.LOADING -> {
                        pd.show()
                    }

                }
            }
        }


    }


    @SuppressLint("SetTextI18n")
    private fun getAllDetailsBusBookingRes(response: BusTempBookingRes?) {
        if (response?.responseHeader?.errorCode == "0000") {
            hitApiForGetTampBookingResponse(Gson().toJson(response),response)
            bin.fullLayout.visibility = View.GONE
            bin.reviewLayout.visibility = View.VISIBLE
            bin.reviewBookingInclude.userName.text = bin.passengerDetails.passengerName.text.trim()
            bin.reviewBookingInclude.userAge.text = age.toString() + " Yrs"
            bin.reviewBookingInclude.seatNumber.text = "Seat No. " + mStash.getStringValue(Constants.seatNumber, "")
            bin.reviewBookingInclude.mobileNo.text = bin.userMobileNo.text.trim()
            bin.reviewBookingInclude.emailId.text = bin.userEmailId.text.trim()
            bin.reviewBookingInclude.companyName.text =
                intent.getStringExtra(Constants.travelCompanyName).orEmpty()
            bin.reviewBookingInclude.busName.text =
                intent.getStringExtra(Constants.busName).orEmpty()
            bin.reviewBookingInclude.fromLocation.text =
                mStash.getStringValue(Constants.fromDesignationName, "")
            bin.reviewBookingInclude.toLocation.text =
                mStash.getStringValue(Constants.toDesignationName, "")
            bin.reviewBookingInclude.boardingPoint.text =
                mStash.getStringValue(Constants.boardingPoint, "")
            bin.reviewBookingInclude.droppingPoint.text =
                mStash.getStringValue(Constants.droppingPoint, "")
            bin.reviewBookingInclude.arrivalTime.text =
                intent.getStringExtra(Constants.arrivalTime).orEmpty()
            bin.reviewBookingInclude.totalTime.text =
                intent.getStringExtra(Constants.travelTime).orEmpty()
            mStash.setStringValue(Constants.booking_RefNo, response.bookingRefNo.toString())

            if( !response.bookingRefNo.isNullOrEmpty()){
                bin.reviewBookingInclude.finalSeatLayout.visibility=View.VISIBLE
            }

            Log.d("bookingRefNo", response.bookingRefNo.toString())
            toast(response.responseHeader?.errorCode.toString())
           // getAllBusAddMoney(response.bookingRefNo.toString())

        }
        else {
            Toast.makeText(this, response?.responseHeader?.errorInnerException.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun getAllBusAddMoney(referenceId: String) {
        val busAddMoneyReq = BusAddMoneyReq(
            clientRefNo = referenceId,
            refNo = referenceId,
            transactionType = 0,
            productId = "0",
            iPAddress = mStash.getStringValue(Constants.deviceIPAddress, ""),
            requestId = mStash.getStringValue(Constants.requestId, ""),
            imeINumber = "1232972532",
            registrationID = mStash.getStringValue(Constants.MerchantId, "")
        )
        Log.d("BusAddMoneyReq", Gson().toJson(busAddMoneyReq))
        AppLog.d("BusAddMoneyReq",Gson().toJson(busAddMoneyReq))
        viewModel.getAllAddMoney(busAddMoneyReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        pd.dismiss()
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                getAllBusAddMoneyRes(response)
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        pd.dismiss()
                    }

                    ApiStatus.LOADING -> {
                        pd.show()
                    }
                }
            }
        }
    }


    private fun getAllBusAddMoneyRes(response: BusAddMoneyRes?) {
        AppLog.d("BusAddMoneyResponse",Gson().toJson(response))
        if (response?.responseHeader?.errorCode == "0000") {
            getAllBusTicketing(mStash.getStringValue(Constants.booking_RefNo, "").toString())
        }
        else {
            Toast.makeText(this, response?.responseHeader?.errorInnerException.plus("Amount: ").plus(response!!.amount), Toast.LENGTH_SHORT).show()
        }
    }


    private fun getAllBusTicketing(referenceId: String) {
        val busTicketingReq = BusTicketingReq(
            bookingRefNo = referenceId,
            iPAddress = mStash.getStringValue(Constants.deviceIPAddress, ""),
            requestId = mStash.getStringValue(Constants.requestId, ""),
            imeINumber = "125424463",
            registrationID = mStash.getStringValue(Constants.MerchantId, ""))

        Log.d("busTempBookingReq", Gson().toJson(busTicketingReq))
        AppLog.d("busTempBookingReq",Gson().toJson(busTicketingReq))

        viewModel.getAllBusTicketing(busTicketingReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        pd.dismiss()
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                AppLog.d("busTempBookingRes",Gson().toJson(response))
                                getAllBusTicketingRes(response)
                                getAddBusTicketRequest(mStash.getStringValue(Constants.booking_RefNo, "").toString(),Gson().toJson(busTicketingReq))
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        pd.dismiss()
                        getAddBusTicketRequest(mStash.getStringValue(Constants.booking_RefNo, "").toString(),Gson().toJson(busTicketingReq))
                    }

                    ApiStatus.LOADING -> {
                        pd.show()
                    }
                }
            }
        }

    }


    // hit api for add bus ticket request.............................................................................................
    private fun getAddBusTicketRequest(referenceId: String,apirequest:String) {
        val busTicketingReq = AddTicketReq(
            requestId =mStash.getStringValue(Constants.requestId, "") ,
            iPAddress = mStash.getStringValue(Constants.deviceIPAddress, ""),
            imeINumber = "125424463",
            bookingRefNo = referenceId,
            createdBy = mStash.getStringValue(Constants.RegistrationId, ""),
            registrationID = mStash.getStringValue(Constants.MerchantId, ""),
            loginID = mStash.getStringValue(Constants.RegistrationId, ""),
            apiRequest = apirequest)

    Log.d("busAddTicketRequest", Gson().toJson(busTicketingReq))

    viewModel.getAddBusTicketRequest(busTicketingReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        pd.dismiss()
                        Constants.uploadDataOnFirebaseConsole(Gson().toJson(resource.data),"BusSeatingAddBusTicketRequest",this@DemoBusSeat)
                        Log.d("AddBusTicketRequest",resource.data.toString())
                    }

                    ApiStatus.ERROR -> {
                        pd.dismiss()
                    }

                    ApiStatus.LOADING -> {
                        pd.show()
                    }
                }
            }
        }


    }


    private fun hitApiForBusTicketResponse(response: BusTicketingRes){
        val busTicketingReq = AddTicketResponseReq(
            requestId =mStash.getStringValue(Constants.requestId, "") ,
            iPAddress = mStash.getStringValue(Constants.deviceIPAddress, ""),
            imeINumber = "125424463",
            bookingRefNo = mStash.getStringValue(Constants.booking_RefNo, ""),
            errorCode      = response.responseHeader!!.errorCode,
            errorDesc      = response.responseHeader!!.errorDesc,
            errorInnerException  = response.responseHeader!!.errorInnerException,
            createdBy = mStash.getStringValue(Constants.RegistrationId, ""),
            statusId      = response.responseHeader!!.statusId,
            supplierRefNo      = response.supplierRefno,
            transportPNR      = response.transportPNR,
            registrationID = mStash.getStringValue(Constants.MerchantId, ""),
            loginID = mStash.getStringValue(Constants.RegistrationId, ""),
            apiResponse  =  Gson().toJson(response))

        Log.d("busAddTicketRequest", Gson().toJson(busTicketingReq))
        Log.d("apiResponse", Gson().toJson(response))

        viewModel.getAddBusTicketResponse(busTicketingReq).observe(this) { resource ->
            resource?.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        pd.dismiss()
                        getTransferAmountToAgentWithCal()
                        Constants.uploadDataOnFirebaseConsole(Gson().toJson(resource.data),"BusSeatingAddBusTicketResponse",this@DemoBusSeat)
                        Log.d("AddBusTicketResponse",resource.data.toString())
                    }

                    ApiStatus.ERROR -> {
                        pd.dismiss()
                    }

                    ApiStatus.LOADING -> {
                        pd.show()
                    }
                }
            }
        }

    }


    private fun getTransferAmountToAgentWithCal() {
        try {
            val transferAmountToAgentsReq = TransferAmountToAgentsReq(
                transferFrom = mStash!!.getStringValue(Constants.RegistrationId, ""),
                transferTo = "Admin",
                transferAmt = "0",
                remark = "Your bus transaction was successful",
                transferFromMsg = "",
                transferToMsg = "",
                amountType = "Payout",
                actualTransactionAmount = "0",
                transIpAddress = mStash!!.getStringValue(Constants.deviceIPAddress, ""),
                parmUserName = mStash!!.getStringValue(Constants.RegistrationId, "") ,
                merchantCode = mStash!!.getStringValue(Constants.MerchantId, "") ,
                servicesChargeAmt ="0.00",
                servicesChargeGSTAmt = "0.00",
                servicesChargeWithoutGST = "0.00",
                customerVirtualAddress = "",
                retailerCommissionAmt = "0.00",
                retailerId = "",
                paymentMode = "IMPS",
                depositBankName = "",
                branchCodeChecqueNo = "",
                apporvedStatus = "Approved",
                registrationId = mStash!!.getStringValue(Constants.RegistrationId, ""),
                benfiid = "",
                accountHolder = "",
                flag = "Y"
            )

            Log.d("getAllGsonFromAPI", Gson().toJson(transferAmountToAgentsReq))
            getAllApiServiceViewModel.getTransferAmountToAgents(transferAmountToAgentsReq)
                .observe(this) { resource ->
                    resource?.let {
                        when (it.apiStatus) {
                            ApiStatus.SUCCESS -> {
                                it.data?.let { users ->
                                    users.body()?.let { response ->
                                        pd.dismiss()
                                        Log.d("BosPayoutTransaction", response.toString())

                                    }
                                }
                            }

                            ApiStatus.ERROR -> {
                                pd.dismiss()
                                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }

                            ApiStatus.LOADING -> {
                                pd.show()
                            }
                        }
                    }
                }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            pd.dismiss()
            Toast.makeText(
                this,
                e.message.toString() + " " + e.localizedMessage?.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun getAllBusTicketingRes(response: BusTicketingRes) {

        hitApiForBusTicketResponse(response)
        if (response.responseHeader?.errorCode == "0000") {
            bin.reviewBookingInclude.finalSeatLayout.visibility = View.GONE
            bin.reviewBookingInclude.reviewBookingDetailsLayout.visibility = View.VISIBLE
            bin.reviewBookingInclude.showTicketLayout.visibility = View.GONE
            bin.reviewBookingInclude.toolbarHeaderName.text = "Booking Details"
            bin.reviewBookingInclude.referenceNumber.text = response.bookingRefNo.toString()
            bin.reviewBookingInclude.supplierRefNo.text = response.supplierRefno.toString()
            bin.reviewBookingInclude.transportPNR.text = response.transportPNR.toString()
            getAllBusRequaryTicket()
           // toast("Your seat booking is successful")
        }
        else {
            Toast.makeText(this, response.responseHeader?.errorInnerException.toString(), Toast.LENGTH_SHORT).show()
        }

    }


    private fun setDropDown() {
        bin.passengerDetails.passengerDetailsLayout.visibility = View.GONE
        try {
            Constants.getAllBoardingPointAdapter = ArrayAdapter<String>(this, R.layout.spinner_item_selected, Constants.boardingPointName!!)
            Constants.getAllBoardingPointAdapter!!.setDropDownViewResource(R.layout.spinner_right_aligned)
            bin.boardingPointSp.adapter = Constants.getAllBoardingPointAdapter
            bin.boardingPointSp.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        pos: Int,
                        id: Long
                    ) {
                        if (pos >= 0) {
                            try {
                                boardingPointText = parent.getItemAtPosition(pos).toString()
                                mStash.setStringValue(
                                    Constants.boardingPoint,
                                    boardingPointText.toString()
                                )
                                // If you need to perform further actions with selectedAllOperator, do it here.
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            boardingPointText = ""
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // Handle the case where nothing is selected, if needed
                    }
                }

// Notify the adapter that the data set has changed (if data is dynamically added/removed)
            Constants.getAllBoardingPointAdapter!!.notifyDataSetChanged()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        try {
            Constants.getAllDroppingPointAdapter = ArrayAdapter<String>(
                this,
                R.layout.spinner_item_selected,
                Constants.droppingPointName!!
            )


            Constants.getAllDroppingPointAdapter!!.setDropDownViewResource(R.layout.spinner_right_aligned)
            bin.droppingPointSp.adapter = Constants.getAllDroppingPointAdapter
            bin.droppingPointSp.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                        if (pos >= 0) {
                            try {
                                droppingPointText = parent.getItemAtPosition(pos).toString()
                                mStash.setStringValue(
                                    Constants.droppingPoint,
                                    droppingPointText.toString()
                                )

                                // If you need to perform further actions with selectedAllOperator, do it here.
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            droppingPointText = ""
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // Handle the case where nothing is selected, if needed
                    }
                }
            Constants.getAllDroppingPointAdapter!!.notifyDataSetChanged()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
        */
/*************************************************** title Spinner ************************************//*

//        Constants.titleName = arrayListOf("Select your title", "Mr", "Mrs","Ms")

        val arrayListSpinner = resources.getStringArray(R.array.gender_array)//title_type
        val adapters = ArrayAdapter(this, R.layout.spinner_right_aligned, arrayListSpinner)
        adapters.setDropDownViewResource(R.layout.spinner_right_aligned)
        bin.passengerDetails.titleSp.adapter = adapters
        bin.passengerDetails.titleSp.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    titleText = if (position > 0) {
                        parent!!.getItemAtPosition(position).toString()
                    } else {
                        null
                    }
                    Log.e("TAG", "onItemSelected: $titleText")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        bin.passengerDetails.titleSp.setSelection(0)

        */
/************************************************ gender Spinner *****************************//*

//        Constants.genderName = arrayListOf("Select your gender", "Male", "Female")

*/
/*        bin.passengerDetails.passengerGender.visibility=View.VISIBLE
        val arrayGenderSpinner = resources.getStringArray(R.array.gender_array)
        val genderadapters = ArrayAdapter(this@BusSeating, R.layout.spinner_right_aligned, arrayGenderSpinner)
        genderadapters.setDropDownViewResource(R.layout.spinner_right_aligned)
        bin.passengerDetails.passengerGender.adapter = genderadapters

      *//*
*/
/*  bin.passengerDetails.passengerGender.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (position > 0) {
                        genderText = parent!!.getItemAtPosition(position).toString()
                        selectedIndex = if (position == 1) 0 else 1
                        toast(selectedIndex.toString())
                        Log.d("TAG", "SelectedGender: $genderText, Index: $selectedIndex")
                    }
                    else {
                        genderText = ""
                        selectedIndex = -1 // Or reset state
                        Log.e("TAG", "Please select gender")
                    }

                    Log.e("TAG", "onItemSelected: $genderText")

                }


                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selectedIndex = -1
                }

            }*//*
*/
/*

        bin.passengerDetails.passengerGender.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.d("Spinner", "Item selected at position $position")
                    if (position > 0) {
                        genderText = parent!!.getItemAtPosition(position).toString()
                        selectedIndex = if (position == 1) 0 else 1
                        toast(selectedIndex.toString())
                    } else {
                        genderText = ""
                        selectedIndex = -1
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        bin.passengerDetails.passengerGender.setSelection(0)*//*


    }


    private fun setSpinnerForGender(){
        val arrayGenderSpinner = resources.getStringArray(R.array.gender_array)
        val genderadapters = ArrayAdapter(this@DemoBusSeat, R.layout.spinner_right_aligned, arrayGenderSpinner)
        genderadapters.setDropDownViewResource(R.layout.spinner_right_aligned)
        bin.passengerDetails.passengerGender.adapter = genderadapters

        bin.passengerDetails.passengerGender.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.d("Spinner", "Item selected at position $position")
                    if (position > 0) {
                        genderText = parent!!.getItemAtPosition(position).toString()
                        selectedIndex = if (position == 1) 0 else 1
                        toast(selectedIndex.toString())
                        Log.d("Gender Data",genderText+"")
                    }
                    else {
                        genderText = ""
                        selectedIndex = -1
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        bin.passengerDetails.passengerGender.setSelection(0)


        */
/*  bin.passengerDetails.passengerGender.onItemSelectedListener =
             object : AdapterView.OnItemSelectedListener {

                 override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                     if (position > 0) {
                         genderText = parent!!.getItemAtPosition(position).toString()
                         selectedIndex = if (position == 1) 0 else 1
                         toast(selectedIndex.toString())
                         Log.d("TAG", "SelectedGender: $genderText, Index: $selectedIndex")
                     }
                     else {
                         genderText = ""
                         selectedIndex = -1 // Or reset state
                         Log.e("TAG", "Please select gender")
                     }

                     Log.e("TAG", "onItemSelected: $genderText")

                 }


                 override fun onNothingSelected(parent: AdapterView<*>?) {
                     selectedIndex = -1
                 }

             }*//*

    }


    @SuppressLint("SetTextI18n")
    private fun setupUI() {
        pd = showLoadingDialog(this)
        mStash = MStash.getInstance(this)!!
        Constants.titleName = ArrayList()
        Constants.genderName = ArrayList()
        Constants.genderName?.add("Select your gender")
        Constants.genderName?.add("Select your title")
        list = ArrayList()
        passengerList = ArrayList()

        bin.seatBookingLayout.visibility = View.VISIBLE
        bin.fromLocation.text = mStash.getStringValue(Constants.fromDesignationName, "")
        bin.fromLocation.text = mStash.getStringValue(Constants.fromDesignationName, "")
        bin.toLocation.text = mStash.getStringValue(Constants.toDesignationName, "")
        bin.dateAndTime.text = mStash.getStringValue(Constants.dateAndTime, "")
        bin.companyName.text = intent.getStringExtra(Constants.travelCompanyName).orEmpty()
        bin.busName.text = intent.getStringExtra(Constants.busName).orEmpty()
        bin.arrivalTime.text = intent.getStringExtra(Constants.arrivalTime).orEmpty()
        bin.travelAmount.text = "₹" + intent.getStringExtra(Constants.travelAmount).orEmpty()
        bin.totalTime.text = intent.getStringExtra(Constants.travelTime).orEmpty()

        Constants.boardingPointName = ArrayList()
        Constants.droppingPointName = ArrayList()
        Constants.boardingPointName?.add("Select your boarding Point")
        Constants.droppingPointName?.add("Select your dropping Point")

        bin.confirmBookingLayout.recyclerViewPassenger.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        userPassengerDetailsAdapter = UserPassengerDetailsAdapter(this, passengerList)
        bin.confirmBookingLayout.recyclerViewPassenger.adapter = userPassengerDetailsAdapter
    }


    private fun getAllMappingSeat() {
        val request = BusSeatMapReq(
            boardingId = intent.getStringExtra(Constants.boarding_Id),
            droppingId = intent.getStringExtra(Constants.dropping_Id),
            busKey = intent.getStringExtra(Constants.busKey),
            searchKey = mStash.getStringValue(Constants.searchKey, ""),
            iPAddress = mStash.getStringValue(Constants.deviceIPAddress, ""),
            requestId = mStash?.getStringValue(Constants.requestId, ""),
            imeINumber = "2232323232323",
            registrationID = mStash.getStringValue(Constants.MerchantId, "")
        )

        Log.d("busTempBookingReq", Gson().toJson(request))

        viewModel.getAllBusSeatMap(request).observe(this) { resource ->
            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    pd.dismiss()
                    resource.data?.body()?.let { setupSeatGrid(it) }
                }

                ApiStatus.ERROR -> {
                    pd.dismiss()
                    Toast.makeText(this, "Failed to load seats", Toast.LENGTH_SHORT).show()
                }

                ApiStatus.LOADING -> pd.show()
            }
        }
    }


    private fun setupSeatGrid(response: BusSeatMapRes) {
        val lowerGrid = bin.seatLayout.lowerBerthGrid
        val upperGrid = bin.seatLayout.upperBerthGrid

        bin.seatBookingLayout.visibility = View.VISIBLE
        bin.boardingPointLayout.visibility = View.GONE
        bin.NextBtn.visibility = View.VISIBLE
        bin.proceedBtn.visibility = View.GONE
        bin.proceedToBookBtn.visibility = View.GONE


        lowerGrid.removeAllViews()
        upperGrid.removeAllViews()
        mStash.setStringValue(Constants.seatMap_Key, response.seatMapKey.toString())

        Log.d("removeAllViews", mStash.getStringValue(Constants.seatMap_Key, "").toString())

        try {
            Constants.boardingPointName?.clear()
            Constants.droppingPointName?.clear()
            Constants.boardingPointName?.add("Select your boarding Point")
            Constants.droppingPointName?.add("Select your dropping Point")
            response.boardingDetails.forEach { boardingPoint ->
                Constants.boardingPointName?.add(boardingPoint.boardingAddress!!)
            }
            Constants.getAllBoardingPointAdapter?.notifyDataSetChanged()
            response.droppingDetails.forEach { droppingPoint ->
                Constants.droppingPointName?.add(droppingPoint.droppingAddress!!)
            }
            Constants.getAllDroppingPointAdapter?.notifyDataSetChanged()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        response.seatMap.forEach { seat ->
            val row = seat.row?.toIntOrNull() ?: 0
            val col = seat.column ?: 0
            val zIndex = seat.zIndex?.toIntOrNull() ?: 0
            val length = seat.length?.toIntOrNull() ?: 1
//            val seatName = seat.seatNumber ?: ""
            val seatName = ("₹" + seat.fareMaster?.basicAmount) ?: ""

            mStash.setStringValue(Constants.seatNumber, seat.seatNumber.toString())

            val seatView = createSeatView(seatName.toString(), this, row, col, length, seat)

            if (zIndex == 1) {
//                bin.seatLayout.upperBerthGrid.visibility = View.VISIBLE
//                bin.seatLayout.lowerBerthGrid.visibility = View.GONE
                upperGrid.addView(seatView)
            } else {
//                bin.seatLayout.upperBerthGrid.visibility = View.GONE
//                bin.seatLayout.lowerBerthGrid.visibility = View.VISIBLE
                lowerGrid.addView(seatView)
            }
        }
    }


    @SuppressLint("InflateParams", "SetTextI18n")
    private fun createSeatView(seatName: String, context: Context, row: Int, column: Int, length: Int, seat: SeatMap): View {
        val seatView = LayoutInflater.from(context).inflate(R.layout.seat_item, null)
        val tvPrice = seatView.findViewById<TextView>(R.id.tvPrice)
        tvPrice.text = seatName
//        tvPrice.text = seat.seatNumber
        tvPrice.setTextColor(Color.BLACK)

        val isSleeper = length == 2
        val width = if (isSleeper) 100 else 100
        val height = if (isSleeper) 150 else 100

        val layoutParams = FrameLayout.LayoutParams(width, height).apply {
            topMargin = 10
            leftMargin = 10
        }

        seatView.layoutParams = layoutParams

        // Set initial background
        when {
            seat.ladiesSeat == true -> seatView.setBackgroundResource(R.drawable.bg_ladies_seat)
            seat.bookable == true -> seatView.setBackgroundResource(R.drawable.bg_edittext)
            else -> seatView.setBackgroundResource(R.drawable.booked_seat)
        }

        seatView.setOnClickListener {
            if (seat.bookable != true) {
                Toast.makeText(context, "Seat not available", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedSeats.contains(seat)) {
                selectedSeats.remove(seat)

                // Set background after deselection
                if (seat.ladiesSeat == true) {
                    seatView.setBackgroundResource(R.drawable.bg_ladies_seat)
                } else {
                    seatView.setBackgroundResource(R.drawable.bg_edittext)
                }

                // Remove passenger form
                bin.passengerDetails.passengerDetailsLayout.findViewWithTag<View>(seat.seatNumber)
                    ?.let {
                        bin.passengerDetails.passengerDetailsLayout.removeView(it)
                    }

            }
            else {
                selectedSeats.add(seat)
                bin.finalSeatLayout.visibility = View.VISIBLE
                seatView.setBackgroundResource(R.drawable.your_seat)

                // Add passenger form
                val formView = LayoutInflater.from(context).inflate(R.layout.passenger_details, null)
                formView.tag = seat.seatNumber
                val typePrefix = if ((seat.zIndex?.toIntOrNull() ?: 0) == 1) "U" else "L"
                formView.findViewById<TextView>(R.id.seatTitle).text =
                    "Seat $typePrefix${seat.seatNumber}"
                bin.passengerDetails.passengerDetailsLayout.addView(formView)
            }

            // Keep passenger forms count in sync
            while (bin.passengerDetails.passengerDetailsLayout.childCount > selectedSeats.size) {
                bin.passengerDetails.passengerDetailsLayout.removeViewAt(0)
            }

            // Update selected seat label and amount
            val seatLabels = selectedSeats.map {
                val type = if ((it.zIndex?.toIntOrNull() ?: 0) == 1) "U" else "L"
                "$type${it.seatNumber}"
            }

            bin.selectedSeat.text = seatLabels.joinToString(" ")
            val totalAmount = selectedSeats.sumOf { it.fareMaster?.basicAmount ?: 0 }
            bin.finalAmount.text = "₹ $totalAmount"
            bin.reviewBookingInclude.totalprice.text = "₹ $totalAmount"
            Log.d("Total Amount", " $totalAmount")
        }

        return seatView
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getPassengerDetailsList(): List<PaXDetails> {

        val paxList = mutableListOf<PaXDetails>()

        for (i in 0 until bin.passengerDetails.passengerDetailsLayout.childCount) {
            val formView = bin.passengerDetails.passengerDetailsLayout.getChildAt(i)

            val fullName = formView.findViewById<EditText>(R.id.passengerName).text.toString().trim()
            val gender = formView.findViewById<Spinner>(R.id.passengerGender).selectedItem.toString()
            val dob = formView.findViewById<EditText>(R.id.passengerDob).text.toString().trim()
            val seatNumber = formView.tag?.toString() ?: ""
            val title = formView.findViewById<Spinner>(R.id.titleSp).selectedItem.toString()
            val berth = seatNumber.firstOrNull().toString() // "U" or "L" based on your logic

            if(gender.equals("Male")){
                selectedIndex=0
            }

            if(gender.equals("Female")){
                selectedIndex=1
            }

            try {
                age = calculateAgeFromDOB(dob)
                val next = getNextNumber()
                val seatNumber = selectedSeats[i].seatNumber

                val dateOfBirth = getChangeDateFormat(dob)


                paxList.add(
                    PaXDetails(
                        Age = age,
                        DOB = dateOfBirth,
                        Fare = list,
                        Gender = selectedIndex.toString(),
                        IdNumber = "",
                        IdType = 0,
                        LadiesSeat = false,
                        PAXId = next.toString(),
                        PAXName = fullName,
                        PenaltyCharge = "",
                        Primary = true,
                        SeatNumber = seatNumber,
                        Status = "",
                        TicketNumber = "",
                        Title = title
                    )
                )

            } catch (e: DateTimeParseException) {
                e.printStackTrace()
            }
        }
        return paxList
    }


}


*/
