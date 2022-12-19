package com.example.farmhouseapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.farmhouseapp.R
import com.example.farmhouseapp.SharedPreferencesUtils
import com.example.farmhouseapp.Users
import com.example.farmhouseapp.models.User
import com.example.farmhouseapp.utils.Constants.Companion.adminEmail
import com.example.farmhouseapp.utils.Constants.Companion.adminPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login_admn.*
import kotlinx.android.synthetic.main.activity_sign_up_form.*

class LoginAdmnActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admn)
        loginadmin.setOnClickListener {
            if (TextUtils.isEmpty(email_et_loginadmin.getText())) {
                email_et_loginadmin.setError("required")
                email_et_loginadmin.requestFocus()
            } else if (TextUtils.isEmpty(password_et_loginadmin.getText())) {
                password_et_loginadmin.setError("required")
                password_et_loginadmin.requestFocus()
            } else {

                if (email_et_loginadmin.text.toString().equals("admin") && (password_et_loginadmin.text.toString().equals(
                        "1111"
                    ))
                ) {
                    SharedPreferencesUtils.setUserRole(this, "${Users.ADMIN}")
                    startActivity(Intent(this@LoginAdmnActivity, MainScreen::class.java))
                    finish()

                }

            }


        }


    }
}