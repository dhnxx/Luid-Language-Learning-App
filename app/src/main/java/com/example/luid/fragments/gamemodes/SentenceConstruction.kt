package com.example.luid.fragments.gamemodes

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adapters.PhaseThreeAdapter
import com.example.luid.classes.SentenceConstruction
import com.example.luid.database.DBConnect

class SentenceConstruction : AppCompatActivity() {

    private lateinit var questionList: ArrayList<SentenceConstruction>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhaseThreeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence_construction)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        questionList = ArrayList()
        getQuestionList()
        adapter = PhaseThreeAdapter(recyclerView, questionList)
        recyclerView.adapter = adapter

    }

    @SuppressLint("Range")
    private fun getQuestionList() {

        val question = ArrayList<String>()
        val questiont = ArrayList<String>()
        val sentence = ArrayList<String>()
        val sentencet = ArrayList<String>()
        var level = 3
        var phase = 3
        var db = DBConnect(applicationContext).readableDatabase
        val selectQuery = "SELECT * FROM ${DBConnect.questions_tb} WHERE level = $level AND phase = $phase"
        val cursor: Cursor
        cursor = db.rawQuery(selectQuery, null)
        // CREATE TEMP TABLE QUESTION
        db.execSQL("CREATE TABLE IF NOT EXISTS ${DBConnect.temp_qstion} AS SELECT * FROM ${DBConnect.questions_tb} WHERE level = level AND phase = phase")

        if (cursor.moveToFirst()) {
            do {
                var questions = cursor.getString(cursor.getColumnIndex("question"))
                var translation = cursor.getString(cursor.getColumnIndex("translation"))

                questiont.add(questions)
                sentencet.add(translation)

            } while (cursor.moveToNext())
        }
        for (i in 0 until 10) {

            question.add(questiont[i])
            sentence.add(sentencet[i])

            questionList.add(
                SentenceConstruction(
                    question[i],
                    sentence[i],
                    R.drawable.home,
                )
            )
        }
    }
}


