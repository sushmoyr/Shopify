package com.example.shopify.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid:String = "",
    val firstName:String = "",
    val lastName:String = "",
    val email:String = "",
    val image:String = "",
    val mobile:String = "",
    val gender:String = "",
    val profileCompleted:Boolean = false
):Parcelable
