package com.example.tublessin_montir.domain.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tublessin_montir.config.RetrofitBuilder

class LoginViewModel : ViewModel() {
    val loginRepository: LoginRepository

    init {
        val loginAPI = RetrofitBuilder.createRetrofit().create(LoginAPI::class.java)
        loginRepository = LoginRepository(loginAPI)
    }

    fun getLoginAccountInfo() = loginRepository.loginAccountInfo as LiveData<Login>
    fun requestMontirLogin(loginAccount: LoginAccount) = loginRepository.requestMontirLogin(loginAccount)
}