package com.example.shopify.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shopify.R
import com.example.shopify.data.model.User
import com.example.shopify.databinding.FragmentRegisterBinding
import com.example.shopify.ui.activities.MainActivity
import com.example.shopify.ui.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {


    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.registerBtn.setOnClickListener {
            registerUser()

            Log.d("button", "Button Clicked")
        }

        return binding.root
    }

    private fun registerUser() {
        val firstname = binding.firstNameRegister.text.toString()
        val lastname = binding.lastNameRegister.text.toString()
        val email = binding.emailRegister.text.toString()
        val password = binding.passwordRegister.text.toString()
        val confirmpass = binding.confirmPasswordRegister.text.toString()

        if (mainViewModel.validateRegisterData(firstname, lastname, email, password, confirmpass)) {
            view?.isUserInteractionEnabled(false)
            binding.progress.visibility = View.VISIBLE
            binding.rootLayoutRegister.alpha = 0.5f
            //firebase stuff
            email.trim(' ')


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) {
                    if (it.isSuccessful) {
                        Toast.makeText(requireContext(), "Sign in Successful", Toast.LENGTH_SHORT)
                            .show()
                        view?.isUserInteractionEnabled(true)
                        binding.progress.visibility = View.GONE
                        binding.rootLayoutRegister.alpha = 1f
                        val user = auth.currentUser!!
                        //user entry
                        val newUser = User(
                            user.uid,
                            firstname,
                            lastname,
                            email,
                            "",
                            "",
                            "Not set",
                            false
                        )

                        mainViewModel.insertUserToFirebase(newUser)
                        Log.d("firebaseRepo", "Data sent to viewModel")

                        //navigate to next activity
                        Handler().postDelayed({
                            startActivity(Intent(activity, MainActivity::class.java))
                        }, 1000)

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Unable to create account!! Try again",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("result", it.result.toString())
                        view?.isUserInteractionEnabled(true)
                        binding.progress.visibility = View.GONE
                        binding.rootLayoutRegister.alpha = 1f
                    }
                }
        } else {
            Toast.makeText(requireContext(), "Please fill everything properly", Toast.LENGTH_SHORT)
                .show()
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