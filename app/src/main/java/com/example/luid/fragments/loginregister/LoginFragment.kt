package com.example.luid.fragments.loginregister

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
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private lateinit var fbauth: FirebaseAuth
    private lateinit var etemail: EditText
    private lateinit var etpass: EditText
    private lateinit var loginbutton: Button

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

        loginbutton.setOnClickListener(){
            val email = etemail.text.toString()
            val pass = etpass.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()){
                fbauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(context, LoginRegister::class.java)
                        startActivity(intent)
                    } else{
                        Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else{
                Toast.makeText(context, "Fields are empty..", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }


            return view
        }
    }
