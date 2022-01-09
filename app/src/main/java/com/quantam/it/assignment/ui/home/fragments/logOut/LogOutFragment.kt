package com.quantam.it.assignment.ui.home.fragments.logOut

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.quantam.it.assignment.AppPreferences
import com.quantam.it.assignment.databinding.FragmentLogOutBinding
import com.quantam.it.assignment.ui.auth.activity.AuthenticationActivity
import com.quantam.it.assignment.ui.auth.viewModel.AuthVM
import com.quantam.it.assignment.ui.auth.viewModel.AuthVMF
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class LogOutFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val factory: AuthVMF by instance()

    private lateinit var binding: FragmentLogOutBinding

    private lateinit var viewModel: AuthVM

    private lateinit var fragmentContext: Context
    private lateinit var appPreferences: AppPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogOutBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this, factory).get(AuthVM::class.java)

        fragmentContext = requireContext()
        appPreferences = AppPreferences(fragmentContext)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appPreferences.saveId("")
        appPreferences.saveName("")
        appPreferences.setIsUserLogIn(false)

        viewModel.userLogOut()

        startActivity(Intent(fragmentContext, AuthenticationActivity::class.java))
        requireActivity().finish()
    }

}