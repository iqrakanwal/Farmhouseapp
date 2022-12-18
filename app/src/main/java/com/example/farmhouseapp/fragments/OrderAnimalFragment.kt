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
import com.example.farmhouseapp.models.FarmName
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_order_animal.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class OrderAnimalFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_order_animal, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adViewModel.getAnimal()
        adViewModel.getFarmFromAnimal(adViewModel.getAnimal().farmName.toString(), ::farmName)
        animalName.text = adViewModel.getAnimal().name
        farmname.text = adViewModel.getAnimal().farmName
        breedname.text = adViewModel.getAnimal().catagory
        price.text = adViewModel.getAnimal().price
        quantityavailable.text = adViewModel.getAnimal().quantity
        Glide.with(requireContext()).load(adViewModel.getAnimal().images).into(coverPhoto)
        // Toast.makeText(requireContext(), "${adViewModel.getAnimal().name}" ,Toast.LENGTH_SHORT).show()
        ordernow.setOnClickListener {
            findNavController().navigate(R.id.action_orderAnimalFragment_to_addBillingInformation)
        }
        chatwithseller.setOnClickListener {
            SharedPreferencesUtils.setOwner(requireContext(), adViewModel.getFarm().farmOwner)

            findNavController().navigate(R.id.action_orderAnimalFragment_to_chattingwithFargment)
        }

    }

    private fun farmName(farmName: FarmName) {
        farmlocation.text = farmName.location
        farmPhonenum.text = farmName.phoneNo
        onwername.text = farmName.farmOwner
        adViewModel.setFarm(farmName)
    }

}