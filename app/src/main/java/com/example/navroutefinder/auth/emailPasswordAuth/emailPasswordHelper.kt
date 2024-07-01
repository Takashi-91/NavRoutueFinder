package com.example.navroutefinder.auth.emailPasswordAuth

import android.app.Activity
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.navroutefinder.R
import com.example.navroutefinder.auth.ui.Login
import com.example.navroutefinder.ui.Dashboard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class emailPasswordHelper(private val activity: Activity, private val mAuth: FirebaseAuth) {

    // Password validation criteria
    private fun isPasswordStrong(password: String): Boolean {
        val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
        return passwordPattern.matches(password)
    }

    // Email validation using Android's built-in Patterns
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Check if the email or username exists in the database
    private fun doesUserExist(email: String, username: String, callback: (Boolean) -> Unit) {
        val database = FirebaseDatabase.getInstance().reference
        database.child("users").orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        callback(true)
                    } else {
                        database.child("users").orderByChild("username").equalTo(username)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    callback(snapshot.exists())
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    callback(false)
                                }
                            })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(false)
                }
            })
    }

    fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Please enter all the details", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isEmailValid(email)) {
            Toast.makeText(activity, "Invalid email format", Toast.LENGTH_SHORT).show()
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
    }

    fun registerUser(
        email: String, username: String, password: String, confirmPassword: String,
        emailValid: TextView, usernameValid: TextView,
        passwordValid: TextView, passwordConValid: TextView
    )  {
        var isValid = true

        // Reset error messages
        emailValid.visibility = View.GONE
        usernameValid.visibility = View.GONE
        passwordValid.visibility = View.GONE
        passwordConValid.visibility = View.GONE

        if (email.isEmpty()) {
            emailValid.text = activity.getString(R.string.email_is_required)
            emailValid.visibility = View.VISIBLE
            isValid = false
        } else if (!isEmailValid(email)) {
            emailValid.text = activity.getString(R.string.invalid_email_format)
            emailValid.visibility = View.VISIBLE
            isValid = false
        }

        if (username.isEmpty()) {
            usernameValid.text = activity.getString(R.string.username_is_required)
            usernameValid.visibility = View.VISIBLE
            isValid = false
        }

        if (password.isEmpty()) {
            passwordValid.text = activity.getString(R.string.password_is_required)
            passwordValid.visibility = View.VISIBLE
            isValid = false
        } else if (!isPasswordStrong(password)) {
            passwordValid.text =
                activity.getString(R.string.password_must_be_at_least_8_characters_long_contain_at_least_one_uppercase_letter_one_lowercase_letter_one_number_and_one_special_character)
            passwordValid.visibility = View.VISIBLE
            isValid = false
        }

        if (confirmPassword.isEmpty()) {
            passwordConValid.text = activity.getString(R.string.confirm_password_is_required)
            passwordConValid.visibility = View.VISIBLE
            isValid = false
        } else if (password != confirmPassword) {
            passwordConValid.text = activity.getString(R.string.passwords_do_not_match)
            passwordConValid.visibility = View.VISIBLE
            isValid = false
        }

        if (!isValid) {
            return
        }

        doesUserExist(email, username) { exists ->
            if (exists) {
                emailValid.text = activity.getString(R.string.user_already_exists)
                emailValid.visibility = View.VISIBLE
            } else {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        user?.let { SaveToDatabase.addUserToDatabase(it, username) }
                        activity.startActivity(Intent(activity, Login::class.java))
                    } else {
                        Toast.makeText(activity, "Retry Registration Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
