package com.example.farmhouseapp.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.farmhouseapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupwithEmailPassword : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var verificationId: String? = null
    private var mFirebaseDatabase: DatabaseReference? = null
    private var mFirebaseUser: FirebaseUser? = null
    private var mFirebaseInstance: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupwith_email_password)
        mAuth = FirebaseAuth.getInstance()
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance?.getReference()




    }
}