package com.example.tublessin_montir.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tublessin_montir.R
import com.example.tublessin_montir.domain.chat.ChatViewModel
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private val chatViewModel = ChatViewModel()
    private lateinit var montirId: String
    private lateinit var customerId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        montirId = Prefs.getString("id", "0")
        customerId = Prefs.getString("userId", "0")

        navController = (home_navigation as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottom_navigation, navController)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeMontir -> {
                    navController.navigate(R.id.action_to_maps)
                    true
                }
                R.id.historyTransaction -> {
                    navController.navigate(R.id.action_to_history)

                    true
                }
                R.id.montirRating -> {
                    navController.navigate(R.id.action_toreview)
                    true
                }
                R.id.montirProfile -> {
                    navController.navigate(R.id.action_to_profile)
                    true
                }
                else -> {
                    println("MASUK ELSE")
                    false
                }
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        val handler = Handler()
//        val runnable: Runnable = object : Runnable {
//            override fun run() {
//                chatViewModel.RequestChatList(montirId, customerId)
//                handler.postDelayed(this, 2000)
//            }
//        }
////Start
//        handler.postDelayed(runnable, 2000)
//    }

}