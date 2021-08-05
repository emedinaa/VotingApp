package com.example.voting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.voting.databinding.FragmentVotersBinding

class VotersFragment : Fragment() {

    /**
     * https://developer.android.com/topic/libraries/view-binding
     * https://developer.android.com/guide/fragments
     */
    private var _binding: FragmentVotersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVotersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}