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
import androidx.navigation.fragment.navArgs
import com.example.todomiau.databinding.FragmentEditTaskBinding
import com.example.todomiau.model.Task
import com.example.todomiau.viewModel.TaskViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditTaskFragment : Fragment() {

    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!

    private val args: EditTaskFragmentArgs by navArgs()
    private val taskViewModel: TaskViewModel by viewModels()

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var selectedDate: Date = Date()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tietEditName.setText(args.name)
        binding.tietEditDescription.setText(args.description)
        selectedDate = Date(args.dateMillis)
        binding.tietEditDate.setText(dateFormat.format(selectedDate))

        binding.tietEditDate.setOnClickListener {
            val calendar = Calendar.getInstance().apply { time = selectedDate }
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, y, m, d ->
                calendar.set(y, m, d)
                selectedDate = calendar.time
                binding.tietEditDate.setText(dateFormat.format(selectedDate))
            }, year, month, day)

            datePicker.show()
        }

        binding.btUpdateTask.setOnClickListener {
            val name = binding.tietEditName.text.toString().trim()
            val description = binding.tietEditDescription.text.toString().trim()

            if (name.isEmpty() || description.isEmpty()) {
                Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

            val updatedTask = Task(
                id = args.id,
                name = name,
                description = description,
                date = selectedDate,
                userId = currentUserId
            )

            taskViewModel.updateTask(updatedTask)
            Toast.makeText(requireContext(), "Tarea actualizada", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editTaskFragment_to_FirstFragment)
        }

        binding.btBackEdit.setOnClickListener {
            findNavController().navigate(R.id.action_editTaskFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
