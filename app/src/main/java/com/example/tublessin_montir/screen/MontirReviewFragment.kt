package com.example.tublessin_montir.screen

import android.content.ContextWrapper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tublessin_montir.R
import com.example.tublessin_montir.domain.montir.MontirRating
import com.example.tublessin_montir.domain.montir.MontirViewModel
import com.example.tublessin_montir.recyleview.ReviewRecyleAdapter
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_montir_review.*


class MontirReviewFragment : Fragment() {
    private val montirViewModel = MontirViewModel()
    private lateinit var montirId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_montir_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        montirId = Prefs.getString("id", "0")

        montir_review_list_recycle_view.layoutManager = LinearLayoutManager(this.context)
        montirViewModel.requestGetMontirDetail(montirId)
        montirViewModel.getMontirAccountInfo().observe(viewLifecycleOwner, Observer {
            if (!it.result.profile.rating_list.isNullOrEmpty()){
                montir_review_list_recycle_view.adapter = ReviewRecyleAdapter(it.result.profile.rating_list)
                layoutNoReview.isVisible = false
            } else {
                layoutNoReview.isVisible = true
            }
        })
    }
}