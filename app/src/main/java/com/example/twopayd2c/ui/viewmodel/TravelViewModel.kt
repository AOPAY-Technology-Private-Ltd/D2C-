package com.example.twopayd2c.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.twopayd2c.data.model.travelmodel.promocode.GetPromotionListReq
import com.example.twopayd2c.data.model.travelmodel.travel.bus.addMoney.BusAddMoneyReq
import com.example.twopayd2c.data.model.travelmodel.travel.bus.addMoney.BusAddMoneyRes
import com.example.twopayd2c.data.model.travelmodel.travel.bus.busTicket.BusBookingListReq
import com.example.twopayd2c.data.model.travelmodel.travel.bus.busTicket.BusBookingListRes
import com.example.twopayd2c.data.model.travel.flight.AirTicketingReq
import com.example.twopayd2c.data.model.travel.flight.AirTicketingResponse
import com.example.twopayd2c.data.model.travel.flight.airReprintresponse.AirReprintRespo
import com.example.twopayd2c.data.model.travelmodel.bus.busBooking.BusTempBookingReq
import com.example.twopayd2c.data.model.travelmodel.bus.busBooking.BusTempBookingRes
import com.example.twopayd2c.data.model.travelmodel.bus.busRequery.BusRequeryReq
import com.example.twopayd2c.data.model.travelmodel.bus.busRequery.BusRequeryRes
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.BusSeatMapReq
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.BusSeatMapRes
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.AddTicketReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.AddTicketResp
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.AddTicketResponseReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.AddTicketResponseRes
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusCancelTicketListRespo
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusManageCancelTicketReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusManageCancelTicketResp
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusPassangerDetailsRes
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusPassengerDetailsReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusPaxRequeryResponseReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTampBookTicketResponseRequest
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTampBookingResp
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTempBookingRequest
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketCancelReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketCancelRes
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketCancelRespoResponse
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketCancelResponseReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketCancellationChargeReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketCancellationChargeRes
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketingReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketingRes
import com.example.twopayd2c.data.model.travelmodel.bus.city.BusCityListReq
import com.example.twopayd2c.data.model.travelmodel.bus.city.BusCityListRes
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.BusCommissionReq
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.BusCommissionResp
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.ServiceChargeReq
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.ServiceChargeResp
import com.example.twopayd2c.data.model.travelmodel.bus.promocode.GetPromotionListResp
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.BusSearchReq
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.BusSearchRes
import com.example.twopayd2c.data.model.travelmodel.flight.AirReprintReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirTicketCancelReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirportListReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirportListResp
import com.example.twopayd2c.data.model.travelmodel.flight.FlightAddPaymentReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightAddPaymentResponse
import com.example.twopayd2c.data.model.travelmodel.flight.FlightRePriceReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightRepriceResponse
import com.example.twopayd2c.data.model.travelmodel.flight.FlightSearchReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightSearchResp
import com.example.twopayd2c.data.model.travelmodel.flight.FlightTempBookingReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightTempBookingResponse
import com.example.twopayd2c.data.model.travelmodel.flight.flightticketcancelResponse.TicketCancelResponse
import com.example.twopayd2c.data.model.travelmodel.login.CustomerLoginDetailsRequest
import com.example.twopayd2c.data.model.travelmodel.login.CustomerLoginDetailsResponse
import com.example.twopayd2c.data.model.travelmodel.login.D2CSignUpRequest
import com.example.twopayd2c.data.model.travelmodel.login.D2CSignUpResponse
import com.example.twopayd2c.data.model.travelmodel.login.SendOtpRequest
import com.example.twopayd2c.data.model.travelmodel.login.SendOtpResponse
import com.example.twopayd2c.data.model.travelmodel.login.ValidateOtpRequest
import com.example.twopayd2c.data.model.travelmodel.login.ValidateOtpResponse
import com.example.twopayd2c.data.repository.TravelRepository
import com.example.twopayd2c.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.Response
import java.io.IOException

class TravelViewModel(private val travelRepository: TravelRepository): ViewModel() {

