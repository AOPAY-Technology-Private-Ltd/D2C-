package com.example.twopayd2c.ui.view.fragment

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.twopayd2c.adapter.BannerAdapter
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.walleBalance.GetBalanceReq
import com.example.twopayd2c.data.model.travelmodel.walleBalance.GetBalanceRes
import com.example.twopayd2c.data.repository.GetAllAPIServiceRepository
import com.example.twopayd2c.data.viewModelFactory.GetAllApiServiceViewModelFactory
import com.example.twopayd2c.databinding.FragmentHomeBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.view.activity.travel.TravelBookingHomePage
import com.example.twopayd2c.ui.view.activity.NotificationPage
import com.example.twopayd2c.ui.view.activity.TransactionHistory
import com.example.twopayd2c.ui.view.activity.transactionhistory.TransactionHistoryPage
import com.example.twopayd2c.ui.view.activity.travel.TravelBookingHomePage.Companion.serviceName
import com.example.twopayd2c.ui.viewmodel.GetAllApiServiceViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.SharedPreference
import com.google.gson.Gson
import kotlin.jvm.java


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val sliderHandler = Handler(Looper.getMainLooper())
    private var isAmountVisible = false
    var  actualAmount : String = ""
    lateinit var dialog : Dialog
    private lateinit var getAllApiServiceViewModel: GetAllApiServiceViewModel
    lateinit var preference: SharedPreference


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Handle Menu Icon Click
        val menuIcon = view.findViewById<ImageView>(R.id.menu_icon)
        menuIcon.setOnClickListener {
            val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer)
            drawer.openDrawer(GravityCompat.START)
        }

        // 2. Handle Notification Click
        val bellIcon = view.findViewById<ImageView>(R.id.bell_icon)
        bellIcon.setOnClickListener {
            startActivity(Intent(requireContext(), com.example.twopayd2c.ui.view.activity.NotificationPage::class.java))
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        getAllApiServiceViewModel = ViewModelProvider(this, GetAllApiServiceViewModelFactory(GetAllAPIServiceRepository(ApiClient.apiAllInterface)))[GetAllApiServiceViewModel::class.java]

        initView()
        getWalletBalance()
        setOnClickListner()
        setupAmountToggle()
        setbanner()
        return binding.root
    }


    fun initView(){
        // IMPORTANT: Let the AppBar handle the top status bar space
        ViewCompat.setOnApplyWindowInsetsListener(binding.appBarDashBoard.appBar) { view, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(0, statusBar.top, 0, 0)
            insets
        }

        preference= SharedPreference(requireContext())

    }


    fun setOnClickListner(){

        binding.appBarDashBoard.viewmorelayout.setOnClickListener {
            openDialogForAllServices()
        }

        binding.appBarDashBoard.buslayout.setOnClickListener {
            serviceName= requireContext().resources.getString(R.string.bus)
            startActivity(Intent(requireContext(), TravelBookingHomePage::class.java))
        }

        binding.appBarDashBoard.flightlayout.setOnClickListener {
            serviceName= requireContext().resources.getString(R.string.flight)
            startActivity(Intent(requireContext(), TravelBookingHomePage::class.java))
        }

        binding.appBarDashBoard.transactionhistorylayout.setOnClickListener {
            startActivity(Intent(requireContext(), TransactionHistoryPage::class.java))
        }

    }


    fun setbanner(){
        // 1. Prepare your list of images
        val images = listOf(R.drawable.image1, R.drawable.image2, R.drawable.image3)

        // 2. Set the Adapter
        binding.appBarDashBoard.viewpager.adapter = BannerAdapter(images)

        // 3. Register the auto-scroll runnable
        binding.appBarDashBoard.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000) // 3 seconds delay
            }
        })
    }


    private val sliderRunnable = Runnable {
        val nextItem = (binding.appBarDashBoard.viewpager.currentItem + 1) % (binding.appBarDashBoard.viewpager.adapter?.itemCount ?: 1)
        binding.appBarDashBoard.viewpager.setCurrentItem(nextItem, true)
    }


    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }


    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }


    private fun setupAmountToggle() {
        binding.appBarDashBoard.ivToggleAmount.setOnClickListener {
            isAmountVisible = !isAmountVisible

            if (isAmountVisible) {
                binding.appBarDashBoard.ivToggleAmount.setImageResource(R.drawable.openeyes)
            } else {
                binding.appBarDashBoard.ivToggleAmount.setImageResource(R.drawable.closeeyes)
            }
            toggleAmountWithAnimation(isAmountVisible)
        }
    }


    private fun toggleAmountWithAnimation(show: Boolean) {
        binding.appBarDashBoard.balance.animate()
            .alpha(0f)
            .setDuration(100)
            .withEndAction {
                binding.appBarDashBoard.balance.text = if (show) actualAmount else "₹ ******"
                binding.appBarDashBoard.balance.animate().alpha(1f).duration = 100
            }
    }


    fun openDialogForAllServices(){
        dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.rechargebillmoreitemlayout)

        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        dialog.setCanceledOnTouchOutside(false)

        var cancel = dialog.findViewById<ImageView>(R.id.cancelimage)
        var cardview = dialog.findViewById<CardView>(R.id.cardview)

        cancel.setOnClickListener {
            animateDialogCardClose(cardview) {
                dialog.dismiss()
            }
        }

        dialog.show()
        animateDialogCard(cardview)
    }


    fun animateDialogCard(view: View) {
        // Final size (after layout)
        view.post {
            val finalWidth = view.width
            val finalHeight = view.height

            // Start smaller
            val startWidth = (finalWidth * 0.50).toInt()
            val startHeight = (finalHeight * 0.50).toInt()

            val params = view.layoutParams
            params.width = startWidth
            params.height = startHeight
            view.layoutParams = params

            view.scaleX = 0.50f
            view.scaleY = 0.50f
            view.alpha = 0f

            val widthAnimator = ValueAnimator.ofInt(startWidth, finalWidth)
            widthAnimator.addUpdateListener {
                params.width = it.animatedValue as Int
                view.layoutParams = params
            }

            val heightAnimator = ValueAnimator.ofInt(startHeight, finalHeight)
            heightAnimator.addUpdateListener {
                params.height = it.animatedValue as Int
                view.layoutParams = params
            }

            val animatorSet = AnimatorSet().apply {
                playTogether(widthAnimator, heightAnimator)
                duration = 550
                interpolator = DecelerateInterpolator()
            }

            animatorSet.start()

            view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(550)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }

    }


    fun animateDialogCardClose(view: View, onEnd: () -> Unit) {
        val params = view.layoutParams

        val startWidth = view.width
        val startHeight = view.height

        val endWidth = (startWidth * 0.50).toInt()
        val endHeight = (startHeight * 0.50).toInt()

        val widthAnimator = ValueAnimator.ofInt(startWidth, endWidth)
        widthAnimator.addUpdateListener {
            params.width = it.animatedValue as Int
            view.layoutParams = params
        }

        val heightAnimator = ValueAnimator.ofInt(startHeight, endHeight)
        heightAnimator.addUpdateListener {
            params.height = it.animatedValue as Int
            view.layoutParams = params
        }

        val animatorSet = AnimatorSet().apply {
            playTogether(widthAnimator, heightAnimator)
            duration = 500 // slightly faster than open
            interpolator = DecelerateInterpolator()
        }

        animatorSet.start()

        view.animate()
            .scaleX(0.50f)
            .scaleY(0.50f)
            .alpha(0f)
            .setDuration(500)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction {
                onEnd()
            }
            .start()
    }


    private fun getWalletBalance() {

            val walletBalanceReq = GetBalanceReq(
                parmUser =preference.getStringValue(ConstantClass.UserID,""),
                flag = "CreditBalance"
            )
            getAllApiServiceViewModel.getWalletBalance(walletBalanceReq).observe(requireActivity()) { resource ->
                    when (resource.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            resource.data?.let { users ->
                                users.body()?.let { response ->
                                    ConstantClass.dialog.dismiss()
                                    getAllWalletBalanceRes(response)
                                }
                            }
                        }

                        ApiStatus.ERROR -> {
                            ConstantClass.dialog.dismiss()
                        }

                        ApiStatus.LOADING -> {
                            if (isAdded && context != null)
                            {
                                ConstantClass.loading(requireContext())
                            }

                        }
                    }
        }
    }

    private fun getAllWalletBalanceRes(response: GetBalanceRes) {
        val data = Gson().toJson(response)

        if (response.isSuccess == true) {
            actualAmount = "₹" + response.data[0].result.toString()
            preference.setStringValue(ConstantClass.WalletAmount,response.data[0].result.toString())
            toggleAmountWithAnimation(isAmountVisible)
            Log.d("actualBalance", response.data[0].result.toString())
        } else {

        }


    }

}