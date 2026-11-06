package com.example.twopayd2c.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.login.CustomerLoginDetailsRequest
import com.example.twopayd2c.data.model.travelmodel.login.D2CSignUpRequest
import com.example.twopayd2c.data.model.travelmodel.login.SendOtpRequest
import com.example.twopayd2c.data.model.travelmodel.login.ValidateOtpRequest
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.data.viewModelFactory.TravelViewModelFactory
import com.example.twopayd2c.databinding.ActivityLoginPageBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.viewmodel.TravelViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.getDeviceIpAddress
import com.example.twopayd2c.utils.ConstantClass.Companion.getStateFromLatLng
import com.example.twopayd2c.utils.ConstantClass.Companion.hasLocationPermission
import com.example.twopayd2c.utils.ConstantClass.Companion.isLocationEnabled
import com.example.twopayd2c.utils.ConstantClass.Companion.login
import com.example.twopayd2c.utils.ConstantClass.Companion.openKeyboard
import com.example.twopayd2c.utils.SharedPreference
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson

class LoginPage : AppCompatActivity() {
    lateinit var binding: ActivityLoginPageBinding
    var checkLoginTypeMobile: Boolean = true
    private lateinit var viewModel: TravelViewModel
    lateinit var countDownTimer: CountDownTimer
    var LoginValue: String = ""
    lateinit var preference: SharedPreference
    var latitude : Double = 0.0
    var longitude : Double = 0.0
    var stateName : String =""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())

            // Ensure we always have enough padding for either the
            // system navigation bar OR the keyboard (whichever is larger)
            val bottomPadding = Math.max(systemBars.bottom, imeInsets.bottom)

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                bottomPadding
            )

            insets
        }

        viewModel = ViewModelProvider(
            this, TravelViewModelFactory(
                TravelRepository(ApiClient.apiAllTravelAPI, ApiClient.apiBusAddRequestlAPI)
            )
        )[TravelViewModel::class.java]

        preference = SharedPreference(this)
        binding.proceedlayout.isEnabled = false

        setOnClickListner()
    }

    private fun checkPermissions(): Boolean {
        val fineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        return  fineLocationPermission== PackageManager.PERMISSION_GRANTED && coarLocationPermission== PackageManager.PERMISSION_GRANTED && camera== PackageManager.PERMISSION_GRANTED
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show()
            }
        }
        
    }




    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentLocation() {

        val fused = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        if (fused.lastLocation != null) {
            fused.lastLocation.addOnSuccessListener {
                it?.let { location ->
                    latitude = location.latitude
                    longitude = location.longitude

                    Log.d("LatLongg", "$latitude $longitude")
                    Log.d("CurrentLocation", "Lat: $latitude, Long: $longitude")
                    val state = getStateFromLatLng(this, latitude, longitude)
                    stateName = state
                    var mobile = binding.mobilenumber.text.toString().trim()
                    var emailIs = binding.emailId.text.toString().trim()
                    Log.d("STATE", state)
                    when {
                        checkLoginTypeMobile -> hitApiForSendOTP(mobile)
                        else -> hitApiForSendOTP(emailIs)
                    }

                } ?: run {
                    Log.e("CurrentLocation", "Location is null. Check GPS or Permissions.")
                }
            }

        }
    }


    override fun onResume() {
        super.onResume()
        observeKeyboard()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setOnClickListner() {

        binding.logintype.setOnClickListener {
            if (binding.logintype.text.toString().equals(getString(R.string.login_through_email))) {
                binding.enternumbertxt.text = getString(R.string.enter_email_id)
                binding.logintype.text = getString(R.string.login_through_mob)
                binding.mailicon.setImageResource(R.drawable.smartphone)
                binding.formobilelogin.visibility = View.GONE
                binding.formaillogin.visibility = View.VISIBLE
                checkLoginTypeMobile = false
                binding.mobilenumber.setText("")
                openKeyboard(this, binding.emailId, false)
            } else {
                binding.enternumbertxt.text = getString(R.string.enter_mobile_number)
                binding.logintype.text = getString(R.string.login_through_email)
                binding.mailicon.setImageResource(R.drawable.mailicon)
                binding.formobilelogin.visibility = View.VISIBLE
                binding.formaillogin.visibility = View.GONE
                checkLoginTypeMobile = true
                binding.emailId.setText("")
                openKeyboard(this, binding.mobilenumber, true)
            }
            binding.proceedlayout.isEnabled = false
            binding.proceedlayout.backgroundTintList = ContextCompat.getColorStateList(this@LoginPage, R.color.disablebuttoncolor)

        }

        binding.mobilenumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val (isValid, errorMsg) = validation()
                if (isValid) {
                    binding.proceedlayout.isEnabled = true
                    binding.proceedlayout.backgroundTintList =
                        ContextCompat.getColorStateList(this@LoginPage, R.color.primary_blue)
                } else {
                    binding.proceedlayout.isEnabled = false
                    binding.proceedlayout.backgroundTintList =
                        ContextCompat.getColorStateList(this@LoginPage, R.color.disablebuttoncolor)
                }


            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.emailId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.d("Check", "$checkLoginTypeMobile")
                val (isValid, errorMsg) = validation()
                if (isValid) {
                    binding.proceedlayout.isEnabled = true
                    binding.proceedlayout.backgroundTintList =
                        ContextCompat.getColorStateList(this@LoginPage, R.color.primary_blue)
                } else {
                    binding.proceedlayout.isEnabled = false
                    binding.proceedlayout.backgroundTintList =
                        ContextCompat.getColorStateList(this@LoginPage, R.color.disablebuttoncolor)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.proceedlayout.setOnClickListener {

            if(!checkPermissions()){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 101)
            }
            else{
                if (hasLocationPermission(this)) {
                    if (isLocationEnabled(this)) {
                        // ✅ Get location (lat, long)
                        getCurrentLocation()
                    } else {
                        // ❌ Location OFF → Show dialog
                        showLocationEnableDialog()
                    }
                }
                else {
                    // ❌ Ask for permission
                    showLocationEnableDialog()
                }


            }



        }

        binding.pinview.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

                if (validatePin()) {
                    binding.VerifyOtpLayout.isEnabled = true
                    binding.VerifyOtpLayout.backgroundTintList =
                        ContextCompat.getColorStateList(this@LoginPage, R.color.primary_blue)
                } else {
                    binding.VerifyOtpLayout.isEnabled = false
                    binding.VerifyOtpLayout.backgroundTintList =
                        ContextCompat.getColorStateList(this@LoginPage, R.color.disablebuttoncolor)
                }


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        binding.resendtxt.setOnClickListener {
            if (LoginValue.isNotBlank()) {
                hitApiForReSendOTP(LoginValue)
            } else {
                binding.loginscreen.visibility = View.VISIBLE
                binding.otpscreen.visibility = View.GONE
            }

        }

        binding.VerifyOtpLayout.setOnClickListener {
            hitApiForCheckOTPValidation()
        }

    }

    fun showLocationEnableDialog() {
        AlertDialog.Builder(this)
            .setTitle("Enable Location")
            .setMessage("Location is turned off. Please enable it to continue.")
            .setPositiveButton("Turn On") { _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    fun hitApiForCheckOTPValidation() {
        var otp = binding.pinview.text.toString().trim()
        var request = ValidateOtpRequest(
            taskType = ConstantClass.validate,
            mobileOrEmail = LoginValue,
            otPType = login,
            otpCode = otp
        )

        viewModel.checkOTPValidate(request).observe(this) { resource ->
            when(resource.apiStatus){
                ApiStatus.SUCCESS -> {
                    val response = resource.data?.body()
                     binding.pinview.text?.clear()
                    if(response?.isSuccess!!){
                        hitApiForCustomerLoginDetails(LoginValue)
                    }
                    else {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(this,response.returnMessage,Toast.LENGTH_SHORT).show()
                    }
                }

                ApiStatus.ERROR-> {
                    ConstantClass.dialog.dismiss()
                }

                ApiStatus.LOADING->{
                    ConstantClass.loading(this)
                }
            }
        }
    }

    fun hitApiForReSendOTP(mobileOrmail: String) {
        binding.otpsubtitle.text =
            "${resources.getString(R.string.enter_the_otp_you_received_on)} ${mobileOrmail}"

        var request = SendOtpRequest(
            taskType = ConstantClass.send,
            mobileOrEmail = mobileOrmail,
            otPType = ConstantClass.login,
            otpCode = ""
        )
        LoginValue = mobileOrmail
        viewModel.getsendOTPRequest(request).observe(this) { resource ->
            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    val response = resource.data?.body()
                    ConstantClass.dialog.dismiss()
                    Toast.makeText(this, response?.returnMessage.toString(), Toast.LENGTH_LONG)
                        .show()

                    if (response?.isSuccess!!) {
                        startOtpTimer(binding.resendtxt, binding.timer)

                    } else {

                    }

                }

                ApiStatus.ERROR -> {
                    ConstantClass.dialog.dismiss()
                    Toast.makeText(this, resource.message.toString() ?: "Error", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("busListError", resource.message.toString())
                }

                ApiStatus.LOADING -> {
                    ConstantClass.loading(this)
                }

            }

        }

    }

    fun hitApiForSendOTP(mobileOrmail: String) {
        binding.otpsubtitle.text = "${resources.getString(R.string.enter_the_otp_you_received_on)} ${mobileOrmail}"


        var request = SendOtpRequest(
            taskType = ConstantClass.send,
            mobileOrEmail = mobileOrmail,
            otPType = ConstantClass.login,
            otpCode = ""
        )
        LoginValue = mobileOrmail
        viewModel.getsendOTPRequest(request).observe(this) { resource ->
            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    val response = resource.data?.body()
                    ConstantClass.dialog.dismiss()
                    Toast.makeText(this, response?.returnMessage.toString(), Toast.LENGTH_LONG)
                        .show()

                    if (response?.isSuccess!!) {
                        binding.loginscreen.visibility = View.GONE
                        binding.otpscreen.visibility = View.VISIBLE
                        startOtpTimer(binding.resendtxt, binding.timer)
                    } else {
                        binding.loginscreen.visibility = View.VISIBLE
                        binding.otpscreen.visibility = View.GONE
                    }


                }

                ApiStatus.ERROR -> {
                    ConstantClass.dialog.dismiss()
                    Toast.makeText(this, resource.message.toString() ?: "Error", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("busListError", resource.message.toString())
                }

                ApiStatus.LOADING -> {
                    ConstantClass.loading(this)
                }

            }

        }

    }

    fun hitApiForCustomerLoginDetails(mobileOrmail: String) {
        preference.setStringValue(ConstantClass.MerchantId,ConstantClass.BOSMerchantCode)
        preference.setStringValue(ConstantClass.AdminCode,ConstantClass.BOSAdminCode)

        var request = CustomerLoginDetailsRequest(
            loginId = mobileOrmail,
        )

        viewModel.GetCustomerLoginDetailsReq(request).observe(this) { resource ->
            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    val response = resource.data?.body()
                     Log.d("LoginResponse",Gson().toJson(response))
                     Log.d("LoginResp", Gson().toJson(response))
                    if (response?.isSuccess!!) {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(this, response?.returnMessage.toString(), Toast.LENGTH_LONG).show()
                        preference.setStringValue(ConstantClass.UserID,response.data?.userID)
                        preference.setStringValue(ConstantClass.RegistrationId, response.data?.userID)
                        preference.setStringValue(ConstantClass.AgentType,response.data?.agentType)
                        preference.setStringValue(ConstantClass.ApplicationType,response.data?.applicationType)
                        preference.setStringValue(ConstantClass.ApplicationMode,response.data?.applicationMode)
                        preference.setStringValue(ConstantClass.FirstName,response.data?.firstName)
                        preference.setStringValue(ConstantClass.LastName,response.data?.lastName)
                        preference.setStringValue(ConstantClass.CustomerEmailID,response.data?.emailId)
                        preference.setStringValue(ConstantClass.CustomerMobileNumber,response.data?.mobileNo)
                        preference.setStringValue(ConstantClass.AdminBaseUrl,response.data?.adminBaseUrl)
                        preference.setStringValue(ConstantClass.FullName,response.data?.fullName)
                        preference.setStringValue(ConstantClass.KycApproved,response.data?.kycApproved)
                        preference.setStringValue(ConstantClass.State,response.data?.state)
                        preference.setBooleanValue(ConstantClass.Logged,true)
                        val intent = Intent(this@LoginPage, DashBoard::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                    else{
                        if(response.returnCode.equals("BOS-403")){
                            ConstantClass.dialog.dismiss()
                            binding.loginscreen.visibility = View.VISIBLE
                            binding.otpscreen.visibility = View.GONE
                            Toast.makeText(this, response.returnMessage.toString() , Toast.LENGTH_SHORT).show()
                        }
                        else{
                            hitApiForCustomerSignUp()
                        }
                    }
                }

                ApiStatus.ERROR -> {
                    ConstantClass.dialog.dismiss()
                    Toast.makeText(this, resource.message.toString() ?: "Error", Toast.LENGTH_SHORT).show()
                    Log.d("busListError", resource.message.toString())
                }

                ApiStatus.LOADING -> {

                }

            }

        }

    }

    fun hitApiForCustomerSignUp(){
        var mobile =""
        var emailID = ""
        if(checkLoginTypeMobile){
            mobile = binding.mobilenumber.text.toString()
        }
        else{
            emailID = binding.emailId.text.toString()
        }
        val ipAdress = getDeviceIpAddress()
        preference?.setStringValue(ConstantClass.deviceIPAddress, ipAdress)


        var request = D2CSignUpRequest(
            merchantCode = ConstantClass.BOSMerchantCode,
            ipAddress = ipAdress,
            adminCode = ConstantClass.BOSAdminCode,
            emailID = emailID,
            mobileNo = mobile,
            state = stateName
        )

        Log.d("SignUpReq", Gson().toJson(request))
        viewModel.GetCustomerSignupReq(request).observe(this) { resource ->
            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    val response = resource.data?.body()
                    Log.d("SignUpResp", Gson().toJson(response))
                    ConstantClass.dialog.dismiss()
                    Toast.makeText(this, response?.returnMessage.toString(), Toast.LENGTH_LONG).show()
                    if (response?.isSuccess!!) {
                        preference.setStringValue(ConstantClass.UserID,response.data?.userID)
                        preference!!.setStringValue(ConstantClass.RegistrationId, response.data?.userID)
                        preference.setStringValue(ConstantClass.AgentType,response.data?.agentType)
                        preference.setStringValue(ConstantClass.ApplicationType,response.data?.applicationType)
                        preference.setStringValue(ConstantClass.ApplicationMode,response.data?.applicationMode)
                        preference.setStringValue(ConstantClass.FirstName,response.data?.firstName)
                        preference.setStringValue(ConstantClass.LastName,response.data?.lastName)
                        preference.setStringValue(ConstantClass.CustomerEmailID,response.data?.emailId)
                        preference.setStringValue(ConstantClass.CustomerMobileNumber,response.data?.mobileNo)
                        preference.setStringValue(ConstantClass.AdminBaseUrl,response.data?.adminBaseUrl)
                        preference.setStringValue(ConstantClass.FullName,response.data?.fullName)
                        preference.setStringValue(ConstantClass.KycApproved,response.data?.kycApproved)
                        preference.setStringValue(ConstantClass.State,response.data?.state)
                        preference.setBooleanValue(ConstantClass.Logged,true)
                        val intent = Intent(this@LoginPage, DashBoard::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }

                }

                ApiStatus.ERROR -> {
                    ConstantClass.dialog.dismiss()
                    Toast.makeText(this, resource.message.toString() ?: "Error", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("busListError", resource.message.toString())
                }

                ApiStatus.LOADING -> {
                }

            }

        }

    }

    fun validation(): Pair<Boolean, String> {
        val mob = binding.mobilenumber.text.toString()
        val email = binding.emailId.text.toString()


        if (checkLoginTypeMobile) {
            if (mob.length < 10) {
                val msg = "Enter valid mobile number"
                binding.mobilenumber.error = msg
                return Pair(false, msg)
            }
        } else {

            if (email.isEmpty()) {
                val msg = "Enter email"
                binding.emailId.error = msg
                return Pair(false, msg)
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                val msg = "Enter valid email"
                binding.emailId.error = msg
                return Pair(false, msg)
            }
        }

        return Pair(true, "")
    }

    fun validatePin(): Boolean {
        val pin = binding.pinview.text.toString()

        return when {
            pin.isEmpty() -> {
                binding.pinview.error = "Enter PIN"
                false
            }

            pin.length < 6 -> {   // change to 6 if needed
                binding.pinview.error = "Enter complete PIN"
                false
            }

            else -> true
        }
    }

    fun startOtpTimer(resendtxt: TextView, timer: TextView) {
        resendtxt.visibility = View.GONE
        binding.timerlayout.visibility = View.VISIBLE

        countDownTimer = object : CountDownTimer(5 * 60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                timer.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timer.text = "00:00"
                binding.timerlayout.visibility = View.GONE
                resendtxt.visibility = View.VISIBLE
            }
        }
        countDownTimer.start()
    }

    fun observeKeyboard(): Boolean {
        val rootView = binding.root
        var isk = false
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)

            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom

            val isKeyboardOpen = keypadHeight > screenHeight * 0.15

            if (isKeyboardOpen) {
                Log.d("Keyboard", "OPEN")
                fadeOut(binding.characterImageview)
            } else {
                Log.d("Keyboard", "CLOSE")
                fadeIn(binding.characterImageview)
            }
            isk = isKeyboardOpen

            return@addOnGlobalLayoutListener
        }
        return isk
    }

    // Fade In
    fun fadeIn(imageView: ImageView) {
        imageView.alpha = 0f
        imageView.visibility = View.VISIBLE
        imageView.animate()
            .alpha(1f)
            .setDuration(500)
    }

    // Fade Out
    fun fadeOut(imageView: ImageView) {
        imageView.animate()
            .alpha(0f)
            .setDuration(500)
            .withEndAction {
                imageView.visibility = View.GONE
            }
    }


}