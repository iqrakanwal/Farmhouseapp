package com.example.farmhouseapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.farmhouseapp.ui.MainScreen
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit


class MainShow : AppCompatActivity() {
    // variable for FirebaseAuth class
    private var mAuth: FirebaseAuth? = null

    // variable for our text input
    // field for phone and OTP.
    private var edtPhone: EditText? = null  // variable for our text input

    // field for phone and OTP.
    private var edtOTP: EditText? = null

    // buttons for generating OTP and verifying OTP
    private var verifyOTPBtn: Button? = null  // buttons for generating OTP and verifying OTP
    private var generateOTPBtn: Button? = null

    // string for storing our verification ID
    private var verificationId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_show)
        // of our FirebaseAuth.
        // of our FirebaseAuth.
        mAuth = FirebaseAuth.getInstance()

        // initializing variables for button and Edittext.

        // initializing variables for button and Edittext.
        edtPhone = findViewById<EditText>(R.id.idEdtPhoneNumber)
        edtOTP = findViewById<EditText>(R.id.idEdtOtp)
        verifyOTPBtn = findViewById<Button>(R.id.idBtnVerify)
        generateOTPBtn = findViewById<Button>(R.id.idBtnGetOtp)

        // setting onclick listener for generate OTP button.

        // setting onclick listener for generate OTP button.
        generateOTPBtn?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // below line is for checking whether the user
                // has entered his mobile number or not.
                if (TextUtils.isEmpty(edtPhone?.getText().toString())) {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    Toast.makeText(
                        this@MainShow,
                        "Please enter a valid phone number.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // if the text field is not empty we are calling our
                    // send OTP method for getting OTP from Firebase.
                    val phone = "+92" + edtPhone?.getText().toString()
                    sendVerificationCode(phone)
                }
            }
        })

        // initializing on click listener
        // for verify otp button

        // initializing on click listener
        // for verify otp button
        verifyOTPBtn?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // validating if the OTP text field is empty or not.
                if (TextUtils.isEmpty(edtOTP?.getText().toString())) {
                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(this@MainShow, "Please enter OTP", Toast.LENGTH_SHORT).show()
                } else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    verifyCode(edtOTP?.getText().toString())
                }
            }
        })
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    // if the code is correct and the task is successful
                    // we are sending our user to new activity.
                    val i = Intent(this@MainShow, MainScreen::class.java)
                    startActivity(i)
                    finish()
                } else {
                    // if the code is not correct then we are
                    // displaying an error message to the user.
                    Toast.makeText(this@MainShow, task.exception!!.message, Toast.LENGTH_LONG)
                        .show()
                }
            })
    }


    private fun sendVerificationCode(number: String) {
        // this method is used for getting
        // OTP on user phone number.
        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // callback method is called on Phone auth provider.
    private val   // initializing our callbacks for on
    // verification callback method.
            mCallBack: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            // below method is used when
            // OTP is sent from Firebase
            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
                verificationId = s
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
                    edtOTP?.setText(code)

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
                Toast.makeText(this@MainShow, e.message, Toast.LENGTH_LONG).show()
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
}