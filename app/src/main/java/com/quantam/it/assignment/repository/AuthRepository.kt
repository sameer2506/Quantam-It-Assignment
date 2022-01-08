package com.quantam.it.assignment.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.quantam.it.assignment.network.Results
import com.quantam.it.assignment.network.dataSource.AuthDS
import com.quantam.it.assignment.utils.log
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepository(
    private val context: Context
) : AuthDS {

    private val auth: FirebaseAuth = Firebase.auth

    private val firebaseFirestore = Firebase.firestore

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Results<Boolean> =
        suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    cont.resume(Results.Success(true))
                }
                .addOnFailureListener {
                    cont.resume(Results.Error(it))
                }
        }

    override suspend fun saveUserDetails(
        data: HashMap<String, Any>
    ): Results<Boolean> =
        suspendCoroutine { cont ->
            firebaseFirestore
                .collection("user")
                .add(data)
                .addOnSuccessListener {
                    cont.resume(Results.Success(true))
                }
                .addOnFailureListener {
                    cont.resume(Results.Error(it))
                }
        }

    override suspend fun sendVerificationEmail(emailId: String): Results<Boolean> =
        suspendCoroutine { cont ->
            auth
                .currentUser!!
                .sendEmailVerification()
                .addOnSuccessListener {
                    cont.resume(Results.Success(true))
                }
                .addOnFailureListener {
                    cont.resume(Results.Error(it))
                }
        }
}