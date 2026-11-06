package com.example.twopayd2c.data.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.twopayd2c.data.repository.PayoutRepository
import com.example.twopayd2c.ui.viewmodel.PayoutViewModel


class PayoutViewModelFactory constructor(private val repository: PayoutRepository):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PayoutViewModel::class.java)) {
            PayoutViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}