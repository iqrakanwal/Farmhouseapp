package com.example.farmhouseapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.farmhouseapp.AdminMainScreen
import com.example.farmhouseapp.R
import com.example.farmmanagment.ui.LoginActivity
import kotlinx.android.synthetic.main.activity_first_screen.*

class FirstScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)
        admin.setOnClickListener {
            startActivity(Intent(this@FirstScreen, AdminScreen::class.java))
            finish()
        }
        buyer.setOnClickListener {
            startActivity(Intent(this@FirstScreen, LoginActivity::class.java))
            finish()
        }
        seller.setOnClickListener {
            startActivity(Intent(this@FirstScreen, LoginActivity::class.java))
            finish()
        }
        doctor.setOnClickListener {
            startActivity(Intent(this@FirstScreen, LoginActivity::class.java))
            finish()
        }


    }
}