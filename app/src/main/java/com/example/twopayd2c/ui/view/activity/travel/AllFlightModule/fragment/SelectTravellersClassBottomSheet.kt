package com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.fragment

import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu.OnDismissListener
import androidx.fragment.app.FragmentActivity
import com.example.twopayd2c.R
import com.example.twopayd2c.data.model.travelmodel.flight.FlightClassSelectionModel
import com.example.twopayd2c.databinding.TravellersclassItemBottomsheetBinding
import com.example.twopayd2c.ui.adapter.FlightClassFilterAdapter
import com.example.twopayd2c.ui.view.activity.travel.fragment.FlightSearchHomePageFragment
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.ConstantClass.Companion.adultCount
import com.example.twopayd2c.utils.ConstantClass.Companion.childCount
import com.example.twopayd2c.utils.ConstantClass.Companion.className
import com.example.twopayd2c.utils.ConstantClass.Companion.classNumber
import com.example.twopayd2c.utils.ConstantClass.Companion.classnameForPrint
import com.example.twopayd2c.utils.ConstantClass.Companion.infantCount
import com.example.twopayd2c.utils.ConstantClass.Companion.totalCount
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SelectTravellersClassBottomSheet:BottomSheetDialogFragment() , FlightClassFilterAdapter.OnClickListener {
    private lateinit var binding : TravellersclassItemBottomsheetBinding
    var adultcount: Int = 1
    var childcount: Int = 0
    var infantcount: Int = 0

    var classname : String = ""
    var classnameforprint : String = ""
    var classnumber: String = ""

    var classNameList : MutableList<FlightClassSelectionModel> = arrayListOf()
    lateinit var classSelectionAdapter : FlightClassFilterAdapter
    var itemForSelectedClass: FlightClassSelectionModel? = null

    companion object {
        const val TAG = "SelectTravellersClassBottomSheet"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = TravellersclassItemBottomsheetBinding.inflate(inflater, container, false)
        addDataForClassName()
        setonclicklistner()
        setdataontext()
        return binding.root
    }


    private fun addDataForClassName(){
        classNameList.clear()
        classNameList.add(FlightClassSelectionModel(ConstantClass.EconomyClass,"0",ConstantClass.EconomyClass,false))
        classNameList.add(FlightClassSelectionModel(ConstantClass.Premimumeconomy,"3",ConstantClass.Premimumeconomy,false))
        classNameList.add(FlightClassSelectionModel(ConstantClass.Business,"1",ConstantClass.Business,false))
        classNameList.add(FlightClassSelectionModel(ConstantClass.Firstclass,"2","First",false))
        classSelectionAdapter = FlightClassFilterAdapter(requireContext(),classNameList,this)
        binding.showflightclasslist.adapter = classSelectionAdapter
        Log.d("Classname",className)
        classSelectionAdapter.upDateCheck(className)
    }


    private fun setdataontext(){
        binding.numberofadult.text=adultCount.toString().trim()
        binding.numberofchild.text=childCount.toString().trim()
        binding.numberofinfanttxt.text=infantCount.toString().trim()
        totalCount = (adultCount+childCount+infantCount).toString()
        childcount = childCount
        infantcount = infantCount
        adultcount = adultCount
    }



    private fun setonclicklistner(){

        binding.cross.setOnClickListener {
            dialog!!.dismiss()
        }

        binding.additionAdult.setOnClickListener {
            adultcount++
            binding.numberofadult.text=adultcount.toString().trim()
        }

        binding.minimizeAdult.setOnClickListener {
            if(binding.numberofadult.text.toString().toInt()>1 &&  binding.numberofinfanttxt.text.toString().toInt()!=binding.numberofadult.text.toString().toInt()) {
                adultcount--
                binding.numberofadult.text = adultcount.toString().trim()
            }else{
                Toast.makeText(requireContext(),"Number of Infants can't  exceed number of adults",Toast.LENGTH_SHORT).show()
            }
        }


        binding.additionChild.setOnClickListener {
            childcount++
            binding.numberofchild.text = childcount.toString().trim()
        }


        binding.minimizeChild.setOnClickListener {
            if( binding.numberofchild.text.toString().toInt()>0) {
                childcount--
                binding.numberofchild.text = childcount.toString().trim()
            }
        }

        binding.additionInfanttxt.setOnClickListener {
            if(infantcount<adultcount){
                infantcount++
                binding.numberofinfanttxt.text=infantcount.toString().trim()
            }
            else{
                Toast.makeText(requireContext(),"Number of Infants can't  exceed number of adults",Toast.LENGTH_SHORT).show()
            }

        }

        binding.minimizeInfanttxt.setOnClickListener {
            if( binding.numberofinfanttxt.text.toString().toInt()>0) {
                infantcount--
                binding.numberofinfanttxt.text = infantcount.toString().trim()
            }
        }


        binding.donelayout.setOnClickListener {
            adultCount =  binding.numberofadult.text.toString().toInt()
            childCount =  binding.numberofchild.text.toString().toInt()
            infantCount = binding.numberofinfanttxt.text.toString().toInt()
            itemForSelectedClass?.let {
                ConstantClass.className = it.className
                classnameforprint = it.classnameforprint
            }
            Log.d("Classname",className)
            dismiss()
        }

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // used to show the bottom sheet dialog
        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                    sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED

                val layoutParams = sheet.layoutParams
                val windowHeight = Resources.getSystem().displayMetrics.heightPixels
                layoutParams.height = windowHeight
                sheet.layoutParams = layoutParams
            }
        }
        return super.onCreateDialog(savedInstanceState)

    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        parentFragmentManager.setFragmentResult(TAG,Bundle())
    }


    override fun setonclicklistnerFlightClass(item: FlightClassSelectionModel) {
        itemForSelectedClass = item
       /* classname = ""
        classname= item.className
        classnameforprint = item.classnameforprint
        classnumber = item.classnumber

        classnameForPrint = classnameforprint
        classNumber = classnumber*/

    }


}