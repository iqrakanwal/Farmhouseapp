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
import com.example.farmhouseapp.AnimalAdaptor
import com.example.farmhouseapp.DoctorsAdaptor
import com.example.farmhouseapp.R
import com.example.farmhouseapp.models.Doctor
import com.example.farmhouseapp.ui.FirstScreen
import com.example.farmhouseapp.ui.FirstScreen.Companion.userAccount
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_doctors_lsit.*
import kotlinx.android.synthetic.main.fragment_show_farm.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DoctorsLsit : Fragment() {
    lateinit var animalAdaptor: DoctorsAdaptor
    lateinit var layout: LinearLayoutManager
    private val adViewModel: MainViewModel by sharedViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors_lsit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adViewModel.getAllDoctors(
            userAccount.userName,
            ::allDoctor
        )

    }


    fun allDoctor(list: java.util.ArrayList<Doctor?>){
        if (list.size == null) {
            Toast.makeText(requireContext(), "no animal in this farm", Toast.LENGTH_SHORT).show()
        } else {
            layout = LinearLayoutManager(requireContext())
            animalAdaptor = DoctorsAdaptor(requireContext(), list, ::itemClicked)
            doctors.layoutManager = layout
            val dividerItemDecoration = DividerItemDecoration(
                doctors.getContext(),
                layout.getOrientation()
            )

            doctors.addItemDecoration(dividerItemDecoration)
            doctors.adapter = animalAdaptor
        }

    }

    private fun itemClicked(animal: Doctor) {
        adViewModel.setDoctors(animal)
        findNavController().navigate(R.id.action_doctorsLsit_to_doctorDetails)
    }
}