package com.example.shopify.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shopify.R
import com.example.shopify.databinding.FragmentForgetPassBinding
import com.google.firebase.auth.FirebaseAuth


class ForgetPassFragment : Fragment() {

    private var _binding: FragmentForgetPassBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgetPassBinding.inflate(inflater, container, false)

        binding.forgetPassBtn.setOnClickListener {
            val email = binding.emailForgetPass.text.toString()
            if (email.isEmpty())
                Toast.makeText(requireContext(), "Invalid", Toast.LENGTH_SHORT).show()
            else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(requireContext(), "Email sent", Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigate(R.id.action_forgetPassFragment_to_loginFragment)
                        } else {
                            Toast.makeText(requireContext(), "Try again", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        return binding.root
    }
}