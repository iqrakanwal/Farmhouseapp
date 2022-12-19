package com.example.farmhouseapp.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.farmhouseapp.R
import com.example.farmhouseapp.SharedPreferencesUtils
import com.example.farmhouseapp.Users
import com.example.farmhouseapp.models.User
import com.example.farmhouseapp.utils.Constants
import com.example.farmhouseapp.utils.Constants.Companion.senderid
import com.example.farmhouseapp.utils.Constants.Companion.users
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up_form.*
import java.lang.Exception

class SignUpForm : AppCompatActivity() {
    // ...
// Initialize Firebase Auth
    private lateinit var userSelected: String
    private var mAuth: FirebaseAuth? = null
    private var verificationId: String? = null
    private var mFirebaseDatabase: DatabaseReference? = null
    private var mFirebaseUser: FirebaseUser? = null
    private var mFirebaseInstance: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_form)
        mAuth = FirebaseAuth.getInstance()
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance?.getReference()
        /*   get_opt_btn.setOnClickListener {
               if (TextUtils.isEmpty(phone_num_et?.getText().toString())) {
                   // when mobile number text field is empty
                   // displaying a toast message.
                   Toast.makeText(
                       this@SignUpForm,
                       "Please enter a valid phone number.",
                       Toast.LENGTH_SHORT
                   ).show()
               } else {
                   // if the text field is not empty we are calling our
                   // send OTP method for getting OTP from Firebase.
                   val phone = "+92" + phone_num_et?.getText().toString()
                   sendVerificationCode(phone)
               }
           }*/

        role_et.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                Log.e("fjhdhdjfhf", "${p0?.id}")
                val selectedId: Int? = p0?.getCheckedRadioButtonId()
                var id = 0
                var radioButton = findViewById(selectedId!!) as RadioButton
                Log.e("fdglk", "${radioButton.id}   ${selectedId.toString()}")

                if (p1 == R.id.buyyer) {
                    Log.e("radioid", "centimeterture")
                    userSelected = "${Users.BUYER}"
                } else if (p1 == R.id.farmer) {
                    userSelected = "${Users.SELLER}"
                } else if (p1 == R.id.doctor) {
                    userSelected = "${Users.DOCTOR}"


                }
            }


        })


        image1.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(name.getText())) {
                name.setError("required")
                name.requestFocus()
            } else if (TextUtils.isEmpty(phone_num_et.getText())) {
                phone_num_et.setError("required")
                phone_num_et.requestFocus()
            } else if (TextUtils.isEmpty(password.getText())) {
                password.setError("required")
                password.requestFocus()
            } else if (password_et.text.trim().toString() != password_again_et.text.trim()
                    .toString()
            ) {
                Toast.makeText(this, "Password did not match!", Toast.LENGTH_SHORT).show()

            } else {
                mAuth?.createUserWithEmailAndPassword(
                    email_et.text.toString(),
                    password_et.text.toString()
                )
                    ?.addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val firebaseUseruser = mAuth?.currentUser
                            val user = User()
                            user.name = name_et.getText().toString()
                            user.mobile_num = phone_num_et.getText().toString()
                            user.password = password_et.getText().toString()
                            if (userSelected != null) {
                                user.role = userSelected
                            }
                            user.email = email_et.getText().toString()
                            SharedPreferencesUtils.setUUid(this,  mAuth?.getCurrentUser()?.getUid().toString() )
                            FirebaseDatabase.getInstance().reference.child("${users}").push()
                                .setValue(user)
                                .addOnSuccessListener {
                                    SharedPreferencesUtils.setFirstName(
                                        this,
                                        name_et.getText().toString()
                                    )
                                    SharedPreferencesUtils.setUserRole(
                                        this,
                                        userSelected)
                                    SharedPreferencesUtils.setUserEmail(
                                        this,
                                        email_et.getText().toString()
                                    )

                                    SharedPreferencesUtils.setUserPhone(this,
                                        phone_num_et.getText().toString())
                                    Toast.makeText(
                                        applicationContext,
                                        "Signup Sucessfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this@SignUpForm, MainScreen::class.java))
                                    finish()
                                }
                                .addOnFailureListener(object : OnFailureListener {
                                    override fun onFailure(p0: Exception) {

                                    }

                                });


                            //   updateUI(user)
                        } else if(task.isCanceled) {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed. ${task.exception}}", Toast.LENGTH_SHORT
                            ).show()
                            //updateUI(null)
                        }
                    }


                /*

*/
            }


        })

    }

    /*  private fun sendVerificationCode(number: String) {
          // this method is used for getting
          // OTP on user phone number.
          val options = PhoneAuthOptions.newBuilder(mAuth!!)
              .setPhoneNumber(number) // Phone number to verify
              .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
              .setActivity(this) // Activity (for callback binding)
              .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
              .build()
          PhoneAuthProvider.verifyPhoneNumber(options)
      }*/

    /*  private val   // initializing our callbacks for on
      // verification callback method.
              mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
          object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
              // below method is used when
              // OTP is sent from Firebase
              override fun onCodeSent(
                  s: String,
                  forceResendingToken: PhoneAuthProvider.ForceResendingToken
              ) {
                  super.onCodeSent(s, forceResendingToken)
                  // when we receive the OTP it
                  // contains a unique id which
                  // we are storing in our string
                  // which we have already created.
                  verificationId = s
                  Toast.makeText(this@SignUpForm, "${verificationId}", Toast.LENGTH_SHORT).show()
              }

              // this method is called when user
              // receive OTP from Firebase.
              override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                  // below line is used for getting OTP code
                  // which is sent in phone auth credentials.
                  val code = phoneAuthCredential.smsCode

                  // checking if the code
                  // is null or not.
                  if (code != null) {
                      // if the code is not null then
                      // we are setting that code to
                      // our OTP edittext field.
                     *//* opt.visibility = View.VISIBLE
                    otp_et.visibility = View.VISIBLE*//*
                    otp_et?.setText(code)

                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verifycode method.
                    verifyCode(code)
                }
            }

            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {
                // displaying error message with firebase exception.
                Toast.makeText(this@SignUpForm, e.message, Toast.LENGTH_LONG).show()
            }
        }

    // below method is use to verify code from Firebase.
    private fun verifyCode(code: String) {
        // below line is used for getting
        // credentials from our verification id and code.
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    // if the code is correct and the task is successful
                    // we are sending our user to new activity.
                    val i = Intent(this@SignUpForm, MainScreen::class.java)
                    startActivity(i)
                    finish()
                } else {
                    // if the code is not correct then we are
                    // displaying an error message to the user.
                    Toast.makeText(this@SignUpForm, task.exception!!.message, Toast.LENGTH_LONG)
                        .show()
                }
            })
    }*/

}