package com.example.tublessin_montir.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tublessin_montir.R
import com.example.tublessin_montir.domain.login.LoginAccount
import com.example.tublessin_montir.domain.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val loginViewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        loginViewModel.getLoginAccountInfo().observe(this, Observer {
//            if (it.token != null) {
//                startActivity(Intent(this, TestActivity::class.java))
//            }
//        })

    }

    fun onLoginClicked(view: View) {
        loginViewModel.requestMontirLogin(
            LoginAccount(
                username = editTextUsername.text.toString(),
                password = editTextPassword.text.toString()
            )
        )
        loginViewModel.getLoginAccountInfo().observe(this, Observer {
            if (it != null) {
                println("sukses login")
//                println(it.message)
//                println(it.token)
//                println(it.account.id)
//                println(it.account.username)
//                println(it.account.password)
//                println(it.account.status_account)
//                startActivity(Intent(this, MapsActivity::class.java))
                startActivity(Intent(this, ReviewActivity::class.java))
            }
        })
    }

    fun onNewRegisterClicked(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}