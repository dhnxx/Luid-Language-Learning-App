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
import com.example.luid.databinding.ActivityLoginRegisterBinding
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var etemail: EditText
    private lateinit var etpass: EditText
    private lateinit var etconpass: EditText
    private lateinit var registerButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_register, container, false)
        val loginButton = view.findViewById<TextView>(R.id.loginRedirectText)

        firebaseAuth = FirebaseAuth.getInstance()
        etemail = view.findViewById(R.id.signup_email)
        etpass = view.findViewById(R.id.signup_password)
        etconpass = view.findViewById(R.id.signup_confirm)
        registerButton = view.findViewById(R.id.signup_button)


        registerButton.setOnClickListener(){
            val email = etemail.text.toString()
            val pass = etpass.text.toString()
            val confpass = etconpass.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && confpass.isNotEmpty()){
                if(pass == confpass){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if(it.isSuccessful){

                            val intent = Intent(context, LoginRegister::class.java)
                            startActivity(intent)
                        } else {

                            Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else{

                    Toast.makeText(context, "Password does not match.", Toast.LENGTH_SHORT).show()
                }
            } else{

                Toast.makeText(context, "Fields are empty..", Toast.LENGTH_SHORT).show()
            }

        }



        loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

        }





        return view
    }


}