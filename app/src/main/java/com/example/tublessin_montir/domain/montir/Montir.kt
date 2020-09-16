package com.example.tublessin_montir.domain.montir

class MontirResponeMessage(
    val respone: String = "",
    val code: String = "",
    val result: MontirAccount
)

class MontirAccount(
    val id: Int = 0,
    val username: String = "",
    val password: String = "",
    val status_account: String = "",
    val profile: MontirProfile
)

class MontirProfile(
    val id: Int = 0,
    val firstname: String = "",
    val lastname: String = "",
    val born_date: String = "",
    val gender: String = "",
    val ktp: String = "",
    val address: String = "",
    val city: String = "",
    val email: String = "",
    val phone_number: String = "",
    val imageURL: String = "",
    val verified_account: String = "",
    val date_updated: String = "",
    val date_created: String = "",
    val status: MontirStatus,
    val rating_list: List<MontirRating>,
    val location: MontirLocation
)

class MontirProfileUpdated(
    val id: Int = 0,
    val firstname: String = "",
    val lastname: String = "",
    val born_date: String = "",
    val gender: String = "",
    val ktp: String = "",
    val address: String = "",
    val city: String = "",
    val email: String = "",
    val phone_number: String = ""
)

class MontirRating(
    val rating: Int = 0,
    val rater_id: String = "",
    val review: String = "",
    val date_created: String = ""
)

class MontirLocation(
    val latitude: Double = 0.0000,
    val longitude: Double = 0.0000,
    val date_updated: String = ""
)

class MontirStatus(
    val status_operational: String = "",
    val status_activity: String = "",
    val date_updated: String = ""
)