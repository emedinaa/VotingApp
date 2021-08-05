package com.example.voting.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.voting.R
import com.example.voting.data.UserViewModel
import com.example.voting.databinding.FragmentLoginBinding

/**
 * https://developer.android.com/topic/libraries/view-binding
 * https://developer.android.com/guide/fragments
 */

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val mUserViewModel by viewModels<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            tvSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_perfilFragment)
            }
            buttonIngresar.setOnClickListener {

                val name = etUser.editText?.text.toString()
                val passWord = etPassword.editText?.text.toString()


                context?.let { it1 ->
                    mUserViewModel.getLoginDetails(it1, name, passWord)
                        ?.observe(viewLifecycleOwner, {

                            if (it == null) {
                                Toast.makeText(
                                    context,
                                    "User or PassWord no found ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                //  Toast.makeText(context, "Found", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_logInFragment_to_listFragment)

                                //Metodo para borrar datos del editText
                                etUser.editText?.setText("")
                                etPassword.editText?.setText("")

                            }

                        })
                }
            }


            //Metodo para conservar los datos en el editTExt
            etUser.editText?.setText(mUserViewModel.username_.value)
            etPassword.editText?.setText(mUserViewModel.passWord_.value)

            etUser.editText?.doOnTextChanged { _: CharSequence?, _: Int, _: Int, _: Int ->
                mUserViewModel.username_.value = etUser.editText?.text.toString()
            }
            etPassword.editText?.doOnTextChanged { _: CharSequence?, _: Int, _: Int, _: Int ->
                mUserViewModel.passWord_.value = binding.etPassword.editText?.text.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}