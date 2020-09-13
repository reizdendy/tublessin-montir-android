package com.example.tublessin_montir.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat


class GPSUtils private constructor() {
    private var locationManager: LocationManager? = null
    var latitude: String? = null
    var longitude: String? = null

    fun initPermissions(activity: Activity?) {
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION
        )
    }

    fun findDeviceLocation(activity: Activity) {
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //Check gps is enable or not
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Write Function To enable gps
            gpsEnable(activity)
        } else {
            //GPS is already On then
            getLocation(activity)
        }
    }

    private fun getLocation(activity: Activity) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            val LocationGps: Location? =
                locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val LocationNetwork: Location? =
                locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            val LocationPassive: Location? =
                locationManager!!.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            if (LocationGps != null) {
                val lat: Double = LocationGps.getLatitude()
                val longi: Double = LocationGps.getLongitude()
                latitude = lat.toString()
                longitude = longi.toString()
            } else if (LocationNetwork != null) {
                val lat: Double = LocationNetwork.getLatitude()
                val longi: Double = LocationNetwork.getLongitude()
                latitude = lat.toString()
                longitude = longi.toString()
            } else if (LocationPassive != null) {
                val lat: Double = LocationPassive.getLatitude()
                val longi: Double = LocationPassive.getLongitude()
                latitude = lat.toString()
                longitude = longi.toString()
            } else {
                Toast.makeText(activity, "Can't Get Your Location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gpsEnable(activity: Activity) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setMessage("Enable GPS").setCancelable(false)
            .setPositiveButton("YES",
                DialogInterface.OnClickListener { dialog, which ->
                    activity.startActivity(
                        Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS
                        )
                    )
                }).setNegativeButton("NO",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    companion object {
        private const val REQUEST_LOCATION = 1
        val instance = GPSUtils()
    }
}