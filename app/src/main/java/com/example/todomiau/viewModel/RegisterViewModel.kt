package com.example.todomiau.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class RegisterViewModel: ViewModel() {
        private val _loaderState = MutableLiveData<Boolean>()
        val loaderState: LiveData<Boolean>
            get() = _loaderState
        private val firebase = FirebaseAuth.getInstance()
}