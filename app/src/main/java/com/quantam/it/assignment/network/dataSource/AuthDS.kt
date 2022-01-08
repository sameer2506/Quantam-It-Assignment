package com.quantam.it.assignment.network.dataSource

import com.quantam.it.assignment.network.Results

interface AuthDS {

    suspend fun createUserWithEmailAndPassword(email: String, password: String): Results<Boolean>

    suspend fun saveUserDetails(data: HashMap<String, Any>): Results<Boolean>

    suspend fun sendVerificationEmail(emailId: String): Results<Boolean>
}