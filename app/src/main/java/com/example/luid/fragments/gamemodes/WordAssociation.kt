package com.example.luid.fragments.gamemodes

import android.content.ContentValues
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
import com.example.luid.classes.PhaseOneClass
import com.example.luid.database.DBConnect.Companion.questions_tb
import com.example.luid.database.DBConnect.Companion.temp_qstion


class WordAssociation : AppCompatActivity() {
    private lateinit var questionList: ArrayList<WordAssociationClass>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhaseOneAdapter
    private lateinit var decoy: ArrayList<String>
    private lateinit var progressbar: ProgressBar
    private var contextphaseone: Context = this





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_association)





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

    private fun phaseone() {




        var level = intent.getIntExtra("Level",0)
        var phase = 1
        var db = DBConnect(applicationContext).readableDatabase
        val selectQuery = "SELECT * FROM $questions_tb WHERE level = $level AND phase = $phase"
        val cursorte: Cursor
        cursorte = db.rawQuery(selectQuery, null)
        val cv = ContentValues()


        try {
            if (cursorte.moveToFirst()) {
                do {

                    var levelDB = cursorte.getString(1)
                    var phaseDB = cursorte.getString(2)
                    var questions = cursorte.getString(3)
                    var kapampanganDB = cursorte.getString(4)
                    var englishDB = cursorte.getString(5)
                    var tagalogDB = cursorte.getString(6)
                    var translationDB = cursorte.getString(7)
                    var game_sessionDB = cursorte.getString(8)
                    var easiness_factorDB = cursorte.getString(9)
                    var intervalDB = cursorte.getString(10)
                    var diff_lvlDB = cursorte.getString(11)
                    var times_reviewedDB = cursorte.getString(12)
                    var visibility = cursorte.getString(13)
                    var drawable = cursorte.getString(14)

                    cv.put("level", levelDB)
                    cv.put("phase", phaseDB)
                    cv.put("question", questions)
                    cv.put("kapampangan", kapampanganDB)
                    cv.put("english", englishDB)
                    cv.put("tagalog", tagalogDB)
                    cv.put("translation", translationDB)
                    cv.put("game_session", game_sessionDB)
                    cv.put("easiness_factor", easiness_factorDB)
                    cv.put("interval", intervalDB)
                    cv.put("difficulty_level", diff_lvlDB)
                    cv.put("times_viewed", times_reviewedDB)
                    cv.put("visibility", visibility)
                    cv.put("drawable", drawable)
                    db.insert(temp_qstion, null, cv)
                } while (cursorte.moveToNext())
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

        val queryans = "SELECT * FROM $temp_qstion "
        val cursorans: Cursor
        cursorans = db.rawQuery(queryans, null)
        var kap = PhaseOneClass().getKapampangan(cursorans)
        var eng = PhaseOneClass().getEnglish(cursorans)
        var tag = PhaseOneClass().getTagalog(cursorans)
        var img = PhaseOneClass().getImg(cursorans)
        var prompt = PhaseOneClass().getPrompt((cursorans))
        val decoyImage = ArrayList<String>()
        val answers = ArrayList<String>()
        var question = ArrayList<String>()
        decoy = ArrayList()
        for (i in 0 until 10) {


            decoy.clear()


            if (decoy.isNotEmpty()) {


                throw IllegalStateException("Decoy ArrayList is not empty.")


            } else {

                when ((1..4).random()) {

                    1 -> {
                        decoy.clear()

                        question.add("What is " + kap[i] + " in Tagalog?")
                        answers.add(tag[i])

                        decoy.addAll(tag)
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

                    2 -> {
                        decoy.clear()


                        question.add("What is " + kap[i] + " in English?")
                        answers.add(eng[i])

                        decoy.addAll(eng)
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
        db.execSQL("DELETE FROM $temp_qstion")
        db.close()

        cursorans.close()
        cursorte.close()

    }

}

