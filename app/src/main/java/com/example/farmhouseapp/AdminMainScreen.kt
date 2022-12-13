package com.example.farmhouseapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_admin_main_screen.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AdminMainScreen : Fragment() {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_main_screen, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFarm.setOnClickListener {
            adViewModel.setTitle("Add Farm")
            findNavController().navigate(R.id.action_adminMainScreen_to_addFarmHouse)
        }
        addAnimal.setOnClickListener {
            adViewModel.setTitle("Add Farm")
            findNavController().navigate(R.id.action_adminMainScreen_to_addAnimals)
        }
        edtiFarm.setOnClickListener {
            adViewModel.setTitle("Add Farm")


        }
        deleteFarm.setOnClickListener { }
        addbuyer.setOnClickListener {
            adViewModel.setTitle("Add Buyer")
            adViewModel.setUserType("Buyer")

            findNavController().navigate(R.id.action_adminMainScreen_to_addUser)
        }
        addseller.setOnClickListener {
            adViewModel.setTitle("Add Seller")
            adViewModel.setUserType("Seller")

            findNavController().navigate(R.id.action_adminMainScreen_to_addUser)
        }
        adddoctor.setOnClickListener {
            adViewModel.setTitle("Add Doctor")
            adViewModel.setUserType("Doctor")
            findNavController().navigate(R.id.action_adminMainScreen_to_addDoctor)
        }


    }
}