package com.example.twopayd2c.ui.view.activity.paymentgateway

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.media3.exoplayer.upstream.Allocation
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.paymentgateway.PaymentGatewayReq
import com.example.twopayd2c.data.model.travelmodel.walleBalance.GetBalanceReq
import com.example.twopayd2c.data.repository.GetAllAPIServiceRepository
import com.example.twopayd2c.data.viewModelFactory.GetAllApiServiceViewModelFactory
import com.example.twopayd2c.databinding.ActivityPayemntGateWayPageBinding
import com.example.twopayd2c.databinding.PassengerSavedFlightItemLayoutBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.viewmodel.GetAllApiServiceViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.dialog
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.net.CookieManager

class PayemntGateWayPage : AppCompatActivity() {
    lateinit var binding: ActivityPayemntGateWayPageBinding
    lateinit var Amount : String
    private lateinit var getAllApiServiceViewModel: GetAllApiServiceViewModel
    lateinit var  dialog : Dialog
    lateinit var blurBitmap : Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPayemntGateWayPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getAllApiServiceViewModel = ViewModelProvider(this, GetAllApiServiceViewModelFactory(
            GetAllAPIServiceRepository(ApiClient.apipaymentGateWayInterface)
        )
        )[GetAllApiServiceViewModel::class.java]


        if(intent.hasExtra(ConstantClass.DebitAmount)){
            this.Amount = intent.getStringExtra(ConstantClass.DebitAmount).toString()
            hitApiForGetPaymentGatewayURL()
        }

    }


    private fun hitApiForGetPaymentGatewayURL() {

        val walletBalanceReq = PaymentGatewayReq(
            payerMobile ="8960288174",
            amount = Amount,
            registrationID = "AOP-554",
            payerName = "Annu Satija",
            callbackUrl = "https://example.com",
        )
        getAllApiServiceViewModel.getPaymentGatewayURL(walletBalanceReq).observe(this) { resource ->
            when (resource.apiStatus) {
                ApiStatus.SUCCESS -> {
                    resource.data?.let { users ->
                        users.body()?.let { response ->
                            if(response.status?.toLowerCase().equals("true")){
                                var urlHtml= response.preparePOSTForm
                                loadOnWebView(urlHtml!!)
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


    fun loadOnWebView(html: String) {
        Log.d("htmlresp", html)
        // 1. Extract the Action URL and the Data
        val actionRegex = """action\s*=\s*['"]([^'"]+)['"]""".toRegex(RegexOption.IGNORE_CASE)
        val url = actionRegex.find(html)?.groupValues?.get(1) ?: return

        // 2. Extract input names and values into a POST string
        // Format: key1=value1&key2=value2
        val inputRegex = """<input.*?name=['"]([^'"]+)['"].*?value=['"]([^'"]*)['"].*?>""".toRegex(RegexOption.IGNORE_CASE)
        val postData = inputRegex.findAll(html).map {
            "${it.groupValues[1]}=${java.net.URLEncoder.encode(it.groupValues[2], "UTF-8")}"
        }.joinToString("&")

        binding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            allowContentAccess = true
            allowFileAccess = true
            loadsImagesAutomatically = true
            // Payment gateways need these for redirects
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            userAgentString = "Mozilla/5.0 (Linux; Android 12; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        val cookieManager = android.webkit.CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(binding.webView, true)
        }


        binding.webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                ConstantClass.loading(this@PayemntGateWayPage)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Log this to see if the gateway is redirecting you
                android.util.Log.d("WebView", "Loaded: $url")

                ConstantClass.dialog.dismiss()

            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                ConstantClass.dialog.dismiss()
            }
        }

        // 3. Directly POST the data to the URL
        // This bypasses the "Submit" button logic entirely
        binding.webView.postUrl(url, postData.toByteArray())

    }

    override fun onBackPressed() {
        goBackAlert()
    }

    fun goBackAlert(){
        dialog = Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.paymentcancelalert)
        dialog.setCanceledOnTouchOutside(false)

        dialog.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT

            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }

        var imageview = dialog.findViewById<ImageView>(R.id.backgroundblurimage)
        var done = dialog.findViewById<TextView>(R.id.done)
        var cancel = dialog.findViewById<TextView>(R.id.cancel)


        blurBitmap = blurBitmap(this@PayemntGateWayPage,getScreenBitmap(binding.main))

        if(blurBitmap!=null){
            imageview.setImageBitmap(blurBitmap)
        }

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        done.setOnClickListener {
            super.onBackPressed()
            finish()
        }

        dialog.show()
    }

    fun getScreenBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun blurBitmap(context: Context, image: Bitmap): Bitmap {
        val rs = RenderScript.create(context)
        val input = android.renderscript.Allocation.createFromBitmap(rs, image)
        val output = android.renderscript.Allocation.createTyped(rs, input.type)

        val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        script.setRadius(20f)
        script.setInput(input)
        script.forEach(output)

        output.copyTo(image)
        rs.destroy()

        return image
    }


}