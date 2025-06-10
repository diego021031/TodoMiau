package com.example.todomiau

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todomiau.databinding.FragmentTaskDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class TaskDetailFragment : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    // ✅ Safe Args
    private val args: TaskDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = args.name
        val description = args.description
        val dateMillis = args.dateMillis

        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(dateMillis))

        binding.tvTaskName.text = name
        binding.tvTaskDescription.text = description
        binding.tvTaskDate.text = "Fecha: $formattedDate"

        binding.btBackDetail.setOnClickListener {
            findNavController().navigate(R.id.action_taskDetailFragment_to_firstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
