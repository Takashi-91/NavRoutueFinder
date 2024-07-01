package com.example.navroutefinder.auth.facebookAuth

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.example.navroutefinder.auth.emailPasswordAuth.SaveToDatabase
import com.example.navroutefinder.models.User
import com.example.navroutefinder.ui.Dashboard
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FacebookLoginHelper(
    private val activity: Activity,
    private val mAuth: FirebaseAuth,
    private val facebookButton: LoginButton,
    private val callback: (FirebaseUser?) -> Unit
) {

    private lateinit var callbackManager: CallbackManager

    init {
        setupFacebookLogin()
    }

    private fun setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create()
        facebookButton.setReadPermissions("email", "public_profile")
        facebookButton.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(TAG, "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d(TAG, "facebook:onError", error)
                }
            }
        )
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth.currentUser
                    callback(user)
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    callback(null)
                    updateUI(null)
                }
            }
    }

    fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // For example, start a new activity
            activity.startActivity(Intent(activity, Dashboard::class.java))
        } else {
            // Handle login failure
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val TAG = "FacebookLoginHelper"
    }
}
