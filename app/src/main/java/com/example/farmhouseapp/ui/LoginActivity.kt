package com.example.farmhouseapp.ui
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.farmhouseapp.R
import com.example.farmhouseapp.SharedPreferencesUtils
import com.example.farmhouseapp.UserAccount
import com.example.farmhouseapp.Users
import com.example.farmhouseapp.models.Orders
import com.example.farmhouseapp.utils.Constants
import com.example.farmhouseapp.models.User
import com.example.farmhouseapp.ui.FirstScreen.Companion.userAccount
import com.example.farmhouseapp.utils.Constants.Companion.senderid
import com.example.farmhouseapp.utils.Constants.Companion.users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up_form.*


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
                        mFirebaseDatabase?.child("${users}")?.get()?.addOnSuccessListener { it ->
                            Log.e("jckx", "${it.children}")
                            it.children
                            for (it in it.getChildren()) {
                                val day: User = it.getValue(User::class.java)!!
                                if(day.email==task.result.user?.email){
                                    userAccount = UserAccount()
                                    userAccount.userName = day.name
                                    userAccount.phone = day.mobile_num
                                    userAccount.email = day.email
                                    userAccount.password = day.password
                                    userAccount.role = day.role


                                }
                            }
                            SharedPreferencesUtils.setUUid(this,  mAuth?.getCurrentUser()?.getUid().toString() )
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