    fun getsendOTPRequest(req: SendOtpRequest): LiveData<Resource<Response<SendOtpResponse>>> {

        val liveData = MutableLiveData<Resource<Response<SendOtpResponse>>>()

        liveData.postValue(Resource.loading(data = null)) // 🔥 Show loading state

        viewModelScope.launch {
            try {
                val response = withTimeout(40_000) {
                    travelRepository.getsendOTPRequest(req)
                }
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))   // ✅ Success
                }
                else {
                    Log.d("CompanyDetailsReq", response.message())
                    liveData.postValue(Resource.error(message = "server error Investor Details: ${response.message()}")) // ❌ Error
                }
            } catch (e: TimeoutCancellationException) {
                liveData.postValue(Resource.error(message = "Request time out. Please try again."))
            } catch (e: IOException) {
                liveData.postValue(Resource.error(message = "No internet connection. Please check your network."))
            } catch (e: Exception) {
                liveData.postValue(Resource.error(message = "Something went wrong: ${e.localizedMessage}"))
            }
        }
        return liveData
    }

    fun checkOTPValidate(req: ValidateOtpRequest): LiveData<Resource<Response<ValidateOtpResponse>>> {

        val liveData = MutableLiveData<Resource<Response<ValidateOtpResponse>>>()

        liveData.postValue(Resource.loading(data = null)) // 🔥 Show loading state

        viewModelScope.launch {
            try {
                val response = withTimeout(40_000) {
                    travelRepository.validateOtpRequest(req)
                }
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))   // ✅ Success
                }
                else {
                    Log.d("CompanyDetailsReq", response.message())
                    liveData.postValue(Resource.error(message = "server error Investor Details: ${response.message()}")) // ❌ Error
                }
            } catch (e: TimeoutCancellationException) {
                liveData.postValue(Resource.error(message = "Request time out. Please try again."))
            } catch (e: IOException) {
                liveData.postValue(Resource.error(message = "No internet connection. Please check your network."))
            } catch (e: Exception) {
                liveData.postValue(Resource.error(message = "Something went wrong: ${e.localizedMessage}"))
            }
        }
        return liveData
    }

    fun GetCustomerLoginDetailsReq(req: CustomerLoginDetailsRequest): LiveData<Resource<Response<CustomerLoginDetailsResponse>>> {

        val liveData = MutableLiveData<Resource<Response<CustomerLoginDetailsResponse>>>()

        liveData.postValue(Resource.loading(data = null)) // 🔥 Show loading state
        viewModelScope.launch {
            try {
                val response = withTimeout(40_000) {
                    travelRepository.GetCustomerLoginDetailsReq(req)
                }
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))   // ✅ Success
                }
                else {
                    Log.d("CompanyDetailsReq", response.message())
                    liveData.postValue(Resource.error(message = "server error Investor Details: ${response.message()}")) // ❌ Error
                }
            } catch (e: TimeoutCancellationException) {
                liveData.postValue(Resource.error(message = "Request time out. Please try again."))
            } catch (e: IOException) {
                liveData.postValue(Resource.error(message = "No internet connection. Please check your network."))
            } catch (e: Exception) {
                liveData.postValue(Resource.error(message = "Something went wrong: ${e.localizedMessage}"))
            }
        }
        return liveData
    }

    fun GetCustomerSignupReq(req: D2CSignUpRequest): LiveData<Resource<Response<CustomerLoginDetailsResponse>>> {

        val liveData = MutableLiveData<Resource<Response<CustomerLoginDetailsResponse>>>()

        liveData.postValue(Resource.loading(data = null)) // 🔥 Show loading state
        viewModelScope.launch {
            try {
                val response = withTimeout(40_000) {
                    travelRepository.GetCustomerSignupReq(req)
                }
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))   // ✅ Success
                }
                else {
                    Log.d("CompanyDetailsReq", response.message())
                    liveData.postValue(Resource.error(message = "server error Investor Details: ${response.message()}")) // ❌ Error
                }
            } catch (e: TimeoutCancellationException) {
                liveData.postValue(Resource.error(message = "Request time out. Please try again."))
            } catch (e: IOException) {
                liveData.postValue(Resource.error(message = "No internet connection. Please check your network."))
            } catch (e: Exception) {
                liveData.postValue(Resource.error(message = "Something went wrong: ${e.localizedMessage}"))
            }
        }
        return liveData
    }

    fun getAllBusCityList(req: BusCityListReq): LiveData<Resource<Response<BusCityListRes>>> {

        val liveData = MutableLiveData<Resource<Response<BusCityListRes>>>()

        liveData.postValue(Resource.loading(data = null)) // 🔥 Show loading state

        viewModelScope.launch {
            try {
                val response = withTimeout(40_000) {
                    travelRepository.getAllBusCityList(req)
                }
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))   // ✅ Success
                } else {
                    Log.d("CompanyDetailsReq", response.message())
                    liveData.postValue(Resource.error(message = "server error Investor Details: ${response.message()}")) // ❌ Error
                }
            } catch (e: TimeoutCancellationException) {
                liveData.postValue(Resource.error(message = "Request time out. Please try again."))
            } catch (e: IOException) {
                liveData.postValue(Resource.error(message = "No internet connection. Please check your network."))
            } catch (e: Exception) {
                liveData.postValue(Resource.error(message = "Something went wrong: ${e.localizedMessage}"))
            }
        }
        return liveData
    }

    fun getAllBusSearchList(req: BusSearchReq): LiveData<Resource<Response<BusSearchRes>>> {

        val liveData = MutableLiveData<Resource<Response<BusSearchRes>>>()

        liveData.postValue(Resource.loading(data = null)) // 🔥 Show loading state

        viewModelScope.launch {
            try {
                val response = withTimeout(40_000) {
                    travelRepository.getAllBusSearchList(req)
                }
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))   // ✅ Success
                } else {
                    Log.d("CompanyDetailsReq", response.message())
                    liveData.postValue(Resource.error(message = "server error Investor Details: ${response.message()}")) // ❌ Error
                }
            } catch (e: TimeoutCancellationException) {
                liveData.postValue(Resource.error(message = "Request time out. Please try again."))
            } catch (e: IOException) {
                liveData.postValue(Resource.error(message = "No internet connection. Please check your network."))
            } catch (e: Exception) {
                liveData.postValue(Resource.error(message = "Something went wrong: ${e.localizedMessage}"))
            }
        }
        return liveData
    }

    fun getAllBusSeatMap(req: BusSeatMapReq): LiveData<Resource<Response<BusSeatMapRes>>> {

        val liveData = MutableLiveData<Resource<Response<BusSeatMapRes>>>()

        liveData.postValue(Resource.loading(data = null)) // 🔥 Show loading

        viewModelScope.launch {
            try {

                val response = withTimeout(40_000) {
                    travelRepository.getAllBusSeatMap(req)
                }

                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))   // ✅ Success
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error Seat Map: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(message = "Request time out. Please try again.")
                )

            } catch (e: IOException) {
                liveData.postValue(
                    Resource.error(message = "No internet connection. Please check your network.")
                )

            } catch (e: Exception) {
                liveData.postValue(
                    Resource.error(message = "Something went wrong: ${e.localizedMessage}")
                )
            }
        }

        return liveData
    }

    fun getAllBusTempBooking(req: BusTempBookingReq): LiveData<Resource<Response<BusTempBookingRes>>> {

        val liveData = MutableLiveData<Resource<Response<BusTempBookingRes>>>()

        liveData.postValue(Resource.loading(data = null)) // 🔥 Show loading

        viewModelScope.launch {
            try {

                val response = withTimeout(40_000) {
                    travelRepository.getAllBusTempBooking(req)
                }

                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))   // ✅ Success
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )

            } catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )

            } catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getBusTampBookRequest(req: BusTempBookingRequest): LiveData<Resource<Response<BusTampBookingResp>>> {

        val liveData = MutableLiveData<Resource<Response<BusTampBookingResp>>>()

        liveData.postValue(Resource.loading(data = null)) // 🔥 Loading state

        viewModelScope.launch {

            try {

                val response = withTimeout(40_000) {
                    travelRepository.getBusTampBookRequest(req)
                }

                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))  // ✅ Success
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {

                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )

            } catch (e: IOException) {

                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )

            } catch (e: Exception) {

                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getTampBusTicketResponse(req: BusTampBookTicketResponseRequest): LiveData<Resource<Response<BusTampBookingResp>>> {

        val liveData = MutableLiveData<Resource<Response<BusTampBookingResp>>>()

        liveData.postValue(Resource.loading(data = null)) // 🔥 Loading

        viewModelScope.launch {

            try {

                val response = withTimeout(40_000) {
                    travelRepository.getBusTampBookResponse(req)
                }

                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))   // ✅ Success
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {

                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )

            } catch (e: IOException) {

                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )

            } catch (e: Exception) {

                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getBusCommissionRequest(req: BusCommissionReq): LiveData<Resource<Response<BusCommissionResp>>> {

        val liveData = MutableLiveData<Resource<Response<BusCommissionResp>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // API Call with 30s Timeout
                val response = withTimeout(40_000) {
                    travelRepository.getBusCommissionRequest(req)
                }

                // ✅ Check if the response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(
                        Resource.error(
                          
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                      
                        message = "Request timed out. Please try again."
                    )
                )
            } catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                      
                        message = "No internet connection. Please check your network."
                    )
                )
            } catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                      
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getPromotionListRequest(req: GetPromotionListReq): LiveData<Resource<Response<GetPromotionListResp>>> {

        val liveData = MutableLiveData<Resource<Response<GetPromotionListResp>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(40_000) {
                    travelRepository.GetPromotionListReq(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            } catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            } catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getAllAddMoney(req: BusAddMoneyReq): LiveData<Resource<Response<BusAddMoneyRes>>> {

        val liveData = MutableLiveData<Resource<Response<BusAddMoneyRes>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getAllAddMoney(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            } catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            } catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getAddBusTicketRequest(req: AddTicketReq): LiveData<Resource<Response<AddTicketResp>>> {

        val liveData = MutableLiveData<Resource<Response<AddTicketResp>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getAddBusTicketRequest(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error(
                            message = "Server error: ${response.message()}"
                        ))
                }

            }
            catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            }
            catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            }
            catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getAllBusTicketing(req: BusTicketingReq): LiveData<Resource<Response<BusTicketingRes>>> {

        val liveData = MutableLiveData<Resource<Response<BusTicketingRes>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getAllBusTicketing(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error(
                        message = "Server error: ${response.message()}"
                    ))
                }

            }
            catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            }
            catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            }
            catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getAllBusRequary(req: BusRequeryReq): LiveData<Resource<Response<BusRequeryRes>>> {

        val liveData = MutableLiveData<Resource<Response<BusRequeryRes>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getAllBusRequary(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error(
                        message = "Server error: ${response.message()}"
                    ))
                }

            }
            catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            }
            catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            }
            catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    // ....................Working here for requery response ....................................................................
    fun getPassangerDetailsRequest(req: BusPaxRequeryResponseReq): LiveData<Resource<Response<BusBookingListRes>>> {

        val liveData = MutableLiveData<Resource<Response<BusBookingListRes>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getPaxRequeryResponseRequest(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error(
                        message = "Server error: ${response.message()}"
                    ))
                }

            }
            catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            }
            catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            }
            catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getAddBusTicketResponse(req: AddTicketResponseReq): LiveData<Resource<Response<AddTicketResponseRes>>> {

        val liveData = MutableLiveData<Resource<Response<AddTicketResponseRes>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getAddBusTicketResponse(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error(
                        message = "Server error: ${response.message()}"
                    ))
                }

            }
            catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            }
            catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            }
            catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getBusTicketCancellationCharge(req: BusTicketCancellationChargeReq): LiveData<Resource<Response<BusTicketCancellationChargeRes>>> {

        val liveData = MutableLiveData<Resource<Response<BusTicketCancellationChargeRes>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getBusTicketCancellationChargeRequest(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error(
                        message = "Server error: ${response.message()}"
                    ))
                }

            }
            catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            }
            catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            }
            catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun ServiceChargeReq(req: ServiceChargeReq): LiveData<Resource<Response<ServiceChargeResp>>> {

        val liveData = MutableLiveData<Resource<Response<ServiceChargeResp>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.GetServiceChargeReq(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error(
                        message = "Server error: ${response.message()}"
                    ))
                }

            }
            catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            }
            catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            }
            catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getBusManageCancelTicketRequest(req: BusManageCancelTicketReq): LiveData<Resource<Response<BusManageCancelTicketResp>>> {

        val liveData = MutableLiveData<Resource<Response<BusManageCancelTicketResp>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getBusManageCancelTicketReq(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error(
                        message = "Server error: ${response.message()}"
                    ))
                }

            }
            catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            }
            catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            }
            catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getBusTicketCancelRequest(req: BusTicketCancelReq): LiveData<Resource<Response<BusTicketCancelRes>>> {

        val liveData = MutableLiveData<Resource<Response<BusTicketCancelRes>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getBusTicketCancelRequest(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error(
                        message = "Server error: ${response.message()}"
                    ))
                }

            }
            catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            }
            catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            }
            catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getBusTicketCancelResponseReq(req: BusTicketCancelResponseReq): LiveData<Resource<Response<BusTicketCancelRespoResponse>>> {

        val liveData = MutableLiveData<Resource<Response<BusTicketCancelRespoResponse>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getBusTicketCancelResponseReq(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error(
                        message = "Server error: ${response.message()}"
                    ))
                }

            }
            catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            }
            catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            }
            catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getBusBookListResponse(req: BusBookingListReq): LiveData<Resource<Response<BusBookingListRes>>> {

        val liveData = MutableLiveData<Resource<Response<BusBookingListRes>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getBusBookListResponse(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error(
                        message = "Server error: ${response.message()}"
                    ))
                }

            }
            catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            }
            catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            }
            catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getPassangerDetailsRequest(req: BusPassengerDetailsReq) : LiveData<Resource<Response<BusPassangerDetailsRes>>> {

        val liveData = MutableLiveData<Resource<Response<BusPassangerDetailsRes>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getPassangerDetails(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error(
                        message = "Server error: ${response.message()}"
                    ))
                }

            }
            catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            }
            catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            }
            catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }


    fun getBusCancelTicketRequest(req: BusBookingListReq) : LiveData<Resource<Response<BusCancelTicketListRespo>>> {

        val liveData = MutableLiveData<Resource<Response<BusCancelTicketListRespo>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getBusCancelTicketDetails(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(Resource.error(
                        message = "Server error: ${response.message()}"
                    ))
                }

            }
            catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            }
            catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            }
            catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }



    // for flight .................................................................................................................

    fun getAirportListRequet(req: AirportListReq): LiveData<Resource<Response<AirportListResp>>> {

        val liveData = MutableLiveData<Resource<Response<AirportListResp>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(40_000) {
                    travelRepository.getAirportListRequest(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            } catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            } catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getFlightSearchRequest(req: FlightSearchReq): LiveData<Resource<Response<FlightSearchResp>>> {

        val liveData = MutableLiveData<Resource<Response<FlightSearchResp>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getFlightSearchRequest(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            } catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            } catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getFlightRePriseRequest(req: FlightRePriceReq): LiveData<Resource<Response<FlightRepriceResponse>>> {

        val liveData = MutableLiveData<Resource<Response<FlightRepriceResponse>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getFlightRepriceRequest(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            } catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            } catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getFlightTempBookingRequest(req: FlightTempBookingReq): LiveData<Resource<Response<FlightTempBookingResponse>>> {

        val liveData = MutableLiveData<Resource<Response<FlightTempBookingResponse>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getFlightTempBookingRequestt(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            } catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            } catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getFlightAddPaymentRequest(req: FlightAddPaymentReq): LiveData<Resource<Response<FlightAddPaymentResponse>>> {

        val liveData = MutableLiveData<Resource<Response<FlightAddPaymentResponse>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getFlightAddPaymentRequest(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            } catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            } catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getAirTicketingRequest(req: AirTicketingReq): LiveData<Resource<Response<AirTicketingResponse>>> {

        val liveData = MutableLiveData<Resource<Response<AirTicketingResponse>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                val response = withTimeout(60_000) {
                    travelRepository.getAirTicketingRequest(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            } catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            } catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getAirTicketReprintRequest(req: AirReprintReq): LiveData<Resource<Response<AirReprintRespo>>> {

        val liveData = MutableLiveData<Resource<Response<AirReprintRespo>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getAirTicketReprintRequest(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            } catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            } catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

    fun getAirTicketCancelRequest(req: AirTicketCancelReq): LiveData<Resource<Response<TicketCancelResponse>>> {

        val liveData = MutableLiveData<Resource<Response<TicketCancelResponse>>>()

        // 🔥 Initial Loading State
        liveData.postValue(Resource.loading(data = null))

        viewModelScope.launch {
            try {
                // ⏳ API Call with 10s Timeout
                val response = withTimeout(60_000) {
                    travelRepository.getAirTicketCancelRequest(req)
                }

                // ✅ Check if response is successful
                if (response!!.isSuccessful) {
                    liveData.postValue(Resource.success(response))
                } else {
                    liveData.postValue(
                        Resource.error(
                            message = "Server error: ${response.message()}"
                        )
                    )
                }

            } catch (e: TimeoutCancellationException) {
                liveData.postValue(
                    Resource.error(
                        message = "Request timed out. Please try again."
                    )
                )
            } catch (e: IOException) {
                liveData.postValue(
                    Resource.error(
                        message = "No internet connection. Please check your network."
                    )
                )
            } catch (e: Exception) {
                liveData.postValue(
                    Resource.error(
                        message = "Something went wrong: ${e.localizedMessage}"
                    )
                )
            }
        }

        return liveData
    }

}