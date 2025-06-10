package com.example.todomiau.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todomiau.core.ResultWrapper
import com.example.todomiau.model.Task
import com.example.todomiau.network.TaskRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean> get() = _loaderState

    private val _operationSuccess = MutableLiveData<Boolean>()
    val operationSuccess: LiveData<Boolean> get() = _operationSuccess

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    fun createTask(id: String, name: String, description: String, date: Date) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val task = Task(
            id = id,
            name = name,
            description = description,
            date = date,
            userId = currentUserId
        )

        _loaderState.value = true
        viewModelScope.launch {
            when (val result = repository.createTask(task)) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _operationSuccess.value = true
                    getAllTasks()
                }
                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    val errorMessage = result.exception.message
                }
            }
        }
    }

    fun getAllTasks() {
        _loaderState.value = true
        viewModelScope.launch {
            when (val result = repository.getAllTasks()) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _tasks.value = result.data
                }
                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    val errorMessage = result.exception.message
                }
            }
        }
    }

    fun updateTask(task: Task) {
        _loaderState.value = true
        viewModelScope.launch {
            when (val result = repository.updateTask(task)) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _operationSuccess.value = true
                    getAllTasks()
                }
                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    val errorMessage = result.exception.message
                }
            }
        }
    }

    fun deleteTask(taskId: String) {
        _loaderState.value = true
        viewModelScope.launch {
            when (val result = repository.deleteTask(taskId)) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _operationSuccess.value = true
                    getAllTasks()
                }
                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    val errorMessage = result.exception.message
                }
            }
        }
    }
}
