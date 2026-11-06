package com.example.twopayd2c.network

import com.example.twopayd2c.data.model.travelmodel.promocode.GetPromotionListReq
import com.example.twopayd2c.data.model.travelmodel.transferAMountToAgent.TransferAmountToAgentsRes
import com.example.twopayd2c.data.model.travelmodel.travel.bus.addMoney.BusAddMoneyReq
import com.example.twopayd2c.data.model.travelmodel.travel.bus.addMoney.BusAddMoneyRes
import com.example.twopayd2c.data.model.travelmodel.travel.bus.busTicket.BusBookingListReq
import com.example.twopayd2c.data.model.travelmodel.travel.bus.busTicket.BusBookingListRes
import com.example.twopayd2c.data.model.travelmodel.travel.flight.AirTicketBookingRequest
import com.example.twopayd2c.data.model.travelmodel.travel.flight.AirTicketBookingResponse
import com.example.twopayd2c.data.model.travelmodel.travel.flight.airbookingticketList.AirTicketListResp
import com.example.twopayd2c.data.model.travelmodel.walletBalance.merchantBal.GetMerchantBalanceReq
import com.example.twopayd2c.data.model.travelmodel.walletBalance.merchantBal.GetMerchantBalanceRes
import com.example.twopayd2c.data.model.merchant.apiServiceCharge.GetPayoutCommercialReq
import com.example.twopayd2c.data.model.merchant.apiServiceCharge.GetPayoutCommercialRes
import com.example.twopayd2c.data.model.recharge.payout.PayoutAmountReq
import com.example.twopayd2c.data.model.recharge.payout.PayoutAmountRes
import com.example.twopayd2c.data.model.transactionhistory.TransactionHistoryReq
import com.example.twopayd2c.data.model.transactionhistory.TransactionHistoryResp
import com.example.twopayd2c.data.model.travel.flight.FlightRequeryReq
import com.example.twopayd2c.data.model.travel.flight.FlightRequeryResponse
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
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketCancelRespoResponse
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusTicketCancelResponseReq
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.BusCommissionReq
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.BusCommissionResp
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.ServiceChargeReq
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.ServiceChargeResp
import com.example.twopayd2c.data.model.travelmodel.bus.promocode.GetPromotionListResp
import com.example.twopayd2c.data.model.travelmodel.bus.ticketCancellation.CheckActiveStatusReq
import com.example.twopayd2c.data.model.travelmodel.bus.ticketCancellation.CheckActiveStatusResp
import com.example.twopayd2c.data.model.travelmodel.flight.AirCommissionReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirCommissionResp
import com.example.twopayd2c.data.model.travelmodel.flight.AirTicketBookingResponseRequest
import com.example.twopayd2c.data.model.travelmodel.flight.AirportListReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirportListResp
import com.example.twopayd2c.data.model.travelmodel.flight.FlightSearchReq
import com.example.twopayd2c.data.model.travelmodel.flight.FlightSearchResp
import com.example.twopayd2c.data.model.travelmodel.flight.GetAirTicketListReq
import com.example.twopayd2c.data.model.travelmodel.login.CustomerLoginDetailsRequest
import com.example.twopayd2c.data.model.travelmodel.login.CustomerLoginDetailsResponse
import com.example.twopayd2c.data.model.travelmodel.login.D2CSignUpRequest
import com.example.twopayd2c.data.model.travelmodel.login.D2CSignUpResponse
import com.example.twopayd2c.data.model.travelmodel.login.SendOtpRequest
import com.example.twopayd2c.data.model.travelmodel.login.SendOtpResponse
import com.example.twopayd2c.data.model.travelmodel.login.ValidateOtpRequest
import com.example.twopayd2c.data.model.travelmodel.login.ValidateOtpResponse
import com.example.twopayd2c.data.model.travelmodel.paymentgateway.PaymentGatewayReq
import com.example.twopayd2c.data.model.travelmodel.paymentgateway.PaymentGatewayResp
import com.example.twopayd2c.data.model.travelmodel.transferAMountToAgent.TransferAmountToAgentsReq
import com.example.twopayd2c.data.model.travelmodel.walleBalance.GetBalanceReq
import com.example.twopayd2c.data.model.travelmodel.walleBalance.GetBalanceRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterface {

    // Login Api
    @POST("/api/newOtp/SendOtp")
    suspend fun sendOTPRequest(@Body req: SendOtpRequest): Response<SendOtpResponse>?


    @POST("/api/newOtp/ValidateOtp")
    suspend fun checkOtpValidate(@Body req: ValidateOtpRequest): Response<ValidateOtpResponse>?


    @POST("/api/Login/D2CLogin")
    suspend fun GetCustomerLoginDetailsReq(@Body req: CustomerLoginDetailsRequest): Response<CustomerLoginDetailsResponse>?


    @POST("/api/Signup/D2CSignup")
    suspend fun GetCustomerSignupReq(@Body req: D2CSignUpRequest): Response<CustomerLoginDetailsResponse>?


    @POST("api/TravelServiceMaster/GetBusCommissionValues")
    suspend fun getBusCommissionRequest(@Body req: BusCommissionReq): Response<BusCommissionResp>?


    @POST("api/BusApi/BusReueryResponse")
    suspend fun getPaxRequeryResponseReq(@Body req: BusPaxRequeryResponseReq): Response<BusBookingListRes>? //Annu this api using at ticket book and cancel both time for update data in db


    @POST("api/BusApi/tickets/response")
    suspend fun getAddTicketResponse(@Body req: AddTicketResponseReq): Response<AddTicketResponseRes>? // Annu

    @POST("api/BusApi/BusAddTicketRequest")
    suspend fun getAddTicketRequest(@Body req: AddTicketReq): Response<AddTicketResp>? // Annu


    @POST("api/PromoCode/d2cPromotionResult")
    suspend fun GetPromotionListReq(@Body req: GetPromotionListReq): Response<GetPromotionListResp>?


    @POST("api/Airport/Airportlist")
    suspend fun getAirPortList(@Body req: AirportListReq): Response<AirportListResp>?


    @POST("api/Balance/GetBalance")
    suspend fun getWalletBalance(@Body req: GetBalanceReq): Response<GetBalanceRes>?


    @POST("AOP/App/BOSPaymentGateway3")
    suspend fun getPaymentGatewayURL(@Body req: PaymentGatewayReq): Response<PaymentGatewayResp>?


    @POST("api/TravelServiceMaster/GetCommissionValues")
    suspend fun getAirCommissionReq(@Body req: AirCommissionReq): Response<AirCommissionResp>?


    @POST("api/Balance/MerchantDebitBalance")
    suspend fun getAllMerchantBalance (@Body req: GetMerchantBalanceReq): Response<GetMerchantBalanceRes>?


    @POST("api/Air/api/AirReueryResponse")
    suspend fun getAirTicketRequeryReq(@Body req: FlightRequeryReq): Response<FlightRequeryResponse>? // Annu


    @POST("api/Air/api/TicketRequest")
    suspend fun uploadTicketBookingRequest(@Body req: AirTicketBookingRequest): Response<AirTicketBookingResponse>? // Annu


    @POST("api/Air/api/TicketResponse")
    suspend fun uploadTicketBookingResponse(@Body req: AirTicketBookingResponseRequest): Response<AirTicketBookingResponse>? // Annu


    @POST("api/Payout/BosPayoutTransaction")
    suspend fun getTransferAmountToAgents(@Body req: TransferAmountToAgentsReq): Response<TransferAmountToAgentsRes>?


    @POST("api/BusApi/requerylist")
    suspend fun getPassangerDetails(@Body req: BusPassengerDetailsReq): Response<BusPassangerDetailsRes>?


    @POST("api/BusApi/BusCancelList")
    suspend fun getBusCancelList(@Body req: BusBookingListReq): Response<BusCancelTicketListRespo>?


    @POST("api/UserSetting/ManageUserSetting")
    suspend fun GetServiceChargeReq(@Body req: ServiceChargeReq): Response<ServiceChargeResp>?


    @POST("api/Cancellation/ManageCancell")
    suspend fun getManageBusCancelList(@Body req: BusManageCancelTicketReq): Response<BusManageCancelTicketResp>?


    @POST("api/BusApi/BusCancelResponse")
    suspend fun getAllBusTicketCancelResponseReq(@Body req: BusTicketCancelResponseReq): Response<BusTicketCancelRespoResponse>? // Annu


    // Check active status ..........
    @POST("api/Login/CheckLoginValidity")
    suspend fun GetRetailerActiveReq(@Body req: CheckActiveStatusReq): Response<CheckActiveStatusResp>?


    @POST("api/BusApi/bookingList")
    suspend fun getAllBusBookingList(@Body req: BusBookingListReq): Response<BusBookingListRes>? // Annu



    @POST("api/Air/api/AirbookingList")
    suspend fun getAirTicketListReq(@Body req: GetAirTicketListReq): Response<AirTicketListResp>? // Annu



    //only commercial for payout
    @POST("api/Commercial/GetAndroidCommercial")
    suspend fun getAllApiPayoutCommercialCharge(@Body req: GetPayoutCommercialReq): Response<GetPayoutCommercialRes>?



    // for payout
    @POST("api/V5/Payout/BOSUPIPayoutApp")
    suspend fun sendAmount(@Body req: PayoutAmountReq?): Response<PayoutAmountRes>?



    // for transaction History api
    @POST("/api/Reports/D2CAdminLadgerReport")
    suspend fun getTransactionHistory(@Body req: TransactionHistoryReq?): Response<TransactionHistoryResp>?

}