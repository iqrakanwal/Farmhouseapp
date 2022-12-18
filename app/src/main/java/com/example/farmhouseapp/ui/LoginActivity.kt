package com.example.farmhouseapp.ui
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.farmhouseapp.R
import com.example.farmhouseapp.SharedPreferencesUtils
import com.example.farmhouseapp.UserAccount
import com.example.farmhouseapp.utils.Constants
import com.example.farmhouseapp.models.User
import com.example.farmhouseapp.ui.FirstScreen.Companion.userAccount
import com.example.farmhouseapp.utils.Constants.Companion.senderid
import com.example.farmhouseapp.utils.Constants.Companion.users
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
                        mFirebaseDatabase?.child("${users}")?.get()?.addOnSuccessListener { it ->
                            Log.e("jckx", "${it.children}")
                            it.children
                            for (it in it.getChildren()) {
                                val day: User? = it.getValue(User::class.java)
                                SharedPreferencesUtils.setUserRole(this, day?.role!!)
                                Log.e("dsfkjsdf", "${day?.name}")
                                Log.e("dsfkjsdf", "${day?.role}")
                                Log.e("dsfkjsdf", "${day?.email}")

                                arraylist?.add(day!!);

                            }
                            Toast.makeText(
                                this,
                                "${SharedPreferencesUtils.getUserRole(this)}",
                                Toast.LENGTH_SHORT
                            ).show()
                            for (d in arraylist!!) {
                                if (d.email == task.result.user?.email) {
                                    SharedPreferencesUtils.setFirstName(this, d.name)
                                    SharedPreferencesUtils.setUserEmail(this, d.email)
                                    userAccount = UserAccount()
                                    userAccount.userName = d.name
                                    userAccount.phone = d.mobile_num
                                    userAccount.email = d.email
                                    userAccount.password = d.password
                                    userAccount.role = d.role
                                    Constants.userEmail = task.result.user?.email!!
                                    Constants.userRole = d.role
                                    SharedPreferencesUtils.setUserRole(this, d.role)



                                    /*    Constants.mainUser.name = d.name
                                        Constants.mainUser.password = d.password
                                        Constants.mainUser.mobile_num = d.mobile_num
                                        Constants.mainUser.role = d.role
                                        Constants.mainUser.email = d.email*/
                                    Toast.makeText(
                                        this,
                                        "${SharedPreferencesUtils.getFirstName(this)}",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }

                            SharedPreferencesUtils.setUUid(this,  mAuth?.getCurrentUser()?.getUid().toString() )
                            // mAuth?.currentUser?.email
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