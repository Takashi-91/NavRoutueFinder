package com.example.navroutefinder.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.navroutefinder.R
import com.example.navroutefinder.auth.emailPasswordAuth.LoginHelper
import com.example.navroutefinder.auth.facebookAuth.FacebookLoginHelper
import com.example.navroutefinder.auth.googleAuth.GoogleSignInHelper
import com.example.navroutefinder.ui.Dashboard
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewSignUp: TextView
    private lateinit var facebookBtn: LoginButton
    private lateinit var googleBtn: Button
    private lateinit var loginHelper: emailPasswordHelper
    private lateinit var facebookHelper: FacebookLoginHelper
    private lateinit var googleSignInHelper: GoogleSignInHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Login", "Context: $this")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        editTextUsername = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.button_login)
        textViewSignUp = findViewById(R.id.ViewLogin)
        facebookBtn = findViewById(R.id.facebookBtn)
        googleBtn = findViewById(R.id.googleBtn)
        loginHelper = LoginHelper(this, mAuth)
        facebookHelper = FacebookLoginHelper(this, mAuth,facebookBtn) { facebookHelper.updateUI(null) }
        googleSignInHelper = GoogleSignInHelper(this, mAuth)



        buttonLogin.setOnClickListener {
            val email = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            loginHelper.loginUser(email, password)
        }

        textViewSignUp.setOnClickListener {
            startActivity(Intent(this@Login, Signup::class.java))
        }

        googleBtn.setOnClickListener {
            googleSignInHelper.signInWithGoogle()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleSignInHelper.handleActivityResult(requestCode, resultCode, data)
    }

}
