package com.quantam.it.assignment.ui.auth.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quantam.it.assignment.repository.AuthRepository

@Suppress("UNCHECKED_CAST")
class AuthVMF(
    private val repository: AuthRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthVM(repository) as T
    }

}