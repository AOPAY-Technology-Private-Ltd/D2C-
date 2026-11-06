package com.example.twopayd2c.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList.AirTicketListResp
import com.example.twopayd2c.data.model.recharge.payout.PayoutAmountReq
import com.example.twopayd2c.data.model.recharge.payout.PayoutAmountRes
import com.example.twopayd2c.data.model.recharge.payout.payoutStatus.PayoutStatusReq
import com.example.twopayd2c.data.model.travelmodel.flight.GetAirTicketListReq
import com.example.twopayd2c.data.repository.PayoutRepository
import com.example.twopayd2c.network.ApiClient
import com.example.twopayd2c.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException


class PayoutViewModel constructor(private val repository: PayoutRepository) : ViewModel() {

    fun sendAmount(req: PayoutAmountReq): LiveData<Resource<Response<PayoutAmountRes>>> {
        val liveData = MutableLiveData<Resource<Response<PayoutAmountRes>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.sendAmount(req)
                }

                if (response?.isSuccessful == true) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error("Server error"))
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(Resource.error("Request timeout"))

            } catch (e: IOException) {
                liveData.postValue(Resource.error("No internet"))

            } catch (e: CancellationException) {
                throw e // ✅ VERY IMPORTANT

            } catch (e: Exception) {
                liveData.postValue(Resource.error("Something went wrong"))
            }
        }

        return liveData
    }


   /* fun getPayoutStatus(req: PayoutStatusReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(ApiResponse.success(data = repository.getPayoutStatus(req)))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getPayoutAmountTransaction(req: PayoutAppReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {

            val result = repository.getPayoutAmountTransaction(req)
            if (result.body() != null) {
                if (result.body()?.data != null) {
                    emit(ApiResponse.success(data = result))
                } else {
                    emit(
                        ApiResponse.error(
                            data = null,
                            message = result?.body()?.message ?: "Error Occurred!"
                        )
                    )
                }
            } else {
                emit(
                    ApiResponse.error(
                        data = null,
                        message = result.message() ?: "Error Occurred!"
                    )
                )
            }
//            emit(ApiResponse.success(data = repository.getPayoutAmountTransaction(req)))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun sendAllPayoutAmount(req: AOPPayOutReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {

            val result = repository.sendAllPayoutAmount(req)
            if (result?.body() != null) {
                if (result.body()?.initiateAuthGenericFundTransferAPIResp?.metaData != null) {
                    emit(ApiResponse.success(data = result))
                } else {
                    emit(
                        ApiResponse.error(
                            data = null,
                            message = result?.body()?.message ?: "Error Occurred!"
                        )
                    )
                }
            } else {
                emit(
                    ApiResponse.error(
                        data = null,
                        message = result?.message() ?: "Error Occurred!"
                    )
                )
            }
//            emit(ApiResponse.success(data = repository.getPayoutAmountTransaction(req)))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }*/

}