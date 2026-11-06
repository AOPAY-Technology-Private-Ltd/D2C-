package com.example.twopayd2c.data.repository


import com.example.twopayd2c.data.model.travelmodel.transferAMountToAgent.TransferAmountToAgentsReq
import com.example.twopayd2c.data.model.travelmodel.travel.flight.AirTicketBookingRequest
import com.example.twopayd2c.data.model.travelmodel.walletBalance.merchantBal.GetMerchantBalanceReq
import com.example.twopayd2c.data.model.merchant.apiServiceCharge.GetPayoutCommercialReq
import com.example.twopayd2c.data.model.transactionhistory.TransactionHistoryReq
import com.example.twopayd2c.data.model.travel.flight.FlightRequeryReq
import com.example.twopayd2c.data.model.travelmodel.bus.busTicket.BusPassengerDetailsReq
import com.example.twopayd2c.data.model.travelmodel.bus.forservicecharge.ServiceChargeReq
import com.example.twopayd2c.data.model.travelmodel.bus.ticketCancellation.CheckActiveStatusReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirCommissionReq
import com.example.twopayd2c.data.model.travelmodel.flight.AirTicketBookingResponseRequest
import com.example.twopayd2c.data.model.travelmodel.flight.GetAirTicketListReq
import com.example.twopayd2c.data.model.travelmodel.paymentgateway.PaymentGatewayReq
import com.example.twopayd2c.data.model.travelmodel.walleBalance.GetBalanceReq
import com.example.twopayd2c.network.ApiInterface

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class GetAllAPIServiceRepository(private val apiInterface: ApiInterface) {
    suspend fun getWalletBalance(req: GetBalanceReq) = apiInterface.getWalletBalance(req)
    suspend fun getPaymentGatewayURL(req: PaymentGatewayReq) = apiInterface.getPaymentGatewayURL(req)

    suspend fun getFlightCommissionReq(req: AirCommissionReq)= apiInterface.getAirCommissionReq(req)
    suspend fun getAllMerchantBalance(req: GetMerchantBalanceReq) = apiInterface.getAllMerchantBalance(req)


    // air requery request ..........................
    suspend fun getAirRequeryRequest(req: FlightRequeryReq)= apiInterface.getAirTicketRequeryReq(req)
    suspend fun uploadTicketBookingRequest(req: AirTicketBookingRequest)= apiInterface.uploadTicketBookingRequest(req)
    suspend fun uploadTicketBookingResponseRequest(req: AirTicketBookingResponseRequest)= apiInterface.uploadTicketBookingResponse(req)

    suspend fun getTransferAmountToAgents(req: TransferAmountToAgentsReq) = apiInterface.getTransferAmountToAgents(req)
    suspend fun getPassangerDetails(req: BusPassengerDetailsReq)= apiInterface!!.getPassangerDetails(req)

    suspend fun GetRetailerActiveReq(req: CheckActiveStatusReq)= apiInterface!!.GetRetailerActiveReq(req)
    suspend fun getAirTicketListRequest(req: GetAirTicketListReq) = apiInterface.getAirTicketListReq(req)

    // payout charges .....

    suspend fun getAllApiPayoutCommercialCharge(req: GetPayoutCommercialReq) = apiInterface.getAllApiPayoutCommercialCharge(req)

    suspend fun getTransactionHistory(req: TransactionHistoryReq) = apiInterface.getTransactionHistory(req)

}