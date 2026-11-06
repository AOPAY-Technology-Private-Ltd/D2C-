package com.example.twopayd2c.ui.view.activity.travel.AllBusModule.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twopayd2c.databinding.IndividualFilterLayoutBinding
import com.example.twopayd2c.ui.adapter.BusIndividualFilterAdapter
import com.example.twopayd2c.ui.view.activity.travel.AllBusModule.BusSearchDetails.Companion.SearchBusesList
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.fragment.FlightFilterIndividualBottomSheet
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.AllBusList
import com.example.twopayd2c.utils.ConstantClass.Companion.BusListForFilter
import com.example.twopayd2c.utils.ConstantClass.Companion.busDepartureList
import com.example.twopayd2c.utils.ConstantClass.Companion.busOperatorNameList
import com.example.twopayd2c.utils.ConstantClass.Companion.busTypeList
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BusFilterIndividualBottomSheet : BottomSheetDialogFragment(),BusIndividualFilterAdapter.OnClickListener {

    private var onCloseListener: (() -> Unit)? = null

    lateinit var binding : IndividualFilterLayoutBinding
    lateinit var adapter : BusIndividualFilterAdapter

    private var selectedOperators = emptyList<String>()
    private var selectedBusType = emptyList<String>()
    private var selectedDeparture = emptyList<String>()


    companion object{
        var filterType:String = ""
        const val TAG = "BusSearchSheet"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = IndividualFilterLayoutBinding.inflate(inflater, container, false)
        if(SearchBusesList.isNullOrEmpty()){
            // Start fresh every time
            BusListForFilter = AllBusList.toMutableList()
        }else{
            BusListForFilter = SearchBusesList
        }
        setDataOnView()
        setOnClickListner()
        return binding.root
    }


    fun setOnClickListner(){

        binding.cross.setOnClickListener {
            filterType=""
            if(SearchBusesList.size>0) {

            }
            else {
                SearchBusesList.addAll(AllBusList)
            }
            dismiss()
        }

        binding.showbusseslayout.setOnClickListener {
            dismiss()
        }

    }


    private fun setDataOnView(){
        binding.filterType.text = filterType
        binding.showbusoperatorlist.layoutManager = LinearLayoutManager(requireContext())
        when{
            filterType == ConstantClass.BusType -> {adapter = BusIndividualFilterAdapter(requireContext(), busTypeList, BusIndividualFilterType.BUSTYPE,this)
                binding.chooseairlineslayout.visibility=View.GONE}

            filterType == ConstantClass.BusOperator ->{adapter = BusIndividualFilterAdapter(requireContext(), busOperatorNameList, BusIndividualFilterType.OPERATOR,this)
                binding.chooseairlineslayout.visibility=View.VISIBLE}

            filterType == ConstantClass.BusDeparture -> { adapter = BusIndividualFilterAdapter(requireContext(), busDepartureList, BusIndividualFilterType.DEPARTURE,this)
            binding.chooseairlineslayout.visibility=View.GONE}
        }
        binding.showbusoperatorlist.adapter = adapter
    }


    override fun setonclicklistner(selectedNames: List<String>, filterType: BusIndividualFilterType) {
        when (filterType) {
            BusIndividualFilterType.OPERATOR ->   {selectedOperators = selectedNames}
            BusIndividualFilterType.BUSTYPE ->    {selectedBusType = selectedNames }
            BusIndividualFilterType.DEPARTURE ->  {selectedDeparture = selectedNames }
        }
        applyFilters()
    }


    fun applyFilters() {

        // Always start from original list
        var filteredList = AllBusList.toMutableList()

        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        // 1️⃣ Bus Type Filter (AC / Non AC)
        if (selectedBusType.isNotEmpty()) {
            filteredList = filteredList.filter { bus ->
                when {
                    selectedBusType.contains(ConstantClass.nonac) -> bus.ac == false
                    selectedBusType.contains(ConstantClass.ac) -> bus.ac == true
                    else -> true
                }
            }.toMutableList()
        }

        // 2️⃣ Departure Filter
        if (selectedDeparture.isNotEmpty()) {

            filteredList = filteredList.filter { bus ->
                val time = bus.departureTime

                if (time.isNullOrEmpty()) return@filter false

                runCatching {
                    val date = dateFormat.parse(time)
                    val calendar = Calendar.getInstance()
                    calendar.time = date!!
                    val hour = calendar.get(Calendar.HOUR_OF_DAY)

                    when {
                        selectedDeparture.contains(ConstantClass.before6am) -> hour < 6
                        selectedDeparture.contains(ConstantClass.after6am) -> hour in 6..11
                        selectedDeparture.contains(ConstantClass.after12pm) -> hour in 12..17
                        selectedDeparture.contains(ConstantClass.after6pm) -> hour >= 18
                        else -> true
                    }
                }.getOrDefault(false)
            }.toMutableList()
        }

        // 3️⃣ Operator Filter
        if (selectedOperators.isNotEmpty()) {
            filteredList = filteredList.filter { bus ->
                bus.operatorName != null && selectedOperators.contains(bus.operatorName)
            }.toMutableList()
        }

        // Final Update
        BusListForFilter = filteredList
        SearchBusesList.clear()
        SearchBusesList.addAll(filteredList)
    }


    enum class BusIndividualFilterType {
        OPERATOR,
        BUSTYPE,
        DEPARTURE
    }


    fun setOnCloseListener(listener: () -> Unit) {
        onCloseListener = listener
    }


    override fun onDismiss(dialog: android.content.DialogInterface) {
        super.onDismiss(dialog)
        onCloseListener?.invoke()
    }


}