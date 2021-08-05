package com.example.voting.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voting.R
import com.example.voting.data.UserViewModel
import com.example.voting.databinding.FragmentListBinding

/**
 * https://developer.android.com/topic/libraries/view-binding
 * https://developer.android.com/guide/fragments
 */

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val mUserViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recyclerview
        val adapter = ListAdapter()
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { voters ->
            adapter.setData(voters)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_candidateFragment)

        }

        //Metodo para bloquear el boton a tras
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // With blank your fragment BackPressed will be disabled.
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}