package com.example.tublessin_montir.screen

import android.Manifest
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.tublessin_montir.R
import com.example.tublessin_montir.config.defaultHost
import com.example.tublessin_montir.domain.montir.MontirLocation
import com.example.tublessin_montir.domain.montir.MontirStatus
import com.example.tublessin_montir.domain.montir.MontirViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_maps.*
import kotlinx.android.synthetic.main.fragment_maps.operationOff
import kotlinx.android.synthetic.main.fragment_maps.operationOn
import kotlinx.android.synthetic.main.fragment_montir_profile.*

class MapsFragment : Fragment(), View.OnClickListener {


    private lateinit var map: GoogleMap
    private val montirViewModel = MontirViewModel()
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var montirId: String

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
        activity?.fragmentManager?.popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        operationOff.setOnClickListener(this)
        operationOn.setOnClickListener(this)
        getCurrentLocation.setOnClickListener(this)

        Prefs.Builder()
            .setContext(this.activity)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(this.activity?.packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        montirId = Prefs.getString("id", "0")
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
                operationOff.visibility = View.INVISIBLE
                montirViewModel.updateMontirStatusOperational(
                    montirId,
                    MontirStatus("A", "1")
                )
                operationOn.visibility = View.VISIBLE
            }
            operationOn -> {
                operationOn.visibility = View.INVISIBLE
                montirViewModel.updateMontirStatusOperational(
                    montirId,
                    MontirStatus("N", "1")
                )
                operationOff.visibility = View.VISIBLE
            }
            getCurrentLocation -> {
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
            }
        }
    }

}