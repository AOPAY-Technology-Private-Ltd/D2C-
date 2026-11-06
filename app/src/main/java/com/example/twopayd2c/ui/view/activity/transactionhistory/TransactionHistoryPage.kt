package com.example.twopayd2c.ui.view.activity.transactionhistory

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.FilterItem
import com.example.twopayd2c.data.model.merchant.apiServiceCharge.GetPayoutCommercialReq
import com.example.twopayd2c.data.model.transactionhistory.TransactionHistoryDataItem
import com.example.twopayd2c.data.model.transactionhistory.TransactionHistoryReq
import com.example.twopayd2c.data.repository.GetAllAPIServiceRepository
import com.example.twopayd2c.data.viewModelFactory.GetAllApiServiceViewModelFactory
import com.example.twopayd2c.databinding.ActivityTransactionHistoryPageBinding
import com.example.twopayd2c.databinding.AirlineslistitemlayotBinding
import com.example.twopayd2c.databinding.FilterItemLayoutBinding
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.ui.adapter.FilterAdapter
import com.example.twopayd2c.ui.adapter.TransactionHistoryListAdapter
import com.example.twopayd2c.ui.viewmodel.GetAllApiServiceViewModel
import com.example.twopayd2c.utils.ApiStatus
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.SharedPreference
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.internal.http2.Http2Reader
import java.util.logging.Handler

class TransactionHistoryPage : AppCompatActivity(), FilterAdapter.setOnItemCheckListner {

    lateinit var binding: ActivityTransactionHistoryPageBinding
    private lateinit var getAllApiServiceViewModel: GetAllApiServiceViewModel

    lateinit var preference : SharedPreference
    var historyDataList : MutableList<TransactionHistoryDataItem?>? = mutableListOf()
    var filterDataList : MutableList<TransactionHistoryDataItem?>? = mutableListOf()
    lateinit var transactionHistoryListAdapter: TransactionHistoryListAdapter

    var filterItem: MutableList<FilterItem> = mutableListOf()

    lateinit var adapter : FilterAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTransactionHistoryPageBinding.inflate(layoutInflater)
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


        getAllApiServiceViewModel = ViewModelProvider(this, GetAllApiServiceViewModelFactory(GetAllAPIServiceRepository(ApiClient.apiAllInterface)))[GetAllApiServiceViewModel::class.java]
        preference = SharedPreference(this)

