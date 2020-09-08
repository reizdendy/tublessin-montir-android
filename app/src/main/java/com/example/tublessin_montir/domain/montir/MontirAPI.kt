package com.example.tublessin_montir.domain.montir

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MontirAPI{

    @POST("/account/register/montir")
    fun registerMontir(@Body montirAccount: MontirAccount): Call<MontirResponeMessage>

}
