package com.example.navroutefinder.auth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.navroutefinder.R
import com.example.navroutefinder.auth.emailPasswordAuth.emailPasswordHelper
import com.example.navroutefinder.auth.facebookAuth.FacebookLoginHelper
import com.example.navroutefinder.auth.googleAuth.GoogleSignInHelper
import com.facebook.login.widget.LoginButton
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewSignUp: TextView
    private lateinit var emailPasswordHelper: emailPasswordHelper
    private lateinit var facebookHelper: FacebookLoginHelper
    private lateinit var googleSignInHelper: GoogleSignInHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Login", "Context: $this")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        editTextUsername = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.ConfirmPassword)
        buttonLogin = findViewById(R.id.button_login)
        textViewSignUp = findViewById(R.id.ViewLogin)
        emailPasswordHelper = emailPasswordHelper(this, mAuth)

        val facebookLoginButton = findViewById<LoginButton>(R.id.facebookBtn)
        facebookHelper = FacebookLoginHelper(this, mAuth, facebookLoginButton) { user ->
            facebookHelper.updateUI(user)
        }

        googleSignInHelper = GoogleSignInHelper(this, mAuth)

        val googleSignInButton = findViewById<SignInButton>(R.id.googleBtn)
        setGooglePlusButtonText(googleSignInButton, "Login with Google")
        googleSignInButton.setOnClickListener {
            googleSignInHelper.signInWithGoogle()
        }
        facebookLoginButton.text = "Login with Facebook"

        buttonLogin.setOnClickListener {
            val email = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            emailPasswordHelper.loginUser(email, password)
        }

        textViewSignUp.setOnClickListener {
            startActivity(Intent(this@Login, Signup::class.java))
        }
    }

    private fun setGooglePlusButtonText(signInButton: SignInButton, buttonText: String) {
        // Find the TextView inside SignInButton and set its text
        for (i in 0 until signInButton.childCount) {
            val v = signInButton.getChildAt(i)
            if (v is TextView) {
                v.text = buttonText
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookHelper.onActivityResult(requestCode, resultCode, data)
        googleSignInHelper.handleActivityResult(requestCode, resultCode, data)
    }
}
