package com.example.farmhouseapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farmhouseapp.AnimalAdaptor
import com.example.farmhouseapp.OrdersAdaptor
import com.example.farmhouseapp.R
import com.example.farmhouseapp.models.Animal
import com.example.farmhouseapp.models.Orders
import com.example.farmhouseapp.ui.FirstScreen
import com.example.farmhouseapp.ui.FirstScreen.Companion.userAccount
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_order_history2.*
import kotlinx.android.synthetic.main.fragment_show_animal.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.ArrayList


class OrderHistory : Fragment() {
    private val adViewModel: MainViewModel by sharedViewModel()
    lateinit var ordersAdaptor: OrdersAdaptor
    var arrayList: ArrayList<Orders?> = arrayListOf()
    lateinit var layout: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_history2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout = LinearLayoutManager(requireContext())
      Log.e("username", "${userAccount.userName}")
        adViewModel.getOrderFromBuyer(userAccount.userName) {
            Log.e("username", "${it.size}")

            if (it.size == null) {
                Toast.makeText(requireContext(), "Not Available", Toast.LENGTH_SHORT).show()
            } else {
                arrayList.addAll(it)
                ordersAdaptor = OrdersAdaptor(requireContext(), arrayList, ::itemCliked)
                orders.layoutManager = layout
                val dividerItemDecoration = DividerItemDecoration(
                    orders.getContext(),
                    layout.getOrientation()
                )
                orders.addItemDecoration(dividerItemDecoration)
                orders.adapter = ordersAdaptor
            }


        }


    }

    private fun itemCliked(orders: Orders) {

    }

}