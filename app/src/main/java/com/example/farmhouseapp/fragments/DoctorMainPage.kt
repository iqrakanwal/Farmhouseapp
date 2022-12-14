package com.example.farmhouseapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.farmhouseapp.R
import com.example.farmhouseapp.SharedPreferencesUtils
import com.example.farmhouseapp.models.Doctor
import com.example.farmhouseapp.ui.FirstScreen
import com.example.farmhouseapp.ui.FirstScreen.Companion.userAccount
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_doctor_main_page.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DoctorMainPage : Fragment() {
    private val adViewModel: MainViewModel by sharedViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_doctor_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adViewModel.getDoctor(
            userAccount.email,
            userAccount.userName, ::Done
        )
        addInformations.setOnClickListener {
            findNavController().navigate(R.id.action_doctorMainPage_to_addDoctor2)
        }
    }

    private fun Done(doctor: Doctor?) {
        Toast.makeText(requireContext(), "${doctor}xdfdsfsdf", Toast.LENGTH_SHORT).show()
        if (doctor != null) {
            name.text = doctor.name
            designation.text = doctor.designation
            hopsitalname.text = doctor.hospital
            email.text = doctor.email
            Glide.with(requireContext()).load(doctor.profilepicture).into(circularImage)
            addInformations.visibility = View.GONE
        } else {
            name.visibility = View.GONE
            designation.visibility = View.GONE
            hopsitalname.visibility = View.GONE
            email.visibility = View.GONE
            circularImage.visibility = View.GONE
            appointmentstext.visibility = View.GONE
            appointments.visibility = View.GONE
            addInformations.visibility = View.VISIBLE


        }


    }


}