package com.example.todomiau.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todomiau.databinding.ItemTaskBinding
import com.example.todomiau.model.Task
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onClick: (Task) -> Unit,
    private val onEdit: (Task) -> Unit,
    private val onDelete: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setUpUI(task: Task) {
            binding.taskTitleTextView.text = task.name
            binding.taskDescriptionTextView.text = task.description

            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(task.date)
            binding.taskDateTextView.text = "Fecha: $formattedDate"

            binding.root.setOnClickListener {
                onClick(task)
            }

            binding.editButton.setOnClickListener {
                onEdit(task)
            }

            binding.deleteButton.setOnClickListener {
                onDelete(task)
            }
        }
    }

    fun add(taskItems: List<Task>) {
        tasks.clear()
        tasks.addAll(taskItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUpUI(tasks[position])
    }
}
