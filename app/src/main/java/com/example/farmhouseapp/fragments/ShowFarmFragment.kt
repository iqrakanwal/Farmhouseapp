package com.example.farmhouseapp.fragments

import android.os.Bundle
import android.util.Log
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
import com.example.farmhouseapp.ui.FirstScreen.Companion.userAccount
import com.example.farmhouseapp.utils.Constants.Companion.farmid
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


        Log.e("dfhkdjfh", "${userAccount.userName}")
        Log.e("dfhkdjfh", "${userAccount.email}")
        Log.e("dfhkdjfh", "${userAccount.role}")
        Log.e("dfhkdjfh", "${userAccount.password}")
        Log.e("dfhkdjfh", "${userAccount.userName}")
       // farmname.text = SharedPreferencesUtils.getFarmName(requireContext()).toString()
        adViewModel.getSellerFarm(
            userAccount.userName,
            ::getFarm
        )

        addFarmicon.setOnClickListener {
            findNavController().navigate(R.id.action_showFarmFragment_to_addFarmInSeller)
        }



    }

    fun getFarm(farm: FarmName) {
        Log.e("hjhj", "${farm.name}")
        if (farm != null) {
            coverphoto.visibility = View.VISIBLE
            profilepicture.visibility = View.VISIBLE
            farmname.visibility = View.VISIBLE
            numoffarms.visibility = View.VISIBLE
            animals.visibility = View.VISIBLE
            addFarmicon.visibility = View.GONE
            animalsoffarms.visibility = View.VISIBLE
            Toast.makeText(requireContext(), "${farm.name}fxgfdgffd", Toast.LENGTH_SHORT).show()
                farmname.text = farm.name
            locationoffarm.text = farm.location
            numoffarms.text = farm.phoneNo
            Glide.with(requireContext()).load(farm.coverProfile).into(coverphoto)
            Glide.with(requireContext()).load(farm.coverProfile).into(profilepicture)
            farmid=farm
            Yourorder.setOnClickListener {
                findNavController().navigate(R.id.action_showFarmFragment_to_yourOrders)
            }

            chats.setOnClickListener {
                findNavController().navigate(R.id.action_showFarmFragment_to_chatList)



            }
            getAnimalofFarm(farmid.name)
        } else {
            coverphoto.visibility = View.GONE
            profilepicture.visibility = View.GONE
            farmname.visibility = View.GONE
            numoffarms.visibility = View.GONE
            animals.visibility = View.GONE
            animalsoffarms.visibility = View.GONE
            locationoffarm.visibility = View.GONE
            locationoffarm.visibility = View.VISIBLE


        }

    }

    private fun getAnimalofFarm(name: String) {
        adViewModel.getAnimalsofFarm(
            name, ::listOfAnimal
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

    override fun onResume() {
        super.onResume()


     /*   farmname.text = SharedPreferencesUtils.getFarmName(requireContext()).toString()
        adViewModel.getSellerFarm(
            userAccount.userName,
            ::getFarm
        )*/

    }

}



