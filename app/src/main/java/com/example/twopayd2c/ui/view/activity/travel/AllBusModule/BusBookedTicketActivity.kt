package com.example.twopayd2c.ui.view.activity.travel.AllBusModule

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twopayd2c.R
import com.example.twopayd2c.activity.DashBoard
import com.example.twopayd2c.data.model.travelmodel.bus.busRequery.BusRequeryRes
import com.example.twopayd2c.data.model.travelmodel.bus.busRequery.PaXDetails
import com.example.twopayd2c.data.model.travelmodel.bus.busRequery.TicketDetailsForGenerateTicket
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.SeattypeModel
import com.example.twopayd2c.databinding.ActivityBusBookedTicketBinding
import com.example.twopayd2c.ui.adapter.UserPassengerDetailsAdapter
import com.example.twopayd2c.utils.ConstantClass.Companion.formatDateBusTicket
import com.example.twopayd2c.utils.ConstantClass.Companion.formatWithLineLimit
import com.example.twopayd2c.utils.QRCodeGenerator.generateQRPdf
import com.example.twopayd2c.utils.QRCodeGenerator.sharePdfFile
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

import java.util.Hashtable

class BusBookedTicketActivity : AppCompatActivity() {
       lateinit var binding : ActivityBusBookedTicketBinding
       private  var passengerList: ArrayList<PaXDetails> = arrayListOf()
       private lateinit var userPassengerDetailsAdapter: UserPassengerDetailsAdapter
       var ticketDetails : MutableList<TicketDetailsForGenerateTicket> = mutableListOf()
       var data : MutableList<MutableList<String?>> = mutableListOf()
       var headers: Array<String> = arrayOf("Name", "Gender", "Seat No","Ticket Number")
       private var qrText: String? = null
       private var qrBitmap: Bitmap? = null


