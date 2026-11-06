package com.example.twopayd2c.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.LocationManager
import android.media.MediaMetadataRetriever
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.Buses
import com.example.twopayd2c.activity.AdditionalDetailsPage.Companion.count
import com.example.twopayd2c.activity.AdditionalDetailsPage.Companion.progress
import com.example.twopayd2c.activity.EducationPage
import com.example.twopayd2c.activity.FamilyMemberPage
import com.example.twopayd2c.activity.GenderPage
import com.example.twopayd2c.activity.LoginPage
import com.example.twopayd2c.activity.MaritalStatusPage
import com.example.twopayd2c.data.model.RetailerWiseServicesDataItem
import com.example.twopayd2c.data.model.travelmodel.flight.DataItem
import com.example.twopayd2c.data.model.travelmodel.flight.FlightsItem
import com.example.twopayd2c.data.model.travelmodel.flight.TripDetailsItem
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.network.NetworkConstant
import com.example.twopayd2c.utils.ConstantClass.Companion.FlowConfigration.isTaskCompleted
import java.net.Inet4Address
import java.net.NetworkInterface
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Random
import java.util.concurrent.TimeUnit
import java.util.jar.Manifest
import kotlin.math.round
import kotlin.math.roundToInt

class ConstantClass {

    companion object{
        val FromBus= "leavingfrom"
        val send= "SEND"
        val login= "LOGIN"
        val validate= "VALIDATE"

        val customer : String  = "Customer"

        // Customer details..................................
        var OnBoardingPage ="onboardingpage"
        var Logged = "logged"
        val UserID ="userID"
        val AgentType ="agentType"
        val ApplicationType ="applicationType"
        val ApplicationMode ="applicationMode"
        val FirstName ="firstName"
        val LastName ="lastName"
        val CustomerEmailID ="emailId"
        val CustomerMobileNumber ="mobileNo"
        val AdminBaseUrl ="adminBaseUrl"
        var AdminCode = "adminCode"
        val MerchantCode ="merchantCode"
        val FullName ="fullName"
        val KycApproved ="kycApproved"

        val ToBus= "goingto"
        val BusType = "Bus Type"
        val BusDeparture = "Departure"
        val BusOperator = "Bus Operator"
        var startDate : String= ""
        var endDate : String  = ""


        var WalletAmount = "walletamount"
        var WalletAmountClick = "walletamountclick"

        val FlightPrice = "Flight Price"
        val FlightType = "Flight Type"
        val FlightDeparture = "Flight Departure"
        val FlightAirlines = "Flight Airlines"

        var SearchFromToEditText=""
        var FromCityName:String = ""
        var ToCityName:String=""
        var ToCityId:String=""
        var FromCityId:String=""
        var requestId = "requestId"
        var RegistrationId = "RegistrationId"
        var TotalSeatAmount = "totalseatamount"
        var FUTUREDATALIST = "futureDataList"
        var imeINumber = "imeINumber"
        var MaleGender = "Male"
        var FemaleGender = "Female"
        var deviceIPAddress = "deviceIPAddress"
        var MerchantId = "MerchantId"
        var fromDesignationName = "fromDesignationName"
        var fromDesignationId = "fromDesignationId"
        var toDesignationName = "toDesignationName"
        var toDesignationId = "toDesignationId"
        var dateAndTime = "dateAndTime"
        var RecentSavedData = "recentSearchBusList"
        var RecentSavedFlightData = "recentSearchFlightList"
        var searchKey = "searchKey"
        var busKey = "busKey"
        var boarding_Id = "boarding_Id"
        var boarding_Time = "boarding_time"
        var dropping_Id = "dropping_Id"
        var dropping_Time = "dropping_time"
        var boardingPoint = "boardingPoint"
        var droppingPoint = "droppingPoint"
        var travelCompanyName = "travelCompanyName"
        var busName = "busName"
        var travelAmount = "travelAmount"
        var ratingCount = "ratingcount"
        var seatMap_Key = "seatMap_Key"
        var arrivalTime = "arrivalTime"
        var travelTime = "travelTime"
        var merchantBalance = "merchantBalance"

        var State = "State"
        var ac = "Ac"
        var nonac = "Non AC"

        var nonstop = "Non-Stop"
        var stops = "Stops"

        var before6am  ="Before 6 am"
        var after6am  = "6am - 12 pm"
        var after12pm  ="12pm - 6 pm"
        var after6pm  ="After 6 pm"


        var retailer_CommissionType = "retailer_CommissionType"
        var serviceType = "serviceType"
        var retailerCommissionWithoutTDS = "retailerCommissionWithoutTDS"
        var retailerCommission = "retailerCommission"
        var tds = "tds"
        var gst = "gst"
        var totalTransaction = "totalTransaction"
        var serviceCharge = "serviceCharge"
        var serviceChargewithgst = "serviceChargewithgst"
        var actualbusticketamt = "actualbusticketamt"
        var serviceChargeWithGST = "serviceChargeWithGST"

        var actualAmountServiceChargeWithGST = "actualAmountServiceChargeWithGST"

        var priceFilter : Boolean = false
        var flightTypeFilter : Boolean = false
        var departureFilter : Boolean = false
        var flightOperatorFilter : Boolean = false


        // for flight
        var adultCount: Int = 1
        var childCount: Int = 0
        var infantCount: Int = 0
        var className : String = "Economy"
        var classnameforprint : String = "Economy"
        var EconomyClass= "Economy"
        var Premimumeconomy= "Premium Economy"
        var Business= "Business"
        var Firstclass= "First Class"
        var  fromAirportCode: String = ""
        var  fromAirportName: String = ""
        var  fromCountryName: String = ""
        var  checkinBag: String = ""
        var  cabinBag: String = ""

        var travelType:Int=0
        var FlightSearchKey = "search_Key"
        var FlightSearchData = "flightsearchdata"
        var FlightLowToHigh = "Low to high"
        var FlightHighToLow = "High to low"
        var FlightFilterData = "flightfilterdata"
        var FlightAllList = "allflightlist"
        var FlightKey = "flight_key"
        var MobileNumber = "MobileNumber"

        var AirTotalTicketPrice = "flight_ticket_price"
        var AirTotalOperatorPrice = "flight_ticket_operator"
        var AirTotalBasicPrice = "flight_ticket_Basic"
        var DebitAmount = "BookTicketAmount"
        var BookingRefNo = "bookingRefNo"
        var ForPayoutBookingRefId = "Payout_booking_RefNo"



        var Adult = "Adult"
        var Child = "Child"
        var Infant = "Infant"

        var Ms = "Ms."
        var Mstr = "Mstr."
        var Mr = "Mr."
        var Mrs = "Mrs."

        var FlightPassengerFutureDate = "PassengerData"

        var EmailID = "Emailid"
        var ContactNumber = "Contact Number"
        var CheckNonStop = false

        val BOSMerchantCode  = "AOP-5027"
        val BOSAdminCode  = "BOS-1002"


        var TripDetailsList: MutableList<TripDetailsItem?> = mutableListOf()
        var FlightDetails: MutableList<FlightsItem?> = mutableListOf()


        // using for filter ........................................................
        var airportNameList: MutableList<Pair<String, Boolean>> = mutableListOf()
        var priceList: MutableList<Pair<String, Boolean>> = mutableListOf()
        var flightTypeList: MutableList<Pair<String, Boolean>> = mutableListOf()
        var flightDepartureList: MutableList<Pair<String, Boolean>> = mutableListOf()
        //............................................................................

        var FlightListForFilter: MutableList<FlightsItem> = mutableListOf()
        var SearchFlightList: MutableList<FlightsItem> = mutableListOf()
        var AllFlightList: MutableList<FlightsItem> = mutableListOf()
        var FlightList: MutableList<DataItem>? = mutableListOf()
        var FlightHint: MutableList<DataItem>? = mutableListOf()
        var getAllFlightListAdapter: ArrayAdapter<DataItem>? = null
        var lastFilterList: MutableList<FlightsItem> = mutableListOf()

        var toAirportCode: String= ""
        var toAirportName: String = ""
        var toCountryName: String = ""

        var classNumber: String = "0"
        var totalCount: String = ""
        var classnameForPrint: String = ""
        var bookingType: Int = 0
        var datepassangerandclassstring: String = ""
        var checkReverse = false
        var srCitizen:Boolean= false
        var studentFare:Boolean= false
        var defenceFare:Boolean= false
        var seniorCitizen:String= "Senior Citizens"
        var students:String= "Students"
        var defenceforces:String= "Defence Forces"


        // for air ticket .....................

        var BaseFare: String = ""
        var TaxAndFees: String = ""
        var GrossFare: String = ""
        val airlinecode ="airlinecode"
        var totalDurationTime: String = ""



        var nonStops : Boolean = false
        var Stops : Boolean =false
        lateinit var  dialog : Dialog

        var before6layout : Boolean = false
        var after6amlayout : Boolean =false
        var after12pmlayout : Boolean = false
        var after6pmlayout : Boolean =false
        var nonAC : Boolean = false
        var AC : Boolean =false
        //from bus search bottomSheet...............................
        var checkFromBus: Boolean = false
        var busListName: ArrayList<String>? = arrayListOf()
        var toLocationName: ArrayList<String>? = arrayListOf()
        var busListNameMap : HashMap<String, Int>? = hashMapOf()
        var toLocationNameMap: HashMap<String, Int>? = hashMapOf()
        var getAllBusListAdapter: ArrayAdapter<String>? = null
        //............................................................
        var getAllBoardingPointAdapter: ArrayAdapter<String>? = null
        var getAllDroppingPointAdapter: ArrayAdapter<String>? = null
        var boardingPointName: ArrayList<String>? = null
        var droppingPointName: ArrayList<String>? = null

        var BusDepartureDateAndTime:String= ""
        var FlightDepartureDateAndTime:String= ""

        var BusListForFilter: MutableList<Buses> = mutableListOf()
        var AllBusList: MutableList<Buses> = mutableListOf()
        var busOperatorNameList: MutableList<Pair<String, Boolean>> = mutableListOf()
        var busTypeList: MutableList<Pair<String, Boolean>> = mutableListOf()
        var busDepartureList: MutableList<Pair<String, Boolean>> = mutableListOf()
        var busBoardingPointList: MutableList<Pair<String, Boolean>> = mutableListOf()
        var busDropingPointList: MutableList<Pair<String, Boolean>> = mutableListOf()
        var Currentwithnext5dateList:MutableList<String> = mutableListOf()

        var RETAILERALLSERVICES: List<RetailerWiseServicesDataItem?>? = listOf()

        var currentNumber = 1
        fun getNextNumber(): Int {
            return currentNumber++
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun calculateDOBFromAge(ageString: String): String {
            val age = ageString.toIntOrNull() ?: return null.toString()  // Safe conversion
            val today = LocalDate.now()
            val dob = today.minusYears(age.toLong())

            val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
            return dob.format(formatter)
        }

        fun startFlow(clickedItem: String,context: Context) {
            // 1. Get items not yet completed (you'll need your own check logic here)
            val incomplete = FlowConfigration.allFeatures.filter { !isTaskCompleted(context,it) }

            // 2. Put the clicked item first, then the rest
            val queue = ArrayList<String>()
            queue.add(clickedItem)
            queue.addAll(incomplete.filter { it != clickedItem })

            // 3. Start the first Activity in the queue
            navigateToNextStep(queue,context)

        }

        private fun navigateToNextStep(queue: ArrayList<String>,context: Context) {
            if (queue.isEmpty()) {
                // Return to main page or finish
                return
            }
            count=0
            progress=0
            val nextStep = queue.removeAt(0)
            val intent = when (nextStep) {
                FlowConfigration.GENDER -> Intent(context, GenderPage::class.java)
                FlowConfigration.MARITAL -> Intent(context, MaritalStatusPage::class.java)
                FlowConfigration.EDUCATION -> Intent(context, EducationPage::class.java)
                FlowConfigration.FAMILY -> Intent(context, FamilyMemberPage::class.java)
                else -> null
            }

            intent?.let {
                it.putStringArrayListExtra("FLOW_QUEUE", queue)
                count++
                context.startActivity(it)
            }
        }

        object FlowConfigration {
            const val GENDER = "gender"
            const val MARITAL = "marital"
            const val EDUCATION = "education"
            const val FAMILY = "family"

            val allFeatures = listOf(GENDER, MARITAL, EDUCATION, FAMILY)

            fun isTaskCompleted(context: Context, task: String): Boolean {
                val prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                return !prefs.getString(task, null).isNullOrEmpty()
            }
        }

        fun getVideoThumbnail( uri: Uri,context: Context): Bitmap? {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(context, uri)

            val bitmap = retriever.frameAtTime
            retriever.release()
            return bitmap
        }

        fun generateRandomNumber(): String {
            val random = Random()
            val uniqueNumbers = HashSet<String>()

            while (uniqueNumbers.size < 8) {
                val randomNumber = random.nextInt(90000000) + 10000000
                uniqueNumbers.add(randomNumber.toString())
            }

            return uniqueNumbers.first()
        }

        fun getDeviceIpAddress(): String? {
            try {
                val interfaces = NetworkInterface.getNetworkInterfaces()
                for (networkInterface in interfaces) {
                    val addresses = networkInterface.inetAddresses
                    for (address in addresses) {
                        if (!address.isLoopbackAddress && address is Inet4Address) {
                            return address.hostAddress
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        @SuppressLint("NewApi")
        fun getCurrentwithnext5date() {
            val today = LocalDate.now()
            Currentwithnext5dateList.clear()
            for (i in 0..5) {
                val nextDay = today.plusDays(i.toLong())
                Currentwithnext5dateList.add(nextDay.toString())
            }
        }
        
        fun convertDate(input: String): String {

            val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("EEE , dd", Locale.getDefault())

            val date = inputFormat.parse(input)
            return outputFormat.format(date!!)
        }

        fun searchDateIfNotInList(input: String): String {
            val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("dd MMM yyyy" , Locale.getDefault())

            val date = inputFormat.parse(input)
            return outputFormat.format(date!!)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun searchDateFormList(input: String): String {
            val date = LocalDate.parse(input)
            return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        }

        fun calculateTimeDifference(startTimeStr: String, endTimeStr: String): String {
            val format = SimpleDateFormat("hh:mm a", Locale.getDefault()) // Format like "9:00 AM"
            val calendar = Calendar.getInstance()

            val startTime = format.parse(startTimeStr)
            val endTime = format.parse(endTimeStr)

            if (startTime != null && endTime != null) {

                val startCal = Calendar.getInstance().apply { time = startTime }
                val endCal = Calendar.getInstance().apply { time = endTime }

                if (endCal.before(startCal)) {
                    endCal.add(Calendar.DAY_OF_MONTH, 1)
                }

                val diffInMillis = endCal.timeInMillis - startCal.timeInMillis
                val diffInHours = diffInMillis / (1000 * 60 * 60)
                val diffInMinutes = (diffInMillis / (1000 * 60)) % 60

                return "$diffInHours h $diffInMinutes m"
            }
            return "Invalid time"
        }

        fun getRetailerAllServices():List<RetailerWiseServicesDataItem>{
            var servicesList : MutableList<RetailerWiseServicesDataItem> = mutableListOf()
            servicesList.add(RetailerWiseServicesDataItem("y","F0115","DMT"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0116","Bill Payment")) // emi,munsipal
            servicesList.add(RetailerWiseServicesDataItem("y","F0118","FastTag Recharge"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0119","Pan Card"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0120","Aadhar"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0121","PG2"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0122","PG3"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0124","PaymentLink"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0125","Credit Card"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0126","StandardPaymentLink"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0127","UPIPaymentLink"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0128","DMTV1"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0129","DMTV2"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0130","WalletMoney"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0131","YesBankVPA"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0111","Payin"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0112","Payout"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0113","CIBIL"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0114","PG"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0132","PayinV1"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0133","BusApi"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0134","AirApi"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0135","CreditAlalytics"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0136","BasicPanDetailsApi"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0137","PanApi"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0138","DGLockerAadhaarValidationApi"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0139","BasicUpiValidation"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0140","RechargeV2"))
            servicesList.add(RetailerWiseServicesDataItem("y","F0141","AEPSV1Api")) // cashwithdraw,ministatmnt,balanaceenquiery

            return servicesList
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun travelDateFormat(input: String?): String {

            if (input.isNullOrBlank()) return ""

            val outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

            // 1️⃣ Try if already in MM/dd/yyyy format
            return try {
                val alreadyFormatted = LocalDate.parse(input, outputFormatter)
                alreadyFormatted.format(outputFormatter)
            } catch (e: Exception) {

                // 2️⃣ Try parsing "d MMM yyyy"
                try {
                    val inputFormatter =
                        DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)

                    val date = LocalDate.parse(input, inputFormatter)
                    date.format(outputFormatter)

                } catch (e: Exception) {
                    // 3️⃣ If completely invalid format
                    input
                }
            }
        }

        fun generateRandomRating(): Double {
            val random = kotlin.random.Random.nextDouble(1.0, 5.0)
            return round(random * 10) / 10   // keeps 1 decimal like 3.2
        }


        fun getRatingColor(rating: Double): Int {
            return when {
                rating < 2.5 -> android.graphics.Color.RED
                rating < 4.0 -> android.graphics.Color.parseColor("#FFA500") // Orange
                else -> android.graphics.Color.parseColor("#4CAF50") // Green
            }
        }

        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }


        fun loading(context: Context){
            dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.loader)

            dialog.window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                statusBarColor = Color.TRANSPARENT
                navigationBarColor = Color.TRANSPARENT
                decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
            }

            dialog.setCanceledOnTouchOutside(false)

            dialog.show()

        }


        fun convertToMillis(dateString: String): Long {
            val format = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            val date = format.parse(dateString)
            return date?.time ?: System.currentTimeMillis()
        }


        fun convertFlightEditDateFormat(inputDate: String): String {
            return try {
                val inputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

                val date = inputFormat.parse(inputDate)
                outputFormat.format(date!!)
            } catch (e: Exception) {
                inputDate
            }
        }


        fun GetAirlineLogo(airlineCode: String): String {
            return "${NetworkConstant.IMAGE_BASE_URL}/AirlineLogos/${airlineCode}.png"
        }



        fun animateOpenFromTripCard(booknowView: View, tripcard: View) {
            // Ensure visibility and camera distance for 3D effect
            booknowView.cameraDistance = 8000 * booknowView.context.resources.displayMetrics.density
            booknowView.visibility = View.VISIBLE

            // Get tripcard Y location
            val tripLocation = IntArray(2)
            tripcard.getLocationOnScreen(tripLocation)
            val tripY = tripLocation[1]

            // Get booknow original Y location
            val bookLocation = IntArray(2)
            booknowView.getLocationOnScreen(bookLocation)
            val bookY = bookLocation[1]

            val offsetY = tripY - bookY

            // Apply initial state
            booknowView.translationY = offsetY.toFloat()
            booknowView.rotationX = -90f
            booknowView.alpha = 0f
            booknowView.scaleY = 0.8f

            // Animate to normal state
            booknowView.animate()
                .translationY(0f)
                .rotationX(0f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(400)
                .start()

        }


        fun animateCloseToTripCard(booknowView: View, tripcard: View) {
            // Ensure camera distance for 3D effect
            booknowView.cameraDistance = 8000 * booknowView.context.resources.displayMetrics.density

            // Get tripcard Y location
            val tripLocation = IntArray(2)
            tripcard.getLocationOnScreen(tripLocation)
            val tripY = tripLocation[1]

            // Get booknow original Y location
            val bookLocation = IntArray(2)
            booknowView.getLocationOnScreen(bookLocation)
            val bookY = bookLocation[1]

            val offsetY = tripY - bookY

            // Animate to close state
            booknowView.animate()
                .translationY(offsetY.toFloat())
                .rotationX(90f)
                .scaleY(0.8f)
                .alpha(0f)
                .setDuration(600)
                .withEndAction {
                    booknowView.visibility = View.GONE

                    // Reset the view to default state for future re-use
                    booknowView.translationY = 0f
                    booknowView.rotationX = 0f
                    booknowView.scaleY = 1f
                    booknowView.alpha = 1f
                }
                .start()
        }


        fun formatDate1(inputDate: String): String {
            val inputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEE", Locale.getDefault())

            return try {
                val date = inputFormat.parse(inputDate)
                outputFormat.format(date!!)
            } catch (e: Exception) {
                "Invalid Date"
            }
        }

        fun formatDateBusTicket(inputDate: String): String {
            val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

            return try {
                val date = inputFormat.parse(inputDate)
                outputFormat.format(date!!)
            } catch (e: Exception) {
                "Invalid Date"
            }
        }


        fun formatDate2(inputDate: String): String {
            val inputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

            return try {
                val date = inputFormat.parse(inputDate)
                outputFormat.format(date!!)
            } catch (e: Exception) {
                "Invalid Date"
            }
        }


        fun calculateLayoverTime(arrival: String, departure: String): String {
            val format = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
            val arrivalTime = format.parse(arrival)
            val departureTime = format.parse(departure)

            if (arrivalTime != null && departureTime != null && departureTime.after(arrivalTime)) {
                val diffInMillis = departureTime.time - arrivalTime.time
                val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60
                return "${hours}h ${minutes}m"
            }
            return "Invalid Time"
        }


        fun roundToNextHundred(amount: Double): Int {
            return (kotlin.math.ceil(amount / 100) * 100).toInt()
        }


        fun Double.toInrFormat(): String {
            val rounded = this.roundToInt()
            val formatted = NumberFormat.getNumberInstance(Locale("en", "IN"))
                .format(rounded)
            return "₹ $formatted"
        }


        fun openKeyboard(context:Context,editText: EditText,isMobile:Boolean) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            // 1. Remove focus & close keyboard
            editText.clearFocus()
            imm.hideSoftInputFromWindow(editText.windowToken, 0)

            editText.inputType = if (isMobile) {
                InputType.TYPE_CLASS_PHONE
            }
            else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }

            // 3. Re-focus & open keyboard
            editText.postDelayed({
                editText.requestFocus()
                editText.setSelection(editText.text.length)
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
            }, 150)


        }


        fun getStateFromLatLng(context: Context, latitude: Double, longitude: Double): String {
            return try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    address.adminArea ?: ""   // This gives the STATE
                } else {
                    ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun splitDateTimeTicket(datetimeStr: String): Pair<String, String> {
            val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
            val dateTime = LocalDateTime.parse(datetimeStr, formatter)

            val datePart = dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
            val timePart = dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))

            return Pair(datePart, timePart)
        }


        fun calculateTotalFlightDuration(flightSegments: List<Map<String, String>>): String {
            val format = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())

            // Parse first departure and last arrival
            val firstDeparture = format.parse(flightSegments.first()["departure_DateTime"]!!)
            val lastArrival = format.parse(flightSegments.last()["arrival_DateTime"]!!)

            val durationInMillis = lastArrival!!.time - firstDeparture!!.time

            val hours = TimeUnit.MILLISECONDS.toHours(durationInMillis)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis) % 60

            return "${hours}h ${minutes}m"
        }



        @RequiresApi(Build.VERSION_CODES.O)
        fun splitDateTime(datetimeStr: String): Pair<String, String> {
            val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")
            val dateTime = LocalDateTime.parse(datetimeStr, formatter)

            val datePart = dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
            val timePart = dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))

            return Pair(datePart, timePart)
        }

        fun convertDurationToReadableFormat(duration: String, addHours: Int = 0): String {
            val parts = duration.split(":")
            val hours = parts[0].toIntOrNull() ?: 0
            val minutes = parts[1].toIntOrNull() ?: 0

            val totalHours = hours + addHours

            return "${totalHours}h ${minutes}m"
        }

        fun calculateTotalFlightDurationTicketPrint(flightSegments: List<Map<String, String>>): String {
            val format = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())

            // Parse first departure and last arrival
            val firstDeparture = format.parse(flightSegments.first()["departure_DateTime"]!!)
            val lastArrival = format.parse(flightSegments.last()["arrival_DateTime"]!!)

            val durationInMillis = lastArrival!!.time - firstDeparture!!.time

            val hours = TimeUnit.MILLISECONDS.toHours(durationInMillis)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis) % 60

            return "${hours}h ${minutes}m"
        }

        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }


        fun generateRandomString(length: Int = 25): String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }


        fun hasLocationPermission(context: Context): Boolean {
            return ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }

        fun isLocationEnabled(context: Context): Boolean {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }

        fun String.formatWithLineLimit(limit: Int = 16): String {
            return chunked(limit).joinToString("\n")
        }


        fun checkActiveStatusAndLogout(context: Context, activeStatus: Boolean?, preference: SharedPreference) {
            if (!activeStatus!!) {
                try {
                    Toast.makeText(context, "Your account is inactive. Please contact support.", Toast.LENGTH_LONG).show()
                    preference.setBooleanValue(ConstantClass.Logged, false)

                    var intent=Intent(context, LoginPage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)

                    // Optional: if inside Activity, finish it
                    if (context is Activity) {
                        context.finish()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }



        fun transactionformatDate(input: String): String {
            return try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val date = inputFormat.parse(input)

                val outputFormat = SimpleDateFormat("MMM dd, yyyy, h:mm a", Locale.getDefault())
                outputFormat.format(date!!)

            } catch (e: Exception) {
                input
            }
        }


    }

}