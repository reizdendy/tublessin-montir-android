package com.example.tublessin_montir.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tublessin_montir.R
import com.example.tublessin_montir.domain.montir.*
import com.example.tublessin_montir.util.TextValidator
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private val montirViewModel = MontirViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }


    fun onRegisterOKClicked(view: View) {
        println("Masuk ke click register")
        montirViewModel.registerMontir(
            MontirAccount(
                username = registerUsername.text.toString(),
                password = registerPassword.text.toString(),
                profile = MontirProfile(
                    firstname = registerFirstname.text.toString(),
                    lastname = registerLastname.text.toString(),
                    gender = registerGender.selectedItem.toString(),
                    city = registerCity.text.toString(),
                    email = registerEmail.text.toString(),
                    phone_number = registerMobileNumber.text.toString(),
                    status = MontirStatus(),
                    rating_list = emptyList(),
                    location = MontirLocation()
                )
            )
        )
        println("masuk ke montir acount info")
        montirViewModel.getMontirAccountInfo().observe(this, Observer {
            if (it.code == "200") {
                Toast.makeText(
                    this, "Register Berhasil",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            } else if (it.code == "900") {
                Toast.makeText(
                    this, "Register Gagal. Username sudah digunakan",
                    Toast.LENGTH_LONG
                ).show()
            } else if (it.code == "800") {
                Toast.makeText(
                    this, "Register Gagal. Nomor Telefon Sudah Digunakan",
                    Toast.LENGTH_LONG
                ).show()
            } else if (it.code == "700") {
                Toast.makeText(
                    this, "Register Gagal. Email Sudah Digunakan",
                    Toast.LENGTH_LONG
                ).show()
            }
//            if (it != null) {
//                println("sukses register")
//                finish()
//        }
    })
}

    fun haveAccountOnClicked(view: View) {
        finish()
    }

}