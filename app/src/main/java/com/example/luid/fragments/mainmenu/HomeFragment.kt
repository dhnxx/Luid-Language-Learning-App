package com.example.luid.fragments.mainmenu

import android.content.Context
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

        ////////////////////////LEVEL SELECTION/////////////////////////////

        levelRecyclerView = view.findViewById(R.id.recyclerView)

        levelRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        levelRecyclerView.adapter = LevelAdapter(getLevelList())

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(levelRecyclerView)


        // onCreateView of HomeFragment from LevelAdapter
        levelRecyclerView.adapter = LevelAdapter(getLevelList()).apply {
            setOnItemClickListener { levelSelection ->


                // Store the selected switch state in the ViewModel
                levelSwitchStateViewModel.setSwitchState(levelSelection.levelID)


                // Edit recyclerview's data here based on the level selected
                when (levelSelection.levelID) {
                    "Level 1" -> {
                        Toast.makeText(activity, "Level 1 Test", Toast.LENGTH_LONG).show()

                    }

                    "Level 2" -> {
                        Toast.makeText(activity, "Level 2 Test", Toast.LENGTH_LONG).show()
                    }

                    "Level 3" -> {
                        Toast.makeText(activity, "Level 3 Test", Toast.LENGTH_LONG).show()
                    }

                    "Level 4" -> {
                        Toast.makeText(activity, "Level 4 Test", Toast.LENGTH_LONG).show()
                    }

                    else -> {

                    }
                }
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
                "Level 1",
                "Introduction",
                R.drawable.home,
                "Start your Kapampangan journey with basic words, personal pronouns, connectors/articles, and adjectives!",
            true
            ),
            LevelSelection(
                "Level 2",
                "Numbers",
                R.drawable.profile,
                "Start your Kapampangan journey with basic words, personal pronouns, connectors/articles, and adjectives!",
                2 <= currLevel
            ),
            LevelSelection(
                "Level 3",
                "People",
                R.drawable.about,
                "Start your Kapampangan journey with basic words, personal pronouns, connectors/articles, and adjectives!",
                3 <= currLevel
            ),
            LevelSelection(
                "Level 4",
                "Basic Conversations",
                R.drawable.settings,
                "Start your Kapampangan journey with basic words, personal pronouns, connectors/articles, and adjectives!",
                4 <= currLevel
            ),
        )


    }


}


