package com.example.farmhouseapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.farmhouseapp.OrderStatus
import com.example.farmhouseapp.R
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_orderdetail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class Orderdetail : Fragment() {

    private val adViewModel: MainViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orderdetail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productname.text = adViewModel.getOrder()?.animalid?.name
        delieverorder.setOnClickListener {
            adViewModel.updateOrder("${OrderStatus.DELIVERED}", ::orderDelivered)
        }

    }

    fun orderDelivered(string: String) {
        Toast.makeText(requireContext(), "${string}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_orderdetail_to_showFarmFragment)
    }
}