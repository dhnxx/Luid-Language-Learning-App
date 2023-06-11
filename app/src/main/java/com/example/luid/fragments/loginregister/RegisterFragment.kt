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
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.luid.R
import com.example.luid.classes.User
import com.example.luid.database.DatabaseBackup
import com.example.luid.databinding.ActivityLoginRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.FileOutputStream
import java.io.IOException


class RegisterFragment : Fragment() {

    private lateinit var dbref: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var etemail: EditText
    private lateinit var etuname: EditText
    private lateinit var etfname: EditText
    private lateinit var etlname: EditText
    private lateinit var etpass: EditText
    private lateinit var etconpass: EditText
    private lateinit var registerButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val loginButton = view.findViewById<TextView>(R.id.loginRedirectText)

        firebaseAuth = FirebaseAuth.getInstance()
        etemail = view.findViewById(R.id.signup_email)
        etuname = view.findViewById(R.id.signup_uname)
        etfname = view.findViewById(R.id.signup_fname)
        etlname = view.findViewById(R.id.signup_lname)
        etpass = view.findViewById(R.id.signup_password)
        etconpass = view.findViewById(R.id.signup_confirm)
        registerButton = view.findViewById(R.id.signup_button)

        dbref = FirebaseDatabase.getInstance().getReference("Users")
        registerButton.setOnClickListener() {
            val email = etemail.text.toString()
            val uname = etuname.text.toString()
            val fname = etfname.text.toString()
            val lname = etlname.text.toString()
            val pass = etpass.text.toString()
            val confpass = etconpass.text.toString()


            val user = User(uname, fname, lname)
            if (email.isNotEmpty() && pass.isNotEmpty() && confpass.isNotEmpty() && uname.isNotEmpty() && fname.isNotEmpty() && lname.isNotEmpty()) {
                if (isStrongPassword(pass)) {
                    if (pass == confpass) {
                        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val uid = firebaseAuth.currentUser?.uid
                                    println(uid)
                                    if (uid != null) {
                                        println("Inside uid != null")
                                        dbref.child(uid).setValue(user).addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                println("Inside successful setvalueuser")
                                                Toast.makeText(
                                                    context,
                                                    "Successful",
                                                    Toast.LENGTH_SHORT
                                                )
                                                checkAndCopyDatabase(requireContext())

                                                val currentUser = FirebaseAuth.getInstance().currentUser?.uid
                                                if(currentUser != null) {
                                                    DatabaseBackup().backup(requireContext(), currentUser)
                                                }


                                                val intent =
                                                    Intent(context, LoginRegister::class.java)
                                                startActivity(intent)




                                            } else {
                                                println("Inside failed setvalueuser")
                                                Toast.makeText(
                                                    context,
                                                    "Failed to update profile",
                                                    Toast.LENGTH_SHORT
                                                )
                                            }

                                        }
                                    }
                                    Toast.makeText(
                                        context,
                                        it.exception.toString(),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                } else {

                                    Toast.makeText(
                                        context,
                                        it.exception.toString(),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                    } else {

                        Toast.makeText(context, "Password does not match.", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(context, "Password is not strong enough.", Toast.LENGTH_SHORT)
                        .show()

                    // create a dialog box to show the password requirements

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Password Requirements")
                    builder.setMessage("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
                    builder.setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                        etpass.setText("")
                        etconpass.setText("")
                    }

                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.show()


                }


            } else {

                Toast.makeText(context, "Fields are empty..", Toast.LENGTH_SHORT).show()
            }


        }



        loginButton.setOnClickListener {

            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

        }





        return view
    }

    private fun isStrongPassword(password: String): Boolean {
        val minLength = 8
        val minUpperCase = 1
        val minLowerCase = 1
        val minDigits = 1
        val minSpecialChars = 1

        // Check password length
        if (password.length < minLength) {
            return false
        }

        // Check uppercase letters
        var upperCaseCount = 0
        for (char in password) {
            if (char.isUpperCase()) {
                upperCaseCount++
            }
        }
        if (upperCaseCount < minUpperCase) {
            return false
        }

        // Check lowercase letters
        var lowerCaseCount = 0
        for (char in password) {
            if (char.isLowerCase()) {
                lowerCaseCount++
            }
        }
        if (lowerCaseCount < minLowerCase) {
            return false
        }

        // Check digits
        var digitCount = 0
        for (char in password) {
            if (char.isDigit()) {
                digitCount++
            }
        }
        if (digitCount < minDigits) {
            return false
        }

        // Check special characters
        val specialChars = setOf(
            '!',
            '@',
            '#',
            '$',
            '%',
            '^',
            '&',
            '*',
            '(',
            ')',
            '-',
            '_',
            '+',
            '=',
            '{',
            '}',
            '[',
            ']',
            '|',
            '\\',
            ':',
            ';',
            '<',
            '>',
            ',',
            '.',
            '?',
            '/'
        )
        var specialCharCount = 0
        for (char in password) {
            if (specialChars.contains(char)) {
                specialCharCount++
            }
        }
        if (specialCharCount < minSpecialChars) {
            return false
        }

        // Password meets all criteria
        return true
    }

    private fun checkAndCopyDatabase(context: Context) {
        val databasePath = context.getDatabasePath("LuidDB.db")


        if (!databasePath.exists()) {
            // Database file doesn't exist, so copy the template to the user's phone
            try {
                val inputStream = context.assets.open("LuidDB.db")
                val outputStream = FileOutputStream(databasePath)
                val buffer = ByteArray(1024)
                var length: Int

                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(buffer, 0, length)
                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()
            } catch (e: IOException) {

                e.printStackTrace()


            }
        }
    }


}