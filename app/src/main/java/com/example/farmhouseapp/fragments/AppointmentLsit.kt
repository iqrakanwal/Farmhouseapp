package com.example.farmhouseapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farmhouseapp.AppointmentsAdaptor
import com.example.farmhouseapp.OrdersAdaptor
import com.example.farmhouseapp.R
import com.example.farmhouseapp.models.Appointments
import com.example.farmhouseapp.models.Orders
import com.example.farmhouseapp.ui.FirstScreen
import com.example.farmhouseapp.ui.FirstScreen.Companion.userAccount
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_appointment_lsit.*
import kotlinx.android.synthetic.main.fragment_order_history2.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.ArrayList


class AppointmentLsit : Fragment() {
    private val adViewModel: MainViewModel by sharedViewModel()
    lateinit var ordersAdaptor: AppointmentsAdaptor
    var arrayList: ArrayList<Appointments?> = arrayListOf()
    lateinit var layout: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment_lsit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout = LinearLayoutManager(requireContext())
        adViewModel.getAppointment("${userAccount.userName}") { it
            arrayList.clear()
            if (it.size == null) {
                Toast.makeText(requireContext(), "Not Available", Toast.LENGTH_SHORT).show()
            } else {
                arrayList.addAll(it)
                ordersAdaptor = AppointmentsAdaptor(requireContext(), arrayList, ::itemCliked)
                appointmentss.layoutManager = layout
                val dividerItemDecoration = DividerItemDecoration(
                    appointmentss.getContext(),
                    layout.getOrientation()
                )
                appointmentss.addItemDecoration(dividerItemDecoration)
                appointmentss.adapter = ordersAdaptor
            }


        }

    }

    private fun itemCliked(orders: Appointments) {
        adViewModel.serAppointment(orders)
        findNavController().navigate(R.id.action_appointmentLsit_to_appointmentDetails)


    }
}