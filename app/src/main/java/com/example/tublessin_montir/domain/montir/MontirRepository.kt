package com.example.tublessin_montir.domain.montir

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
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

    fun getMontirById(id: Int) {
        montirAPI.getMontirByID(id).enqueue(object : Callback<MontirResponeMessage> {
            override fun onResponse(call: Call<MontirResponeMessage>, response: Response<MontirResponeMessage>) {
                val responseData = response.body()

                if (responseData?.code == "200") {
                    val res = responseData.result
                    val gson = Gson()
                    montirAccountInfo.value = gson.fromJson(gson.toJson(res), MontirResponeMessage::class.java)
                }
            }
            override fun onFailure(call: Call<MontirResponeMessage>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun updateMontirLocation(id:Int, montirLocation: MontirLocation){

    }


}