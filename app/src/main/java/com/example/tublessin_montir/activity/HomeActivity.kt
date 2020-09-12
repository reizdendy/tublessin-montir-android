package com.example.tublessin_montir.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tublessin_montir.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navController = (home_navigation as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottom_navigation, navController)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeMontir -> {
                    navController.navigate(R.id.action_to_maps)
                    this.fragmentManager.popBackStack()
                    true
                }

                R.id.montirRating -> {
                    navController.navigate(R.id.action_toreview)
                    this.fragmentManager.popBackStack()
                    true
                }
                R.id.montirProfile -> {
                    navController.navigate(R.id.action_to_profile)
                    this.fragmentManager.popBackStack()
                    true
                }
                else -> {
                    println("MASUK ELSE")
                    false
                }
            }
        }
    }
}