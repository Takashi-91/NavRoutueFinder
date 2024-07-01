package com.example.navroutefinder.auth.emailPasswordAuth

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.navroutefinder.auth.Login
import com.example.navroutefinder.ui.Dashboard
import com.google.firebase.auth.FirebaseAuth

class LoginHelper(private val activity: Activity, private val mAuth: FirebaseAuth) {

    fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Please enter all the details", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show()
                activity.startActivity(Intent(activity, Dashboard::class.java))
            } else {
                Toast.makeText(activity, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }
        fun registerUser(email: String, password: String) {

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(activity, "Please enter all the details", Toast.LENGTH_SHORT).show()
                return
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "Registration successful", Toast.LENGTH_SHORT).show()
                    activity.startActivity(Intent(activity, Login::class.java))
                } else {
                    Toast.makeText(activity, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

