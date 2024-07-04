package com.dhn.luid.fragments.gamemodes

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
import com.dhn.luid.R
import com.dhn.luid.adapters.ResultAdapter
import com.dhn.luid.classes.ResultScreen
import com.dhn.luid.classes.SMLeitner
import com.dhn.luid.database.DBConnect
import com.dhn.luid.database.DBConnect.Companion.questions_tb
import com.dhn.luid.database.DBConnect.Companion.temp_qstion
import com.dhn.luid.database.DBConnect.Companion.user_records_tb
import com.dhn.luid.database.DatabaseBackup
import com.dhn.luid.fragments.mainmenu.MainActivity
import com.google.firebase.auth.FirebaseAuth


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
        correctPercentageText.text = "$score"
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

        var db = DBConnect(applicationContext).readableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb} WHERE level = $level AND phase = $phase", null)
        var cv = ContentValues()

        var colTime = cursor.getColumnIndex("time_spent")
        var colLives = cursor.getColumnIndex("lives")
        var colGame = cursor.getColumnIndex("game_session_number")
        var colID = cursor.getColumnIndex("_id")

        var lives = 0
        var date = sm.getToday().joinToString(separator = "-")
        var id = 0

        println("COUNT : ${cursor.count}")
        println("DATE : ${date}")

//        cursor.moveToFirst()
//        if (!(cursor.count == 1 && cursor.getInt(colTime) == 0 &&
//                    cursor.getInt(colLives) != 0)){
//            sm.addSession(this, level, phase)
//        }

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
        //
        var db = DBConnect(applicationContext).writableDatabase
        var sm = SMLeitner()
        var lowestGameSession = sm.getLowestGameSessionNumber(this, level, phase)
        var cv = ContentValues()
        var id = 0
        var cursor2 = db.rawQuery("SELECT * FROM $user_records_tb WHERE level = $level AND phase = $phase", null)
        var idCol = cursor2.getColumnIndex("_id")
        if(cursor2.moveToLast()){
            cv.put("game_session_number", lowestGameSession)
            id = cursor2.getInt(idCol)
        }
        db.update("user_records", cv, "_id = $id", null)

        cursor2.close()
        db.close()
        
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        if(currentUser != null) {
            DatabaseBackup().backup(this, currentUser)
        }
        val intent2 = Intent(this, MainActivity::class.java)
        intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent2)
        finish()

    }

}