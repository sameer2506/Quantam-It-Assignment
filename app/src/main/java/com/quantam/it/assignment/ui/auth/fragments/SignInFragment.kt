package com.quantam.it.assignment.ui.auth.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.quantam.it.assignment.R
import com.quantam.it.assignment.databinding.FragmentSignInBinding
import com.quantam.it.assignment.databinding.FragmentSignUpBinding
import com.quantam.it.assignment.network.Results
import com.quantam.it.assignment.security.isPasswordValidation
import com.quantam.it.assignment.security.isValidMail
import com.quantam.it.assignment.security.isValidMobile
import com.quantam.it.assignment.ui.auth.viewModel.AuthVM
import com.quantam.it.assignment.ui.auth.viewModel.AuthVMF
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentContext = requireContext()
        fragmentActivity = requireActivity()

        binding = FragmentSignInBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(fragmentActivity, factory).get(AuthVM::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkUserIsLogin()

        binding.tvRegisterNow.setOnClickListener {
            findNavController().navigate(R.id.action_sign_in_to_sign_up_fragment)
        }

        binding.btnSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun checkUserIsLogin() {
        viewModel.checkUserLogin()

        viewModel.checkUserLogin.observe(viewLifecycleOwner, {
            when (it) {
                is Results.Success -> {
                    if (it.data) {
                       // checkUserStatus()
                    }
                }
                is Results.Error -> {
                    log("Error in testing")
                }

                is Results.Loading -> {
                }
            }
        })
    }


    private fun signIn(){

        if (!isValidate())
            return

        val emailId: String = binding.etEmailId.text.toString()
        val password: String = binding.etPassword.text.toString()

        viewModel.signInUsingEmail(emailId, password)
        viewModel.signInUsingEmail.observe(viewLifecycleOwner, {
            when(it){
                is Results.Loading -> {}
                is Results.Success -> {
                    fragmentContext.toast("Authenticated")
                }
                is Results.Error -> {
                    fragmentContext.toast("Something went wrong. Try again later.")
                    logError(it.exception.localizedMessage!!)
                }
            }
        })
    }

    private fun isValidate(): Boolean{

        if (!isValidMail(binding.etEmailId.text.toString())){
            binding.etEmailId.error = "Invalid email id."
            return false
        }

        if (!isPasswordValidation(binding.etPassword.text.toString())){
            binding.etPassword.error = "Incorrect format of password."
            return false
        }

        return true
    }

}