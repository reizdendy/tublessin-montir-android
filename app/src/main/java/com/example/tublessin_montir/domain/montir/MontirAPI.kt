package com.example.tublessin_montir.domain.montir

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface MontirAPI {

    @POST("/account/register/montir")
    fun registerMontir(@Body montirAccount: MontirAccount): Call<MontirResponeMessage>

    @GET("/montir/profile/detail/{id}")
    fun requestGetMontirDetail(@Path("id") id: String): Call<MontirResponeMessage>

    @Multipart
    @POST("/montir/profile/image/upload/{id}")
    fun uploadMontirProfilePicture(
        @Part imagename: MultipartBody.Part,
        @Path("id") id: String
    ): Call<MontirResponeMessage>
}
