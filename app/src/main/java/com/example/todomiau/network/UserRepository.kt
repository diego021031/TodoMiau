package com.example.todomiau.network

import com.example.todomiau.core.ResultWrapper
import com.example.todomiau.core.safeCall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String): ResultWrapper<FirebaseUser> = safeCall {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        result.user ?: throw Exception("usuario no encontrado")
    }
}