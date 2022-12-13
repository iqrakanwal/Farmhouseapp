package com.example.farmhouseapp.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.farmhouseapp.R
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_user.*
import kotlinx.android.synthetic.main.fragment_add_user.phone_num_et
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AddUser : Fragment() {


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
        return inflater.inflate(R.layout.fragment_add_user, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (adViewModel.userTile != null)
            addwhat.text = adViewModel.getTitle()





        add.setOnClickListener {

            if (TextUtils.isEmpty(name_user.getText())) {
                name_user.setError("required")
                name_user.requestFocus()
            } else if (TextUtils.isEmpty(phone_num_et.getText())) {
                phone_num_et.setError("required")
                phone_num_et.requestFocus()
            } else if (TextUtils.isEmpty(email_et_user.getText())) {
                email_et_user.setError("required")
                email_et_user.requestFocus()
            } else {


                adViewModel.addUser(
                    name_user.text.toString(),
                    phone_num_et.text.toString(),
                    adViewModel.getUserType().toString(),
                    email_et_user.text.toString(),
                    "admin123"
                )


            }
        }
    }
}

