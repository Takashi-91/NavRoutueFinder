package com.example.navroutefinder

import android.app.Activity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginHelp(private val activity: Activity, private val mAuth: FirebaseAuth) {

    fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
