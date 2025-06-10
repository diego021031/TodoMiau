package com.example.todomiau

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todomiau.databinding.FragmentFirstBinding
import com.example.todomiau.view.adapters.TaskAdapter
import com.example.todomiau.viewModel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btAdd.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        taskAdapter = TaskAdapter(
            tasks = mutableListOf(),
            onClick = { task ->
                val action = FirstFragmentDirections.actionFirstFragmentToTaskDetailFragment(
                    name = task.name,
                    description = task.description,
                    dateMillis = task.date.time
                )
                findNavController().navigate(action)
            },
            onEdit = { task ->
                val action = FirstFragmentDirections.actionFirstFragmentToEditTaskFragment(
                    id = task.id,
                    name = task.name,
                    description = task.description,
                    dateMillis = task.date.time
                )
                findNavController().navigate(action)
            },
            onDelete = { task ->
                taskViewModel.deleteTask(task.id)
            }
        )

        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }

        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            Log.d("DEBUG_TASKS", "Recibidas ${tasks.size} tareas")
            taskAdapter.add(tasks)
        }
    }

    override fun onResume() {
        super.onResume()
        taskViewModel.getAllTasks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
