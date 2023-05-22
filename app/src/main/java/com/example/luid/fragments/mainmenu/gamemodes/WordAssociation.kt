package com.example.luid.fragments.mainmenu.gamemodes

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adapters.PhaseOneAdapter
import com.example.luid.classes.WordAssociationClass
import com.example.luid.database.DBConnect
import com.example.luid.database.DBConnect.Companion.engWord
import com.example.luid.database.DBConnect.Companion.kapWord
import com.example.luid.database.DBConnect.Companion.questions_tb
import com.example.luid.database.DBConnect.Companion.tagWord


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

    @SuppressLint("Range")
    private fun hello() {
        val questions = ArrayList<String>()
        val answers = ArrayList<String>()
        val decoy = ArrayList<String>()


        val selectQuery = "SELECT * FROM $questions_tb"
        val cursor: Cursor?

        try {
            var helper = DBConnect(applicationContext)
            var db = helper.readableDatabase
            cursor = db.rawQuery(selectQuery, null)

            cursor?.close()
            db.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        decoy.removeAll(answers.toSet())
        decoy.shuffle()


        // Use the exclusive range operator until to avoid index out of bounds
        for (i in 0 until questions.size) {
            questionList.add(
                WordAssociationClass(
                    questions[i],
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
// Add questions to the list
// mag loloop yung query dito na mag aadd ng question sa list
// question.add("What the dog doing")
// Add answers to the list
// connected ang query dito sa questions
// answers.add("Bork")
// Add decoys to the list
// ang query dito is lahat ng words (ang problema dito is yung different choices based sa question
// ex. what is tagalog of ebun? dapat ang choices is tagalog din
// baka magkaroon tayo ng when case dito depende sa questions
//   decoy.add("Bork")
//addtolist.add(WordAssociationClass(questions, correct, correctImg, decoy1, decoy1Img, decoy2, decoy2Img, decoy3, decoy3Img))
// Remove any decoy that matches an answer,
// eto matic na matatanggal yung mga sagot sa choices, para walang duplicate sa choices
/*   if (cursor.moveToFirst()) {
               do {
                   val tagword = cursor.getString(cursor.getColumnIndex("$tagWord"))
                   val kapword = cursor.getString(cursor.getColumnIndex("$kapWord"))
                   val engword = cursor.getString(cursor.getColumnIndex("$engWord"))
                   val randquestiontype = (1..2).random()
                   when(randquestiontype) {
                       1 -> {
                           when(randquestiontype) {
                               1 -> {questions.add("What is " + kapword + " in " + tagword)


                               }
                               2 -> {questions.add("What is " + kapword + " in " + engword)}
                           }
                       }
                       2 -> {
                           when(randquestiontype){
                               1 -> {questions.add("What is " + kapword + " in " + tagword)}
                               2 -> {questions.add("What is "+ tagword +" in "+ kapword)}
                           }
                       }
                   }



                   answers.add(engword)
                   decoy.add(kapword)
               } while (cursor.moveToNext())
           } */