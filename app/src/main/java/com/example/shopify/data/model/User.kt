package com.example.shopify.data.model

data class User(
    val uid:String = "",
    val firstName:String = "",
    val lastName:String = "",
    val email:String = "",
    val image:String = "",
    val mobile:String = "",
    val gender:String = "",
    val profileCompleted:Boolean = false
)
