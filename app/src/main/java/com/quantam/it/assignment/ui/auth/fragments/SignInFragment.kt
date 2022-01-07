package com.quantam.it.assignment.ui.auth.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.quantam.it.assignment.R

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_sign_in, container, false)

        val switchFragment = view.findViewById<TextView>(R.id.tv_register_now)
        switchFragment.setOnClickListener {
            findNavController().navigate(R.id.action_sign_in_to_sign_up)
        }

        return view
    }

}