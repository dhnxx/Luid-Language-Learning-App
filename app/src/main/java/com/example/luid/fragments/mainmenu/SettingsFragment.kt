package com.example.luid.fragments.mainmenu

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.luid.R
import com.example.luid.database.DatabaseBackup
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.io.FileOutputStream
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class SettingsFragment : Fragment() {
    private lateinit var fbauth: FirebaseAuth
    private lateinit var button: Button
    private lateinit var accountLayout: LinearLayout
    private lateinit var dataSync: RelativeLayout
    private lateinit var name: TextView
    private lateinit var email: TextView
    private lateinit var nmLayout: RelativeLayout
    private lateinit var changePassword: RelativeLayout
    private lateinit var changeAvatar: RelativeLayout
    private lateinit var saveButton: Button


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        fbauth = FirebaseAuth.getInstance()

        button = view.findViewById(R.id.Button1)
        accountLayout = view.findViewById(R.id.accountLayout)
        dataSync = view.findViewById(R.id.dataSync)
        name = view.findViewById(R.id.name)
        email = view.findViewById(R.id.email)
        changePassword = view.findViewById(R.id.changePassword)
        changeAvatar = view.findViewById(R.id.changeAvatar)
        nmLayout = view.findViewById(R.id.nightModeLayout)
        saveButton = view.findViewById(R.id.saveButton)
        accountLayout.alpha = 1f
        dataSync.alpha = 1f
        val context = requireContext()

        //disable nmlayout

        nmLayout.isEnabled = false
        nmLayout.isClickable = false
        nmLayout.alpha = 0.5f
        changeAvatar.isEnabled = false
        changeAvatar.isClickable = false
        changeAvatar.alpha = 0.5f

        // Initialize FirebaseStorage instance
        val storage = FirebaseStorage.getInstance()
        val uid = fbauth.currentUser?.uid.toString()
        // Get a reference to the root of your storage
        val storageRef = FirebaseStorage.getInstance().reference
        val avatarRef = storageRef.child("backups/user_$uid/avatar_$uid.jpg")


        //change auth password (not forgot password) when change password is clicked
        changePassword.setOnClickListener {

            //create a dialog with custom view for changing password
            val dialog = layoutInflater.inflate(R.layout.dialog_changepassword, null)
            val builder = android.app.AlertDialog.Builder(context)
                .setView(dialog)
            val alertDialog = builder.show()
            //get the edit texts from the dialog
            val oldPassword = dialog.findViewById<EditText>(R.id.editBox)
            val newPassword = dialog.findViewById<EditText>(R.id.editBox2)
            val confirmPassword = dialog.findViewById<EditText>(R.id.editBox3)
            //get the buttons from the dialog
            val cancel = dialog.findViewById<Button>(R.id.btnCancel)
            val change = dialog.findViewById<Button>(R.id.btnReset)
            //when cancel is clicked, dismiss the dialog

            changeAvatar.setOnClickListener{


                //dito yung code sa change avatar
            }




            cancel.setOnClickListener {
                alertDialog.dismiss()
            }
            //when change is clicked, change the password if the old password is correct and the new password is strong enough with the istrongpassword function
            change.setOnClickListener {
                //get the current user
                val user = fbauth.currentUser
                //get the old password from the edit text
                val oldPass = oldPassword.text.toString()
                //get the new password from the edit text
                val newPass = newPassword.text.toString()
                //get the confirm password from the edit text
                val confirmPass = confirmPassword.text.toString()
                //check if the old password is correct
                val credential = EmailAuthProvider.getCredential(user!!.email!!, oldPass)
                user.reauthenticate(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //check if the new password is strong enough
                        if (isStrongPassword(newPass)) {
                            //check if the new password and confirm password match
                            if (newPass == confirmPass) {
                                //change the password
                                user.updatePassword(newPass).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        //password changed successfully
                                        Toast.makeText(
                                            context,
                                            "Password changed successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        alertDialog.dismiss()
                                    } else {
                                        //password change failed
                                        Toast.makeText(
                                            context,
                                            "Password change failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } else {
                                //new password and confirm password do not match
                                Toast.makeText(
                                    context,
                                    "New password and confirm password do not match",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            //new password is not strong enough
                            Toast.makeText(
                                context,
                                "New password is not strong enough",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        //old password is incorrect
                        Toast.makeText(context, "Old password is incorrect", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }


        }


        if (fbauth.currentUser == null) {
            // User is not logged in / Guest Mode

            //make the account layout and data sync alpha 0.5
            accountLayout.alpha = 0.5f
            dataSync.alpha = 0.5f
            //disable the account layout and data sync
            accountLayout.isEnabled = false
            dataSync.isEnabled = false

            button.text = "Login/Register"
            button.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_loginRegister)
            }


        } else {
            // User is logged in

            accountLayout.visibility = View.VISIBLE
            dataSync.visibility = View.VISIBLE
            button.text = "Logout"
            //get the user's name and email in shared preferences
            val sharedPref = requireContext().getSharedPreferences("loginPrefs", 0)
            val fname = sharedPref.getString("fname", "")
            val lname = sharedPref.getString("lname", "")
            val emailPref = sharedPref.getString("email", "")
            //set the name and email textviews to the user's name and email
            name.text = "$fname $lname"
            email.text = emailPref

            button.setOnClickListener {
                //logout current user
                fbauth.signOut()
                findNavController().navigate(R.id.action_settingsFragment_to_loginRegister)
                //delete shared preferences
                val editor = requireContext().getSharedPreferences("loginPrefs", 0).edit()
                editor.clear()
                editor.apply()

                // Clear database path
                val databasePath = context.getDatabasePath("LuidDB.db").absolutePath
                val absolutePath = File(databasePath)
                absolutePath.delete()

            }

            saveButton.setOnClickListener {
                val currentUser = FirebaseAuth.getInstance().currentUser?.uid
                if (currentUser != null) {
                    DatabaseBackup().backup(context, currentUser)
                    Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show()
                }
            }

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

}