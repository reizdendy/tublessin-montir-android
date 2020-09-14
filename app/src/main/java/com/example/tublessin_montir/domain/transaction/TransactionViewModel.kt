package com.example.tublessin_montir.domain.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tublessin_montir.config.RetrofitBuilder

class TransactionViewModel : ViewModel() {
    val transactionRepository = TransactionRepository(
        RetrofitBuilder.createRetrofit().create(
            TransactionAPI::class.java
        )
    )

    fun transactionList() =
        transactionRepository.transactionLiveData as LiveData<TransactionResponeMessage>

    fun RequestMontirTransactionList(montirId: String) =
        transactionRepository.GetMontirTransactionList(montirId)

    fun UpdateStatusTransaction(transactionId: String, transaction: Transaction) =
        transactionRepository.UpdateStatusTransaction(transactionId, transaction)

    fun PostNewTransaction(transaction: Transaction) =
        transactionRepository.PostNewTransaction(transaction)
}