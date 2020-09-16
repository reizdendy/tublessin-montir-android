package com.example.tublessin_montir.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.tublessin_montir.R
import com.example.tublessin_montir.domain.montir.MontirLocation
import com.example.tublessin_montir.domain.montir.MontirStatus
import com.example.tublessin_montir.domain.montir.MontirViewModel
import com.example.tublessin_montir.domain.transaction.TransactionViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : Fragment(), View.OnClickListener {

    private lateinit var map: GoogleMap
    private val montirViewModel = MontirViewModel()
    private val transactionViewModel = TransactionViewModel()
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var montirId: String
    lateinit var navController: NavController

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        enableMyLocation()
        montirViewModel.requestGetMontirDetail(montirId)
        montirViewModel.getMontirAccountInfo().observe(viewLifecycleOwner, Observer {
            var lat = it.result.profile.location.latitude
            var long = it.result.profile.location.longitude
            var montirPosition = LatLng(lat, long)
            googleMap.addMarker(
                MarkerOptions().position(montirPosition).title("Marker in Montir Position")
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(montirPosition))
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        operationOff.setOnClickListener(this)
        operationOn.setOnClickListener(this)

        navController = Navigation.findNavController(view)

        montirId = Prefs.getString("id", "0")

        montirViewModel.getMontirAccountInfo().observe(viewLifecycleOwner, Observer {
            if (it.result.profile.status.status_operational == "A") {
                operationOn.isVisible = true
                operationOff.isVisible = false
            } else {
                operationOff.isVisible = true
                operationOn.isVisible = false
            }
        })

        transactionViewModel.transactionList().observe(viewLifecycleOwner, Observer {
            if (!it.Results.results.isNullOrEmpty()) {
                if (it.Results.results[0].status == "On Process") {
                    Prefs.putString("transactionId", it.Results.results[0].id)
                    Prefs.putString("userId", it.Results.results[0].id_user)
                    Prefs.putDouble("userLatitude", it.Results.results[0].location.latitude)
                    Prefs.putDouble("userLongitude", it.Results.results[0].location.longitude)
                    navController.navigate(R.id.action_mapsFragment_to_notifyOrderFragment)
                }
            }
        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this.requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this.requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this.requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map.isMyLocationEnabled = true

        } else {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            operationOff -> {
                operationOff.isVisible = false
                montirViewModel.updateMontirStatusOperational(
                    montirId,
                    MontirStatus("A", "1")
                )
                operationOn.isVisible = true
            }
            operationOn -> {
                operationOn.isVisible = false
                montirViewModel.updateMontirStatusOperational(
                    montirId,
                    MontirStatus("N", "1")
                )
                operationOff.isVisible = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                map.clear()
                val montirPosition = LatLng(map.myLocation.latitude, map.myLocation.longitude)
                map.addMarker(
                    MarkerOptions().position(montirPosition).title("Marker in Montir Position")
                )
                map.moveCamera(CameraUpdateFactory.newLatLng(montirPosition))
                montirViewModel.updateMontirLocation(
                    montirId,
                    MontirLocation(map.myLocation.latitude, map.myLocation.longitude)
                )

                Prefs.putDouble("latitudePosition", map.myLocation.latitude)
                Prefs.putDouble("longitudePosition", map.myLocation.longitude)

                transactionViewModel.RequestMontirTransactionList(montirId)

                handler.postDelayed(this, 5000)
            }
        }
//Start
        handler.postDelayed(runnable, 7000)
    }
}