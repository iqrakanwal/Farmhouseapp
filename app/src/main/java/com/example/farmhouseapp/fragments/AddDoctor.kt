package com.example.farmhouseapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.farmhouseapp.R
import com.example.farmhouseapp.SharedPreferencesUtils
import com.example.farmhouseapp.models.Animal
import com.example.farmhouseapp.models.Doctor
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_animalin_seller.*
import kotlinx.android.synthetic.main.fragment_add_animals.*
import kotlinx.android.synthetic.main.fragment_add_doctor.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.IOException


class AddDoctor : Fragment() {


    private val adViewModel: MainViewModel by sharedViewModel()
    val PICK_IMAGE = 1
    var doctorpath: String? = ""
    var doctorFilePath: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_doctor, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addimagedoctor.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)


        }
        nameofdoctor.text = SharedPreferencesUtils.getFirstName(requireContext()).toString()
        email.text = SharedPreferencesUtils.getUserEmail(requireContext()).toString()
        SharedPreferencesUtils.getFirstName(requireContext())

        saveinformation.setOnClickListener {
            if (TextUtils.isEmpty(desgination_et.getText())) {
                desgination_et.setError("required")
                desgination_et.requestFocus()
            } else if (TextUtils.isEmpty(hopital_et.getText())) {
                hopital_et.setError("required")
                hopital_et.requestFocus()
            } else if (TextUtils.isEmpty(contactnum_et.getText())) {
                contactnum_et.setError("required")
                contactnum_et.requestFocus()
            } else {


                uploadProfileImage(doctorFilePath!!)

            }
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            if (data == null || data.data == null) {
                return
            }
            doctorFilePath = data.data
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        doctorFilePath
                    )
                profileimage.setImageBitmap(bitmap);
                // uploadImage(coverFilePath!!)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadProfileImage(filePath: Uri) {
        adViewModel.uploadImage(filePath, ::uploadedProfilePhoto, requireContext())
    }

    private fun uploadedProfilePhoto(s: String) {
        var doctor = Doctor()
        doctor.name =  SharedPreferencesUtils.getFirstName(requireContext()).toString()
        doctor.designation = desgination_et.text.toString()
        doctor.profilepicture = s
        doctor.email = SharedPreferencesUtils.getUserEmail(requireContext()).toString()
        doctor.hospital = hopital_et.text.toString()
        doctor.contactnum = contactnum_et.text.toString()
        adViewModel.adDoctor(doctor, ::Done)
    }

    private fun Done(s: String) {
        Toast.makeText(requireContext(), "${s}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addDoctor2_to_doctorMainPage)
    }

}