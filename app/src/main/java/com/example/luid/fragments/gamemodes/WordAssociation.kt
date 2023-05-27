package com.example.luid.fragments.mainmenu.gamemodes

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adapters.PhaseOneAdapter
import com.example.luid.classes.WordAssociationClass
import com.example.luid.database.DBConnect
import com.example.luid.database.DBConnect.Companion.questions_tb
import com.example.luid.database.DBConnect.Companion.temp_qstion


class WordAssociation : AppCompatActivity() {
    private lateinit var questionList: ArrayList<WordAssociationClass>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhaseOneAdapter
    private lateinit var decoy: ArrayList<String>
    private lateinit var progressbar: ProgressBar
    private lateinit var answers: ArrayList<String>
    var contextphaseone: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_association)

        // intent is used for communicating between fragments to separate activities
        val selectPhase = intent.getStringExtra("Phase")

        progressbar = findViewById(R.id.progressBar)
        progressbar.progress = 1

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        questionList = ArrayList()
        // getQuestionList(selectPhase)
        phaseone()
        adapter = PhaseOneAdapter(recyclerView, questionList, progressbar, contextphaseone)
        recyclerView.adapter = adapter

        progressbar.max = questionList.size


    }

    @SuppressLint("Range")
    private fun phaseone() {
        var level = 1
        var phase = 1
        var db = DBConnect(applicationContext).readableDatabase
        val selectQuery = "SELECT * FROM $questions_tb WHERE level = $level AND phase = $phase"
        val cursor: Cursor
        cursor = db.rawQuery(selectQuery, null)
        // CREATE TEMP TABLE QUESTION
        db.execSQL("CREATE TABLE IF NOT EXISTS $temp_qstion AS SELECT * FROM $questions_tb WHERE level = level AND phase = phase")
        var kap = ArrayList<String>()
        var eng = ArrayList<String>()
        var tag = ArrayList<String>()
        var question = ArrayList<String>()
        decoy = ArrayList()
        answers = ArrayList()
        if (cursor.moveToFirst()) {
            do {
                var kaplist = cursor.getString(cursor.getColumnIndex("kapampangan"))
                var taglist = cursor.getString(cursor.getColumnIndex("tagalog"))
                var englist = cursor.getString(cursor.getColumnIndex("english"))

                kap.add(kaplist)
                tag.add(taglist)
                eng.add(englist)

            } while (cursor.moveToNext())
        }
        for(i in 0 until 10) {
            decoy.clear()

            when ( (1..4).random() ){

                1 -> {
                    decoy.clear()

                    question.add("What is " + kap[i] + " in Tagalog?")
                    answers.add(tag[i])

                    decoy.addAll(tag)
                    for(i in answers){
                        for(j in tag) {
                            if (decoy.contains(i)) {
                                decoy.remove(i)
                                if(answers.contains(j)){
                                    decoy.add(j)
                                }
                            }
                        }
                    }
                }

                2 -> {
                    decoy.clear()

                    question.add("What is " + kap[i] + " in English?")
                    answers.add(eng[i])

                    decoy.addAll(eng)
                    for(i in answers){
                        for(j in eng) {
                            if (decoy.contains(i)) {
                                decoy.remove(i)
                                if(answers.contains(j)){
                                    decoy.add(j)
                                }
                            }
                        }
                    }

                }

                3 -> {
                    decoy.clear()

                    question.add("What is " + tag[i] + " in Kapampangan?")
                    answers.add(kap[i])
                    decoy.addAll(kap)
                    for(i in answers){
                        for(j in kap) {
                            if (decoy.contains(i)) {
                                decoy.remove(i)
                                if(answers.contains(j)){
                                    decoy.add(j)
                                }
                            }
                        }
                    }

                }

                4 -> {
                    decoy.clear()
                    question.add("What is " + eng[i] + " in Kapampangan?")
                    answers.add(kap[i])

                    decoy.addAll(kap)
                    for(i in answers){
                        for(j in kap) {
                            if (decoy.contains(i)) {
                                decoy.remove(i)
                                if(answers.contains(j)){
                                    decoy.add(j)
                                }
                            }
                        }
                    }

                }


            }


            decoy.shuffle()
            var randInd = ArrayList<Int>()
            for (k in 1..decoy.size) {
                randInd.add(k)
            }
            questionList.add(
                WordAssociationClass(
                    question[i],
                    answers[i],
                    R.drawable.home,
                    decoy[randInd[0]],
                    R.drawable.home,
                    decoy[randInd[1]],
                    R.drawable.home,
                    decoy[randInd[2]],
                    R.drawable.home
                )
            )
        }
        // clear question temp table after
        cursor.close()
    }
}



