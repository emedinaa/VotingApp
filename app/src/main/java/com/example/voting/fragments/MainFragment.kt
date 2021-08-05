package com.example.voting.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.voting.R
import com.example.voting.databinding.FragmentMainBinding

/**
 * https://developer.android.com/topic/libraries/view-binding
 * https://developer.android.com/guide/fragments
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            cvAdmin.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_logInFragment)
            }

            cvVoters.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_votersFragment)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}