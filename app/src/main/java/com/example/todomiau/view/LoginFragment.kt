package com.example.todomiau.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.todomiau.R
import com.example.todomiau.databinding.FragmentLoginBinding



/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var isValid: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        setupView()
        return binding.root

    }


    private fun setupView() {
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.tietEmail.addTextChangedListener {
            if (binding.tietEmail.text.toString().isEmpty()) {
                binding.tfEmail.error = "Por favor introduce un correo"
                isValid = false
            } else {
                isValid = true
            }
        }
        binding.tietPassword.addTextChangedListener {
            if (binding.tietPassword.text.toString().isEmpty()) {
                binding.tfPassword.error = "Por favor introduce una contraseña"
                isValid = false
            } else {
                isValid = true
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}