       companion object{
          lateinit var BusRequeryResponse : BusRequeryRes
          lateinit var SleeperAmount : String
          lateinit var SeaterAmount : String
          lateinit var OperatorGST : String
          lateinit var OnwordFare : String
          lateinit var ConvenienceFee : String
          lateinit var promoDiscount : String
          lateinit var grandTotal : String
          var forServiceChargeSeatTypeBus : MutableList<SeattypeModel> = mutableListOf()
       }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBusBookedTicketBinding.inflate(layoutInflater)
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
        setDataOnUi()
    }


    fun setDataOnUi(){
        binding.companyName.text =  BusRequeryResponse.busDetail?.operatorName
        binding.fromLocation.text = BusRequeryResponse.busDetail?.fromCity
        binding.toLocation.text =  BusRequeryResponse.busDetail?.toCity
        binding.departureDate.text = BusRequeryResponse.busDetail?.departureTime
        binding.arrivalDate.text = BusRequeryResponse.busDetail?.arrivalTime
        var bordingName =  BusRequeryResponse.busDetail?.boardingDetails?.boardingName
        binding.boardingPoint.text = bordingName?.formatWithLineLimit(16)
        var droppingName =  BusRequeryResponse.busDetail?.droppingDetails?.droppingName
        binding.droppingPoint.text = droppingName?.formatWithLineLimit(16)
        binding.operatorName.text = BusRequeryResponse.busDetail?.operatorName
        binding.bustypetxt.text = BusRequeryResponse.busDetail?.busType
        binding.customerName.text = BusRequeryResponse.customerDetail?.customerName
        binding.customerContactdetails.text = "${BusRequeryResponse.customerDetail?.customerMobile} | ${BusRequeryResponse.customerDetail?.customerEmail}"
        var travelDate = formatDateBusTicket(BusRequeryResponse.busDetail?.travelDate!!)
        binding.travelDate.text = travelDate

        if(BusRequeryResponse.busDetail?.busType!!.toLowerCase().contains("ac")){
            binding.bustypeicon.setImageResource(R.drawable.bustype_ac)
        }else
        {
            binding.bustypeicon.setImageResource(R.drawable.nonacbustype)
        }

        val originalText = BusRequeryResponse.transportPNR
        binding.pnrnumber.text = originalText?.formatWithLineLimit(16)

        binding.bookingIDnumber.text = BusRequeryResponse.bookingRefNo

        BusRequeryResponse.paXDetails.forEach { passengerData ->
            passengerList.add(passengerData)
        }

        userPassengerDetailsAdapter = UserPassengerDetailsAdapter(this, passengerList)
        binding.passengerdetailslayout.adapter = userPassengerDetailsAdapter

        qrText=  buildQrDataWithPassengers(BusRequeryResponse.transportPNR,BusRequeryResponse.bookingRefNo ,BusRequeryResponse.busDetail?.fromCity,BusRequeryResponse.busDetail?.toCity,BusRequeryResponse.busDetail?.travelDate, BusRequeryResponse.busDetail?.busType,passengerList)

        Log.d("qrData", qrText.toString())

        qrBitmap = generateQrCode(qrText!!)

        qrBitmap?.let {binding.ticketQRCode.setImageBitmap(it) }


        // for ticket generation in pdf format....................................
        for(passenger in passengerList){
            if(passenger.gender==0){
                data.add(mutableListOf(passenger.paXName,"Male",passenger.seatNumber,passenger.ticketNumber))
            }else{
                data.add(mutableListOf(passenger.paXName,"Female",passenger.seatNumber,passenger.ticketNumber))
            }

        }


        ticketDetails.add(TicketDetailsForGenerateTicket(BusRequeryResponse.cancellationPolicy!!,BusRequeryResponse.transportPNR.toString(), BusRequeryResponse.bookingRefNo.toString(),BusRequeryResponse.busDetail?.fromCity.toString(),BusRequeryResponse.busDetail?.toCity.toString(),
            BusRequeryResponse.busDetail?.departureTime.toString() , BusRequeryResponse.busDetail?.travelDate.toString() , BusRequeryResponse.busDetail?.arrivalTime.toString(),"",BusRequeryResponse.busDetail?.operatorName.toString(),
            "You will get the driver contact number and vehicle number 30 mins to 1 hours before departure",BusRequeryResponse.busDetail?.boardingDetails?.boardingAddress.toString(),BusRequeryResponse.busDetail?.droppingDetails?.droppingAddress.toString() ,"",BusRequeryResponse.busDetail?.boardingDetails?.boardingTime.toString(),
            BusRequeryResponse.busDetail?.busType.toString(),"$SleeperAmount", "$SeaterAmount","$OperatorGST","${OnwordFare}","$ConvenienceFee"," $promoDiscount","₹ $grandTotal"))


    }


    fun setOnClickListner(){

        binding.homeicon.setOnClickListener {
            val intent = Intent(this@BusBookedTicketActivity, DashBoard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.shareticket.setOnClickListener {
            qrBitmap?.let {
                SavedAndShareTicket(context = this,fileName = "MyBusTicket_${System.currentTimeMillis()}",qrBitmap!!,data,ticketDetails)
            }
        }


        binding.downloadETicket.setOnClickListener {
            qrBitmap?.let {
                openDialogForGenerateTicket(context = this,fileName = "MyBusTicket_${System.currentTimeMillis()}",qrBitmap!!,data,ticketDetails)
            }
        }
    }



    fun openDialogForGenerateTicket(context: Context, fileName: String, bitmap:Bitmap, data:MutableList<MutableList<String?>>, ticketDetails :MutableList<TicketDetailsForGenerateTicket>){
        if(!ticketDetails.isEmpty()&&ticketDetails.size>0) {
            var ticketDetails = ticketDetails.get(0)
            generateQRPdf(context,bitmap,  fileName,data, headers,ticketDetails,forServiceChargeSeatTypeBus){it.second.let {
                Toast.makeText(context, "PDF saved to Downloads", Toast.LENGTH_SHORT).show()
            }

            }

        }


    }


    fun SavedAndShareTicket(context: Context, fileName: String, bitmap:Bitmap, data:MutableList<MutableList<String?>>, ticketDetails :MutableList<TicketDetailsForGenerateTicket>){
        if(!ticketDetails.isEmpty()&&ticketDetails.size>0) {
            var ticketDetails = ticketDetails.get(0)
            generateQRPdf(context,bitmap,  fileName,data, headers,ticketDetails,forServiceChargeSeatTypeBus){it.second.let {file ->
                file?.sharePdfFile(context)
            }

            }

        }


    }


    private fun buildQrDataWithPassengers(pnr: String?, bookingRefNo: String?, fromCity: String?, toCity: String?, travelDate: String?, busType: String?, passengerList: ArrayList<PaXDetails>): String {
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


    fun generateQrCode(content: String, size: Int = 512): Bitmap {
        val hints = Hashtable<EncodeHintType, String>().apply {
            put(EncodeHintType.CHARACTER_SET, "UTF-8")
        }

        val bitMatrix: BitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints)

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


}