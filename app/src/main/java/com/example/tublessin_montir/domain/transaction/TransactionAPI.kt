package com.example.tublessin_montir.domain.transaction

import retrofit2.Call
import retrofit2.http.*

interface TransactionAPI {
    @POST("transaction/add")
    fun PostNewTransaction(
        @Body transaction: Transaction,
        @Header("Authorization") token: String
    ): Call<TransactionResponeMessage>

    @GET("transaction/history/get")
    fun GetMontirTransactionList(
        @Query("montirid") montirid: String,
        @Query("userid") userid: String,
        @Header("Authorization") token: String
    ): Call<TransactionResponeMessage>

    @POST("transaction/update/status/{id}")
    fun UpdateStatusTransaction(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body transaction: Transaction
    ): Call<TransactionResponeMessage>
}