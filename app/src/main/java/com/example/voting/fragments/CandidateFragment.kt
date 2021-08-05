package com.example.voting.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.voting.R
import com.example.voting.data.UserViewModel
import com.example.voting.data.entities.Voters
import com.example.voting.databinding.FragmentCandidateBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * https://developer.android.com/topic/libraries/view-binding
 * https://developer.android.com/guide/fragments
 */
class CandidateFragment : Fragment() {

    private var _binding: FragmentCandidateBinding? = null
    private val binding get() = _binding!!

    private val mUserViewModel by viewModels<UserViewModel>()
    private var stringPath: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCandidateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btAddCandidate.setOnClickListener {
            insertDataToDatabase()
        }

        binding.imageViewSelect.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    openGallery()
                } else {
                    val permissionRequest =
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissionRequest, REQUEST_PERMISSION_CODE)
                }

            } else {
                openGallery()
            }
        }
    }

    //Select photo from gallery
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    Toast.makeText(
                        context,
                        "Unable to update location without permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_GALLERY) {
            if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_GALLERY) {
                //File path
                val selectedImageUri: Uri? = data?.data

                //Convert Uri to a Bitmap
                val source = selectedImageUri?.let {
                    context?.let { it1 ->
                        ImageDecoder.createSource(
                            it1.contentResolver, it
                        )
                    }
                }

                val bitmap = source?.let { ImageDecoder.decodeBitmap(it) }
                binding.imageViewSelect.setImageBitmap(bitmap)

                //Creation date
                //File creation
                val fileCreation = System.currentTimeMillis()
                val imageName = ("$fileCreation${"image.jpg"}")

                //If it does not exist create the temporary
                val file = File(context?.getExternalFilesDir("PhotoTemp/"), imageName)
                if (file.exists()) {
                    file.delete()
                }
                //Save the temporary
                try {
                    val out = FileOutputStream(file)
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, out)
                    out.flush()
                    out.close()

                } catch (e: IOException) {

                }

                stringPath = file.toString()


            } else {
                Toast.makeText(context, "You didn't any photo", Toast.LENGTH_SHORT).show()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun insertDataToDatabase() {
        with(binding) {
            val firstName = etAddFirstName.editText?.text.toString()
            val lastName = etAddLastName.editText?.text.toString()
            val votingCard = etAddNumerCard.editText?.text.toString()
            // Method to convert the path to a file
            val myFile = File(stringPath).toString()

            if (inputCheck(firstName, lastName, votingCard)) {
                // Create User Object
                val voters = Voters(
                    0,
                    firstName,
                    lastName,
                    votingCard, myFile
                )
                // Add Data to Database
                mUserViewModel.addVoters(voters)
                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
                // Navigate Back
                findNavController().navigate(R.id.action_candidateFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun inputCheck(firstName: String, lastName: String, votingCrad: String): Boolean {
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(
            votingCrad
        ))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val REQUEST_IMAGE_GALLERY = 100
        private const val REQUEST_PERMISSION_CODE = 101
    }


}