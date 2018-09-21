package com.gmerwan.mobiledevdeliveryapp.retrofit

import com.gmerwan.mobiledevdeliveryapp.entity.Delivery
import retrofit2.Call
import retrofit2.http.*

interface Endpoint {

    @GET("deliveries")
    fun getDeliveries():Call<List<Delivery>>
}