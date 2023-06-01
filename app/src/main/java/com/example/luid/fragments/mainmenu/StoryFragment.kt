package com.example.luid.fragments.mainmenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.luid.R
import androidx.navigation.fragment.navArgs
import com.example.luid.classes.SMLeitner
import com.example.luid.fragments.gamemodes.SentenceConstruction
import com.example.luid.fragments.gamemodes.SentenceFragment
import com.example.luid.fragments.gamemodes.WordAssociation
import com.example.luid.fragments.gamemodes.WordAssociation2


class StoryFragment : Fragment() {
    private val args: StoryFragmentArgs by navArgs()
    private val selectedLevel: Int by lazy { args.level }
    private val selectedPhase: Int by lazy { args.phase}

    private lateinit var context: Context


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sm = SMLeitner()

        val view = inflater.inflate(R.layout.fragment_story, container, false)

        val intent1 = Intent(context, WordAssociation2::class.java)
        val intent2 = Intent(context, SentenceFragment::class.java)
        val intent3 = Intent(context, SentenceConstruction::class.java)

        val start = view.findViewById<Button>(R.id.startGameButton)

        println(selectedPhase)

        start?.setOnClickListener {

            when (selectedPhase) {
                1 -> {
                    intent1.putExtra("level", selectedLevel)
                    sm.addSession(context, selectedLevel, selectedPhase)
                    startActivity(intent1)
                }
                2 -> {
                    intent2.putExtra("level", selectedLevel)
                    sm.addSession(context, selectedLevel, selectedPhase)
                    startActivity(intent2)
                }
                3 -> {
                    intent3.putExtra("level", selectedLevel)
                    sm.addSession(context, selectedLevel, selectedPhase)
                    startActivity(intent3)
                }
            }
        }





        return view

    }
}
