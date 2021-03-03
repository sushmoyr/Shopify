package com.example.shopify.data.repository

import android.util.Log
import com.example.shopify.data.model.User
import com.example.shopify.utils.Constants
import com.example.shopify.utils.MyCallback
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseRepo {
    private val db = Firebase.firestore
    private val auth = Firebase.auth
    fun insertUser(user: User) {
        db.collection(Constants.USER)
            .document(user.uid)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("firestore", "Data added to firestore")
            }
            .addOnFailureListener {
                Log.d("firestore", "Data insert failed")
            }
    }

    fun signOut()
    {
        auth.signOut()
    }

    fun getCurrentUserId(): String? {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d("Fire store", currentUser.uid)
            return currentUser.uid
        }

        return null
    }

    fun getCurrentUser(): User {
        var currentUser = User()
        readCurrentUser(object: MyCallback {
            override fun onCallback(value: User) {
                Log.d("currentuser", value.firstName)
                Log.d("currentuser", value.lastName)
                Log.d("currentuser", value.email)
                Log.d("currentuser", value.uid)
                currentUser = value
            }
        })
        return currentUser
    }


    fun readCurrentUser(myCallback: MyCallback) {
        db.collection(Constants.USER)
            .document(getCurrentUserId()!!)
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                myCallback.onCallback(user!!)
            }
    }

}