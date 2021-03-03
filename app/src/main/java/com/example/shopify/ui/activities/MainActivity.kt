package com.example.shopify.ui.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shopify.R
import com.example.shopify.data.model.User
import com.example.shopify.data.repository.FirebaseRepo
import com.example.shopify.databinding.ActivityMainBinding
import com.example.shopify.utils.Constants
import com.example.shopify.utils.MyCallback

class MainActivity : AppCompatActivity() {

    private val firebaseRepo = FirebaseRepo()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize
        loadUserDetails()

        setUpBottomNavigationBar()

        /*signout.setOnClickListener{
            val auth = Firebase.auth
            auth.signOut()

            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
            
            Handler().postDelayed({
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            },500)
        }*/

    }

    private fun setUpBottomNavigationBar() {
        val navController = findNavController(R.id.main_container)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.productFragment, R.id.cartFragment, R.id.userFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun loadUserDetails() {
        val uid = firebaseRepo.getCurrentUserId()
        Log.d("currentUser", uid.toString())
        if (uid != null) {
            firebaseRepo.readCurrentUser(object : MyCallback {
                override fun onCallback(value: User) {
                    val user = value
                    saveData(user)
                }

            })
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_container)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun saveData(user: User) {
        //sharedPreference
        val sharedPreferences = getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(
            Constants.LOGGED_IN_USER, "${user.firstName} ${user.lastName}"
        )
        editor.apply()
    }
}