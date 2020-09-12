package com.example.tublessin_montir.screen

import android.app.Activity
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.tublessin_montir.R
import com.example.tublessin_montir.config.defaultHost
import com.example.tublessin_montir.domain.montir.MontirViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_montir_profile.*
import kotlinx.android.synthetic.main.fragment_montir_profile.address_profile_view
import kotlinx.android.synthetic.main.fragment_montir_profile.borndate_profile_view
import kotlinx.android.synthetic.main.fragment_montir_profile.email_profile_view
import kotlinx.android.synthetic.main.fragment_montir_profile.firstname_view
import kotlinx.android.synthetic.main.fragment_montir_profile.greentick_view
import kotlinx.android.synthetic.main.fragment_montir_profile.ktp_profile_view
import kotlinx.android.synthetic.main.fragment_montir_profile.lastname_view
import kotlinx.android.synthetic.main.fragment_montir_profile.phone_number_view
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pub.devrel.easypermissions.EasyPermissions


class MontirProfileFragment : Fragment(), View.OnClickListener {

    private val montirViewModel = MontirViewModel()
    lateinit var imageFileChoosed: MultipartBody.Part
    private lateinit var montirId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        activity?.fragmentManager?.popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_montir_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutClicked.setOnClickListener(this)
        Prefs.Builder()
            .setContext(this.activity)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(this.activity?.packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        montirId = Prefs.getString("id", "0")

        montirViewModel.requestGetMontirDetail(montirId)
        montirViewModel.getMontirAccountInfo().observe(viewLifecycleOwner, Observer {
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
                .circleCrop().into(montir_profile_picture_view)
        })

        camera_upload_button.setOnClickListener {
            if (EasyPermissions.hasPermissions(
                    requireActivity(),
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
                .into(montir_profile_picture_view) // Buat nampilin image yang dipilih, pake library glide

            val requestBody = RequestBody.create("multipart".toMediaTypeOrNull(), getFile)
            imageFileChoosed = MultipartBody.Part.createFormData("file", getFile.name, requestBody)
            montirViewModel.uploadMontirProfilePicture(montirId, imageFileChoosed)
        }
    }

    override fun onClick(v: View?) {
        when(v){
            logoutClicked ->{
                activity?.finish()
            }
        }
    }
}