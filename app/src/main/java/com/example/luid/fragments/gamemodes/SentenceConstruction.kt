package com.example.luid.fragments.gamemodes

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adapters.PhaseThreeAdapter
import com.example.luid.classes.SentenceConstruction

class SentenceConstruction : AppCompatActivity() {

    private lateinit var questionList: ArrayList<SentenceConstruction>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhaseThreeAdapter
    private val context: Context = this
    private var level = 0
    private var phase = 0
    private var timeSpent = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence_construction)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        questionList = ArrayList()
        getQuestionList()
        adapter = PhaseThreeAdapter(recyclerView, questionList, context, level, phase, timeSpent)

        recyclerView.adapter = adapter

    }

    private fun getQuestionList() {
        questionList.add(
            SentenceConstruction(
                "test",
                "test",
                R.drawable.home,
            )
        )
    }
}


