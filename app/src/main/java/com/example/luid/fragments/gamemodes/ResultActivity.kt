package com.example.luid.fragments.gamemodes

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adapters.ResultAdapter
import com.example.luid.classes.ResultScreen
import com.example.luid.classes.SMLeitner
import com.example.luid.database.DBConnect
import com.example.luid.database.DBConnect.Companion.questions_tb
import com.example.luid.database.DBConnect.Companion.temp_qstion
import com.example.luid.fragments.mainmenu.MainActivity
import org.w3c.dom.Text


class ResultActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var resultList: ArrayList<ResultScreen>
    private lateinit var adapter: ResultAdapter
    private lateinit var button: Button
    private var level = 0
    private var phase = 0
    private var score = 0.0
    private var reward = 0
    private var totalItems = 0
    private var timeSpent = 0
    private var avgTime = 0

    private lateinit var avgTimeText: TextView
    private lateinit var correctPercentageText: TextView
    private lateinit var currencyText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //recyclerview
        recyclerView = findViewById(R.id.recyclerView)

        //intent


        button = findViewById(R.id.button)

        // Query List to be displayed
        val sm = SMLeitner()

        // Calculate Score
        val extras = intent.extras
        if (extras != null) {
            level = extras.getInt("level") // To be displayed in result Screen
            phase = extras.getInt("phase") // To be displayed in result Screen
            score = extras.getDouble("score") // To be displayed in result Screen
            reward = sm.rewardCalc(score) // To be displayed in result Screen
            totalItems = extras.getInt("totalItems")
            timeSpent = extras.getInt("totalTime") // To be displayed in result Screen
            avgTime = extras.getInt("avgTime") // To be displayed in result Screen
        }
        avgTimeText = findViewById(R.id.avgTime)
        correctPercentageText = findViewById(R.id.correctPercentage)
        currencyText = findViewById(R.id.currency)

        avgTimeText.text = "$avgTime s"
        correctPercentageText.text = "${String.format("%2f", score).toDouble()}%"
        currencyText.text = "$reward"


        //RECYCLER VIEW

        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        resultList = ArrayList()
        add()
        adapter = ResultAdapter(resultList)
        recyclerView.adapter = adapter

        //END OF RECYCLER VIEW


        // FOR RESULT SCREEN


        // JOIN TEMP AND QUESTION TABLE HERE

        // UPDATE USER RECORDS
        sm.updUserRecords(this, level, phase, score, timeSpent, reward)

        // UPDATING ACHIEVEMENTS
        sm.updAchWF(this)
        sm.updAchSS(this)
        sm.updAchTS(this)
        sm.updAchCL(this)


        button.setOnClickListener {

            end()
        }


    }

    override fun onBackPressed() {
        end()
    }


    @SuppressLint("Range")
    private fun add() {
        val sm = SMLeitner()
        val resultListDB = sm.compareEF(this)
        var db = DBConnect(applicationContext).readableDatabase


        for (i in 0 until resultListDB.size) {
            resultList.add(
                ResultScreen(
                    resultListDB[i].word,
                    String.format("%2f", resultListDB[i].finalEF).toDouble(),
                    resultListDB[i].finalDF,
                    resultListDB[i].status
                )
            )
        }


        var cursor: Cursor

        cursor = db.rawQuery(
            "SELECT * FROM $temp_qstion WHERE level = $level AND phase = $phase",
            null
        )

        if (cursor.moveToNext()) {

            val cv = ContentValues()

            do {

                val id = cursor.getLong(cursor.getColumnIndex("_id"))

                val gamesession = cursor.getInt(cursor.getColumnIndex("game_session"))

                val EaFa = cursor.getDouble(cursor.getColumnIndex("easiness_factor"))

                val interval = cursor.getInt(cursor.getColumnIndex("interval"))

                val difflevel = cursor.getInt(cursor.getColumnIndex("difficulty_level"))

                val timviewed = cursor.getInt(cursor.getColumnIndex("times_viewed"))

                cv.put("game_session", gamesession)
                cv.put("easiness_factor", EaFa)
                cv.put("interval", interval)
                cv.put("difficulty_level", difflevel)
                cv.put("times_viewed", timviewed)

                db.update("$questions_tb", cv, "_id = $id", null)

            } while (cursor.moveToNext())
        }

    }

    private fun end() {
        val intent2 = Intent(this, MainActivity::class.java)
        intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent2)
        finish()

    }

}