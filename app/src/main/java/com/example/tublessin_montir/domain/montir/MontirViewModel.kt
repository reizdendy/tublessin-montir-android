package com.example.tublessin_montir.domain.montir

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tublessin_montir.config.RetrofitBuilder

class MontirViewModel : ViewModel(){
    val montirRepository:MontirRepository

    init {
        montirRepository = MontirRepository(RetrofitBuilder.createRetrofit().create(MontirAPI::class.java))
    }

    fun getMontirAccountInfo() = montirRepository.montirAccountInfo as LiveData<MontirResponeMessage>
    fun registerMontir(montirAccount: MontirAccount) = montirRepository.registerMontir(montirAccount)
    fun requestGetMontirDetail(id: String) = montirRepository.requestGetMontirDetail(id)
    fun updateMontirLocation(id:String, montirLocation: MontirLocation) = montirRepository.updateMontirLocation(id, montirLocation)
    fun updateMontirStatusOperational(id:String, montirStatus: MontirStatus) = montirRepository.updateMontirStatusOperational(id, montirStatus)

}