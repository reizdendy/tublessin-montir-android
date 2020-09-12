package com.example.tublessin_montir.activity

import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tublessin_montir.R
import com.example.tublessin_montir.config.defaultHost
import com.example.tublessin_montir.domain.montir.MontirViewModel
import com.example.tublessin_montir.recyleview.ReviewRecyleAdapter
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_review.*

class ReviewActivity : AppCompatActivity() {
    private val montirViewModel = MontirViewModel()
    private lateinit var montirId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()


        rating_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeMontir -> {
                    finish()
                    startActivity(Intent(this, MapsActivity::class.java))
                    true
                }

                R.id.montirProfile -> {
                    finish()
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> {
                    println("MASUK ELSE")
                    false
                }
            }
        }

        montirId = Prefs.getString("id", "0")

        review_list_recycle_view.layoutManager = LinearLayoutManager(this)
        montirViewModel.requestGetMontirDetail(montirId)
        montirViewModel.getMontirAccountInfo().observe(this, Observer {
            if (it.result.profile.rating_list.size != 0){
            review_list_recycle_view.adapter = ReviewRecyleAdapter(it.result.profile.rating_list)
            }
        })

    }
}