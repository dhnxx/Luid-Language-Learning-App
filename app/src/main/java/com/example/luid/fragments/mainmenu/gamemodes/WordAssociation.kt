package com.example.luid.fragments.mainmenu.gamemodes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adapters.PhaseOneAdapter
import com.example.luid.classes.WordAssociationClass


class WordAssociation : AppCompatActivity() {
    private lateinit var questionList: ArrayList<WordAssociationClass>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhaseOneAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_association)

        // intent is used for communicating between fragments to separate activities
        val selectPhase = intent.getStringExtra("Phase")

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        questionList = ArrayList()
        // getQuestionList(selectPhase)
        hello()
        adapter = PhaseOneAdapter(recyclerView, questionList)
        recyclerView.adapter = adapter

    }


    /*
        private fun getQuestionList(switchState: String?) {
            when (switchState) {
                "Phase 1.1" -> {
                    questionlist.add(
                        WordAssociationClass(
                            "What is the color of the sky?",
                            "Blue",
                            R.drawable.home,
                            "Red",
                            R.drawable.settings,
                            "Green",
                            R.drawable.about,
                            "Yellow",
                            R.drawable.profile
                        )
                    )
                }
                "Phase 1.2" -> {
                    questionlist.add(
                        WordAssociationClass(
                            "What is the capital of France?",
                            "Paris",
                            R.drawable.home,
                            "Madrid",
                            R.drawable.settings,
                            "Berlin",
                            R.drawable.about,
                            "London",
                            R.drawable.profile
                        )
                    )
                }
            }
        }
    */
    private fun hello() {
        val question = ArrayList<String>()
        // Add questions to the list
        // mag loloop yung query dito na mag aadd ng question sa list

        question.add("What the dog doing")
        question.add("Milo Dino QUESTION?")
        question.add("TEST TEST")

        val answers = ArrayList<String>()
        // Add answers to the list
        // connected ang query dito sa questions
        answers.add("Bork")
        answers.add("Milo Dino")
        answers.add("Test Test")

        val decoy = ArrayList<String>()
        // Add decoys to the list

        // ang query dito is lahat ng words (ang problema dito is yung different choices based sa question
        // ex. what is tagalog of ebun? dapat ang choices is tagalog din
        // baka magkaroon tayo ng when case dito depende sa questions
        decoy.add("Bork")
        decoy.add("Milo Dino")
        decoy.add("Test Test")
        decoy.add("random1")
        decoy.add("random2")
        decoy.add("random3")
        decoy.add("random4")
        decoy.add("random5")
        decoy.add("random6")

        // Remove any decoy that matches an answer,
        // eto matic na matatanggal yung mga sagot sa choices, para walang duplicate sa choices

        decoy.removeAll(answers.toSet())
        decoy.shuffle()


        // Use the exclusive range operator until to avoid index out of bounds
        for (i in 0 until question.size) {
            questionList.add(
                WordAssociationClass(
                    question[i],
                    answers[i],
                    R.drawable.home,
                    decoy[0],
                    R.drawable.home,
                    decoy[1],
                    R.drawable.home,
                    decoy[2],
                    R.drawable.home
                )
            )
        }
    }
}
