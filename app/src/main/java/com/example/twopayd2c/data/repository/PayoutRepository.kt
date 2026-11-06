package com.example.twopayd2c.data.repository

import com.example.twopayd2c.data.model.idfcPayout.AOPPayOutReq
import com.example.twopayd2c.data.model.recharge.payout.PayoutAmountReq
import com.example.twopayd2c.data.model.recharge.payout.payoutBos.PayoutAppReq
import com.example.twopayd2c.data.model.recharge.payout.payoutStatus.PayoutStatusReq
import com.example.twopayd2c.network.ApiInterface

class PayoutRepository(private val apiInterface: ApiInterface) {
    suspend fun sendAmount(req: PayoutAmountReq) = apiInterface.sendAmount(req)
    /*suspend fun getPayoutStatus(req: PayoutStatusReq) = apiInterface.getPayoutStatus(req)
    suspend fun getPayoutAmountTransaction(req: PayoutAppReq) = apiInterface.getPayoutAmountTransaction(req)
    suspend fun sendAllPayoutAmount(req: AOPPayOutReq) = apiInterface.sendAllPayoutAmount(req)*/

}