        binding.alltext.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@TransactionHistoryPage, R.color.primary_blue)))

        hitApiForHistoryData()
        setOnClickListner()
        setFilterData()

    }


    fun setFilterData(){
        filterItem.clear()
        filterItem.add(FilterItem("Load",false))
        filterItem.add(FilterItem("Spend",false))
        filterItem.add(FilterItem("Refund",false))
        filterItem.add(FilterItem("Bonus",false))

        val adapter = FilterAdapter(this, filterItem,this)
        binding.showsfilters.adapter = adapter

    }


    fun setOnClickListner(){

        binding.spends.setOnClickListener {
            ConstantClass.loading(this)
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                binding.spendtxt.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@TransactionHistoryPage, R.color.primary_blue)))
                binding.alltext.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@TransactionHistoryPage, R.color.edtsubblack)))
                binding.filtericon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this@TransactionHistoryPage, R.color.edtsubblack))
                filterDataList?.clear()
                filterDataList = historyDataList?.filter { it?.ledgerType?.toLowerCase().equals("spend", ignoreCase = true) }?.toMutableList()
                setDatinView(filterDataList)
            }

        }

        binding.allTab.setOnClickListener {
            ConstantClass.loading(this)
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                binding.alltext.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@TransactionHistoryPage, R.color.primary_blue)))
                binding.spendtxt.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@TransactionHistoryPage, R.color.edtsubblack)))
                binding.filtericon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this@TransactionHistoryPage, R.color.edtsubblack))
                filterDataList?.clear()
                setDatinView(historyDataList)
            }
        }

        binding.backicon.setOnClickListener {
            finish()
        }

        binding.filters.setOnClickListener {
            openFiltersLayout()

        }

        binding.main.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Finger touched the screen
                    checkFilterVisibility()
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    // Finger is moving
                    checkFilterVisibility()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    // Finger lifted
                    checkFilterVisibility()
                    true
                }
                else -> false
            }
        }

        binding.whenfilteropenlayout.setOnTouchListener { view, event ->

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Finger touched the screen
                    checkFilterVisibility()
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    // Finger is moving
                    checkFilterVisibility()
                    true
                }

                MotionEvent.ACTION_UP -> {
                    // Finger lifted
                    checkFilterVisibility()
                    true
                }

                else -> false

            }

        }

        binding.searchEdttxt.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                var query = p0.toString()
                filterDataList = historyDataList!!.filter { it?.remark?.contains(query, ignoreCase = true) == true || it?.transferFromMsg?.contains(query, ignoreCase = true) == true || it?.refNo?.contains(query, ignoreCase = true) == true } as MutableList<TransactionHistoryDataItem?>
                setDatinView(filterDataList)

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

    }

    private fun hitApiForHistoryData() {
        val transactionHistoryReq = TransactionHistoryReq(
            agenttype = ConstantClass.customer,
            adminCode = ConstantClass.BOSMerchantCode,
            userid = preference.getStringValue(ConstantClass.UserID,"")
        )

        Log.d(TAG, "transactionHistoryReq: " + Gson().toJson(transactionHistoryReq))

        getAllApiServiceViewModel.getTransactionHistory(transactionHistoryReq).observe(this) { resource ->
                resource?.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data?.let { users ->
                                users.body()?.let { response ->
                                    Log.d("transactionHistoryResp", Gson().toJson(response))
                                    historyDataList= response.data
                                    setDatinView(historyDataList)
                                }
                            }
                        }

                        ApiStatus.ERROR -> {
                            ConstantClass.dialog.dismiss()
                        }

                        ApiStatus.LOADING -> {
                            ConstantClass.loading(this)
                        }
                    }
                }
            }

    }


    fun setDatinView(historyDataList : MutableList<TransactionHistoryDataItem?>?){
        ConstantClass.dialog.dismiss()
        if(!historyDataList.isNullOrEmpty()){
            binding.notfoundlayout.visibility= View.GONE
            binding.transactionlist.visibility=View.VISIBLE
            transactionHistoryListAdapter = TransactionHistoryListAdapter(this,historyDataList!!)
            binding.transactionlist.adapter = transactionHistoryListAdapter
        }
        else{
            binding.notfoundlayout.visibility= View.VISIBLE
            binding.transactionlist.visibility= View.GONE

        }

    }


    fun openFiltersLayout(){
        binding.filteroptions.visibility = View.VISIBLE
        binding.whenfilteropenlayout.visibility = View.VISIBLE
        val anim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.filteroptions.startAnimation(anim)
        binding.filteroptions.visibility = View.VISIBLE


    }


    fun checkFilterVisibility(){
        if(binding.filteroptions.visibility == View.VISIBLE){
            binding.filteroptions.visibility = View.GONE
            binding.whenfilteropenlayout.visibility = View.GONE
        }

    }



    override fun checkItemClick(selectedTypes: List<String>, isChecked: Boolean) {
        Log.d("itemName", "${selectedTypes}")

        val filteredList = historyDataList?.filter { item ->
            item?.ledgerType?.lowercase() in selectedTypes
        }?.toMutableList()

        if(selectedTypes.isEmpty()){
            binding.alltext.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@TransactionHistoryPage, R.color.primary_blue)))
            binding.spendtxt.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@TransactionHistoryPage, R.color.edtsubblack)))
            binding.filtericon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this@TransactionHistoryPage, R.color.edtsubblack))
            setDatinView(historyDataList)
        }
        else{
            binding.filtericon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this@TransactionHistoryPage, R.color.primary_blue))
            binding.spendtxt.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@TransactionHistoryPage, R.color.edtsubblack)))
            binding.alltext.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@TransactionHistoryPage, R.color.edtsubblack)))
            setDatinView(filteredList)
        }

    }


}