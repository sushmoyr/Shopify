package com.example.shopify.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shopify.R
import com.example.shopify.ui.activities.MainActivity
import com.example.shopify.ui.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser!=null)
        {
            Toast.makeText(requireContext(), "Welcome Back", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)


        view.register_here .setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        
        view.login_btn.setOnClickListener{
            loginUser()
        }

        view.forgetpassword_register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgetPassFragment)
        }

        return view
    }
    
    private fun loginUser()
    {
        val email = login_email_et.text.toString()
        val password = login_password_et.text.toString()
        if(mainViewModel.validateLoginData(email, password))
        {
            view?.isUserInteractionEnabled(false)
            login_progress.visibility = View.VISIBLE
            root_layout_login.alpha = 0.4f

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            Log.d("userdata", "Email: ${user.email.toString()}")
                        }
                        login_progress.visibility = View.GONE
                        root_layout_login.alpha = 1f



                        Handler().postDelayed({
                            val intent = Intent(activity, MainActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        },1000)

                    } else {
                        Toast.makeText(requireContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
        }
        else
        {
            Toast.makeText(requireContext(), "Field can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun View.isUserInteractionEnabled(enabled: Boolean) {
        isEnabled = enabled
        if (this is ViewGroup && this.childCount > 0) {
            this.children.forEach {
                it.isUserInteractionEnabled(enabled)
            }
        }
    }

}