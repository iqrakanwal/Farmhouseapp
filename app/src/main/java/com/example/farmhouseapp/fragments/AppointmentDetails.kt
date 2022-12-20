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
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_appointment_details.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AppointmentDetails : Fragment() {
    private val adViewModel: MainViewModel by sharedViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adViewModel.getAppointment().appointmentstatus
        sellernamesellername.text = adViewModel.getAppointment().sellername
        doctdesignationap.text = adViewModel.getAppointment().doctorname.designation
        docthospitalsap.text = adViewModel.getAppointment().doctorname.hospital
        slot.text = adViewModel.getAppointment().slot
        contactno.text = adViewModel.getAppointment().doctorname.contactnum
        doneappointment.setOnClickListener {
            adViewModel.updateAppointment("${ApointmentStatus.DONE}", ::appointmenttaken)
        }
    }

    private fun appointmenttaken(s: String) {
        Toast.makeText(requireContext(), "${s}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_appointmentDetails_to_appointmentLsit)
    }

}