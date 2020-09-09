package com.example.tublessin_montir.activity

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tublessin_montir.R
import com.example.tublessin_montir.domain.login.LoginAccount
import com.example.tublessin_montir.domain.login.LoginViewModel
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val loginViewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
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
                Prefs.putString("id", it.account.id.toString())
                startActivity(Intent(this, MapsActivity::class.java))
            }
        })
    }

    fun onNewRegisterClicked(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }


}