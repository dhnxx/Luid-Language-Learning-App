package com.example.luid.fragments.mainmenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adapters.*
import com.example.luid.classes.LevelSelection
import com.example.luid.database.DBConnect
import com.example.luid.database.DBConnect.Companion.achievements_tb
import com.example.luid.database.DBConnect.Companion.questions_tb


class HomeFragment : Fragment() {

    // for Level Selection
    private lateinit var levelRecyclerView: RecyclerView

    //view model to store switch state
    private lateinit var levelSwitchStateViewModel: LevelSwitchStateViewModel




    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Initialize the LevelSwitchStateViewModel instance
        levelSwitchStateViewModel =
            ViewModelProvider(requireActivity()).get(LevelSwitchStateViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bottomNav = activity?.findViewById<View>(R.id.bottomNavigationView)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        bottomNav?.isEnabled = true
        bottomNav?.visibility = View.VISIBLE
        val context = requireContext()

        ////////////////////////LEVEL SELECTION/////////////////////////////


        levelRecyclerView = view.findViewById(R.id.recyclerView)

        levelRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        levelRecyclerView.adapter = LevelAdapter(getLevelList(), context)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(levelRecyclerView)



        // onCreateView of HomeFragment from LevelAdapter
        levelRecyclerView.adapter = LevelAdapter(getLevelList(), context).apply {
            setOnItemClickListener { levelSelection ->


                // Store the selected switch state in the ViewModel
                levelSwitchStateViewModel.setSwitchState(levelSelection.levelID)


                findNavController().navigate(R.id.action_homeFragment_to_tabPhaseReview)
                bottomNav?.isEnabled = false
                bottomNav?.visibility = View.GONE


            }
        }



        return view
    }

    private fun getLevelList(): List<LevelSelection> {
        val db = DBConnect(context).readableDatabase
        var cursor = db.rawQuery("SELECT current_level FROM $achievements_tb WHERE _id = 3", null)
        var currLevel = 0
        var colInd = cursor.getColumnIndex("current_level")

        if(cursor.moveToFirst()){
            currLevel = cursor.getInt(colInd)
        }

        cursor.close()
        db.close()

        return listOf(
            LevelSelection(
                1,
                "Level 1",
                "Pagbabakasyon",
                "(Pamagbakasyun)",
                R.drawable.about_background,
                "Start your Kapampangan journey with basic words, personal pronouns, connectors/articles, and adjectives!",
            true
            ),
            LevelSelection(
                2,
                "Level 2",
                "Pagtuklas",
                "(Pamag--)",
                R.drawable.about_background,
                "Start your Kapampangan journey with basic words, personal pronouns, connectors/articles, and adjectives!",
                2 <= currLevel
            ),
            LevelSelection(
                3,
                "Level 3",
                "Pagpipista",
                "(Pamamyesta)",
                R.drawable.about_background,
                "Start your Kapampangan journey with basic words, personal pronouns, connectors/articles, and adjectives!",
                3 <= currLevel
            ),
            LevelSelection(
                4,
                "Level 4",
                "Pagpapaalam",
                "(Pamagpaalam)",
                R.drawable.about_background,
                "Start your Kapampangan journey with basic words, personal pronouns, connectors/articles, and adjectives!",
                4 <= currLevel
            ),
        )


    }


}


