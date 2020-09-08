package com.example.tublessin_montir.domain.login

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(val loginAPI: LoginAPI){
    var loginAccountInfo = MutableLiveData<Login>()

    fun requestMontirLogin(loginAccount: LoginAccount) {
        loginAPI.requestMontirLogin(loginAccount).enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                if (response.code() == 200) {
                    println("=============================")
                    print("LOGIN SUCCESS -> ")
                    println(response.code())
                    println(response.body())
                    println("=============================")
                    loginAccountInfo.value = response.body()
                } else {
                    println("=============================")
                    print("LOGIN GAGAL -> ")
                    println(response.code())
                    println(response.body())
                    println("=============================")
                    loginAccountInfo.value = response.body()
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                println("=============================")
                print("LOGIN FAILED -> ")
                println(t)
                println("=============================")
            }
        })
    }

}