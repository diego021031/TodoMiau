package com.example.todomiau.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todomiau.core.ResultWrapper
import com.example.todomiau.network.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel: ViewModel() {
    private val repository = UserRepository()
    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState
    private val _sessionValid = MutableLiveData<Boolean>()
    val sessionValid: LiveData<Boolean>
        get() = _sessionValid

    fun requestLogin(email: String, password: String) {
        _loaderState.value = true
        viewModelScope.launch {
            when (val result = repository.login(email, password)) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _sessionValid.value = true
                }
                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    val errorMessage = result.exception.message
                }
            }
        }
    }
}