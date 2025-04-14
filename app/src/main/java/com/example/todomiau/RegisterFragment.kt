package com.example.todomiau

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.todomiau.databinding.FragmentRegisterBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var isValid: Boolean = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setupView()
        return binding.root

    }

   private fun setupView() {
       binding.btBack.setOnClickListener {
           findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
       }

       binding.tietName.addTextChangedListener {
           if (binding.tietName.text.toString().isEmpty()) {
               binding.tfName.error = "Por favor introduce tu nombre"
               isValid = false
           } else {
               isValid = true
           }
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