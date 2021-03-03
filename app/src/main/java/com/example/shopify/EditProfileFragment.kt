package com.example.shopify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.shopify.data.model.User
import com.example.shopify.data.repository.FirebaseRepo
import com.example.shopify.databinding.FragmentEditProfileBinding
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.shopify.utils.Constants

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    val firebaseRepo = FirebaseRepo()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)

        binding.updateProfileButton.setOnClickListener {
            updateProfile()
            findNavController().navigate(R.id.action_editProfileFragment_to_userFragment)
        }

        binding.profileImage.setOnClickListener {
            getProfilePhoto()
        }

        return binding.root
    }

    private fun getProfilePhoto() {
        if(ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            //Permission Granted
            Constants.showImageChooser(requireActivity())
        }
        else{
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                Constants.READ_EXTERNAL_STORAGE_PERMISSION_CODE
            )
        }
    }

    private fun updateProfile() {
        val firstName = binding.firstNameEt.text.toString()
        val lastName = binding.lastNameEt.text.toString()
        val email = binding.emailEt.text.toString()
        val mobile = binding.mobileEt.text.toString()
        val gender = getGender()
        val uid = firebaseRepo.getCurrentUserId()!!
        val user = User(uid, firstName, lastName, email, "", mobile, gender)

        firebaseRepo.updateProfile(requireContext(), user)
        firebaseRepo.updateEmail(email)

    }

    private fun getGender(): String {
        return when(binding.gender.checkedRadioButtonId){
            R.id.gender_male-> "Male"
            R.id.gender_female -> "Female"
            else -> "Not set"
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == Constants.READ_EXTERNAL_STORAGE_PERMISSION_CODE)
        {
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                Constants.showImageChooser(requireActivity())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("debugger", "activity result")
        if(resultCode== Activity.RESULT_OK)
        {
            Log.d("debugger", "result ok")
            if(requestCode==Constants.PICK_IMAGE_CODE)
            {
                Log.d("debugger", "requested")
                if(data!=null)
                {
                    val selectedImageUri = data.data
                    Log.d("debugger", "data is here")
                    Log.d("debugger", selectedImageUri.toString())
                    binding.profileImage.setImageURI(Uri.parse(selectedImageUri.toString()))
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }




}