package com.example.twopayd2c.ui.view.activity.scanpay

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Rect
import android.hardware.camera2.CameraManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twopayd2c.R
import com.example.twopayd2c.databinding.ActivityScanQrforPayPageBinding
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import org.json.JSONException
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ScanQRForPayPage : AppCompatActivity() {
    lateinit var binding: ActivityScanQrforPayPageBinding
    private lateinit var camera: Camera
    private var isFlashOn = false
    private var isScanned = false
    private lateinit var cameraExecutor: ExecutorService
    private val PICK_IMAGE_REQUEST = 1
    private var isExpanded = false

    private val vibrator: Vibrator by lazy {
        this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }


    companion object {
        private const val TAG = "QrCodeReaderFragment"
        private const val VIBRATE_DURATION = 200L
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScanQrforPayPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cameraExecutor = Executors.newSingleThreadExecutor()

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions.launch(REQUIRED_PERMISSIONS)
        }

        init()
        // Start scanning
        startCamera()
        setOnClickListner()
        setupBottomView()

    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            flashLight()
        }
    }

    private val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
//                finish()
            }
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri: Uri? = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                fetchBarcodeFromImage(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchBarcodeFromImage(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val scanner = BarcodeScanning.getClient()

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isEmpty()) {
                    Toast.makeText(this, "Unable to recognise a valid code from uploaded image", Toast.LENGTH_LONG).show()
                    return@addOnSuccessListener
                }

                for (barcode in barcodes) {
                    val value = barcode.rawValue ?: continue

                    if (isValidUpiQr(value)) {
                        processPaymentData(value)
                    } else {
                        Toast.makeText(this, "Invalid Payment QR", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to process image", Toast.LENGTH_SHORT).show()
            }
    }


    private fun isValidUpiQr(data: String): Boolean {
        return try {
            val uri = Uri.parse(data)

            uri.scheme == "upi" &&
                    uri.host == "pay" &&
                    !uri.getQueryParameter("pa").isNullOrEmpty()
        } catch (e: Exception) {
            false
        }
    }



    fun init(){


    }


    fun setOnClickListner(){

        // Flash toggle
        binding.btnFlash.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
            }else {
                flashLight()
            }
        }

        // Close button
        binding.btnClose.setOnClickListener {
            finish()
        }

        binding.uploadImage.setOnClickListener {
            openGallery()
        }

    }



    private fun setupBottomView() {

        binding.bottomview.post {
            val height =     binding.bottomview.height

            // Initially show only small part
            binding.bottomview.translationY = height * 0.6f
        }

        binding.bottomview.setOnTouchListener(object : View.OnTouchListener {
            var startY = 0f

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startY = event.rawY
                    }

                    MotionEvent.ACTION_UP -> {
                        val endY = event.rawY

                        if (startY > endY) {
                            // Swipe UP → Expand
                            expandBottomView( binding.bottomview)
                        } else {
                            // Swipe DOWN → Collapse
                            collapseBottomView( binding.bottomview)
                        }
                    }
                }
                return true
            }
        })
    }


    private fun expandBottomView(view: View) {
        view.animate()
            .translationY(0f)
            .setDuration(300)
            .start()

        isExpanded = true
    }


    private fun collapseBottomView(view: View) {
        view.animate()
            .translationY(view.height * 0.6f)
            .setDuration(300)
            .start()

        isExpanded = false
    }


    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun flashLight() {
        isFlashOn = if (isFlashOn){
            setFlashLight(false)
            false
        } else {
            setFlashLight(true)
            true
        }
    }


    @OptIn(ExperimentalGetImage::class)
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }

            val imageAnalyzer = ImageAnalysis.Builder().build().also {
                it.setAnalyzer(cameraExecutor, BarcodeAnalyzer { qrData ->
                    runOnUiThread {
                        if (!isScanned) {
                            isScanned = true
                            vibrateOnScan()
//                            binding.scannedData.text = qrData
                            processPaymentData(qrData)
                        }
                    }
                })
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)

                camera.cameraControl.enableTorch(isFlashOn)
            } catch (exc: Exception) {
                Toast.makeText(
                    this,
                    "Error starting camera: ${exc.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }, ContextCompat.getMainExecutor(this))
    }


    private fun vibrateOnScan() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(VIBRATE_DURATION, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                vibrator.vibrate(VIBRATE_DURATION)
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun setFlashLight(state: Boolean) {
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        try {
            camera.cameraControl.enableTorch(state)

            if (state) {
                binding.btnFlash.setImageResource(R.drawable.flashlight_off)
            } else {
                binding.btnFlash.setImageResource(R.drawable.flashlight_on)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun processPaymentData(qrData: String) {
        try {
            val uri = Uri.parse(qrData)

            // Extract UPI parameters from the QR code
            val upiId = uri.getQueryParameter("pa") ?: "N/A"
            val payeeName = uri.getQueryParameter("pn") ?: "N/A"
            val merchantCode = uri.getQueryParameter("mc") ?: "N/A"
            val bankingName = uri.getQueryParameter("pn") ?: ""
            val transactionId = uri.getQueryParameter("tid") ?: "N/A"
            val referenceId = uri.getQueryParameter("tr") ?: "N/A"
            val transactionNote = uri.getQueryParameter("tn") ?: "N/A"
            val amount = uri.getQueryParameter("am") ?: ""
            val currencyCode = uri.getQueryParameter("cu") ?: "N/A"

            // Prepare Intent to start DisplayDetailsActivity with extracted details
            val intent = Intent(this, DisplayDetailsActivity::class.java).apply {
                putExtra("upiId", upiId)
                putExtra("payeeName", payeeName)
                putExtra("bankingName", bankingName)
                putExtra("merchantCode", merchantCode)
                putExtra("transactionId", transactionId)
                putExtra("referenceId", referenceId)
                putExtra("transactionNote", transactionNote)
                putExtra("amount", amount)
                putExtra("currencyCode", currencyCode)
            }
            startActivity(intent)
            finish()

        } catch (e: JSONException) {
            Toast.makeText(this, "Invalid QR code data", Toast.LENGTH_SHORT).show()
        }

    }


    private class BarcodeAnalyzer(private val onQrCodeDetected: (qrData: String) -> Unit) :
        ImageAnalysis.Analyzer {
        @OptIn(ExperimentalGetImage::class)
        override fun analyze(imageProxy: ImageProxy) {
            val mediaImage = imageProxy.image ?: return
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient()
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        barcode.rawValue?.let { onQrCodeDetected(it) }
                    }
                }
                .addOnFailureListener {
                    // Handle failure
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }


}