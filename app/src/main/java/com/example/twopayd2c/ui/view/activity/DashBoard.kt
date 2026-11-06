package com.example.twopayd2c.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.twopayd2c.R
import com.example.twopayd2c.databinding.ActivityMainBinding
import com.example.twopayd2c.ui.view.activity.scanpay.ScanQRForPayPage
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.SharedPreference
import java.util.jar.Manifest

class DashBoard : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var navHost:NavHostFragment
    lateinit var preference : SharedPreference
    lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Keep this at the top

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Keep your existing logic for the Bottom Navigation / Keyboard
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val bottomPadding = Math.max(systemBars.bottom, imeInsets.bottom)

            // Apply bottom padding to the main container so the bottom nav isn't cut off
            view.setPadding(0, 0, 0, bottomPadding)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.navigationView) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val bottomPadding = Math.max(systemBars.bottom, imeInsets.bottom)

            // Apply bottom padding to the main container so the bottom nav isn't cut off
            view.setPadding(0, 0, 0, bottomPadding)
            insets
        }

        preference = SharedPreference(this)

        navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHost.navController
        navigateSafe(R.id.homeFragment)
        setOnClickListner()
        checkNavigationSignal()
        setNavigationViewData()

    }


    fun setNavigationViewData(){
        binding.nav.customerNumber.text=  preference.getStringValue(ConstantClass.CustomerMobileNumber,"")
        binding.nav.llLogout.setOnClickListener {
            OpenPopUpLogout()
        }
    }


    private fun checkNavigationSignal() {
        val target = intent.getStringExtra("NAVIGATION_TARGET")
        if (target == "HOME") {
            navigateSafe(R.id.homeFragment)
        }
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent) // Update the activity intent
        checkNavigationSignal()
    }


    @OptIn(UnstableApi::class)
    fun setOnClickListner(){

        binding.nav.drawableclose.setOnClickListener {
            binding.drawer.closeDrawers()
        }


        binding.footerlayoutcontainer.navHome.setOnClickListener {
            setFooterIconColorSelection()
            binding.footerlayoutcontainer.navHome.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_blue))
            navController.navigate(R.id.homeFragment)
        }


        binding.footerlayoutcontainer.navProfile.setOnClickListener {
            setFooterIconColorSelection()
            binding.footerlayoutcontainer.navProfile.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_blue))
            navController.navigate(R.id.profileFragment)
        }


        binding.footerlayoutcontainer.navHelp.setOnClickListener {
            setFooterIconColorSelection()
            binding.footerlayoutcontainer.navHelp.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_blue))
            /*navController.navigate(R.id.helpFragment)*/
            startActivity(Intent(this,HelpCenter::class.java))
        }


        binding.footerlayoutcontainer.navSetting.setOnClickListener {
            setFooterIconColorSelection()
            binding.footerlayoutcontainer.navSetting.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_blue))
            navController.navigate(R.id.settingFragment)

        }


        binding.fab.setOnClickListener {
            startActivity(Intent(this,ScanQRForPayPage::class.java))
        }




    }


    private fun navigateSafe(destination: Int) {
        navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController
        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setPopUpTo(navController.graph.startDestinationId,false)
            .build()

        navController.navigate(destination,null,navOptions)
        setFooterIconColorSelection()
        binding.footerlayoutcontainer.navHome.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_blue))

    }


    private fun setFooterIconColorSelection(){
        binding.footerlayoutcontainer.navHome.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.footer_unselected))
        binding.footerlayoutcontainer.navProfile.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.footer_unselected))
        binding.footerlayoutcontainer.navHelp.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.footer_unselected))
        binding.footerlayoutcontainer.navSetting.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.footer_unselected))
    }


    fun OpenPopUpLogout() {
        dialog = Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.signoutalert)
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

        val cancel = dialog.findViewById<TextView>(R.id.btnCancel)
        val done = dialog.findViewById<TextView>(R.id.btnLogout)
        var imageview = dialog.findViewById<ImageView>(R.id.imageview)

        done.setOnClickListener {
                preference.setBooleanValue(ConstantClass.Logged, false)
                val intent = Intent(this@DashBoard, LoginPage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
        }

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


}