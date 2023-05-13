package com.example.luid.fragments.mainmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luid.R


class LevelFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bottomNav = activity?.findViewById<View>(R.id.bottomNavigationView)


        return inflater.inflate(R.layout.fragment_level, container, false)
    }


}