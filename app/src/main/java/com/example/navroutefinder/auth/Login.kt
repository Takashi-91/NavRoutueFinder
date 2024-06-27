package com.example.navroutefinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.navroutefinder.auth.Signup
import com.example.navroutefinder.ui.Dashboard
import com.google.firebase.auth.FirebaseAuth
class Login : AppCompatActivity() {


    private lateinit var  mAuth: FirebaseAuth
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewSignUp:TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("StudentLogin", "Context: $this")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        editTextUsername = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.button_login)
        textViewSignUp = findViewById(R.id.ViewLogin)



        buttonLogin.setOnClickListener {
            loginUser()
        }
        textViewSignUp.setOnClickListener {
            startActivity(Intent(this@Login, Signup::class.java))
        }
    }


    private fun googleLoginUser() {
        val email = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()


    }

    private fun loginUser() {
        val email = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()



        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this@Login, "Please enter all the details", Toast.LENGTH_SHORT)
                .show()
            return
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@Login, "Login successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@Login, Dashboard::class.java))
            } else {
                Toast.makeText(this@Login, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}