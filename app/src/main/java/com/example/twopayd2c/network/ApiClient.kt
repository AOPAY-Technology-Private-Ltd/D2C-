package com.example.twopayd2c.network

import android.content.Context
import com.example.twopayd2c.ui.view.activity.travel.AllFlightModule.FlightSearchDetails.Companion.context
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private var retrofit: Retrofit? = null

    private fun getAllTravelInstance(): Retrofit {
        // Create OkHttpClient with 1-minute timeout settings
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(RetryOn204Interceptor(maxRetry = 2))
            .connectTimeout(30, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(30, TimeUnit.SECONDS)    // Read timeout
            .writeTimeout(30, TimeUnit.SECONDS)   // Write timeout
           
            .build()

        // Build Retrofit instance with the custom OkHttpClient
        return Retrofit.Builder()
            .baseUrl(NetworkConstant.TRAVEL_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }


    private fun getAllInstance(): Retrofit {
        // Create OkHttpClient with 1-minute timeout settings
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(30, TimeUnit.SECONDS)    // Read timeout
            .writeTimeout(30, TimeUnit.SECONDS)   // Write timeout
           
            .build()

        // Build Retrofit instance with the custom OkHttpClient
        return Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_GETURL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }


    private fun getInstancePaymentGateWay(): Retrofit{
        // Create OkHttpClient with 1-minute timeout settings
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(30, TimeUnit.SECONDS)    // Read timeout
            .writeTimeout(30, TimeUnit.SECONDS)   // Write timeout
           
            .build()

        // Build Retrofit instance with the custom OkHttpClient
        return Retrofit.Builder()
            .baseUrl(NetworkConstant.PAYMENT_GATEWAY_BASEURL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }


    private fun getpayoutInstance(): Retrofit {
        // Create OkHttpClient with 1-minute timeout settings
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(60, TimeUnit.SECONDS)    // Read timeout
            .writeTimeout(60, TimeUnit.SECONDS)   // Write timeout
            .build()

        return Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }


    val apiInterface: ApiInterface = getpayoutInstance().create(ApiInterface::class.java)
    val apiAllTravelAPI: TravelInterface = getAllTravelInstance().create(TravelInterface::class.java)
    val apiBusAddRequestlAPI: ApiInterface = getAllInstance().create(ApiInterface::class.java)
    val apiAllInterface: ApiInterface = getAllInstance().create(ApiInterface::class.java)
    val apipaymentGateWayInterface: ApiInterface = getInstancePaymentGateWay().create(ApiInterface::class.java)

}