package com.example.tublessin_montir.screen

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.tublessin_montir.R
import com.example.tublessin_montir.domain.montir.*
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_update_montir_profile.*


/**
 * A simple [Fragment] subclass.
 * Use the [UpdateMontirProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 * yang diapdet
 * "firstname": "Gerinnnnnnnnnnnnnnnnn",
"lastname": "Prakoso",
"born_date": "1900-12-25",
"gender": "L",
"ktp": "1234444444",
"address": "Bekasiiiii",
"city": "Jakarta",
"email": "kucing@yahoo.com",
"phone_number": "089529723123"
 */
class UpdateMontirProfileFragment : Fragment(), View.OnClickListener {


    private val montirViewModel = MontirViewModel()
    private lateinit var montirId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_montir_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancelUpdateProfileButton.setOnClickListener(this)
        updateProfileButton.setOnClickListener(this)
        montirId = Prefs.getString("id", "0")

        montirViewModel.requestGetMontirDetail(montirId)
        montirViewModel.getMontirAccountInfo().observe(viewLifecycleOwner, Observer { it ->
            editFirstname.text =
                Editable.Factory.getInstance().newEditable(it.result.profile.firstname)
            editLastname.text =
                Editable.Factory.getInstance().newEditable(it.result.profile.lastname)
            editBornDate.text =
                Editable.Factory.getInstance().newEditable(it.result.profile.born_date)
            if (it.result.profile.gender == "L") {
                editGender.setSelection(0)
            } else {
                editGender.setSelection(1)
            }
            editKTP.text = Editable.Factory.getInstance().newEditable(it.result.profile.ktp)
            editAddress.text = Editable.Factory.getInstance().newEditable(it.result.profile.address)
            editCity.text = Editable.Factory.getInstance().newEditable(it.result.profile.city)
            editEmail.text = Editable.Factory.getInstance().newEditable(it.result.profile.email)
            editMobilePhone.text =
                Editable.Factory.getInstance().newEditable(it.result.profile.phone_number)
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            cancelUpdateProfileButton -> {
                this.activity?.onBackPressed()
            }
            updateProfileButton -> {
                montirViewModel.updateMontirProfile(
                    montirId, MontirProfileUpdated(
                        firstname = editFirstname.text.toString(),
                        lastname = editLastname.text.toString(),
                        born_date = editBornDate.text.toString(),
                        gender = editGender.selectedItem.toString(),
                        ktp = editKTP.text.toString(),
                        address = editAddress.text.toString(),
                        city = editCity.text.toString(),
                        email = editEmail.text.toString(),
                        phone_number = editMobilePhone.text.toString()
                    )
                )
                this.activity?.onBackPressed()
            }
        }
    }
}