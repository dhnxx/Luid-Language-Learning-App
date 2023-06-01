package com.example.luid.fragments.gamemodes

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adaptersQ.PhaseTwoAdapter
import com.example.luid.classes.PhaseOneClass
import com.example.luid.classes.PhaseTwoClass
import com.example.luid.classes.SentenceFragment
import com.example.luid.database.DBConnect
import com.example.luid.database.DBConnect.Companion.questions_tb
import com.example.luid.database.DBConnect.Companion.temp_qstion

class SentenceFragment : AppCompatActivity() {

    private lateinit var questionList: ArrayList<SentenceFragment>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhaseTwoAdapter
    private lateinit var progressBar: ProgressBar
    private var context: Context = this
    private var level = 0
    private var phase = 0
    private var timeSpent = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentencefragment)


        progressBar = findViewById(R.id.progressBar)

        progressBar.progress = 1

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        questionList = ArrayList()
        getQuestionList()
        adapter = PhaseTwoAdapter(recyclerView, questionList, progressBar,context,level,phase,timeSpent)
        recyclerView.adapter = adapter

        progressBar.max = questionList.size
    }
    private fun getQuestionList() {

        var level = 1
        var phase = 2
        var db = DBConnect(applicationContext).readableDatabase
        var cursor = db.rawQuery("SELECT * FROM $questions_tb WHERE level = $level AND phase = $phase", null)
        val cv = ContentValues()

        try {
            if (cursor.moveToFirst()) {
                do {

                    cv.put("_id", cursor.getInt(0))
                    cv.put("level", cursor.getString(1))
                    cv.put("phase", cursor.getString(2))
                    cv.put("question", cursor.getString(3))
                    cv.put("kapampangan", cursor.getString(4))
                    cv.put("english", cursor.getString(5))
                    cv.put("tagalog", cursor.getString(6))
                    cv.put("translation", cursor.getString(7))
                    cv.put("game_session", cursor.getString(8))
                    cv.put("easiness_factor", cursor.getString(9))
                    cv.put("interval", cursor.getString(10))
                    cv.put("difficulty_level", cursor.getString(11))
                    cv.put("times_viewed", cursor.getString(12))
                    cv.put("visibility", cursor.getString(13))
                    cv.put("drawable", cursor.getString(14))
                    db.insert("$temp_qstion", null, cv)
                } while (cursor.moveToNext())
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

        cv.clear()
        cursor.close()
        db.close()

        var kap = PhaseTwoClass().getKapampangan(this, level, phase)
        var eng = PhaseTwoClass().getEnglish(this, level, phase)
        var tag = PhaseTwoClass().getTagalog(this, level, phase)
        var img = PhaseTwoClass().getImg(this, level, phase)
        var imgList = ArrayList<Int>()
        var answers = ArrayList<String>()
        var questions = ArrayList<String>()

        print("KAP SIZE: ${kap.size}\n")
        print("ENG SIZE: ${eng.size}\n")
        print("TAG SIZE: ${tag.size}\n")
        print("IMG SIZE: ${img.size}\n")

        for (i in 0 until kap.size){
            when((1..4).random()){
                1 -> {
                    // Kapampangan -> Tagalog
                    questions.add("What is this phrase in Tagalog?\n${kap[i]}")
                    answers.add(tag[i])
                    imgList.add(img[i])
                }
                2 -> {
                    // Kapampangan -> English
                    questions.add("What is this phrase in English?\n${kap[i]}")
                    answers.add(eng[i])
                    imgList.add(img[i])
                }
                3 -> {
                    // Tagalog -> Kapampanngan
                    questions.add("What is this phrase in Kapampangan?\n${tag[i]}")
                    answers.add(kap[i])
                    imgList.add(img[i])
                }
                4 -> {
                    // English -> Kapampanngan
                    questions.add("What is this phrase in Kapampangan?\n${eng[i]}")
                    answers.add(kap[i])
                    imgList.add(img[i])
                }
            }

        }

        var randInd = ArrayList<Int>()
        for (k in 1 until kap.size){
            randInd.add(k)
        }

//        for (i in 1 until kap.size){
//            questionList.add(
//                SentenceFragment(
//                    questions[i],
//                    answers[i],
////                    imgList[i]
//                    R.drawable.home
//                )
//            )
//        }



    }
    private fun refreshview() {


    }


}
