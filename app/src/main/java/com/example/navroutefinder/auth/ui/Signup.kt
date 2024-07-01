package com.example.navroutefinder.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.navroutefinder.R
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.navroutefinder.auth.emailPasswordAuth.LoginHelper
import com.google.firebase.auth.FirebaseAuth

class Signup : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var registerBtn: Button
    private lateinit var textViewLogin:TextView
    private lateinit var loginHelper: LoginHelper



        override fun onCreate(savedInstanceState: Bundle?) {
            Log.d("SignUp", "Context: $this")
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_signup)

        editTextUsername = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        registerBtn = findViewById(R.id.button_register)
        textViewLogin = findViewById(R.id.ViewLogin)
        mAuth = FirebaseAuth.getInstance()
        loginHelper = LoginHelper(this, mAuth)


        registerBtn.setOnClickListener {
            val email = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
           loginHelper.registerUser(email,password)
        }

        textViewLogin.setOnClickListener {
            startActivity(Intent(this@Signup, Login::class.java))
        }
    }


}