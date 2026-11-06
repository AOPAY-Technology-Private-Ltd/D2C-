package com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.twopayd2c.databinding.ContactmobileItemlayoutBinding
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.AddDetailsPassangerActivity
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.SharedPreference

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

class AddContactAndMobileBottomSheet:BottomSheetDialogFragment() {
    private lateinit var binding : ContactmobileItemlayoutBinding
    lateinit var preference : SharedPreference

    companion object {
        const val TAG = "AddContactBottomSheet"
        var contactNumber :String=""
        var emailid : String=""

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ContactmobileItemlayoutBinding.inflate(inflater, container, false)
        preference= SharedPreference(requireContext())
        binding.mobTxt.setText(preference.getStringValue(ConstantClass.ContactNumber,""))
        binding.mailId.setText(preference.getStringValue(ConstantClass.EmailID,""))
        setonclicklistner()
        return binding.root
    }



    private fun setonclicklistner(){
        binding.cross.setOnClickListener {
            dialog!!.dismiss()
        }


        binding.confirmbutton.setOnClickListener {

            if (binding.mobTxt.text.isEmpty() || binding.mobTxt.text.length != 10 || !binding.mobTxt.text.matches(Regex("[0-9]{10}"))) {
                binding.mobTxt.error = "Enter a valid 10-digit mobile number"
            }

            if (binding.mailId.text.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(binding.mailId.text).matches()) {
                binding.mailId.error = "Enter a valid email address"
            }

            contactNumber= binding.mobTxt.text.toString()
            emailid= binding.mailId.text.toString()
            preference.setStringValue(ConstantClass.EmailID,emailid)
            preference.setStringValue(ConstantClass.ContactNumber,contactNumber)
            (context as AddDetailsPassangerActivity).setDataForContactDetails()
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
        //(activity as? FlightMainActivity)?.setData()

       /* if(context is FlightMainActivity){
            (context as? FlightMainActivity)?.setData()
        }
        else {*/
            //(scanForActivity(context)?.supportFragmentManager?.findFragmentByTag("FlightMainFragment") as? FlightMainFragment)?.setData()
       // }

    }



}