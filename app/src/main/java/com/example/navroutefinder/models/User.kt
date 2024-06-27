package com.example.navroutefinder.models

data class User(
    val userId:Int,
    val username:String,
    val email:String,
    val password:CharSequence,
    val confPassword:CharSequence,
    val hashPassword:CharSequence
)
