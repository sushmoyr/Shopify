package com.example.shopify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shopify.data.model.User
import com.example.shopify.data.repository.FirebaseRepo
import com.example.shopify.ui.activities.AuthActivity
import com.example.shopify.ui.viewmodels.MainViewModel
import com.example.shopify.utils.Constants
import com.example.shopify.utils.MyCallback
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*

class UserFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()
    val firebaseRepo = FirebaseRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loadUserDetails()

        val view = inflater.inflate(R.layout.fragment_user, container, false)

        view.signoutBtn.setOnClickListener{
            signOut()
        }

        view.editProfileBtn.setOnClickListener{
            findNavController().navigate(R.id.action_userFragment_to_editProfileFragment)
        }

        return view
    }

    private fun signOut() {
        firebaseRepo.signOut()

        startActivity(Intent(activity, AuthActivity::class.java))
        activity?.finish()
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
                    val name = "${value.firstName} ${value.lastName}"
                    val email = "Email: ${value.email}"
                    var gender:String
                    if(value.gender.isEmpty())
                        gender = "Gender: Not set"
                    else
                        gender = "Gender: ${value.gender}"
                    var mobile: String
                    if(value.mobile.isEmpty())
                        mobile = "Mobile: Not set"
                    else
                        mobile = "Mobile: ${value.mobile}"

                    updateUI(name, email, gender, mobile)

                }

            })
        }
        else
        {
            Toast.makeText(requireContext(), "No user logged in", Toast.LENGTH_SHORT).show()
        }



    }

    private fun updateUI(name: String, email: String, gender: String, mobile: String) {
        profile_name.text = name
        profile_email.text = email
        profile_gender.text = gender
        profile_mobile.text = mobile

    }


}