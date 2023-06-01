package com.example.luid.fragments.gamemodes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luid.R
import com.example.luid.classes.SMLeitner
import com.example.luid.database.DBConnect
import com.example.luid.database.DBConnect.Companion.temp_qstion

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var level = 0
        var phase = 0
        var score = 0.0
        var reward = 0
        var totalItems = 0
        var timeSpent = 0
        var avgTime = 0

        // FOR RESULT SCREEN

        // Query List to be displayed
        val sm = SMLeitner()
        val resultList = sm.compareEF(this)

        // Calculate Score
        val extras = intent.extras
        if(extras != null){
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








    }
}