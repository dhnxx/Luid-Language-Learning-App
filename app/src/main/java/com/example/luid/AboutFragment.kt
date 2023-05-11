package com.example.luid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController


class AboutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_about, container, false)
        val cardluid = view.findViewById<View>(R.id.card_luid)
        val cardquinta = view.findViewById<View>(R.id.card_quinta)
        val bottomNav = activity?.findViewById<View>(R.id.bottomNavigationView)

        bottomNav?.isEnabled = true
        bottomNav?.visibility = View.VISIBLE

        cardluid.setOnClickListener()
        {

            findNavController().navigate(R.id.action_aboutFragment_to_about_luid2)
            bottomNav?.isEnabled = false
            bottomNav?.visibility = View.GONE
        }

        cardquinta.setOnClickListener()
        {

            findNavController().navigate(R.id.action_aboutFragment_to_about_quinta)
            bottomNav?.isEnabled = false
            bottomNav?.visibility = View.GONE
        }


        return view

    }

}