package com.example.tublessin_montir.domain.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {
    @POST("/account/login/montir")
    fun requestMontirLogin(@Body loginAccount: LoginAccount): Call<Login>

}