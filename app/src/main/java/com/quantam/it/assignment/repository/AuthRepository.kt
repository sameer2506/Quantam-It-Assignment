package com.quantam.it.assignment.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.quantam.it.assignment.AppPreferences
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

    private val appPreferences = AppPreferences(context)

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Results<String> =
        suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    log(it.user!!.uid)
                    cont.resume(Results.Success(it.user!!.uid))
                }
                .addOnFailureListener {
                    cont.resume(Results.Error(it))
                }
        }

    override suspend fun saveUserDetails(data: HashMap<String, Any>, id: String): Results<Boolean> =
        suspendCoroutine { cont ->
            firebaseFirestore
                .collection("user")
                .document(id)
                .set(data)
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

    override suspend fun signInUsingEmail(emailId: String, password: String): Results<Boolean> =
        suspendCoroutine { cont ->
            auth
                .signInWithEmailAndPassword(emailId, password)
                .addOnSuccessListener {
                    appPreferences.saveId(auth.uid!!)
                    cont.resume(Results.Success(true))
                }
                .addOnFailureListener {
                    cont.resume(Results.Error(it))
                }
        }

    override suspend fun forgotPassword(email: String): Results<Boolean> =
        suspendCoroutine { cont ->
            auth
                .sendPasswordResetEmail(email)
                .addOnFailureListener {
                    cont.resume(Results.Error(it))
                }
                .addOnSuccessListener {
                    cont.resume(Results.Success(true))
                }
        }

    override suspend fun checkUserLogin(): Results<Boolean> =
        suspendCoroutine { cont ->
            if (auth.currentUser != null) {
                cont.resume(Results.Success(true))
            } else {
                cont.resume(Results.Success(false))
            }
        }

}