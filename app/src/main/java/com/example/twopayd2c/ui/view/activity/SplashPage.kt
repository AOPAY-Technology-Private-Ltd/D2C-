package com.example.twopayd2c.activity

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twopayd2c.R
import com.example.twopayd2c.databinding.ActivitySplashPageBinding
import com.example.twopayd2c.ui.view.activity.OnBoardingPage
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.isInternetAvailable
import com.example.twopayd2c.utils.SharedPreference


class SplashPage : AppCompatActivity() {
    lateinit var binding : ActivitySplashPageBinding
    lateinit var preference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        preference = SharedPreference(this)
        binding.splashThumbnail.visibility=View.VISIBLE
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.splash}")
        setVideoForPlay(videoUri)
    }


    fun setVideoForPlay(uri: Uri){

        binding.videoView.setVideoURI(uri)

        binding.videoView.setOnPreparedListener { mp ->

            binding.videoView.post {
                Log.d("Splash", "Prepared")
                mp.isLooping = false

                binding.splashThumbnail.visibility = View.GONE

                val videoWidth = mp.videoWidth.toFloat()
                val videoHeight = mp.videoHeight.toFloat()

                val viewWidth = binding.videoView.width.toFloat()
                val viewHeight = binding.videoView.height.toFloat()

                val videoRatio = videoWidth / videoHeight
                val viewRatio = viewWidth / viewHeight

                if (videoRatio > viewRatio) {
                    binding.videoView.scaleY = videoRatio / viewRatio
                } else {
                    binding.videoView.scaleX = viewRatio / videoRatio
                }
                textFadeInAnimation()
            }
        }


        binding.videoView.setOnCompletionListener {
            if(isInternetAvailable(this)){
                navigateToNextScreen()
            }else{
                Toast.makeText(this,"No internet connection",Toast.LENGTH_SHORT).show()
            }

        }

        binding.videoView.setOnErrorListener { _, what, extra ->
            Log.e("Splash", "Error: $what $extra")
            navigateToNextScreen()
            true
        }

        binding.videoView.start()
    }



    private fun navigateToNextScreen() {
        var onBoarding = preference.getBoolanValue(ConstantClass.OnBoardingPage,false)
        var logged = preference.getBoolanValue(ConstantClass.Logged,false)

        if(onBoarding){
            if(logged){
                var intent =  Intent(this@SplashPage, DashBoard::class.java)
                startActivity(intent)
            }else{
                var intent =  Intent(this@SplashPage, LoginPage::class.java)
                startActivity(intent)
            }
        }
        else
        {
            var intent =  Intent(this@SplashPage, com.example.twopayd2c.ui.view.activity.OnBoardingPage::class.java)
            startActivity(intent)
        }
        finish()
    }

    fun textFadeInAnimation(){
        binding.bottomtextlayout.animate()
            .alpha(1f)
            .setDuration(800)
            .setStartDelay(3000)   // 3 seconds delay
            .start()



    }


}