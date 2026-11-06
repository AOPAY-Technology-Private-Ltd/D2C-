package com.example.twopayd2c.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twopayd2c.R
import com.example.twopayd2c.activity.LoginPage
import com.example.twopayd2c.databinding.ActivityOnBoardingPageBinding
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.SharedPreference

class OnBoardingPage : AppCompatActivity() {
    lateinit var binding: ActivityOnBoardingPageBinding
    lateinit var preference: SharedPreference
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        preference = SharedPreference(this)
        binding.backarrow.visibility=View.GONE
        changeImageWithAnimation(R.drawable.one)
        animateText(binding.titletxt,binding.subtext)
        setOnClickListner()


    }

    fun setOnClickListner(){
        binding.nextbuttonlayout.setOnClickListener {
            fornextbutton()
        }

        binding.backarrow.setOnClickListener {
            forBackbutton()
        }

        binding.skipbutton.setOnClickListener {
            startActivity(Intent(this@OnBoardingPage, LoginPage::class.java))
        }
    }


    fun fornextbutton(){
        if(count==0){
            binding.secondprogress.setCardBackgroundColor(resources.getColor(R.color.primary_blue))
            binding.thirdprogress.setCardBackgroundColor(resources.getColor(R.color.cardcolor))
            binding.firstprogress.setCardBackgroundColor(resources.getColor(R.color.footer_unselected))
            binding.titletxt.text = getString(R.string.onboardingtitle2)
            binding.subtext.text = getString(R.string.onboardingmsg2)
            changeImageWithAnimation( R.drawable.two)
            animateText(binding.titletxt,binding.subtext)
            binding.nexttext.setText(resources.getString(R.string.next))
            binding.backarrow.visibility=View.VISIBLE
            count++
            return
        }
        if(count==1){
            binding.thirdprogress.setCardBackgroundColor(resources.getColor(R.color.primary_blue))
            binding.firstprogress.setCardBackgroundColor(resources.getColor(R.color.cardcolor))
            binding.secondprogress.setCardBackgroundColor(resources.getColor(R.color.footer_unselected))
            changeImageWithAnimation( R.drawable.three)
            binding.titletxt.text = getString(R.string.onboardingtitle3)
            binding.subtext.text = getString(R.string.onboardingmsg3)
            animateText(binding.titletxt,binding.subtext)
            binding.nexttext.setText(resources.getString(R.string.getstarted))
            binding.backarrow.visibility=View.VISIBLE
            count++
            return
        }

        if(count==2 && binding.nexttext.text==resources.getString(R.string.getstarted)){
            preference.setBooleanValue(ConstantClass.OnBoardingPage,true)
            startActivity(Intent(this@OnBoardingPage, LoginPage::class.java))
            finish()
        }

    }

    fun forBackbutton(){

        if(count==2){
            binding.secondprogress.setCardBackgroundColor(resources.getColor(R.color.primary_blue))
            binding.thirdprogress.setCardBackgroundColor(resources.getColor(R.color.cardcolor))
            binding.firstprogress.setCardBackgroundColor(resources.getColor(R.color.footer_unselected))
            changeImageWithAnimation( R.drawable.two)
            binding.titletxt.text = getString(R.string.onboardingtitle2)
            binding.subtext.text = getString(R.string.onboardingmsg2)
            animateText(binding.titletxt,binding.subtext)
            binding.nexttext.setText(resources.getString(R.string.next))
            binding.backarrow.visibility=View.VISIBLE
            count--
            return

        }

        if(count==1){
            binding.firstprogress.setCardBackgroundColor(resources.getColor(R.color.primary_blue))
            binding.secondprogress.setCardBackgroundColor(resources.getColor(R.color.cardcolor))
            binding.thirdprogress.setCardBackgroundColor(resources.getColor(R.color.footer_unselected))
            changeImageWithAnimation( R.drawable.one)
            binding.titletxt.text = getString(R.string.onboardingtitle1)
            binding.subtext.text = getString(R.string.onboardingmsg1)
            animateText(binding.titletxt,binding.subtext)
            binding.nexttext.setText(resources.getString(R.string.next))
            binding.backarrow.visibility=View.GONE
            count--
            return
        }

    }

    fun animateText(title: TextView, subtitle: TextView) {

        // Reset state (important for ViewPager reuse)
        title.alpha = 0f
        title.translationY = 80f

        subtitle.alpha = 0f
        subtitle.translationY = 80f

        // Title Animation
        title.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .setStartDelay(100)
            .start()

        // Subtitle Animation (slight delay)
        subtitle.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .setStartDelay(300)
            .start()
    }


    private fun changeImageWithAnimation(@DrawableRes newImage: Int) {

        binding.imageview.animate()
            .alpha(0f)
            .scaleX(0.9f)
            .scaleY(0.9f)
            .setDuration(300)
            .withEndAction {

                binding.imageview.setImageResource(newImage)

                binding.imageview.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(300)
                    .start()
            }
            .start()
    }



}