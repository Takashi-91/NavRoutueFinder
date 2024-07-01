package com.example.navroutefinder.auth.emailPasswordAuth

import com.example.navroutefinder.models.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class SaveToDatabase {

    companion object Database {
        fun addUserToDatabase(user: FirebaseUser, username: String) {
            val db = FirebaseDatabase.getInstance()
            val usersRef = db.getReference("Users")

            val userId = user.uid
            val email = user.email

            // Create a new user object without password
            val newUser = User(
                uid = userId,
                username = username,
                email = email ?: "" // Provide a default value if email is null
            )

            usersRef.child(userId).setValue(newUser).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                } else {

                }
            }
        }
    }
}
