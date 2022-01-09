package com.quantam.it.assignment.ui.auth.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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
import com.quantam.it.assignment.ui.spinner.category.CountryCodeSpinnerAdapter
import com.quantam.it.assignment.ui.spinner.category.ObjCountryCodes
import com.quantam.it.assignment.utils.*
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

    private var countryCode: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentContext = requireContext()
        fragmentActivity = requireActivity()

        binding = FragmentSignUpBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(fragmentActivity, factory).get(AuthVM::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpCustomSpinner()

       binding.tvSignIn.setOnClickListener {
           findNavController().navigate(R.id.action_sign_up_to_sign_in_fragment)
       }

        binding.btnSignUp.setOnClickListener {
            signUp()
        }
    }

    private fun setUpCustomSpinner(){

        val countryCodeSpinnerAdapter = CountryCodeSpinnerAdapter(fragmentContext, ObjCountryCodes.list!!)
        binding.spinnerCountryCode.adapter = countryCodeSpinnerAdapter

        binding.spinnerCountryCode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                countryCode = ObjCountryCodes.list!![position].code
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

    }

    private fun signUp(){

        if (!isValidate())
            return

        startLoadingDialog(fragmentActivity)

        val name: String = binding.etName.text.toString()
        val emailId: String = binding.etEmailId.text.toString()
        val contactNo = binding.etPhoneNumber.text.toString()
        val password: String = binding.etPassword.text.toString()

        viewModel.createUserWithEmailAndPassword(emailId, password)
        viewModel.createNewAccount.observe(viewLifecycleOwner, {
            when(it){
                is Results.Loading -> {
                    dismissKeyboard(fragmentActivity)
                }
                is Results.Success -> {
                    loadingDialogDismiss()
                    saveData(name, emailId, contactNo, it.data)
                }
                is Results.Error -> {
                    loadingDialogDismiss()
                    fragmentContext.toast("Something went wrong. Try again later.")
                    logError(it.exception.localizedMessage!!)
                }
            }
        })
    }

    private fun saveData(name: String, emailId: String, contactNo: String, id: String) {
        startLoadingDialog(fragmentActivity)

        val timestamp = Timestamp.now()
        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val netDate = Date(milliseconds)

        val data: HashMap<String, Any> = hashMapOf()
        data["name"] = name
        data["email"] = emailId
        data["contactNo"] = contactNo
        data["createdOn"] = netDate
        data["countryCode"] = countryCode

        viewModel.saveUserDetails(data, id)
        viewModel.saveUserDetails.observe(viewLifecycleOwner, {
            when(it){
                is Results.Loading ->{}
                is Results.Success -> {
                    loadingDialogDismiss()
                    findNavController().navigate(R.id.action_sign_up_to_sign_in_fragment)
                }
                is Results.Error -> {
                    loadingDialogDismiss()
                    fragmentContext.toast("Something went wrong. Try again later.")
                    logError(it.exception.localizedMessage!!)
                }
            }
        })
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