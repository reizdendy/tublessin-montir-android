package com.example.tublessin_montir.config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun defaultHost():String{
    return "http://192.168.43.46:8080/"
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

