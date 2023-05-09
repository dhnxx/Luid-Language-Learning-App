package com.example.luid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.luid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.fragment_home)

        setContentView(binding.root)
        replaceFragment(HomeFragment())
        binding.bottomNavigationView.itemIconTintList = null


        // Display bottom navigation view/ fragments

        binding.bottomNavigationView.setOnItemSelectedListener {


            when (it.itemId) {

                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.info -> replaceFragment(AboutFragment())
                R.id.settings -> replaceFragment(SettingsFragment())

                else -> {

                }

            }

            true

        }
        replaceFragment(HomeFragment())
    }


    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()


    }

}

