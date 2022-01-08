package com.quantam.it.assignment.ui.auth.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.quantam.it.assignment.R
import com.quantam.it.assignment.databinding.FragmentSignUpBinding
import com.quantam.it.assignment.network.Results
import com.quantam.it.assignment.security.isEmpty
import com.quantam.it.assignment.security.isPasswordValidation
import com.quantam.it.assignment.security.isValidMail
import com.quantam.it.assignment.security.isValidMobile
import com.quantam.it.assignment.ui.auth.viewModel.AuthVM
import com.quantam.it.assignment.ui.auth.viewModel.AuthVMF
import com.quantam.it.assignment.utils.logError
import com.quantam.it.assignment.utils.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*

class SignUpFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val factory: AuthVMF by instance()

    private lateinit var fragmentContext: Context
    private lateinit var fragmentActivity: FragmentActivity

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: AuthVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentContext = requireContext()
        fragmentActivity = requireActivity()

        binding = FragmentSignUpBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(fragmentActivity, factory).get(AuthVM::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){

        if (!isValidate())
            return

        val name: String = binding.etName.text.toString()
        val emailId: String = binding.etEmailId.text.toString()
        val contactNo = binding.etPhoneNumber.text.toString()
        val password: String = binding.etPassword.text.toString()

        viewModel.createUserWithEmailAndPassword(emailId, password)
        viewModel.createNewAccount.observe(viewLifecycleOwner, {
            when(it){
                is Results.Loading -> {}
                is Results.Success -> {
                    saveData(name, emailId, contactNo)
                }
                is Results.Error -> {
                    fragmentContext.toast("Something went wrong. Try again later.")
                    logError(it.exception.localizedMessage!!)
                }
            }
        })
    }

    private fun saveData(name: String, emailId: String, contactNo: String) {

        val timestamp = Timestamp.now()
        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val netDate = Date(milliseconds)

        val data: HashMap<String, Any> = hashMapOf()
        data["name"] = name
        data["email"] = emailId
        data["contactNo"] = contactNo
        data["createdOn"] = netDate
        data["countryCode"] = "+91"

        viewModel.saveUserDetails(data)
        viewModel.saveUserDetails.observe(viewLifecycleOwner, {
            when(it){
                is Results.Loading ->{}
                is Results.Success -> {
                    sendVerificationEmail(emailId)
                }
                is Results.Error -> {
                    fragmentContext.toast("Something went wrong. Try again later.")
                    logError(it.exception.localizedMessage!!)
                }
            }
        })
    }

    private fun sendVerificationEmail(emailId: String){
        findNavController().navigate(R.id.action_sign_up_to_sign_in_fragment)

    }

    private fun isValidate(): Boolean{

        if (isEmpty(binding.etName)){
            binding.etName.error = "Name is required."
            return false
        }

        if (!isValidMail(binding.etEmailId.text.toString())){
            binding.etEmailId.error = "Invalid email id."
            return false
        }

        if (!isValidMobile(binding.etPhoneNumber.text.toString())){
            binding.etPhoneNumber.error = "Mobile number required."
            return false
        }

        if (!isPasswordValidation(binding.etPassword.text.toString())){
            binding.etPassword.error = "Incorrect format of password."
            return false
        }

        if (!binding.checkboxTermsAndCondition.isChecked){
            fragmentContext.toast("Please accept terms and conditions.")
            return false
        }

        return true
    }

}