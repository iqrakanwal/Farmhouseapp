package com.example.farmhouseapp.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.farmhouseapp.OrderStatus
import com.example.farmhouseapp.Possibilities
import com.example.farmhouseapp.R
import com.example.farmhouseapp.SharedPreferencesUtils
import com.example.farmhouseapp.models.Orders
import com.example.farmhouseapp.ui.FirstScreen.Companion.userAccount
import com.example.farmhouseapp.utils.Constants.Companion.animalid
import com.example.farmhouseapp.utils.Constants.Companion.farmid
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_animals.*
import kotlinx.android.synthetic.main.fragment_add_billing_information.*
import kotlinx.android.synthetic.main.fragment_order_animal.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AddBillingInformation : Fragment() {
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
        return inflater.inflate(R.layout.fragment_add_billing_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext()).load(animalid.images).into(image)
        productname.text = animalid.name
        productprice.text = animalid.price
        productbreed.text = animalid.catagory
        username.text = userAccount.userName
        email.text = userAccount.email
        phone_num.text = ""
        user_address_et.text
        continuetopayment.setOnClickListener {
            if (TextUtils.isEmpty(user_address_et.getText())) {
                user_address_et.setError("required")
                user_address_et.requestFocus()
            } else {
                var order = Orders()
                order.animalid = animalid
                order.farmName = animalid.name
                order.ownerName = farmid.farmOwner
                order.totalAmount = animalid.price
                order.orderstatus = "${OrderStatus.PENDING}"
                order.buyerName = userAccount.userName
                adViewModel.placeOrder(order, ::Done)
            }
        }
    }

    private fun Done(s: String) {
        if (s == "${Possibilities.SUCCESS}") {
            Toast.makeText(requireContext(), "Order Placed", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addBillingInformation_to_showAnimalFragment)
        } else if (s == "${Possibilities.FAILED}") {
            Toast.makeText(requireContext(), "some issue", Toast.LENGTH_SHORT).show()
        }
    }


}