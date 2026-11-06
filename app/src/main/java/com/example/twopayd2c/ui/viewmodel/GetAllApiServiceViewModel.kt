package com.example.twopayd2c.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.twopayd2c.data.model.travelmodel.transferAMountToAgent.TransferAmountToAgentsReq
import com.example.twopayd2c.data.model.travelmodel.transferAMountToAgent.TransferAmountToAgentsRes
import com.example.twopayd2c.data.model.travelmodel.travel.flight.AirTicketBookingRequest
import com.example.twopayd2c.data.model.travelmodel.travel.flight.AirTicketBookingResponse
import com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList.AirTicketListResp
import com.example.twopayd2c.data.model.travelmodel.walletBalance.merchantBal.GetMerchantBalanceReq
import com.example.twopayd2c.data.model.travelmodel.walletBalance.merchantBal.GetMerchantBalanceRes
import com.example.twopayd2c.data.model.merchant.apiServiceCharge.GetPayoutCommercialReq
import com.example.twopayd2c.data.model.merchant.apiServiceCharge.GetPayoutCommercialRes
import com.example.twopayd2c.data.model.transactionhistory.TransactionHistoryReq
import com.example.twopayd2c.data.model.transactionhistory.TransactionHistoryResp
import com.example.twopayd2c.data.model.travel.flight.FlightRequeryReq
import com.example.twopayd2c.data.model.travel.flight.FlightRequeryResponse
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusPassangerDetailsRes
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusPassengerDetailsReq
import com.example.twopayd2c.data.model.travelmodel.bus.city.BusCityListReq
import com.example.twopayd2c.data.model.travelmodel.bus.city.BusCityListRes
import com.example.twopayd2c.data.model.travelmodel.bus.ticketCancellation.CheckActiveStatusReq
import com.example.twopayd2c.data.model.travelmodel.bus.ticketCancellation.CheckActiveStatusResp
import com.example.twopayd2c.data.model.travelmodel.flight.AirCommissionReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirCommissionResp
import com.example.twopayd2c.data.model.travelmodel.flight.AirTicketBookingResponseRequest
import com.example.twopayd2c.data.model.travelmodel.flight.GetAirTicketListReq
import com.example.twopayd2c.data.model.travelmodel.paymentgateway.PaymentGatewayReq
import com.example.twopayd2c.data.model.travelmodel.paymentgateway.PaymentGatewayResp
import com.example.twopayd2c.data.model.travelmodel.walleBalance.GetBalanceReq
import com.example.twopayd2c.data.model.travelmodel.walleBalance.GetBalanceRes
import com.example.twopayd2c.data.repository.GetAllAPIServiceRepository
import com.example.twopayd2c.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

class GetAllApiServiceViewModel (private val repository: GetAllAPIServiceRepository) : ViewModel() {


    fun getWalletBalance(req: GetBalanceReq): LiveData<Resource<Response<GetBalanceRes>>> {

        val liveData = MutableLiveData<Resource<Response<GetBalanceRes>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.getWalletBalance(req)
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


    fun getPaymentGatewayURL(req: PaymentGatewayReq): LiveData<Resource<Response<PaymentGatewayResp>>> {

        val liveData = MutableLiveData<Resource<Response<PaymentGatewayResp>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.getPaymentGatewayURL(req)
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


    fun getFlightCommissionRequest(req: AirCommissionReq): LiveData<Resource<Response<AirCommissionResp>>> {

        val liveData = MutableLiveData<Resource<Response<AirCommissionResp>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.getFlightCommissionReq(req)
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


    fun getAllMerchantBalance(req: GetMerchantBalanceReq): LiveData<Resource<Response<GetMerchantBalanceRes>>> {

        val liveData = MutableLiveData<Resource<Response<GetMerchantBalanceRes>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.getAllMerchantBalance(req)
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

    fun getPassangerDetailsRequest(req: BusPassengerDetailsReq): LiveData<Resource<Response<BusPassangerDetailsRes>>> {

        val liveData = MutableLiveData<Resource<Response<BusPassangerDetailsRes>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.getPassangerDetails(req)
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

    fun GetRetailerActiveReq(req: CheckActiveStatusReq): LiveData<Resource<Response<CheckActiveStatusResp>>> {

        val liveData = MutableLiveData<Resource<Response<CheckActiveStatusResp>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.GetRetailerActiveReq(req)
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


    // for air requery request

    fun getAirRequeryRequest(req: FlightRequeryReq): LiveData<Resource<Response<FlightRequeryResponse>>> {

        val liveData = MutableLiveData<Resource<Response<FlightRequeryResponse>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.getAirRequeryRequest(req)
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


    fun uploadAirBookingTicketRequest(req: AirTicketBookingRequest): LiveData<Resource<Response<AirTicketBookingResponse>>> {

        val liveData = MutableLiveData<Resource<Response<AirTicketBookingResponse>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.uploadTicketBookingRequest(req)
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


    // for upload Air Booking Ticket Response
    fun uploadAirBookingTicketResponseRequest(req: AirTicketBookingResponseRequest): LiveData<Resource<Response<AirTicketBookingResponse>>> {

        val liveData = MutableLiveData<Resource<Response<AirTicketBookingResponse>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.uploadTicketBookingResponseRequest(req)
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


    fun getTransferAmountToAgents(req: TransferAmountToAgentsReq): LiveData<Resource<Response<TransferAmountToAgentsRes>>> {

        val liveData = MutableLiveData<Resource<Response<TransferAmountToAgentsRes>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.getTransferAmountToAgents(req)
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


    fun getAirTicketListRequest(req: GetAirTicketListReq): LiveData<Resource<Response<AirTicketListResp>>> {
        val liveData = MutableLiveData<Resource<Response<AirTicketListResp>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.getAirTicketListRequest(req)
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


    // payout commercial charges api
    fun getAllApiPayoutCommercialCharge(req: GetPayoutCommercialReq): LiveData<Resource<Response<GetPayoutCommercialRes>>> {

        val liveData = MutableLiveData<Resource<Response<GetPayoutCommercialRes>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.getAllApiPayoutCommercialCharge(req)
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



    fun getTransactionHistory(req: TransactionHistoryReq): LiveData<Resource<Response<TransactionHistoryResp>>> {

        val liveData = MutableLiveData<Resource<Response<TransactionHistoryResp>>>()
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(30_000) {
                    repository.getTransactionHistory(req)
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

}