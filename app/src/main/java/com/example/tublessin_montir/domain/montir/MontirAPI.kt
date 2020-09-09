package com.example.tublessin_montir.domain.montir

import retrofit2.Call
import retrofit2.http.*

interface MontirAPI{

    @POST("/account/register/montir")
    fun registerMontir(@Body montirAccount: MontirAccount): Call<MontirResponeMessage>

    @GET("/montir/profile/detail/{id}")
    fun getMontirByID(@Path("id")id:Int): Call<MontirResponeMessage>

    @GET("/montir/location/{id}")
    fun getMontirLocationByID(@Path("id")id:Int): Call<MontirLocation>

    @PATCH("/montir/profile/update/location/{id}")
    fun updateMontirLocationByID(@Path("id")id:Int, @Body montirLocation: MontirLocation): Call<MontirResponeMessage>


}
