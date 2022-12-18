package com.example.farmhouseapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.farmhouseapp.AnimalAdaptor
import com.example.farmhouseapp.R
import com.example.farmhouseapp.SharedPreferencesUtils
import com.example.farmhouseapp.models.Animal
import com.example.farmhouseapp.models.FarmName
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_show_farm.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ShowFarmFragment : Fragment() {
    lateinit var animalAdaptor: AnimalAdaptor
    lateinit var layout: LinearLayoutManager
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
        return inflater.inflate(R.layout.fragment_show_farm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(
            requireContext(), "${SharedPreferencesUtils.getFarmName(requireContext())}",
            Toast.LENGTH_SHORT
        ).show()
       //SharedPreferencesUtils.setFarmName(requireContext(), "")
        if (SharedPreferencesUtils.getFarmName(requireContext()).toString() == "") {
            coverphoto.visibility = View.GONE
            profilepicture.visibility = View.GONE
            farmname.visibility = View.GONE
            numoffarms.visibility = View.GONE
            animals.visibility = View.GONE
            animalsoffarms.visibility = View.GONE
            locationoffarm.visibility = View.GONE
        } else {
            coverphoto.visibility = View.VISIBLE
            profilepicture.visibility = View.VISIBLE
            farmname.visibility = View.VISIBLE
            numoffarms.visibility = View.VISIBLE
            animals.visibility = View.VISIBLE
            addFarmicon.visibility = View.GONE
            animalsoffarms.visibility = View.VISIBLE
            locationoffarm.visibility = View.VISIBLE

        }
        addFarmicon.setOnClickListener {

            findNavController().navigate(R.id.action_showFarmFragment_to_addFarmInSeller)
        }
        farmname.text = SharedPreferencesUtils.getFarmName(requireContext()).toString()
        adViewModel.getSellerFarm(
            SharedPreferencesUtils.getFirstName(requireContext()).toString(),
            ::getFarm
        )


    }

    fun getFarm(farm: FarmName) {
        Toast.makeText(requireContext(), "${farm}fxgfdgffd", Toast.LENGTH_SHORT).show()
        farmname.text = farm.name
        locationoffarm.text = farm.location
        numoffarms.text = farm.phoneNo
        Glide.with(requireContext()).load(farm.coverProfile).into(coverphoto)
        Glide.with(requireContext()).load(farm.coverProfile).into(profilepicture)
        getAnimalofFarm()
    }


    fun getAnimalofFarm() {
        adViewModel.getAnimalsofFarm(
            SharedPreferencesUtils.getFarmName(requireContext()).toString(), ::listOfAnimal
        )

    }

    private fun listOfAnimal(animals: java.util.ArrayList<Animal?>) {
        if (animals.size == null) {
            Toast.makeText(requireContext(), "no animal in this farm", Toast.LENGTH_SHORT).show()
        } else {
            layout = LinearLayoutManager(requireContext())
            animalAdaptor = AnimalAdaptor(requireContext(), animals, ::itemClicked)
            animalsoffarms.layoutManager = layout
            val dividerItemDecoration = DividerItemDecoration(
                animalsoffarms.getContext(),
                layout.getOrientation()
            )
            animalsoffarms.addItemDecoration(dividerItemDecoration)
            animalsoffarms.adapter = animalAdaptor
        }


    }

    private fun itemClicked(animal: Animal) {


    }


}



