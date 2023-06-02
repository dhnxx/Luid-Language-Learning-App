package com.example.luid.fragments.gamemodes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adapters.ResultAdapter
import com.example.luid.classes.ResultScreen
import com.example.luid.classes.SMLeitner
import com.example.luid.database.DBConnect
import com.example.luid.database.DBConnect.Companion.temp_qstion
import com.example.luid.fragments.mainmenu.MainActivity


class ResultActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var resultList: ArrayList<ResultScreen>
    private lateinit var adapter: ResultAdapter
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //recyclerview
        recyclerView = findViewById(R.id.recyclerView)

        //intent
      //  val intent = Intent(this, MainActivity::class.java)

        button = findViewById(R.id.button)

        var level = 0
        var phase = 0
        var score = 0.0
        var reward = 0
        var totalItems = 0
        var timeSpent = 0
        var avgTime = 0

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

        // Query List to be displayed
        val sm = SMLeitner()
        val resultList = sm.compareEF(this)

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

    private fun add() {

        resultList.add(ResultScreen("word1", 0.0, 0, 1))
        resultList.add(ResultScreen("word2", 0.0, 0, 1))
        resultList.add(ResultScreen("word3", 0.0, 0, 1))


    }

    private fun end() {

        startActivity(intent)
        finish()

    }

}