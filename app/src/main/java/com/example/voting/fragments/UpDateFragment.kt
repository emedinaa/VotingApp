package com.example.voting.fragments

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.voting.R
import com.example.voting.data.UserViewModel
import com.example.voting.data.entities.Voters
import com.example.voting.databinding.FragmentUpDateBinding

/**
 * https://developer.android.com/topic/libraries/view-binding
 * https://developer.android.com/guide/fragments
 */

class UpDateFragment : Fragment() {

    private var _binding: FragmentUpDateBinding? = null
    private val binding get() = _binding!!

    private val arg by navArgs<UpDateFragmentArgs>()
    private val mUserViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etUpDateFirstName.editText?.setText(arg.currentUser.firstName)
        binding.etUpDateLastName.editText?.setText(arg.currentUser.lastName)
        binding.etUpdateNumerCard.editText?.setText(arg.currentUser.votingCard)
        binding.imageViewUpDate.setImageURI(Uri.parse(arg.currentUser.img))

        binding.buttonUpDate.setOnClickListener {
            updateItem()
        }
    }

    private fun updateItem() {
        val firstName = binding.etUpDateFirstName.editText?.text.toString()
        val lastName = binding.etUpDateLastName.editText?.text.toString()
        val votingCard = binding.etUpdateNumerCard.editText?.text.toString()
        //val myFile = File(stringPath).toString()

        if (inputCheck(firstName, lastName, votingCard)) {

            //Create User Object
            val updateUser = Voters(arg.currentUser.votersId, firstName, lastName, votingCard, "")
            // Update Current User
            mUserViewModel.updateVoter(updateUser)
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_upDateFragment_to_listFragment)
        } else {
            Toast.makeText(context, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firsName: String, lastName: String, numberCard: String): Boolean {
        return !(TextUtils.isEmpty(firsName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(
            numberCard
        ))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}