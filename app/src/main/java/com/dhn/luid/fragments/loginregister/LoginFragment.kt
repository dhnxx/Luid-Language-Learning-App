package com.example.luid.fragments.loginregister

import android.app.AlertDialog
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
import android.graphics.drawable.ColorDrawable
import android.util.Patterns
import com.example.luid.classes.User
import com.example.luid.database.DBConnect
import com.example.luid.database.DatabaseBackup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.FileOutputStream
import java.io.IOException


class LoginFragment : Fragment() {

    private lateinit var fbauth: FirebaseAuth
    private lateinit var etemail: EditText
    private lateinit var etpass: EditText
    private lateinit var loginbutton: Button
    private lateinit var guestbutton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dbref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val registerButton = view.findViewById<TextView>(R.id.registerRedirectText)
        val forgot = view.findViewById<TextView>(R.id.forgot_password)
        val context = requireContext()


        fbauth = FirebaseAuth.getInstance()


        etemail = view.findViewById(R.id.login_email)
        etpass = view.findViewById(R.id.login_password)
        loginbutton = view.findViewById(R.id.login_button)
        sharedPreferences =
            requireContext().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        guestbutton = view.findViewById(R.id.guest_button)

        // logged in user
        if (isLoggedIn() && fbauth.currentUser != null) {
            redirectToMain()
            return view
        }

        guestbutton.setOnClickListener() {

            checkAndCopyDatabase(context)
            redirectToMain()
        }

        forgot.setOnClickListener() {
            val builder = AlertDialog.Builder(requireContext())
            val view2 = layoutInflater.inflate(R.layout.dialog_forgot, null)
            val userEmail = view2.findViewById<EditText>(R.id.editBox)
            builder.setView(view2)
            val dialog = builder.create()
            view2.findViewById<Button>(R.id.btnReset).setOnClickListener {
                compareEmail(userEmail)
                dialog.dismiss()
            }
            view2.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                dialog.dismiss()
            }
            if (dialog.window != null) {
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            dialog.show()


        }

        loginbutton.setOnClickListener() {
            val email = etemail.text.toString()
            val pass = etpass.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                fbauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
                        if (currentUser != null) {
                            try {
                                DatabaseBackup().restore(requireContext(), currentUser)
                            } catch (e: Exception) {
                                checkAndCopyDatabase(requireContext())
                                println("CREATED NEW DATABASE ✨✨✨✨")
                                DatabaseBackup().backup(requireContext(), currentUser)

                            }
                        }
                        //
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
        editor.putString("uid", fbauth.currentUser?.uid.toString())
        editor.putString("email", fbauth.currentUser?.email.toString())

        val uid = fbauth.currentUser?.uid
        dbref = FirebaseDatabase.getInstance().getReference("Users/${uid}/")

        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user != null) {
                    editor.putString("fname", user.fname)
                    editor.putString("lname", user.lname)
                    editor.putString("uname", user.uname)
                    editor.apply()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun redirectToMain() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // finish the login activity
    }

    private fun compareEmail(email: EditText) {
        if (email.text.toString().isEmpty()) {
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            return
        }
        fbauth.sendPasswordResetEmail(email.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Check your email", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun checkAndCopyDatabase(context: Context) {
        val databasePath = context.getDatabasePath("LuidDB.db")


        var db = DBConnect(context).readableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)
        var count = cursor.count

        if (!databasePath.exists() || count == 0) {
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

        cursor.close()
        db.close()

    }


}

