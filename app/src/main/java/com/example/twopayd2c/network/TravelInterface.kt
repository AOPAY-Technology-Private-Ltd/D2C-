package com.example.twopayd2c.network

import com.example.twopayd2c.data.model.travelmodel.travel.bus.addMoney.BusAddMoneyReq
import com.example.twopayd2c.data.model.travelmodel.travel.bus.addMoney.BusAddMoneyRes
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
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.BusSearchReq
import com.example.twopayd2c.data.model.travelmodel.bus.searchBus.BusSearchRes
import com.example.twopayd2c.data.model.travelmodel.flight.AirReprintReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirTicketCancelReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightAddPaymentReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightAddPaymentResponse
import com.example.twopayd2c.data.model.travelmodel.flight.FlightRePriceReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightRepriceResponse
import com.example.twopayd2c.data.model.travelmodel.flight.FlightSearchReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightSearchResp
import com.example.twopayd2c.data.model.travelmodel.flight.FlightTempBookingReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightTempBookingResponse
import com.example.twopayd2c.data.model.travelmodel.flight.flightticketcancelResponse.TicketCancelResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TravelInterface {
    @POST("api/V1/Bus/Travel/BusCityList")
    suspend fun getAllBusCityList(@Body req: BusCityListReq): Response<BusCityListRes>?

    @POST("api/V1/Bus/Travel/BusSearch")
    suspend fun getAllBusSearchList(@Body req: BusSearchReq): Response<BusSearchRes>?

    @POST("api/V1/Bus/Travel/BusSeatMap")
    suspend fun getAllBusSeatMap(@Body req: BusSeatMapReq) : Response<BusSeatMapRes>?


    @POST("api/V1/Bus/Travel/BusTempBooking")
    suspend fun getAllBusTempBooking(@Body req: BusTempBookingReq) : Response<BusTempBookingRes>?


    @POST("api/BusApi/BusTempBookingRequest")
    suspend fun getBusTempBookingRequest(@Body req: BusTempBookingRequest): Response<BusTampBookingResp>?


    @POST("api/BusApi/BusTempBookingResponse")
    suspend fun getBusTempBookingResponseRequest(@Body req: BusTampBookTicketResponseRequest): Response<BusTampBookingResp>?

    @POST("api/V1/Bus/Travel/BusRequery")
    suspend fun getAllBusRequary(@Body req: BusRequeryReq): Response<BusRequeryRes>


    @POST("api/V1/Bus/Travel/BusAddMoney")
    suspend fun getAllAddMoney(@Body req: BusAddMoneyReq): Response<BusAddMoneyRes>?

    @POST("api/V1/Bus/Travel/BusCancellationCharges")
    suspend fun getBucCancellationCharges(@Body req: BusTicketCancellationChargeReq): Response<BusTicketCancellationChargeRes>?

    @POST("api/V1/Bus/Travel/BusCancellation")
    suspend fun getBusTicketCancel(@Body req: BusTicketCancelReq): Response<BusTicketCancelRes>?






    @POST("api/V1/Bus/Travel/BusTicketing")
    suspend fun getAllBusTicketing(@Body req: BusTicketingReq): Response<BusTicketingRes>?


    @POST("api/V1/Air/Travel/AirSearch")
    suspend fun getFlightSerahList(@Body req: FlightSearchReq): Response<FlightSearchResp>? // Annu


    @POST("api/V1/Air/Travel/AirRePrice")
    suspend fun getAirRePriceLists(@Body req: FlightRePriceReq): Response<FlightRepriceResponse>? // Annu


    @POST("api/V1/Air/Travel/AirTempBooking")
    suspend fun getAirTempBookingReq(@Body req: FlightTempBookingReq): Response<FlightTempBookingResponse>? // Annu

    @POST("api/V1/Air/Travel/AddPayment")
    suspend fun getAirTicketAddPaymentReq(@Body req: FlightAddPaymentReq): Response<FlightAddPaymentResponse>? // Annu

    @POST("api/V1/Air/Travel/AirTicketing")
    suspend fun getAirTicketingReq(@Body req: AirTicketingReq): Response<AirTicketingResponse>? // Annu


    @POST("api/V1/Air/Travel/AirReprint")
    suspend fun getAirTicketReprintReq(@Body req: AirReprintReq): Response<AirReprintRespo>? // Annu


    @POST("api/V1/Air/Travel/AirTicketCancellation")
    suspend fun getAirTicketCancelationReq(@Body req: AirTicketCancelReq): Response<TicketCancelResponse>? // Annu



}