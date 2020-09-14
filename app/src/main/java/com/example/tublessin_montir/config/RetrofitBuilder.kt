package com.example.tublessin_montir.config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun defaultHost():String{
    return "http://34.101.198.49:8084/"
}

class RetrofitBuilder{
    companion object {
        private val BASE_URL = defaultHost()

        fun createRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}

