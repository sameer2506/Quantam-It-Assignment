package com.quantam.it.assignment.network.dataSource

import com.quantam.it.assignment.network.Results

interface AuthDS {

    suspend fun createUserWithEmailAndPassword(email: String, password: String): Results<String>

    suspend fun saveUserDetails(data: HashMap<String, Any>, id: String): Results<Boolean>

    suspend fun sendVerificationEmail(emailId: String): Results<Boolean>

    suspend fun signInUsingEmail(emailId: String, password: String): Results<Boolean>

    suspend fun forgotPassword(email: String): Results<Boolean>

    suspend fun getUserDetails(): Results<Boolean>

    suspend fun userLogOut(): Results<Boolean>
}