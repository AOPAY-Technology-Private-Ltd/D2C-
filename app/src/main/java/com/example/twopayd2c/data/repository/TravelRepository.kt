package com.example.twopayd2c.data.repository

import com.example.twopayd2c.data.model.travelmodel.promocode.GetPromotionListReq
import com.example.twopayd2c.data.model.travelmodel.travel.bus.addMoney.BusAddMoneyReq
import com.example.twopayd2c.data.model.travelmodel.travel.bus.busTicket.BusBookingListReq
import com.example.twopayd2c.data.model.travel.flight.AirTicketingReq
import com.example.twopayd2c.data.model.travelmodel.bus.busBooking.BusTempBookingReq
import com.example.twopayd2c.data.model.travelmodel.bus.busRequery.BusRequeryReq
import com.example.twopayd2c.data.model.travelmodel.bus.busSeatMap.BusSeatMapReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.AddTicketReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.AddTicketResponseReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusManageCancelTicketReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusPassengerDetailsReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusPaxRequeryResponseReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTampBookTicketResponseRequest
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTempBookingRequest
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketCancelReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketCancelResponseReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketCancellationChargeReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketingReq
import com.example.twopayd2c.data.model.travelmodel.bus.city.BusCityListReq
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.BusCommissionReq
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.ServiceChargeReq
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.BusSearchReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirReprintReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirTicketCancelReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirportListReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightAddPaymentReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightRePriceReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightSearchReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightTempBookingReq
import com.example.twopayd2c.data.model.travelmodel.login.CustomerLoginDetailsRequest
import com.example.twopayd2c.data.model.travelmodel.login.D2CSignUpRequest
import com.example.twopayd2c.data.model.travelmodel.login.SendOtpRequest
import com.example.twopayd2c.data.model.travelmodel.login.ValidateOtpRequest
import com.example.twopayd2c.network.ApiInterface
import com.example.twopayd2c.network.TravelInterface

class TravelRepository(private val travelInterface: TravelInterface, private val apiInterface: ApiInterface?) {
    suspend fun getAllBusCityList(req: BusCityListReq) = travelInterface.getAllBusCityList(req)
    suspend fun getAllBusSearchList(req: BusSearchReq) = travelInterface.getAllBusSearchList(req)
    suspend fun getAllBusSeatMap(req: BusSeatMapReq) = travelInterface.getAllBusSeatMap(req)
    suspend fun getAllBusTempBooking(req: BusTempBookingReq) = travelInterface.getAllBusTempBooking(req)
    suspend fun getBusTampBookRequest(req: BusTempBookingRequest) = travelInterface!!.getBusTempBookingRequest(req)
    suspend fun getBusTampBookResponse(req: BusTampBookTicketResponseRequest) = travelInterface!!.getBusTempBookingResponseRequest(req)
    suspend fun getBusCommissionRequest(req: BusCommissionReq) = apiInterface!!.getBusCommissionRequest(req)
    suspend fun getAllBusRequary(req: BusRequeryReq) = travelInterface.getAllBusRequary(req)


    // login api.....................
    suspend fun getsendOTPRequest(req: SendOtpRequest) = apiInterface!!.sendOTPRequest(req)
    suspend fun validateOtpRequest(req: ValidateOtpRequest) = apiInterface!!.checkOtpValidate(req)
    suspend fun GetCustomerLoginDetailsReq(req: CustomerLoginDetailsRequest) = apiInterface!!.GetCustomerLoginDetailsReq(req)
    suspend fun GetCustomerSignupReq(req: D2CSignUpRequest) = apiInterface!!.GetCustomerSignupReq(req)



    suspend fun getAllAddMoney(req: BusAddMoneyReq) = travelInterface.getAllAddMoney(req)
    suspend fun getAddBusTicketRequest(req: AddTicketReq) = apiInterface!!.getAddTicketRequest(req)
    suspend fun getAllBusTicketing(req: BusTicketingReq) = travelInterface.getAllBusTicketing(req)
    suspend fun getPaxRequeryResponseRequest(req: BusPaxRequeryResponseReq)= apiInterface!!.getPaxRequeryResponseReq(req)
    suspend fun getAddBusTicketResponse(req: AddTicketResponseReq) = apiInterface!!.getAddTicketResponse(req)
    suspend fun getBusTicketCancellationChargeRequest(req: BusTicketCancellationChargeReq)= travelInterface.getBucCancellationCharges(req)
    suspend fun GetServiceChargeReq(req: ServiceChargeReq)= apiInterface!!.GetServiceChargeReq(req)
    suspend fun getBusManageCancelTicketReq(req: BusManageCancelTicketReq)= apiInterface!!.getManageBusCancelList(req)
    suspend fun getBusTicketCancelRequest(req: BusTicketCancelReq)= travelInterface.getBusTicketCancel(req)
    suspend fun getBusTicketCancelResponseReq(req: BusTicketCancelResponseReq) = apiInterface!!.getAllBusTicketCancelResponseReq(req)
    suspend fun getBusBookListResponse(req: BusBookingListReq) = apiInterface!!.getAllBusBookingList(req)
    suspend fun getPassangerDetails(req: BusPassengerDetailsReq)= apiInterface!!.getPassangerDetails(req)
    suspend fun getBusCancelTicketDetails(req: BusBookingListReq)= apiInterface!!.getBusCancelList(req)



    suspend fun GetPromotionListReq(req: GetPromotionListReq)= apiInterface!!.GetPromotionListReq(req)
    suspend fun getAirportListRequest(req: AirportListReq)= apiInterface!!.getAirPortList(req)
    suspend fun getFlightSearchRequest(req: FlightSearchReq)= travelInterface.getFlightSerahList(req)
    suspend fun getFlightRepriceRequest(req: FlightRePriceReq)= travelInterface.getAirRePriceLists(req)



    suspend fun getFlightTempBookingRequestt(req: FlightTempBookingReq)= travelInterface.getAirTempBookingReq(req)
    suspend fun getFlightAddPaymentRequest(req: FlightAddPaymentReq)= travelInterface.getAirTicketAddPaymentReq(req)
    suspend fun getAirTicketingRequest(req: AirTicketingReq)= travelInterface.getAirTicketingReq(req)
    suspend fun getAirTicketReprintRequest(req: AirReprintReq)= travelInterface.getAirTicketReprintReq(req)
    suspend fun getAirTicketCancelRequest(req: AirTicketCancelReq)= travelInterface.getAirTicketCancelationReq(req)


}