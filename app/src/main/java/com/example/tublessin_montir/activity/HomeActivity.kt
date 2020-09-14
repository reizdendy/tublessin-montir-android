package com.example.tublessin_montir.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tublessin_montir.R
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private lateinit var timer: Timer
    private val noDelay = 0L
    private val everyTenSeconds = 10000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navController = (home_navigation as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottom_navigation, navController)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeMontir -> {
                    navController.navigate(R.id.action_to_maps)
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

    override fun onResume() {
        super.onResume()

        val timerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread { /* your code here */ }
            }
        }

        timer = Timer()
        timer.schedule(timerTask, noDelay, everyTenSeconds)
    }

    override fun onPause() {
        super.onPause()

        timer.cancel()
        timer.purge()
    }

}