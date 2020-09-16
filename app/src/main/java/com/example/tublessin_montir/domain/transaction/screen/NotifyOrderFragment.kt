package com.example.tublessin_montir.domain.transaction.screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.final_project.domain.user.UserViewModel
import com.example.tublessin_montir.R
import com.example.tublessin_montir.config.defaultHost
import com.example.tublessin_montir.domain.transaction.Transaction
import com.example.tublessin_montir.domain.transaction.TransactionLocation
import com.example.tublessin_montir.domain.transaction.TransactionViewModel
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_notify_order.*


class NotifyOrderFragment : Fragment(), View.OnClickListener {

    private val transactionViewModel = TransactionViewModel()
    private val userViewModel = UserViewModel()
    private lateinit var transactionId: String
    private lateinit var userId: String
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notify_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickOrder.setOnClickListener(this)
        onCancelOrder.setOnClickListener(this)
        openMapClicked.setOnClickListener(this)
        navController= Navigation.findNavController(view)

        transactionId = Prefs.getString("transactionId", "0")
        userId = Prefs.getString("userId", "0")
        userViewModel.requestGetUserDetail(userId)
        userViewModel.getUserAccountInfo().observe(viewLifecycleOwner, Observer {
            firstname_user_detail.text = it.result.profile.firstname
            lastname_user_detail.text = it.result.profile.lastname
            phone_user_detail.text = it.result.profile.phone_number

            val url = "${defaultHost()}user/file/image/${it.result.profile.imageURL}"
            val glideUrl = GlideUrl(
                url,
                LazyHeaders.Builder()
                    .addHeader("Authorization", "Bearer ${Prefs.getString("token", "0")}")
                    .build()
            )
            Glide.with(this)
                .load(glideUrl)
                .circleCrop().into(photo_user_detail)
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            onClickOrder -> {
                transactionViewModel.UpdateStatusTransaction(
                    transactionId,
                    Transaction(
                        status = "2",
                        location = TransactionLocation(
                            latitude = Prefs.getDouble("latitudePosition", 0.0),
                            longitude = Prefs.getDouble("longitudePosition", 0.0)
                        )
                    )
                )
                navController.navigate(R.id.action_notifyOrderFragment_to_mapsFragment)
            }
            onCancelOrder -> {
                transactionViewModel.UpdateStatusTransaction(
                    transactionId,
                    Transaction(
                        status = "3",
                        location = TransactionLocation(
                            latitude = Prefs.getDouble("latitudePosition", 0.0),
                            longitude = Prefs.getDouble("longitudePosition", 0.0)
                        )
                    )
                )
                navController.navigate(R.id.action_notifyOrderFragment_to_mapsFragment)
            }
            openMapClicked -> {
                val userLat = Prefs.getDouble("userLatitude", 0.0)
                val userLong = Prefs.getDouble("userLongitude", 0.0)
                val montirLat = Prefs.getDouble("latitudePosition", 0.0)
                val montirLong = Prefs.getDouble("longitudePosition", 0.0)

                val uri: Uri = Uri.parse("https://www.google.com/maps/dir/${userLat},${userLong}/'${montirLat},${montirLong}'") // missing 'http://' will cause crashed
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }
}