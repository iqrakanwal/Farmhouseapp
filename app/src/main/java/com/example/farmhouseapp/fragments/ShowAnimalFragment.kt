package com.example.farmhouseapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farmhouseapp.AnimalAdaptor
import com.example.farmhouseapp.R
import com.example.farmhouseapp.models.Animal
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_show_animal.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*


class ShowAnimalFragment : Fragment() {
    private val adViewModel: MainViewModel by sharedViewModel()
    lateinit var animalAdaptor: AnimalAdaptor
    var arrayList: ArrayList<Animal?> = arrayListOf()
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_animal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout = LinearLayoutManager(requireContext())
        adViewModel.getAllAnimals {
            if (it.size == null) {
                Toast.makeText(requireContext(), "Not Available", Toast.LENGTH_SHORT).show()
            } else {
                arrayList.addAll(it)
                animalAdaptor = AnimalAdaptor(requireContext(), arrayList, ::itemCliked)
                animalslist.layoutManager = layout
                val dividerItemDecoration = DividerItemDecoration(
                    animalslist.getContext(),
                    layout.getOrientation()
                )
                animalslist.addItemDecoration(dividerItemDecoration)
                animalslist.adapter = animalAdaptor
            }


        }



        search_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(p0.toString())

            }

            override fun afterTextChanged(p0: Editable?) {
            }


        })


    }

    private fun itemCliked(animal: Animal) {
        adViewModel.setAnimal(animal)
        findNavController().navigate(R.id.action_showAnimalFragment_to_orderAnimalFragment)
    }


    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Animal?> = arrayListOf()

        // running a for loop to compare elements.
        for (item in arrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item?.name?.toLowerCase()?.contains(text.lowercase(Locale.getDefault())) == true) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            animalAdaptor.filterList(filteredlist)
        }
    }

}