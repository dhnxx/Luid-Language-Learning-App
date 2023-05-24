package com.example.luid.fragments.mainmenu.gamemodes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adaptersQ.PhaseTwoAdapter
import com.example.luid.classes.SentenceFragment


class SentenceFragment : AppCompatActivity() {

    private lateinit var questionList: ArrayList<SentenceFragment>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhaseTwoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentencefragment)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        questionList = ArrayList()
        getQuestionList()
        adapter = PhaseTwoAdapter(recyclerView, questionList)
        recyclerView.adapter = adapter

    }


    private fun getQuestionList() {
    questionList.add(
        SentenceFragment(
            "What is Good Morning Neighbours in Kapampangan?", "Magandang Umaga Kapitbahay", R.drawable.home,

        )

    )

        questionList.add(
            SentenceFragment(
                "What is Good Morning Neighbours in Kapampangan?", "Magandang Umaga Kapitbahay", R.drawable.home,

                )

        )


    }
}
