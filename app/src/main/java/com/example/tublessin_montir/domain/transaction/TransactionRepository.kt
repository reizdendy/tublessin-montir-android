package com.example.tublessin_montir.domain.transaction

import androidx.lifecycle.MutableLiveData
import com.pixplicity.easyprefs.library.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TransactionRepository(val transactionAPI: TransactionAPI) {
    val transactionLiveData = MutableLiveData<TransactionResponeMessage>()
    val token =  "Bearer ${Prefs.getString("token", "0")}"

    fun UpdateStatusTransaction(transactionId:String, transaction: Transaction){
        transactionAPI.UpdateStatusTransaction(transactionId, token, transaction).enqueue(object:
            Callback<TransactionResponeMessage> {
            override fun onResponse(
                call: Call<TransactionResponeMessage>,
                response: Response<TransactionResponeMessage>
            ) {
                if (response.code() == 200) {
                    println(response.code())
                    println(response.body())
                    transactionLiveData.value = response.body()
                }
                println(response.code())
                println(response.body())            }

            override fun onFailure(call: Call<TransactionResponeMessage>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun GetMontirTransactionList(montirId:String){
        println("montir id pas transaction : ${montirId}")
        println("tokennya pas transaction : ${token}")
        transactionAPI.GetMontirTransactionList(montirId, "", token).enqueue(object:Callback<TransactionResponeMessage>{
            override fun onResponse(
                call: Call<TransactionResponeMessage>,
                response: Response<TransactionResponeMessage>
            ) {
                if (response.code() == 200) {
                    println(response.code())
                    println(response.body())
                    transactionLiveData.value = response.body()
                }
                println(response.code())
                println(response.body())            }

            override fun onFailure(call: Call<TransactionResponeMessage>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun PostNewTransaction(transaction:Transaction){
        transactionAPI.PostNewTransaction(transaction, token).enqueue(object:Callback<TransactionResponeMessage>{
            override fun onResponse(
                call: Call<TransactionResponeMessage>,
                response: Response<TransactionResponeMessage>
            ) {
                if (response.code() == 200) {
                    println(response.code())
                    println(response.body())
                    transactionLiveData.value = response.body()
                }
                println("Transaction : ${response.code()}")
                println("Transaction : ${response.body()}")
            }

            override fun onFailure(call: Call<TransactionResponeMessage>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}