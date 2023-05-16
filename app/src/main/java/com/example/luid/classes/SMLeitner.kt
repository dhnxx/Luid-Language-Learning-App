package com.example.luid.classes

import android.content.AsyncQueryHandler
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.luid.database.DBConnect


class SMLeitner(context : Context) {
    // Table name declarations
    private val tQuestions = "questions"
    private val tUserRecords = "user_records"
    private val tAchievements = "achievements"

    // Declaration of Database connection and cursors for each table
    var dbHelper = DBConnect(context)
    var db = dbHelper.writableDatabase
//    var rsAchievements = db.rawQuery("SELECT * FROM $TAchievements", null)
//    var rsGameSession = db.rawQuery("SELECT * FROM $TGameSession", null)
//    var rsSmLeit = db.rawQuery("SELECT * FROM $TSmLeit", null)

    private var currentGameSession = 0

    init {
        // Initializing of total game sessions done by the user
        var rsUserRecords = db.rawQuery("SELECT * FROM $tUserRecords", null)
        currentGameSession = rsUserRecords.count
        rsUserRecords.close()
    }

    fun getQuality(
        cursor: Cursor,
        answerKey : String,
        userAnswer : String,
        timeTotal : Long) : Int{
        if(userAnswer != answerKey){
            return if (timeTotal <= 180){
                2
            }else if (timeTotal in 181..300){
                1
            }else{
                0
            }
        }else{
            return if(timeTotal in 0..29){
                5
            }else if(timeTotal in 30..60){
                4
            }else{
                3
            }
        }
    }

    fun getEF(quality : Int, prevEF : Double): Double{
        var newEF = prevEF + (0.1 - ((5 - quality) * 0.08) - ((5 - quality) * 0.02))

        if (newEF < 1.3){ newEF = 1.3 }
        if(newEF > 2.5){ newEF = 2.5 }

        return newEF
    }

    fun getDifficultyLevel(EF : Double) : Int{
        return when(EF){
            in 1.55 .. 1.3 -> 0
            in 2.14 .. 1.5 -> 1
            else -> 2
        }
    }

    fun getNewInterval(
        EF : Double,
        difficultyLevel : Int,
        timesViewed : Int) : Int
    {
        var interval = (EF * (difficultyLevel + timesViewed - 1)).toInt()
        var newInterval : Int = 0
        if (interval <= 0) newInterval = 1

        return newInterval
    }

    fun getNewGameSession(
        newInterval : Int,
        prevGameSession : Int) : Int
    {
        return newInterval + prevGameSession
    }

    fun getScore(totalCorrectAnswers : Int, totalItems : Int) : Double{
        return ((totalCorrectAnswers / totalItems)*100).toDouble()
    }

    fun getReward(score : Double) : Double{
        if (score > 80.0){
            return 10 + ((score/100) * 10)
        }else{
            return  ((score/100) * 10)
        }
    }

    fun updAchWF(){

    }
    fun updAchSS(){

    }
    fun updAchPH(){

    }
    fun updAchCL(){

    }
    fun getReward(){

    }
    fun getScore(){

    }
    fun updUserTable(){

    }

}