package com.example.farmhouseapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farmhouseapp.models.Animal
import com.example.farmhouseapp.models.User
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_admin_main_screen.*
import kotlinx.android.synthetic.main.fragment_show_animal.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.ArrayList


class AdminMainScreen : Fragment() {
    private val adViewModel: MainViewModel by sharedViewModel()
    lateinit var usersAdaptor: UsersAdaptor
    var byer: ArrayList<User?> = arrayListOf()
    var farmer: ArrayList<User?> = arrayListOf()
    var doctorarr: ArrayList<User?> = arrayListOf()
    lateinit var layout: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_main_screen, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFarmerUser()
        loadBuyerUser()
        loadDoctorUser()

        /*     addFarm.setOnClickListener {
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
             }*/


    }

    private fun loadDoctorUser() {
        adViewModel.getAllUsers("${Users.DOCTOR}", ::DoneDoctor)

    }

    private fun DoneDoctor(it: ArrayList<User>) {
        doctorarr.clear()
        if (it.size == null) {
            Toast.makeText(requireContext(), "Not Available", Toast.LENGTH_SHORT).show()
        } else {
            doctorarr.addAll(it)
            layout = LinearLayoutManager(requireContext())

            usersAdaptor = UsersAdaptor(requireContext(), doctorarr )
            doctor.layoutManager = layout
            val dividerItemDecoration = DividerItemDecoration(
                doctor.getContext(),
                layout.getOrientation()
            )
            doctor.addItemDecoration(dividerItemDecoration)
            doctor.adapter = usersAdaptor
        }
    }

    private fun loadBuyerUser() {
        adViewModel.getAllUsers("${Users.BUYER}", ::DoneBuyer)
    }

    private fun DoneBuyer(it: ArrayList<User>) {
        byer.clear()
        if (it.size == null) {
            Toast.makeText(requireContext(), "Not Available", Toast.LENGTH_SHORT).show()
        } else {
            byer.addAll(it)
            layout = LinearLayoutManager(requireContext())

            usersAdaptor = UsersAdaptor(requireContext(), byer)
            buyers.layoutManager = layout
            val dividerItemDecoration = DividerItemDecoration(
                buyers.getContext(),
                layout.getOrientation()
            )
            buyers.addItemDecoration(dividerItemDecoration)
            buyers.adapter = usersAdaptor
        }
    }

    private fun loadFarmerUser() {
        adViewModel.getAllUsers("${Users.SELLER}", ::Done)


    }

    private fun Done(it: ArrayList<User>) {
        if (it.size == null) {
            Toast.makeText(requireContext(), "Not Available", Toast.LENGTH_SHORT).show()
        } else {
            farmer.addAll(it)
            layout = LinearLayoutManager(requireContext())
            usersAdaptor = UsersAdaptor(requireContext(), farmer)
            seller.layoutManager = layout
            val dividerItemDecoration = DividerItemDecoration(
                seller.getContext(),
                layout.getOrientation()
            )
            seller.addItemDecoration(dividerItemDecoration)
            seller.adapter = usersAdaptor
        }
    }

    private fun itemCliked(user: User) {

    }
}