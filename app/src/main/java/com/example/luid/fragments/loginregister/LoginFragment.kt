package com.example.luid.fragments.loginregister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.luid.R
import com.example.luid.fragments.mainmenu.MainActivity
import com.google.firebase.auth.FirebaseAuth
import android.content.SharedPreferences


class LoginFragment : Fragment() {

    private lateinit var fbauth: FirebaseAuth
    private lateinit var etemail: EditText
    private lateinit var etpass: EditText
    private lateinit var loginbutton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val registerButton = view.findViewById<TextView>(R.id.registerRedirectText)

        fbauth = FirebaseAuth.getInstance()
        etemail = view.findViewById(R.id.login_email)
        etpass = view.findViewById(R.id.login_password)
        loginbutton = view.findViewById(R.id.login_button)
        sharedPreferences =
            requireContext().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        if (isLoggedIn()) {
            redirectToMain()
            return view
        }


        loginbutton.setOnClickListener() {
            val email = etemail.text.toString()
            val pass = etpass.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                fbauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        saveLoginStatus()
                        redirectToMain()
                    } else {
                        Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Fields are empty..", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }


        return view
    }

    private fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun saveLoginStatus() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }

    private fun redirectToMain() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Optional: Close the login activity after redirecting to the main activity
    }


}
