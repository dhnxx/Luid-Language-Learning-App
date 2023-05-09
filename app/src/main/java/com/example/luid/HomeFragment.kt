package com.example.luid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.adapters.LevelAdapter


class HomeFragment : Fragment() {

    private lateinit var levelRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        levelRecyclerView = view.findViewById(R.id.recyclerView)
        levelRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        levelRecyclerView.adapter = LevelAdapter(getLevelList())
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(levelRecyclerView)
        levelRecyclerView.scrollToPosition(0) // Scroll to the first position
        return view
    }

    private fun getLevelList(): List<LevelSelection> {
        // Implement your code to get the level list
        return listOf(
            LevelSelection(
                "Level 1",
                "Introduction",
                R.drawable.home,
                "Start your Kapampangan journey with basic words, personal pronouns, connectors/articles, and adjectives!"
            ),
            LevelSelection(
                "Level 2",
                "Numbers",
                R.drawable.profile,
                "Start your Kapampangan journey with basic words, personal pronouns, connectors/articles, and adjectives!"
            ),
            LevelSelection(
                "Level 3",
                "People",
                R.drawable.about,
                "Start your Kapampangan journey with basic words, personal pronouns, connectors/articles, and adjectives!"
            ),
            LevelSelection(
                "Level 4",
                "Basic Conversations",
                R.drawable.settings,
                "Start your Kapampangan journey with basic words, personal pronouns, connectors/articles, and adjectives!"
            ),
        )
    }
}
