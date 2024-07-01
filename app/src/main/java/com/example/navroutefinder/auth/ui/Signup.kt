package com.example.navroutefinder.auth.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.navroutefinder.R
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.navroutefinder.auth.emailPasswordAuth.emailPasswordHelper
import com.google.firebase.auth.FirebaseAuth

class Signup : AppCompatActivity() {

    private lateinit var emailPasswordHelper: emailPasswordHelper
    private lateinit var mAuth: FirebaseAuth
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextUsername: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var registerBtn: Button
    private lateinit var textViewLogin: TextView
    private lateinit var emailValid: TextView
    private lateinit var usernameValid: TextView
    private lateinit var passwordValid: TextView
    private lateinit var passwordConValid: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("SignUp", "Context: $this")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail)
        registerBtn = findViewById(R.id.button_register)
        textViewLogin=findViewById(R.id.ViewLogin)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Initialize emailPasswordHelper
        emailPasswordHelper = emailPasswordHelper(this, mAuth)

        // Set click listeners
        registerBtn.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val confirmPassword = editTextConfirmPassword.text.toString().trim()

            emailPasswordHelper.registerUser(
                email, username, password, confirmPassword,
                emailValid, usernameValid, passwordValid, passwordConValid
            )
        }

        textViewLogin.setOnClickListener {
            startActivity(Intent(this@Signup, Login::class.java))
        }
    }
}
