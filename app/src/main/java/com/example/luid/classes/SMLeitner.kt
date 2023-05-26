package com.example.luid.classes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.luid.database.DBConnect
import java.util.Calendar


class SMLeitner(context : Context) {
    // Table name declarations


    private val tQuestions = "questions"
    private val tUserRecords = "user_records"
    private val tAchievements = "achievements"
    private var currentGameSession = 0


    init {
        // Initialization of total game sessions done by the user
        var dbHelper = DBConnect(context)
        var db = dbHelper.writableDatabase

        var rsUserRecords = db.rawQuery("SELECT * FROM $tUserRecords", null)
        currentGameSession = rsUserRecords.count

        rsUserRecords.close()
        db.close()
    }

    // New Calculated quality value for algorithm
    fun getQuality(answerKey : String, userAnswer : String, timeTotal : Long) : Int{
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

    // New Calculated easiness factor value for algorithm
    fun getEF(quality : Int, prevEF : Double): Double{
        var newEF = prevEF + (0.1 - ((5 - quality) * 0.08) - ((5 - quality) * 0.02))

        if (newEF < 1.3){ newEF = 1.3 }
        if(newEF > 2.5){ newEF = 2.5 }

        return newEF
    }

    // New Calculated difficulty level value for algorithm
    fun getDifficultyLevel(EF : Double) : Int{
        return when(EF){
            in 1.55 .. 1.3 -> 0
            in 2.14 .. 1.5 -> 1
            else -> 2
        }
    }

    // New Calculated interval value for algorithm
    fun getNewInterval(EF : Double, difficultyLevel : Int, timesViewed : Int) : Int {
        var interval = (EF * (difficultyLevel + timesViewed - 1)).toInt()
        var newInterval : Int = 0
        if (interval <= 0) newInterval = 1

        return newInterval
    }

    // New Calculated game session value for algorithm
    fun getNewGameSession(newInterval : Int, prevGameSession : Int) : Int {
        return newInterval + prevGameSession
    }

    // Calculate and return the score
    fun score(totalCorrectAnswers : Int, totalItems : Int) : Double{
        var score = ((totalCorrectAnswers.toDouble() / totalItems.toDouble())*100)
        return score
    }

    // Calculate and Returns the rewards for gameSession
    fun reward(score : Double) : Double{
        return if (score > 80.0){
            10 + ((score/100) * 10)
        }else{
            ((score/100) * 10)
        }
    }

    // Get the latest score from Database
    fun getScore(context: Context) : Double{
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var colScore = "score"
        var cursor = ldb.rawQuery("SELECT $colScore FROM $tUserRecords ORDER BY _id DESC LIMIT 1", null)
        var colScoreInd = cursor.getColumnIndex("$colScore")
        var latestScore = 0.0

        if (cursor.moveToFirst()){
            latestScore = cursor.getDouble(colScoreInd)
        }

        cursor.close()
        ldb.close()

        return latestScore
    }

    // Get the last game_session value from user_records
    fun getLatestGameSessionNumber(context: Context) : Int{
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var colGameSesNum = "game_session_number"
        var cursor = ldb.rawQuery("SELECT $colGameSesNum FROM $tUserRecords ORDER BY _id DESC LIMIT 1", null)
        var ind = cursor.getColumnIndex("$colGameSesNum")
        var q : Int = 0

        if (cursor.moveToFirst()){
            q = cursor.getInt(ind)
        }

        return q
    }

    // Adds a game session row in user_records table
    fun addSession (context: Context){
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var cv = ContentValues()

        var today = getToday()
        var date = today.joinToString(separator = "-")
        var sessionNumber = getLatestGameSessionNumber(context)

        cv.put("game_session_number", "${sessionNumber + 1}")
        cv.put("date_played", "$date")
        cv.put("score", "0.0")
        cv.put("time_spent", "0.0")
        cv.put("replenished", "0")

        ldb.insert("user_records", null, cv)

        cv.clear()
        ldb.close()
    }

    // Updates the user_records table with the data after finishing a game session
    fun updUserRecords(context: Context, score : Double, timeSpent : Int, replenishedHearts : Int){
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        val today = getToday().joinToString()
        val colName = "date_played"
        val cursor = ldb.rawQuery("SELECT * FROM $tUserRecords", null)
        val index = cursor.getColumnIndex(colName)
        var id = 0
        var dbDate = " "

        if (cursor.moveToLast()){
            do{
                dbDate = cursor.getString(index)
                if (dbDate == today){
                    id = cursor.getInt(0)
                    break
                }
            }while (cursor.moveToPrevious())
        }

        val cv = ContentValues()
        cv.put("score", "$score")
        cv.put("time_spent", "$timeSpent")
        cv.put("replenished", "$replenishedHearts")
        ldb.update("$tUserRecords", cv, "_id = $id", null)
    }

    // Methods for Achievements

    // ============================================================
    // Methods to update Wild Fire Achievement in achievements table
    // ============================================================

    // Update achievements table for WF achievement
    fun updAchWF(context: Context){
        val dbHelper = DBConnect(context)
        val ldb = dbHelper.writableDatabase
        val columnName = "date_played"
        var cv = ContentValues()

        val cursor = ldb.rawQuery("SELECT $columnName FROM $tUserRecords", null)
        var currProg = 0
        var currLevel = 0
        var today = getToday()
        var latestDate : List<Int>
        var date1 : List<Int>
        var date2 : List<Int>

        if (cursor.count <= 1){
            currProg = 0
        }else{
            cursor.moveToLast()
            latestDate = getDateDB(cursor)
            date1 = today
            date2 = latestDate
            while (cursor.moveToPrevious()){

                if (((date1[2] - date2[2]) <= 1) && ((date1[1] - date2[1]) <= 1) && ((date1[0] - date2[0]) <= 1)){
                    date1 = date2
                    date2 = getDateBefore(cursor)
                    currProg += 1
                }else{
                    break
                }
            }
        }

        when(currProg){
            in 1 .. 2 -> currLevel = 1
            in 3 .. 4 -> currLevel = 2
            in 5 .. 6 -> currLevel = 3
            else -> currLevel = 4
        }

        cv.put("current_level", "$currLevel")
        cv.put("current_progress", "$currProg")
        ldb.update("achievements", cv, "_id = 1", null)

        cursor.close()
        cv.clear()
        ldb.close()
    }

    // Get the date today
    fun getToday() : List<Int> {
        val today = Calendar.getInstance()
        val day = today.get(Calendar.DAY_OF_MONTH)
        val month = today.get(Calendar.MONTH) + 1
        val year = today.get(Calendar.YEAR)

        return listOf(day, month, year)
    }

    // Get the last date value in user_records
    fun getDateDB(cursor : Cursor) : List<Int>{
        var dbDate : String
        var dbDay : Int = 0
        var dbMonth : Int = 0
        var dbYear : Int = 0
        var dateIndex = cursor.getColumnIndex("date_played")

        dbDate = cursor.getString(dateIndex)
        var dbDateParts : List<String> = dbDate.split("-")
        dbDay = dbDateParts[0].toInt()
        dbMonth = dbDateParts[1].toInt()
        dbYear = dbDateParts[2].toInt()


        return listOf(dbDay, dbMonth, dbYear)
    }

    // Get the previous date from the query
    fun getDateBefore(cursor : Cursor) : List<Int>{
        var dbDate : String
        var dbDay : Int = 0
        var dbMonth : Int = 0
        var dbYear : Int = 0
        var dateIndex = cursor.getColumnIndex("date_played")

        dbDate = cursor.getString(dateIndex)
        var dbDateParts : List<String> = dbDate.split("-")
        dbDay = dbDateParts[0].toInt()
        dbMonth = dbDateParts[1].toInt()
        dbYear = dbDateParts[2].toInt()


        return listOf(dbDay, dbMonth, dbYear)
    }

    // ============================================================
    // Methods to update Sharp Shooter Achievement in achievements table
    // ============================================================

    // Update achievements table for SS achievement
    fun updAchSS(context: Context){
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var colName = "score"
        var cursor = ldb.rawQuery("SELECT $colName FROM $tUserRecords WHERE score = 100.00", null)
        var totalCount = cursor.count
        var currLevel = 0

        if (totalCount == 0) {
            return
        }

        currLevel = when (totalCount){
            1 -> 1
            in 2 .. 3 -> 2
            in 4 .. 5 -> 3
            in 6 .. 7 -> 4
            in 10 .. 8 -> 5
            else -> 5
        }

        val cv = ContentValues()
        cv.put("current_level", currLevel)
        cv.put("current_progress", totalCount)
        ldb.update("$tAchievements", cv, "_id = 2", null)

        cv.clear()
        cursor.close()
        ldb.close()
    }

    // ============================================================
    // Method to update Sharp Shooter Achievement in achievements table
    // ============================================================

    // Update achievements table for TS achievement
    fun updAchTS(context: Context){
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        val colTime = "time_spent"
        val cursor = ldb.rawQuery("SELECT * FROM $tUserRecords", null)
        val dbCurrTime = cursor.getColumnIndex("$colTime")
        var currProg = 0
        var currLevel = 0

        cursor.moveToFirst()
        currProg += cursor.getInt(dbCurrTime)
        while (cursor.moveToNext()){
            currProg += cursor.getInt(dbCurrTime)
        }

        currLevel = when (currProg){
            in 0 .. 100 -> 1
            in 101 .. 200 -> 2
            in 201 .. 300 -> 3
            in 301 .. 400 -> 4
            else -> 4
        }

        var cv = ContentValues()
        cv.put("current_progress", currProg)
        cv.put("current_level", currLevel)
        ldb.update("$tAchievements", cv, "_id = 4",null)

        cv.clear()
        cursor.close()
        ldb.close()
    }

    // ============================================================
    // Method to update Sharp Shooter Achievement in achievements table
    // ============================================================

    // Update achievements table for TS achievement
    fun updAchCL(context: Context){
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var levelList = listOf(1, 2, 3, 4, 5)
        var phaseList = listOf(1, 2, 3)
        var currPhase = 0
        var currLevel = 0

        // For level 1
        for(i in levelList){
            for(k in phaseList){
                if (ifPassed(ldb, i, k)){
                    currLevel = i
                    currPhase = k
                }else{
                    break
                }
            }
        }

        var cv = ContentValues()
        cv.put("current_progress", "$currPhase")
        cv.put("current_level", "$currLevel")
        ldb.update("$tAchievements", cv, "_id = 3", null)

        cv.clear()
        ldb.close()
    }

    // Get the number of items in the third box
    fun getAchCLPassed(db: SQLiteDatabase, level : Int, phase : Int) : Int{
        val dfLevel = 0
        var cursor = db.rawQuery("SELECT * FROM $tQuestions WHERE level = $level AND phase = $phase AND difficulty_level = $dfLevel", null)
        return cursor.count
    }

    // Get the number of total items for that level and phase
    fun getAchCLItems(db: SQLiteDatabase, level : Int, phase : Int) : Int{
        var cursor = db.rawQuery("SELECT * FROM $tQuestions WHERE level = $level AND phase = $phase ", null)
        return cursor.count
    }

    // Check if the user passed the level and/or phase
    fun ifPassed(db: SQLiteDatabase, level: Int, phase: Int): Boolean {
        var pass = getAchCLPassed(db, level, phase)
        var items = getAchCLItems(db, level, phase)

        if ((items != 0) && (pass != 0)) {
            var track  = ((pass.toDouble() / items.toDouble())*100)
            return track >= 80.0
        }else{
            return false
        }
    }

    // ============================================================
    // Method to update Purchased Hearts Achievement in achievements table
    // ============================================================

    // Update achievements table for PH achievement
    fun updAchPH(context: Context){
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var cursor = ldb.rawQuery("SELECT * FROM $tUserRecords", null)
        var colIndex = cursor.getColumnIndex("replenished")
        var rowIndex = 0
        var currPurchased = 0
        var currLevel = 0

        if (cursor.moveToLast()){
            currPurchased = cursor.getInt(colIndex)
            rowIndex = cursor.getInt(0)
        }

        currPurchased += 1

        currLevel = when(currPurchased){
            1 -> 1
            in 2 .. 5 -> 2
            else -> 3
        }

        val cv = ContentValues()
        cv.put("replenished", currPurchased)
        ldb.update("$tUserRecords", cv, "_id = $rowIndex", null)
        cv.clear()
        cv.put("current_progress", currPurchased)
        cv.put("current_level", currLevel)
        ldb.update("$tAchievements", cv, "_id = 5", null)

        cv.clear()
        cursor.close()
        ldb.close()

        ldb.close()
    }



}