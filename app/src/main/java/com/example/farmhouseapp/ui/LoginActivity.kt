package com.example.farmmanagment.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.farmhouseapp.R
import com.example.farmhouseapp.ui.MainScreen
import com.example.farmhouseapp.ui.SignUpForm
import com.example.farmhouseapp.utils.Constants
import com.example.farmhouseapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var verificationId: String? = null
    private var mFirebaseDatabase: DatabaseReference? = null
    private var mFirebaseUser: FirebaseUser? = null
    private var mFirebaseInstance: FirebaseDatabase? = null
    private var arraylist: ArrayList<User>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance?.getReference()
        arraylist = arrayListOf()
        image1_login.setOnClickListener {
            mAuth?.signInWithEmailAndPassword(
                email_et_login.text.toString(),
                password_et_login.text.toString()
            )
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                     //   Toast.makeText(this, "${task.result.user?.email}", Toast.LENGTH_SHORT ).show()
                        arraylist?.clear()
                        mFirebaseDatabase?.child("user")?.get()?.addOnSuccessListener { it ->
                            val day: User? = it.getValue(User::class.java)
                            arraylist?.add(day!!);
                            for (d in arraylist!!) {
                                if (d.email == task.result.user?.email) {
                                    Constants.userEmail = task.result.user?.email!!
                                    Constants.userRole = d.role
                                }
                            }
                            startActivity(Intent(this, MainScreen::class.java))
                            finish()
                        }?.addOnFailureListener {
                            Log.e("firebase", "Error getting data", it)
                        }


                        // Sign in success, update UI with the signed-in user's information
                        // Log.d(TAG, "signInWithEmail:success")
                        //val user = auth.currentUser
                        // updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        // Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        // updateUI(null)
                    }
                }

        }


        /*     database = Firebase.database.reference
             database.child("user")
             database.addValueEventListener(object : ValueEventListener {
                 override fun onDataChange(dataSnapshot: DataSnapshot) {
                     for (data in dataSnapshot.children) {
                         if (data.key.toString() == "user") {
                             val orderNumber = data.value.toString()
                             Log.d("Specific Node Value", orderNumber)
                         }
                     }
                 }

                 override fun onCancelled(databaseError: DatabaseError) {}
             })*/
        //   getUsersData()
        signup.setOnClickListener {
            startActivity(Intent(this, SignUpForm::class.java))
            finish()

        }
    }

  /*  fun getUsersData() {

        Toast.makeText(this, "${database.child("user").key}", Toast.LENGTH_SHORT).show()
        database.child("user").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }

    }
*/

}