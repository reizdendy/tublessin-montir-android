package com.example.tublessin_montir.domain.transaction.screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.tublessin_montir.R
import com.example.tublessin_montir.domain.transaction.Transaction
import com.example.tublessin_montir.domain.transaction.TransactionLocation
import com.example.tublessin_montir.domain.transaction.TransactionViewModel
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_notify_order.*


class NotifyOrderFragment : Fragment(), View.OnClickListener {

    private val transactionViewModel = TransactionViewModel()
    private lateinit var transactionId: String
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