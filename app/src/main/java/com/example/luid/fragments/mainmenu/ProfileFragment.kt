package com.example.luid.fragments.mainmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.luid.R


class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Dynamic achievements card view code.
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val achvLinearLayout = view.findViewById<LinearLayout>(R.id.achvlinearLayout)
        val numberOfCards = 5
        for (i in 0 until numberOfCards) {
            val cardView = layoutInflater.inflate(R.layout.achvcardview, achvLinearLayout, false)
            val titleTextView = cardView.findViewById<TextView>(R.id.achvcurrlvl)
            when (i) {
                0 -> titleTextView.text = "Card 1 Title"
                1 -> titleTextView.text = "Card 2 Title"
                2 -> titleTextView.text = "Card 3 Title"
                3 -> titleTextView.text = "Card 4 Title"
                4 -> titleTextView.text = "Card 5 Title"
            }
            achvLinearLayout.addView(cardView)
        }
        return view
    }
}







