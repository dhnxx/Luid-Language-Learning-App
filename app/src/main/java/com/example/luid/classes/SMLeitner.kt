package com.example.luid.classes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import androidx.core.graphics.toColorLong
import com.example.luid.database.DBConnect
import java.util.Calendar


class SMLeitner(context : Context) {
    // Table name declarations
    private val tQuestions = "questions"
    private val tUserRecords = "user_records"
    private val tAchievements = "achievements"

//    var rsAchievements = db.rawQuery("SELECT * FROM $TAchievements", null)
//    var rsGameSession = db.rawQuery("SELECT * FROM $TGameSession", null)
//    var rsSmLeit = db.rawQuery("SELECT * FROM $TSmLeit", null)

    private var currentGameSession = 0

    init {
        var dbHelper = DBConnect(context)
        var db = dbHelper.writableDatabase
        // Initializing of total game sessions done by the user
        var rsUserRecords = db.rawQuery("SELECT * FROM $tUserRecords", null)
        currentGameSession = rsUserRecords.count
        rsUserRecords.close()
        db.close()
    }

    fun getQuality(cursor: Cursor, answerKey : String, userAnswer : String, timeTotal : Long) : Int{
        if(userAnswer != answerKey){
            return when {
                timeTotal <= 180 -> {
                    2
                }
                timeTotal in 181..300 -> {
                    1
                }
                else -> {
                    0
                }
            }
        }else{
            return when (timeTotal) {
                in 0..29 -> {
                    5
                }
                in 30..60 -> {
                    4
                }
                else -> {
                    3
                }
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

    fun getNewInterval(EF : Double, difficultyLevel : Int, timesViewed : Int) : Int
    {
        var interval = (EF * (difficultyLevel + timesViewed - 1)).toInt()
        var newInterval : Int = 0
        if (interval <= 0) newInterval = 1

        return newInterval
    }

    fun getNewGameSession(newInterval : Int, prevGameSession : Int) : Int
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

    // Methods for Achievements

    //Revise to update current_progress and current_level
    fun updAchWF(context: Context){
        val dbHelper = DBConnect(context)
        val ldb = dbHelper.writableDatabase
        val columnName = "date_played"
        val lCursor = ldb.rawQuery("SELECT $columnName FROM $tUserRecords ORDER BY _id DESC LIMIT 1", null)
        var dbDate : String
        var dbDateParts : List<String>
        var dbDay : Int = 0
        var dbMonth : Int = 0
        var dbYear : Int = 0
        var currProg: Int = 0

        val today = Calendar.getInstance()
        var day = today.get(Calendar.DAY_OF_MONTH)
        val month = today.get(Calendar.MONTH) + 1
        val year = today.get(Calendar.YEAR)

        if (lCursor.count <= 1){
            ldb.close()
            currProg = 1
        }

        if (lCursor.moveToFirst()){
            dbDate = lCursor.getString(2)
            dbDateParts = dbDate.split("/")
            dbDay = dbDateParts[0].toInt()
            dbMonth = dbDateParts[1].toInt()
            dbYear = dbDateParts[2].toInt()
        }

        if (((dbYear - year) == 0) && ((dbMonth - month) == 0) && (dbDay - day == 0)) {
            val cursorCurProg = ldb.rawQuery("SELECT * FROM $tAchievements", null)
            val dbCurrProg = cursorCurProg.getColumnIndex("current_progress")

            if (cursorCurProg.moveToFirst()) {
                currProg = cursorCurProg.getInt(dbCurrProg)
            }

            currProg += 1
            cursorCurProg.close()
        } else {
            ldb.close()
            currProg = 1
        }

        val cv = ContentValues()
        cv.put("current_progress", "${currProg + 1}")
        ldb.update("$tAchievements", cv, "_id = 1", null)

        cv.clear()
        lCursor.close()
        ldb.close()
    }

    // Method to update Sharp Shooter Achievement in achievements table
    fun updAchSS(context: Context){
        var colName = "score"
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var lCursor = ldb.rawQuery("SELECT $colName FROM $tUserRecords WHERE score = 100.00", null)
        var totalCount = lCursor.count
        var currLevel = 0

        if (totalCount == 0) {
            return
        }

        when (totalCount){
            1 -> currLevel = 1
            in 2 .. 3 -> currLevel = 2
            in 4 .. 5 -> currLevel = 3
            in 6 .. 7 -> currLevel = 4
            in 10 .. 8 -> currLevel = 5

        }

        val cv = ContentValues()
        cv.put("current_level", currLevel)
        cv.put("current_progress", totalCount)
        ldb.update("$tAchievements", cv, "_id = 2", null)

        cv.clear()
        lCursor.close()
        ldb.close()
    }
    fun updAchTS(context: Context){
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        val colTime = "time_spent"
        val colCurrProg =  "current_progress"
        val colCurrLevel = "current_level"
        val lCursor = ldb.rawQuery("SELECT SUM($colTime) FROM $tUserRecords", null)
        val dbCurrTime = lCursor.getColumnIndex("$colTime")
        val dbCurrProg = lCursor.getColumnIndex("$colCurrProg")
        val dbCurrlevel = lCursor.getColumnIndex("$colCurrLevel")
        var totalMinutes = 0
        var currProg = 0
        var currLevel = 0

        if (lCursor.moveToFirst()){
            totalMinutes = lCursor.getInt(dbCurrTime)
            currProg = totalMinutes
        }

        currLevel = when (totalMinutes){
            in 0 .. 100 -> 1
            in 101 .. 200 -> 2
            in 201 .. 300 -> 3
            in 301 .. 400 -> 4
            else -> 4
        }

        var cv = ContentValues()
        cv.put("current_progress", currProg)
        cv.put("current_level", currLevel)
        ldb.update("$tAchievements", cv, "id = 4",null)

        cv.clear()
        lCursor.close()
        ldb.close()
    }
    fun updAchCL(context: Context){
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        ldb.close()
    }


    // Get the latest score
    fun getScore(context: Context) : Double{
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var colScore = "score"
        var lCursor = ldb.rawQuery("SELECT $colScore FROM $tUserRecords ORDER BY _id DESC LIMIT 1", null)
        var colScoreInd = lCursor.getColumnIndex("$colScore")
        var latestScore = 0.0

        if (lCursor.moveToFirst()){
            latestScore = lCursor.getDouble(colScoreInd)
        }

        lCursor.close()
        ldb.close()

        return latestScore
    }
    fun updUserTable(context: Context){
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        ldb.close()
    }

}