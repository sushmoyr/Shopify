package com.example.shopify.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.shopify.R
import com.example.shopify.data.model.User
import com.example.shopify.data.repository.FirebaseRepo
import com.example.shopify.utils.Constants
import com.example.shopify.utils.MyCallback
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val firebaseRepo = FirebaseRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize
        loadUserDetails()

        signout.setOnClickListener{
            val auth = Firebase.auth
            auth.signOut()

            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
            
            Handler().postDelayed({
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            },500)
        }

    }

    private fun loadUserDetails()
    {
        val uid = firebaseRepo.getCurrentUserId()
        Log.d("currentUser", uid.toString())
        if(uid!=null)
        {
            firebaseRepo.readCurrentUser(object : MyCallback {
                override fun onCallback(value: User) {
                    val user = value
                    firstnamemain.text = user.firstName
                    lastnamemain.text = user.lastName
                    emailMain.text = user.email
                    uidmain.text = user.uid
                    mobile.text = user.mobile
                    gender.text = user.gender
                    saveData(user)
                }

            })
        }
        else
        {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show()
        }



    }

    private fun saveData(user: User)
    {
        //sharedPreference
        val sharedPreferences = getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(
            Constants.LOGGED_IN_USER, "${user.firstName} ${user.lastName}"
        )
        editor.apply()
    }
}