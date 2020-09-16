package com.example.final_project.domain.user

class UserResponseMessage(
    val response: String = "",
    val code: String = "",
    val result: UserAccount
)

class UserAccount(
    val id: Int = 0,
    val username: String = "",
    val password: String = "",
    val status_account: String = "",
    val profile: UserProfile
)

class UserProfile(
    val id: Int = 0,
    val firstname: String = "",
    val lastname: String = "",
    val gender: String = "",
    val phone_number: String = "",
    val email: String = "",
    val imageURL: String = "",
    val date_updated: String = "",
    val date_created: String = "",
    val location: UserLocation
)

class UserLocation(
    val latitude: Double = 0.0000,
    val longitude: Double = 0.0000,
    val date_updated: String = ""
)