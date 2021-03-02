package com.example.shopify.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shopify.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_forget_pass.*
import kotlinx.android.synthetic.main.fragment_forget_pass.view.*


class ForgetPassFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forget_pass, container, false)

        view.forget_pass_btn.setOnClickListener {
            val email = email_forget_pass.text.toString()
            if(email.isEmpty())
                Toast.makeText(requireContext(), "Invalid", Toast.LENGTH_SHORT).show()
            else
            {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            Toast.makeText(requireContext(), "Email sent", Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigate(R.id.action_forgetPassFragment_to_loginFragment)
                        }
                        else{
                            Toast.makeText(requireContext(), "Try again", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        return view
    }
}