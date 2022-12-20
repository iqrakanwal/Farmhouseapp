package com.example.farmhouseapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.farmhouseapp.ApointmentStatus
import com.example.farmhouseapp.OrderStatus
import com.example.farmhouseapp.R
import com.example.farmhouseapp.models.Appointments
import com.example.farmhouseapp.ui.FirstScreen.Companion.userAccount
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_doctor_details.*
import kotlinx.android.synthetic.main.fragment_orderdetail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DoctorDetails : Fragment() {
    private val adViewModel: MainViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doctname.text = adViewModel.getDoctor()?.name
        var appointments= Appointments()
        appointments.doctorname= adViewModel.getDoctor()!!
        appointments.sellername= userAccount.userName
        appointments.slot= adViewModel.getDoctor()!!.slot
        appointments.appointmentstatus= "${ApointmentStatus.TAKE}"
        takeappointment.setOnClickListener {
            adViewModel.takeApointment(appointments, ::orderDelivered)
        }    }

    private fun orderDelivered(s: String) {
        Toast.makeText(requireContext(), "${s}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_doctorDetails_to_doctorsLsit)



    }

}