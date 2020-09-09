package com.example.tublessin_montir.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tublessin_montir.R
import com.example.tublessin_montir.config.defaultHost
import com.example.tublessin_montir.domain.montir.MontirViewModel
import com.example.tublessin_montir.recyleview.ReviewRecyleAdapter
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_review.*

class ReviewActivity : AppCompatActivity() {
    private val montirViewModel = MontirViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        review_list_recycle_view.layoutManager = LinearLayoutManager(this)
        montirViewModel.requestGetMontirDetail("1")
        montirViewModel.getMontirAccountInfo().observe(this, Observer {
            if (it.result.profile.rating_list.size != 0){
            review_list_recycle_view.adapter = ReviewRecyleAdapter(it.result.profile.rating_list)
            }
        })

    }
}