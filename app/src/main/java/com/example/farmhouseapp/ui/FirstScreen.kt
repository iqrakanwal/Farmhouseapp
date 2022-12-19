package com.example.farmhouseapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.farmhouseapp.R
import com.example.farmhouseapp.UserAccount
import com.example.farmhouseapp.utils.Constants
import kotlinx.android.synthetic.main.activity_first_screen.*

class FirstScreen : AppCompatActivity() {
    companion object {
        lateinit var userAccount: UserAccount
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)
        admin.setOnClickListener {
            startActivity(Intent(this@FirstScreen, LoginAdmnActivity::class.java))
           // finish()
        }
        buyer.setOnClickListener {
            startActivity(Intent(this@FirstScreen, LoginActivity::class.java))
           // finish()
        }
        seller.setOnClickListener {
            startActivity(Intent(this@FirstScreen, LoginActivity::class.java))
           // finish()
        }
        doctor.setOnClickListener {
            startActivity(Intent(this@FirstScreen, LoginActivity::class.java))
          //  finish()
        }


    }
}