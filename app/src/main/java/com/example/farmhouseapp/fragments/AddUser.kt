package com.example.farmhouseapp.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.farmhouseapp.Possibilities
import com.example.farmhouseapp.R
import com.example.farmhouseapp.utils.Constants.Companion.userType
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_user.*
import kotlinx.android.synthetic.main.fragment_add_user.phone_num_et
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AddUser : Fragment() {
    private val adViewModel: MainViewModel by sharedViewModel()
private var progressDailog:ProgressDialog?= null

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
        progressDailog= ProgressDialog(requireContext())
        role_et.text = userType
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
                progressDailog?.show()
                adViewModel.addUser(
                    name_user.text.toString(),
                    phone_num_et.text.toString(),
                    userType,
                    email_et_user.text.toString(),
                    "admin123", ::Done
                )


            }
        }
    }

    private fun Done(s: String) {
        if (s == "${Possibilities.SUCCESS}") {
            progressDailog?.dismiss()
            findNavController().navigate(R.id.action_addUser_to_adminMainScreen)
        }else if(s=="${Possibilities.FAILED}"){
            name_user.text.clear()
            phone_num_et.text.clear()
            email_et_user.text.clear()


        }
    }
}

