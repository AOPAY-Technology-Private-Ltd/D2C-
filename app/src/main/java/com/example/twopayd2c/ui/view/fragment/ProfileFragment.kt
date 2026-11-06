package com.example.twopayd2c.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.twopayd2c.activity.AdditionalDetailsPage
import com.example.twopayd2c.activity.FinancialDetailsPage
import com.example.twopayd2c.activity.PersonalDetailsPage
import com.example.twopayd2c.databinding.FragmentProfileBinding
import com.example.twopayd2c.utils.ConstantClass
import com.example.twopayd2c.utils.SharedPreference


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    lateinit var preference : SharedPreference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        preference = SharedPreference(requireContext())
        setclickListner()
        setview()
        return binding.root
    }

    fun setview(){
        binding.mobnumber.text =  preference.getStringValue(ConstantClass.CustomerMobileNumber,"")
    }

    fun setclickListner(){

        binding.personalcard.setOnClickListener {
            startActivity(Intent(requireContext(), PersonalDetailsPage::class.java))
        }

        binding.financialdetails.setOnClickListener {
            startActivity(Intent(requireContext(), FinancialDetailsPage::class.java))
        }

        binding.additionaldetails.setOnClickListener {
            startActivity(Intent(requireContext(), AdditionalDetailsPage::class.java))
        }

    }

}