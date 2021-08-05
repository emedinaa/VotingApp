package com.example.voting.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.voting.R
import com.example.voting.data.UserViewModel
import com.example.voting.data.entities.User
import com.example.voting.databinding.FragmentPerfilBinding

/**
 * https://developer.android.com/topic/libraries/view-binding
 * https://developer.android.com/guide/fragments
 */

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private val mUserViewModel by viewModels<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addUserBtn.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {

        with(binding) {

            val userName = etAddFirstName.editText?.text.toString()
            val password = etAddPassword.editText?.text.toString()

            if (inputCheck(userName, password)) {

                // Create User Object
                val user = User(
                    0, userName, password
                )

                // Add Data to Database
                mUserViewModel.addUser(user)
                Toast.makeText(context, "Successfully added!", Toast.LENGTH_SHORT).show()
                // Navigate Back
                findNavController().navigate(R.id.action_perfilFragment_to_mainFragment)


            } else {
                Toast.makeText(context, "Please fill out all fields.", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun inputCheck(userName: String, password: String): Boolean {

        return !(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}