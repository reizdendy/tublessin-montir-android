package com.example.tublessin_montir.domain.montir

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import okhttp3.MultipartBody
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MontirRepository(val montirAPI: MontirAPI) {
    var montirAccountInfo = MutableLiveData<MontirResponeMessage>()
    var montirLocationInfo = MutableLiveData<MontirLocation>()

    fun registerMontir(montirAccount: MontirAccount) {
        montirAPI.registerMontir(montirAccount).enqueue(object : Callback<MontirResponeMessage> {
            override fun onResponse(
                call: Call<MontirResponeMessage>,
                response: Response<MontirResponeMessage>
            ) {
                if (response.code() == 200) {
                    println("=============================")
                    print("REGISTER SUCCESS -> ")
                    println(response.code())
                    println(response.body())
                    println("=============================")
                    montirAccountInfo.value = response.body()
                } else {
                    println("=============================")
                    print("REGISTER GAGAL -> ")
                    println(response.code())
                    println(response.body())
                    println("=============================")
                    montirAccountInfo.value = response.body()
                }
            }

            override fun onFailure(call: Call<MontirResponeMessage>, t: Throwable) {
                println("=============================")
                print("REGISTER FAILED -> ")
                println(t)
                println("=============================")
            }
        })
    }


   
    fun requestGetMontirDetail(id: String) {
        montirAPI.getMontirByID(id).enqueue(object : Callback<MontirResponeMessage> {
            override fun onResponse(call: Call<MontirResponeMessage>, response: Response<MontirResponeMessage>) {
                if (response.code() == 200) {
                    montirAccountInfo.value = response.body()
                }
            }
            override fun onFailure(call: Call<MontirResponeMessage>, t: Throwable) {
                println("=============================")
                print("Request Detail Montir Failed -> ")
                println(t)
                println("=============================")
            }
        })
    }

    fun uploadMontirProfilePicture(id: String, image: MultipartBody.Part) {
        montirAPI.uploadMontirProfilePicture(image,id).enqueue(object : Callback<MontirResponeMessage> {
            override fun onFailure(call: Call<MontirResponeMessage>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<MontirResponeMessage>,
                response: Response<MontirResponeMessage>
            ) {
            }
        })
    }

    fun updateMontirLocation(id:String, montirLocation: MontirLocation){
        montirAPI.updateMontirLocationByID(id, montirLocation).enqueue(object : Callback<MontirResponeMessage> {
            override fun onResponse(call: Call<MontirResponeMessage>, response: Response<MontirResponeMessage>) {
                println("=============================")
                println(response.body())
                println(response.code())
                println("=============================")
            }

            override fun onFailure(call: Call<MontirResponeMessage>, t: Throwable) {
                println("=============================")
                println(t)
                println("=============================")
            }
        })

    }

    fun updateMontirStatusOperational(id:String, montirStatus: MontirStatus){
        montirAPI.updateMontirStatusOperational(id, montirStatus).enqueue(object : Callback<MontirResponeMessage> {
            override fun onResponse(call: Call<MontirResponeMessage>, response: Response<MontirResponeMessage>) {
                println("=============================")
                println(response.body())
                println(response.code())
                println("=============================")
            }

            override fun onFailure(call: Call<MontirResponeMessage>, t: Throwable) {
                println("=============================")
                println(t)
                println("=============================")
            }
        })
    }
}