package com.example.todomiau

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todomiau.databinding.FragmentSecondBinding
import com.example.todomiau.viewModel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModels()
    private val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.tietDate.apply {
            isFocusable = false
            isClickable = true
        }

        binding.tietDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, y, m, d ->
                val selectedDate = String.format("%02d/%02d/%04d", d, m + 1, y)
                binding.tietDate.setText(selectedDate)
            }, year, month, day)

            datePicker.show()
        }

        binding.btBack.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.btAddingTask.setOnClickListener {
            val name = binding.tietNameTask.text.toString().trim()
            val description = binding.tietDescription.text.toString().trim()
            val dateStr = binding.tietDate.text.toString().trim()

            if (name.isEmpty() || description.isEmpty() || dateStr.isEmpty()) {
                Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val date: Date = try {
                format.parse(dateStr) ?: throw IllegalArgumentException("Formato inválido")
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Fecha inválida. Usa el formato dd/MM/yyyy", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = UUID.randomUUID().toString()

            taskViewModel.createTask(
                id = id,
                name = name,
                description = description,
                date = date
            )

            Toast.makeText(requireContext(), "Tarea agregada", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
