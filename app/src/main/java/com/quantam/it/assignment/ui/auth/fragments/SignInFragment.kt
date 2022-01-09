package com.quantam.it.assignment.ui.auth.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.quantam.it.assignment.AppPreferences
import com.quantam.it.assignment.R
import com.quantam.it.assignment.databinding.FragmentSignInBinding
import com.quantam.it.assignment.network.Results
import com.quantam.it.assignment.security.isEmpty
import com.quantam.it.assignment.security.isPasswordValidation
import com.quantam.it.assignment.security.isValidMail
import com.quantam.it.assignment.ui.auth.viewModel.AuthVM
import com.quantam.it.assignment.ui.auth.viewModel.AuthVMF
import com.quantam.it.assignment.ui.home.HomeActivity
import com.quantam.it.assignment.utils.log
import com.quantam.it.assignment.utils.logError
import com.quantam.it.assignment.utils.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SignInFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val factory: AuthVMF by instance()

    private lateinit var fragmentContext: Context
    private lateinit var fragmentActivity: FragmentActivity

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: AuthVM

    private lateinit var appPreferences: AppPreferences

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    val regCode: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager

    override fun onStart() {
        super.onStart()

        if (appPreferences.getEmailLoginStatus() || appPreferences.getFbLoginStatus() || appPreferences.getGoogleLoginStatus()){
            startActivity(Intent(fragmentContext, HomeActivity::class.java))
            fragmentActivity.finish()
        }

        /*
        checkUserLogin()

        if (GoogleSignIn.getLastSignedInAccount(fragmentContext) != null) {
            startActivity(Intent(fragmentContext, HomeActivity::class.java))
            fragmentActivity.finish()
        }

         */

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentContext = requireContext()
        fragmentActivity = requireActivity()

        binding = FragmentSignInBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(fragmentActivity, factory).get(AuthVM::class.java)

        appPreferences = AppPreferences(fragmentContext)

        FacebookSdk.sdkInitialize(fragmentContext);

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callbackManager = CallbackManager.Factory.create()

        binding.tvRegisterNow.setOnClickListener {
            findNavController().navigate(R.id.action_sign_in_to_sign_up_fragment)
        }

        binding.btnSignIn.setOnClickListener {
            signIn()
        }

        binding.tvForgotPassword.setOnClickListener {
            forgotPassword()
        }

        FirebaseApp.initializeApp(fragmentContext)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(fragmentActivity, gso)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.imgBtnGoogleLogin.setOnClickListener {
            signInGoogle()
        }

        binding.imgBtnFacebookLogin.setOnClickListener {
            signInWithFacebook()
        }
    }

    private fun signInWithFacebook() {
        // Initialize Facebook Login button
        if (!appPreferences.getFbLoginStatus()){
            LoginManager.getInstance().logInWithReadPermissions(this, mutableListOf("email", "public_profile"))
            LoginManager.getInstance().registerCallback(callbackManager, object :
                FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    log("facebook:onSuccess:$loginResult")
                    firebaseAuthWithFacebook(loginResult.accessToken)
                }

                override fun onCancel() {
                    log("facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    logError("facebook:onError $error")
                }
            })
        }else{
            fragmentContext.toast("already log in")
            startActivity(Intent(fragmentContext, HomeActivity::class.java))
            fragmentActivity.finish()
        }
    }


    private fun firebaseAuthWithFacebook(token: AccessToken) {
        log("handleFacebookAccessToken:$token")
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    log("signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    log("$user")
                    appPreferences.setFbLoginStatus(true)
                    startActivity(Intent(fragmentContext, HomeActivity::class.java))
                    fragmentActivity.finish()
                } else {
                    // If sign in fails, display a message to the user.
                    logError("signInWithCredential:failure ${task.exception}")
                    fragmentContext.toast("Authentication failed.")
//                    updateUI(null)
                }
            }
    }

    private fun signInGoogle() {

        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, regCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == regCode) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                updateUI(account)
            }
        } catch (e: ApiException) {
            fragmentContext.toast("Try again later...")
            log(e.toString())
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                appPreferences.saveId(account.email.toString())
                appPreferences.saveName(account.displayName.toString())
                appPreferences.setGoogleLoginStatus(true)

                startActivity(Intent(fragmentContext, HomeActivity::class.java))
                fragmentActivity.finish()
            }
        }
    }

    private fun checkUserLogin(){

        viewModel.checkUserLogin()
        viewModel.checkUserLogin.observe(viewLifecycleOwner, {
            when(it){
                is Results.Success -> {
                   if (it.data){
                       startActivity(Intent(fragmentContext, HomeActivity::class.java))
                       fragmentActivity.finish()
                   }
                }
                is Results.Loading -> {}
                is Results.Error-> {
                    log(it.exception.localizedMessage!!)
                }
            }
        })
    }

    private fun forgotPassword() {

        if (isEmpty(binding.etEmailId)) {
            binding.etEmailId.error = "Provide email id."
            return
        }

        val email = binding.etEmailId.text.toString()

        viewModel.forgotPassword(email)
        viewModel.forgotPassword.observe(viewLifecycleOwner, {
            when (it) {
                is Results.Loading -> {
                }
                is Results.Error -> {
                    fragmentContext.toast("Something went wrong. Try again later...")
                    log(it.exception.localizedMessage!!)
                }
                is Results.Success -> {
                    showDialogBox()
                }
            }
        })
    }

    private fun showDialogBox() {

        val builder = AlertDialog.Builder(fragmentContext)
        //set title for alert dialog
        builder.setTitle("Forgot Password")
        //set message for alert dialog
        builder.setMessage("Check your email.")

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    private fun signIn() {

        if (!isValidate())
            return

        val emailId: String = binding.etEmailId.text.toString()
        val password: String = binding.etPassword.text.toString()

        viewModel.signInUsingEmail(emailId, password)
        viewModel.signInUsingEmail.observe(viewLifecycleOwner, {
            when (it) {
                is Results.Loading -> {
                }
                is Results.Success -> {
                    getUserDetails()
                }
                is Results.Error -> {
                    fragmentContext.toast("Something went wrong. Try again later.")
                    logError(it.exception.localizedMessage!!)
                }
            }
        })
    }

    private fun getUserDetails(){

        viewModel.getUserDetails()
        viewModel.getUserDetails.observe(viewLifecycleOwner, {
            when (it) {
                is Results.Loading -> {
                }
                is Results.Success -> {
                    startActivity(Intent(fragmentContext, HomeActivity::class.java))
                    fragmentActivity.finish()
                }
                is Results.Error -> {
                    fragmentContext.toast("Something went wrong. Try again later.")
                    logError(it.exception.localizedMessage!!)
                }
            }
        })

    }

    private fun isValidate(): Boolean {

        if (!isValidMail(binding.etEmailId.text.toString())) {
            binding.etEmailId.error = "Invalid email id."
            return false
        }

        if (!isPasswordValidation(binding.etPassword.text.toString())) {
            binding.etPassword.error = "Incorrect format of password."
            return false
        }

        return true
    }

}