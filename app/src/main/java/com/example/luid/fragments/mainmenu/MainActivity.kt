package com.example.luid.fragments.mainmenu

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.luid.R
import com.example.luid.database.DBConnect.Companion.DB_NAME
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        setupWithNavController(bottomNav, navController)
        bottomNav.itemIconTintList = null

        checkAndCopyDatabase(applicationContext)
    }
    @SuppressLint("SuspiciousIndentation")
    private fun checkAndCopyDatabase(context: Context) {
        val databasePath = context.getDatabasePath("LuidDB.db")


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




