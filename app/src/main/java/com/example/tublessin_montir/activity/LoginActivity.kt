package com.example.tublessin_montir.activity

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tublessin_montir.R
import com.example.tublessin_montir.domain.login.LoginAccount
import com.example.tublessin_montir.domain.login.LoginViewModel
import com.example.tublessin_montir.domain.montir.*
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    // Untuk keperluan Oauth Google
    private val RC_SIGN_IN = 12345
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val loginViewModel = LoginViewModel()
    private val montirViewModel by viewModels<MontirViewModel>()
    val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


//        FacebookSdk.sdkInitialize(getApplicationContext())
//        AppEventsLogger.activateApp(this)
//
//        val fbButton = findViewById(R.id.login_button) as LoginButton
//        fbButton.setReadPermissions("email")
//
//        // Callback registration
//        fbButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
//            override fun onSuccess(loginResult: LoginResult?) {
//                // App code
//            }
//
//            override fun onCancel() {
//                // App code
//            }
//
//            override fun onError(exception: FacebookException) {
//                // App code
//            }
//        })
//        val accessToken = AccessToken.getCurrentAccessToken()
//        val isLoggedIn = accessToken != null && !accessToken.isExpired

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
        if(Prefs.contains("id") && Prefs.contains("token") && Prefs.contains("username") && Prefs.contains("password")){
            startActivity(Intent(this, HomeActivity::class.java))
        }
        InitializeGoogleOauth() // function nya dibawah
        mGoogleSignInClient.signOut()
        checkStatusLogin()
    }

    fun onLoginClicked(view: View) {
        if (editTextUsername.text.toString() == "" || editTextPassword.text.toString() == "") {
            Toast.makeText(
                this,
                "Username atau Password Kosong",
                Toast.LENGTH_LONG
            ).show()
        } else {
            loginViewModel.requestMontirLogin(
                LoginAccount(
                    username = editTextUsername.text.toString(),
                    password = editTextPassword.text.toString()
                )
            )
            loginViewModel.getLoginAccountInfo().observe(this, Observer {
                if (it  != null) {
                    println("sukses login")
                    println(it.token)

                    Prefs.putString("id", it.account.id.toString())
                    Prefs.putString("token", it.token)
                    Prefs.putString("username", it.account.username)
                    Prefs.putString("password", it.account.password)
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    Toast.makeText(
                        this,
                        "Username atau Password Salah",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }


//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        callbackManager.onActivityResult(requestCode, resultCode, data)
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    fun onNewRegisterClicked(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    // Mulai dari sini sampai loginUsingGoogleAccount adalah keperluan Google Auth
    private fun checkStatusLogin() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        println("============================")
        println(account)
        println("============================")
        if (account != null) {
            println("masuk ke logout")
            mGoogleSignInClient.signOut()
        }
    }

    private fun InitializeGoogleOauth() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleLoginButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // sukses Login
            val personId = registerUserGoogleAccountToDatabase()
            loginUsingGoogleAccount(personId)
        } catch (e: ApiException) {
            Log.w("Error", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun registerUserGoogleAccountToDatabase(): String? {
        // buat dapetin data user dari google
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        var personId: String? = ""
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            personId = acct.id

            println("============================================")
            println(personId)
            println(personGivenName)
            println(personFamilyName)
            println(personEmail)
            println("============================================")
            montirViewModel.registerMontir(
                MontirAccount(
                    username = "google-${personId}",
                    password = "google-${personId}",
                    profile = MontirProfile(
                        firstname = personGivenName ?: "Belum Terdaftar",
                        lastname = personFamilyName ?: "Belum Terdaftar",
                        gender = "L",
                        city = "Belum Terdaftar",
                        email = personEmail ?: "Belum Terdaftar",
                        phone_number = "Belum Terdaftar",
                        status = MontirStatus(),
                        rating_list = emptyList(),
                        location = MontirLocation()
                    )
                )
            )
        }
        return personId
    }

    private fun loginUsingGoogleAccount(personId: String?) {
        montirViewModel.getMontirAccountInfo().observe(this, Observer {
            loginViewModel.requestMontirLogin(
                LoginAccount(
                    username = "google-${personId}",
                    password = "google-${personId}"
                )
            )
        })

        loginViewModel.getLoginAccountInfo().observe(this, Observer {
            if (it != null) {
                println("succes login")
                Prefs.putString("id", it.account.id.toString())
                Prefs.putString("token", it.token)
                startActivity(Intent(this, HomeActivity::class.java))
            }
        })
    }

}