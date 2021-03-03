package com.example.shopify.utils

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.util.Log

object Constants {
    const val PICK_IMAGE_CODE: Int = 1
    const val READ_EXTERNAL_STORAGE_PERMISSION_CODE: Int = 1212
    const val USER : String = "user"
    const val APP_PREF:String = "shopifyPrefs"
    const val LOGGED_IN_USER:String = "logged_in_user"

    fun showImageChooser(activity: Activity) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        Log.d("debugger", "image picker launched")
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_CODE)
    }
}