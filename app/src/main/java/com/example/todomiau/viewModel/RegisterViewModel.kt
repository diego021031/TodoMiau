package com.example.todomiau.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todomiau.core.ResultWrapper
import com.example.todomiau.network.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {
        private val _loaderState = MutableLiveData<Boolean>()
        val loaderState: LiveData<Boolean>
            get() = _loaderState

        private val _validRegister = MutableLiveData<String>()
        val validRegister: LiveData<String>
            get() = _validRegister


    fun requestSignUp(email: String, password: String) {
        _loaderState.value = true
        viewModelScope.launch {
            when (val result = repository.requestRegister(email, password)) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _validRegister.value = result.data.email
                }

                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    Log.e("Firebase", "Ocurrio un problema")
                }
            }
        }
    }
}