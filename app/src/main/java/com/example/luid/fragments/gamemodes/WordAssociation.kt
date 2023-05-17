package com.example.luid.fragments.gamemodes

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adapters.LevelAdapter
import com.example.luid.adapters.PhaseOneAdapter
import com.example.luid.classes.WordAssociationClass
import com.google.android.material.color.MaterialColors



class WordAssociation : AppCompatActivity() {
    private lateinit var questionlist: ArrayList<WordAssociationClass>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhaseOneAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_association)


/*
        // get the color reference from ?attr
       val color = MaterialColors.getColor(this, androidx.appcompat.R.attr.colorPrimary, Color.BLACK)


       window.navigationBarColor = color
    */
        recyclerView = findViewById(R.id.recyclerView)
       recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
       questionlist = ArrayList()
        getQuestionList()
        adapter = PhaseOneAdapter(recyclerView, questionlist)
        recyclerView.adapter = adapter


    }

    private fun getQuestionList() {
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
                R.drawable.profile,
            )
        )

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
                R.drawable.profile,
            )
        )

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
                R.drawable.profile,
            )
        )

        questionlist.add(
            WordAssociationClass(
                "Which animal is known for its long neck?",
                "Giraffe",
                R.drawable.home,
                "Elephant",
                R.drawable.settings,
                "Lion",
                R.drawable.about,
                "Tiger",
                R.drawable.profile,
            )
        )

        questionlist.add(
            WordAssociationClass(
                "What is the largest planet in our solar system?",
                "Jupiter",
                R.drawable.home,
                "Mars",
                R.drawable.settings,
                "Saturn",
                R.drawable.about,
                "Earth",
                R.drawable.profile,
            )
        )

        questionlist.add(
            WordAssociationClass(
                "Who painted the Mona Lisa?",
                "Leonardo da Vinci",
                R.drawable.home,
                "Vincent van Gogh",
                R.drawable.settings,
                "Pablo Picasso",
                R.drawable.about,
                "Michelangelo",
                R.drawable.profile,
            )
        )


    }
}
