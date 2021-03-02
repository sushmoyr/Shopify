package com.example.shopify.ui.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shopify.data.model.User
import com.example.shopify.data.repository.FirebaseRepo

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val firebaseRepo = FirebaseRepo()

    val currentUser = MutableLiveData<User>()

    fun validateRegisterData(firstName:String, lastName:String, email:String, password:String, confirm:String): Boolean {

        return when{
            firstName.isEmpty() -> false
            lastName.isEmpty() -> false
            email.isEmpty() -> false
            !matchPass(password, confirm) -> false
            else -> true
        }

    }
    private fun matchPass(password: String, confirm: String): Boolean {
        return when{
            password.isEmpty() -> false
            confirm.isEmpty() -> false
            else -> password==confirm
        }
    }

    fun validateLoginData(email:String, password: String): Boolean
    {
        return when{
            email.isEmpty() -> false
            password.isEmpty() -> false
            else -> true
        }
    }

    fun insertUserToFirebase(user: User)
    {
        Log.d("firebaseRepo","Data sent to repo")
        firebaseRepo.insertUser(user)

    }

}
