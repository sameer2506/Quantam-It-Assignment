package com.quantam.it.assignment.ui.auth.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quantam.it.assignment.network.Results
import com.quantam.it.assignment.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthVM(
    private val repository: AuthRepository
) : ViewModel() {

    private val _createNewAccount: MutableLiveData<Results<Boolean>> = MutableLiveData()
    val createNewAccount: LiveData<Results<Boolean>>
        get() = _createNewAccount

    fun createUserWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        _createNewAccount.value = Results.Loading
        _createNewAccount.value = repository.createUserWithEmailAndPassword(email, password)
    }

    private val _saveUserDetails: MutableLiveData<Results<Boolean>> = MutableLiveData()
    val saveUserDetails: LiveData<Results<Boolean>>
        get() = _saveUserDetails

    fun saveUserDetails(data: HashMap<String, Any>) = viewModelScope.launch {
        _saveUserDetails.value = Results.Loading
        _saveUserDetails.value = repository.saveUserDetails(data)
    }

    private val _signInUsingEmail: MutableLiveData<Results<Boolean>> = MutableLiveData()
    val signInUsingEmail: LiveData<Results<Boolean>>
        get() = _signInUsingEmail

    fun signInUsingEmail(emailId: String, password: String) = viewModelScope.launch {
        _signInUsingEmail.value = Results.Loading
        _signInUsingEmail.value = repository.signInUsingEmail(emailId, password)
    }

    private val _checkUserLogin: MutableLiveData<Results<Boolean>> = MutableLiveData()
    val checkUserLogin: LiveData<Results<Boolean>>
        get() = _checkUserLogin

    fun checkUserLogin() = viewModelScope.launch {
        _checkUserLogin.value = Results.Loading
        _checkUserLogin.value = repository.checkUserLogin()
    }

}