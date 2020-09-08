package com.example.tublessin_montir.domain.login

class Login(
    val message: String,
    val token: String,
    val account: LoginAccount
)

class LoginAccount (
    val id: Int=0,
    val username: String="",
    val password: String="",
    val status_account: String=""
)
