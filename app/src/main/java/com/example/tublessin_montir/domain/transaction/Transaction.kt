package com.example.tublessin_montir.domain.transaction

class TransactionResponeMessage(
    val Message: String = "",
    val Code: String = "",
    val Status: String = "",
    val Results: Results
)

class Results(
    val result: Transaction,
    val results: List<Transaction>
)

class Transaction(
    val id: String = "",
    val id_montir: String = "",
    val id_user: String = "",
    val montir_firstname: String = "",
    val user_firstname: String = "",
    val location: TransactionLocation,
    val status: String = "",
    val date_created: String = ""
)

class TransactionLocation(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)