package com.example.twopayd2c.network

class NetworkConstant {
    companion object{
        const val BASE_URL: String = "https://payout.boscenter.in/"

        /*UAT Api*/
        //const val TRAVEL_URL: String = "https://travel.bospay.co.in/"
        /*Live Api*/
        const val TRAVEL_URL: String = "https://travel.bospay.in/"

        /* UAT Api Fr Login*/
        const val BASE_GETURL: String = "https://bosapi.bos.center"
        const val IMAGE_BASE_URL: String = "https://bosapi.bos.center"

        /* Live Api Fr Login*/
        /* private const val BASE_GETURL: String = "https://bosapi.businessonlinesolution.in"
         const val IMAGE_BASE_URL: String = "https://bosapi.businessonlinesolution.in"*/

        // for payment Gateway
        const val PAYMENT_GATEWAY_BASEURL : String = "https://api.aopay.in/api/"
    }

}