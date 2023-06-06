package com.example.luid.fragments.loginregister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.luid.R


class LoginFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val registerButton = view.findViewById<TextView>(R.id.registerRedirectText)


        registerButton.setOnClickListener {
           findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }





        return view
    }

}