package com.example.tublessin_montir.activity

import android.app.Activity
import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible

import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.tublessin_montir.R
import com.example.tublessin_montir.config.defaultHost
import com.example.tublessin_montir.domain.montir.MontirViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_profile.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pub.devrel.easypermissions.EasyPermissions

class ProfileActivity : AppCompatActivity() {
    private val montirViewModel = MontirViewModel()
    lateinit var imageFileChoosed: MultipartBody.Part

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

//        Prefs.Builder()
//            .setContext(this)
//            .setMode(ContextWrapper.MODE_PRIVATE)
//            .setPrefsName(packageName)
//            .setUseDefaultSharedPreference(true)
//            .build()
//
//        Prefs.putString("id", "1")
//        var data = Prefs.getString("id", "10")
//        println("===========================================")
//        println(data)
//        println("===========================================")

        montirViewModel.requestGetMontirDetail("1")
        montirViewModel.getMontirAccountInfo().observe(this, Observer {
            firstname_view.text = it.result.profile.firstname
            lastname_view.text = it.result.profile.lastname
            phone_number_view.text = it.result.profile.phone_number
            email_profile_view.text = it.result.profile.email
            ktp_profile_view.text = it.result.profile.ktp
            borndate_profile_view.text = it.result.profile.born_date
            address_profile_view.text = it.result.profile.address
            if (it.result.profile.verified_account == "Y") {
                greentick_view.visibility = View.VISIBLE
            }
            Glide.with(this)
                .load("${defaultHost()}montir/file/image/${it.result.profile.imageURL}")
                .circleCrop().into(profile_picture_view)
        })

        camera_upload_button_view.setOnClickListener {
            if (EasyPermissions.hasPermissions(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                ImagePicker.with(this) // buat ngambil gambar, pake library implementation 'com.github.dhaval2404:imagepicker:1.7.4'
                    .compress(1024)                     //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(
                        1080,
                        1080
                    )     //Final image resolution will be less than 1080 x 1080(Optional)
                    .cropSquare()
                    .start(300)

            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "This application need your permission to access photo gallery.",
                    991,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 300 && resultCode == Activity.RESULT_OK) {
            val getFile = ImagePicker.getFile(data)!! // dapetin data gambar yang barusan dipilih
            Glide.with(this).load(getFile).circleCrop()
                .into(profile_picture_view) // Buat nampilin image yang dipilih, pake library glide

            val requestBody = RequestBody.create("multipart".toMediaTypeOrNull(), getFile)
            imageFileChoosed = MultipartBody.Part.createFormData("file", getFile.name, requestBody)
            montirViewModel.uploadMontirProfilePicture("1", imageFileChoosed)
        }
    }
}