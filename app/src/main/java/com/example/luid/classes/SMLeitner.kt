package com.example.luid.classes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.luid.database.DBConnect
import com.example.luid.database.DBConnect.Companion.questions_tb
import com.example.luid.database.DBConnect.Companion.temp_qstion
import com.example.luid.database.DBConnect.Companion.user_records_tb
import java.lang.IndexOutOfBoundsException
import java.util.Calendar


class SMLeitner() {

    // Table name declarations
    private val tQuestions = "questions"
    private val tUserRecords = "user_records"
    private val tAchievements = "achievements"
    var currentGameSession = 0


    //FORMULA for Leitner
    //# EF'= EF+(0.1-((5-q)*0.08)-((5-q)0.02))
    //FORMULA for Interval
    //# I = EF * (d + n - 1)
    fun smLeitnerCalc(
        context: Context,
        qId: Int,
        level: Int,
        phase: Int,
        status: Boolean,
        timeSpent: Int
    ) {
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var tempTable = "questions"
        var cursor = ldb.rawQuery(
            "SELECT * FROM $temp_qstion WHERE _id = $qId AND level = $level AND phase = $phase",
            null
        )
        // Old Algorithm Values
        var indID = cursor.getColumnIndex("_id")
        var indGameSession = cursor.getColumnIndex("game_session")
        var indEF = cursor.getColumnIndex("easiness_factor")
        var indInterval = cursor.getColumnIndex("interval")
        var indDF = cursor.getColumnIndex("difficulty_level")
        var indTimesViewed = cursor.getColumnIndex("times_viewed")

        cursor.moveToFirst()
        var id = cursor.getInt(indID)
        var oldGameSession = cursor.getInt(indGameSession)
        var oldEF = cursor.getFloat(indEF)
        var oldTimesViewed = cursor.getInt(indTimesViewed)

        //FORMULA for Leitner
        //# EF'= EF+(0.1-((5-q)*0.08)-((5-q)0.02))
        var newQuality = getNewQuality(status, timeSpent)
        var newEF = getEF(newQuality, oldEF.toDouble())
        var newDF = getNewDifficultyLevel(newEF)
        var newInterval = getNewInterval(newEF, newDF, oldTimesViewed + 1)
        var newGameSession = oldGameSession + newInterval

        var cv = ContentValues()

        cv.put("easiness_factor", "$newEF")
        cv.put("interval", "$newInterval")
        cv.put("game_session", "$newGameSession")
        cv.put("difficulty_level", "$newDF")
        cv.put("times_viewed", "${oldTimesViewed + 1}")

        ldb.update("$temp_qstion", cv, "_id = $id", null)

        println("NEW GAME SESSION : $newGameSession")

        cv.clear()
        cv.put("game_session_number", "$newGameSession")
        cursor = ldb.rawQuery("SELECT * FROM $user_records_tb WHERE level = $level AND phase = $phase", null)
        cursor.moveToLast()
        id = cursor.getInt(indID)

        ldb.update("$user_records_tb", cv, "_id = $id", null)

        cursor.close()
        ldb.close()
    }


    // New Calculated quality value for algorithm
    fun getNewQuality(status: Boolean, timeSpent: Int): Int {
        return if (!status) {
            when {
                timeSpent <= 180 -> 2
                timeSpent in 181..300 -> 1
                else -> 0
            }
        } else {
            when (timeSpent) {
                in 0..29 -> 5
                in 30..60 -> 4
                else -> 3
            }
        }
    }

    // New Calculated easiness factor value for algorithm
    fun getEF(quality: Int, prevEF: Double): Double {
        var newEF = prevEF + (0.1 - ((5 - quality) * 0.08) - ((5 - quality) * 0.02))

        if (newEF < 1.3) {
            newEF = 1.3
        }
        if (newEF > 2.5) {
            newEF = 2.5
        }

        return newEF
    }

    // New Calculated difficulty level value for algorithm
    fun getNewDifficultyLevel(EF: Double): Int {
        return when {
            EF < 1.30 -> 2
            EF in 1.30..1.50 -> 2
            EF in 1.51..2.14 -> 1
            else -> 0
        }
    }

    // New Calculated interval value for algorithm
    fun getNewInterval(EF: Double, difficultyLevel: Int, timesViewed: Int): Int {
        var interval = (EF * (difficultyLevel + timesViewed - 1)).toInt()
        var newInterval = 0

        if (interval <= 0) newInterval = 1
        else newInterval = interval

        return newInterval
    }

    fun buyLives(context: Context): Boolean {
        var db = DBConnect(context).writableDatabase
        var cursor = db.rawQuery("SELECT * FROM $tUserRecords", null)
        var cv = ContentValues()
        var totalCurrency = 0
        var currencyCol = cursor.getColumnIndex("currency")

        if (cursor.moveToLast()) {
            totalCurrency = cursor.getInt(currencyCol)
        }

        println("TOTAL CURRENCY : $totalCurrency\n\n")

        if (!(totalCurrency >= 60)) {
            return false
        }

        cursor.moveToLast()
        var idCol = cursor.getColumnIndex("_id")
        var id = cursor.getInt(idCol)
        var latestCurrency = cursor.getInt(currencyCol)
        latestCurrency -= 60
        cv.put("currency", "$latestCurrency")
        db.update("$tUserRecords", cv, "_id = $id", null)

        cv.clear()
        cursor.close()
        db.close()

        return true
    }

    // Calculate and return the score
    fun scoreCalc(totalCorrectAnswers: Int, totalItems: Int): Double {
        var score = ((totalCorrectAnswers.toDouble() / totalItems.toDouble()) * 100)
        return score
    }

    // Calculate and Returns the rewards for gameSession
    fun rewardCalc(score: Double): Int {
        return when {
            score >= 80.00 -> 70 + ((score / 100) * 10)
            score in 70.00..79.99 -> 60 + ((score / 100) * 10)
            score in 60.00..69.99 -> 60 + ((score / 100) * 10)
            else -> ((score / 100) * 10)
        }.toInt()
    }


    fun getCurrency(context: Context): Int {
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.readableDatabase
        var colCurrency = "currency"
        var cursor =
            ldb.rawQuery("SELECT $colCurrency FROM $tUserRecords ORDER BY _id DESC LIMIT 1", null)
        var colCurrencyInd = cursor.getColumnIndex("$colCurrency")
        var latestScore = 0

        if (cursor.moveToFirst()) {
            latestScore = cursor.getInt(colCurrencyInd)
        }

        cursor.close()
        ldb.close()

        return latestScore
    }


    // Get the latest score from Database
    fun getScore(context: Context): Double {
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var colScore = "score"
        var cursor =
            ldb.rawQuery("SELECT $colScore FROM $tUserRecords ORDER BY _id DESC LIMIT 1", null)
        var colScoreInd = cursor.getColumnIndex("$colScore")
        var latestScore = 0.0

        if (cursor.moveToFirst()) {
            latestScore = cursor.getDouble(colScoreInd)
        }

        cursor.close()
        ldb.close()

        return latestScore
    }

    // Get the last game_session value from user_records
    fun getLatestGameSessionNumber(context: Context, level: Int, phase: Int): Int {
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase

        var colGameSesNum = "game_session_number"

        var cursor = ldb.rawQuery(
            "SELECT * FROM $tUserRecords WHERE level = $level AND phase = $phase",
            null
        )
        var ind = cursor.getColumnIndex("$colGameSesNum")
        var latestGameSessionVal: Int = 0

        cursor.moveToLast()
        latestGameSessionVal = cursor.getInt(ind)
        currentGameSession = latestGameSessionVal

        println("CURRENT GAME SESSION : $currentGameSession")

        return currentGameSession
    }

    fun getLowestGameSessionNumber(context: Context, level: Int, phase: Int): Int{
        var db = DBConnect(context).readableDatabase
        var cursor = db.rawQuery("SELECT MIN(game_session) FROM $questions_tb WHERE level = $level AND phase = $phase", null)
        var lowestGameSession = 0
        if(cursor.moveToFirst()){
            lowestGameSession = cursor.getInt(0)
        }
        return lowestGameSession
    }

    // Adds a game session row in user_records table
    fun addSession(context: Context, level: Int, phase: Int) {
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var cursor = ldb.rawQuery(
            "SELECT * FROM $tUserRecords WHERE level = $level AND phase = $phase",
            null
        )
        var cv = ContentValues()
        var today = listOf<Int>()
        var date = ""

        val indGameSession = cursor.getColumnIndex("game_session_number")
        val indReplenished = cursor.getColumnIndex("replenished")
        val indCurrency = cursor.getColumnIndex("currency")
        val indLives = cursor.getColumnIndex("lives")

        var sessionNumber = 0
        var currency = 0
        var replenished = 0
        var lives = 0

        try {
            if (cursor.count != 0) {
                cursor.moveToLast()

                sessionNumber = cursor.getInt(indGameSession)
                replenished = cursor.getInt(indReplenished)
                currency = cursor.getInt(indCurrency)
                lives = cursor.getInt(indLives)

            } else {
                cursor = ldb.rawQuery("SELECT * FROM $tUserRecords", null)

                sessionNumber = 1

                cursor.moveToLast()
                replenished = cursor.getInt(indReplenished)
                currency = cursor.getInt(indCurrency)
                lives = cursor.getInt(indLives)
            }
        } catch (e: IndexOutOfBoundsException) {
            replenished = 0
            currency = 0
            lives = 5
        } finally {
            today = getToday()
            date = today.joinToString(separator = "-")

            cv.put("game_session_number", "${sessionNumber}")
            cv.put("level", "$level")
            cv.put("phase", "$phase")
            cv.put("date_played", "$date")
            cv.put("score", "0.0")
            cv.put("time_spent", "0.0")
            cv.put("replenished", "$replenished")
            cv.put("currency", "$currency")
            cv.put("lives", "$lives")

            ldb.insert("user_records", null, cv)
        }


        cv.clear()
        ldb.close()
    }

    fun lifeSpent(context: Context) {
        var db = DBConnect(context).writableDatabase
        var cursor = db.rawQuery("SELECT * FROM $tUserRecords", null)
        var cv = ContentValues()

        val indId = cursor.getColumnIndex("_id")
        val indLives = cursor.getColumnIndex("lives")
        var lives = 0
        var id = 0
        val minLives = 0
        val maxLives = 5

        if (cursor.moveToLast()) {
            lives = cursor.getInt(indLives)
            id = cursor.getInt(indId)
        }

        if (lives <= minLives) {
            return
        } else {
            lives -= 1
        }

        cv.put("lives", lives)

        db.update(tUserRecords, cv, "_id = $id", null)

        cursor.close()
        db.close()

    }

    fun lifeGain(context: Context) {
        var db = DBConnect(context).writableDatabase
        var cursor = db.rawQuery("SELECT * FROM $tUserRecords", null)
        var cv = ContentValues()

        val indId = cursor.getColumnIndex("_id")
        val indLives = cursor.getColumnIndex("lives")
        val indCurrency = cursor.getColumnIndex("currency")
        var lives = 0
        val minLives = 0
        val maxLives = 5
        var id = 0
        var currency = 0

        if (cursor.moveToLast()) {
            lives = cursor.getInt(indLives)
            id = cursor.getInt(indId)
            currency = cursor.getInt(indCurrency)
        }

        if (lives >= maxLives) {
            return
        } else {
            lives += 1
        }

        cv.put("lives", lives)

        db.update(tUserRecords, cv, "_id = $id", null)

        cursor.close()
        db.close()

    }

    fun displayLives(context: Context): Int {
        var db = DBConnect(context).readableDatabase
        var cursor = db.rawQuery("SELECT * FROM $tUserRecords", null)
        var cv = ContentValues()

        var colTime = cursor.getColumnIndex("time_spent")
        var colLives = cursor.getColumnIndex("lives")
        var colGame = cursor.getColumnIndex("game_session_number")
        var colID = cursor.getColumnIndex("_id")

        var lives = 0
        var date = getToday().joinToString(separator = "-")
        var id = 0

        cursor.moveToFirst()
        if (cursor.count == 1 && cursor.getInt(colTime) == 0 &&
                cursor.getInt(colLives) == 0 && cursor.getInt(colGame) == 1){

            id = cursor.getInt(colID)

            cv.put("date_played", date)

            db.update(tUserRecords, cv, "_id = $id", null)
            lives = cursor.getInt(colLives)

            cursor.close()
            db.close()

            return lives

        }else{
            if (cursor.moveToLast()) {
                lives = cursor.getInt(colLives)
                id = cursor.getInt(colID)

                cv.put("date_played", date)

                db.update(tUserRecords, cv, "_id = $id", null)

                cursor.close()
                db.close()
            }
        }

        return lives
    }

    // Updates the user_records table with the data after finishing a game session
    fun updUserRecords(
        context: Context,
        level: Int,
        phase: Int,
        score: Double,
        timeSpent: Int,
        currencyEarned: Int
    ) {
        println("PHASE : $phase")
        var db = DBConnect(context).writableDatabase
        val cursor =
            db.rawQuery("SELECT * FROM $tUserRecords WHERE level = $level AND phase = $phase", null)
        val indId = cursor.getColumnIndex("_id")
        val indCurrency = cursor.getColumnIndex("currency")
        var currency = 0
        var id = 0

        cursor.moveToLast()
        id = cursor.getInt(indId)
        currency = cursor.getInt(indCurrency) + currencyEarned

        val cv = ContentValues()
        cv.put("score", "$score")
        cv.put("time_spent", "$timeSpent")
        cv.put("currency", "$currency")
        db.update("$tUserRecords", cv, "_id = $id", null)

        cv.clear()
        cursor.close()
        db.close()
    }

    fun validateQuestionBank(context: Context, level: Int, phase: Int) {
        var db = DBConnect(context).writableDatabase
        currentGameSession = getLatestGameSessionNumber(context, level, phase)
        var cursor = db.rawQuery(
            "SELECT * FROM $tQuestions WHERE level = $level AND phase = $phase AND game_session = $currentGameSession",
            null
        )
        var count = cursor.count
        var cv = ContentValues()

        println("COUNT : $count")


        while (count < 5) {
            cursor = db.rawQuery(
                "SELECT * FROM $tQuestions WHERE level = $level AND phase = $phase AND game_session = $currentGameSession",
                null
            )
            count = cursor.count
            if (!(count < 5)) {
                break
            }
            cursor = db.rawQuery("SELECT * FROM $tUserRecords", null)

            cursor.moveToLast()
            var gameSessionCol = cursor.getColumnIndex("game_session_number")
            var idCol = cursor.getColumnIndex("_id")
            var gameSessionTempVal = cursor.getInt(gameSessionCol)
            var gameSessionValUpd = gameSessionTempVal
            var idVal = cursor.getInt(idCol)
            currentGameSession = gameSessionValUpd + 1

            cv.put("game_session_number", currentGameSession)
            db.update("$tUserRecords", cv, "_id = $idVal", null)
            cv.clear()

            cv.put("game_session", currentGameSession)
//            db.update("$tTempQuestions", cv, "game_session = $gameSessionTempVal", null)
            db.update(
                "$tQuestions", cv,
                "game_session = $gameSessionTempVal AND level = $level AND phase = $phase",
                null
            )
            cv.clear()
            count++
        }

        cv.clear()
        cursor.close()
        db.close()
    }

    fun compareEF(context: Context): ArrayList<ResultScreen> {
        var db = DBConnect(context).readableDatabase
        var cursorTemp = db.rawQuery("SELECT * FROM $temp_qstion", null)
        var cursorMain: Cursor
        var resultScreen = ArrayList<ResultScreen>()

        /*
        parameters || word : String, finalDF : Int, status : Int
        0 -> sustain
        1 -> diminish
        2 -> improve
         */

        cursorTemp.moveToFirst()
        var colTempId = cursorTemp.getColumnIndex("_id")
        var colTempEF = cursorTemp.getColumnIndex("easiness_factor")
        var colTempDF = cursorTemp.getColumnIndex("difficulty_level")
        var colTempKap = cursorTemp.getColumnIndex("kapampangan")

        var idTemp = cursorTemp.getInt(colTempId)
        var efTemp = 0.0
        var dfTemp = 0
        var efMain = 0.0
        var dfMain = 0
        var kapTemp = ""
        var mainEFCol = 0

        do {
            cursorMain = db.rawQuery("SELECT * FROM $temp_qstion WHERE _id = $idTemp", null)
            cursorMain.moveToFirst()
            mainEFCol = cursorMain.getColumnIndex("easiness_factor")

            efTemp = cursorTemp.getDouble(colTempEF)
            dfTemp = cursorTemp.getInt(colTempDF)
            kapTemp = cursorTemp.getString(colTempKap)
            efMain = cursorMain.getDouble(mainEFCol)

            when {
                efTemp < efMain -> resultScreen.add(ResultScreen(kapTemp, efTemp, dfTemp, 1))
                efTemp > efMain -> resultScreen.add(ResultScreen(kapTemp, efTemp, dfTemp, 2))
                else -> resultScreen.add(ResultScreen(kapTemp, efTemp, dfTemp, 0))
            }
        } while (cursorTemp.moveToNext())

        cursorMain.close()
        cursorTemp.close()
        db.close()

        return resultScreen
    }

    // Methods for Achievements

    // ============================================================
    // Methods to update Wild Fire Achievement in achievements table
    // ============================================================

    // Update achievements table for WF achievement
    fun updAchWF(context: Context) {
        val dbHelper = DBConnect(context)
        val ldb = dbHelper.writableDatabase
        val columnName = "date_played"
        var cv = ContentValues()

        val cursor = ldb.rawQuery("SELECT $columnName FROM $tUserRecords", null)
        var currProg = 0
        var tempCurrProg = 0
        var currLevel = 0
        var today = getToday()
        var latestDate: List<Int>
        var date1: List<Int>
        var date2: List<Int>

        if (cursor.count <= 1) {
            currProg = 0
        } else {
            cursor.moveToLast()
            latestDate = getDateDB(cursor)
            date1 = today
            date2 = latestDate
             do{

                if (((date1[2] - date2[2]) == 1) && ((date1[1] - date2[1]) == 0) && ((date1[0] - date2[0]) == 0)) {
                    date1 = date2
                    date2 = getDateBefore(cursor)
                    tempCurrProg += 1
                } else {
                    currProg = 0
                    break
                }
            }while (cursor.moveToPrevious())
        }



        when (currProg) {
            in 1..2 -> currLevel = 1
            in 3..4 -> currLevel = 2
            in 5..6 -> currLevel = 3
            else -> currLevel = 4
        }

        if (tempCurrProg == 0){
            currProg = 0
            currLevel = 0
        }else{
            currProg = tempCurrProg
        }

        cv.put("current_level", "$currLevel")
        cv.put("current_progress", "$currProg")
        ldb.update("achievements", cv, "_id = 1", null)

        cursor.close()
        cv.clear()
        ldb.close()
    }

    // Get the date today
    fun getToday(): List<Int> {
        val today = Calendar.getInstance()
        val day = today.get(Calendar.DAY_OF_MONTH)
        val month = today.get(Calendar.MONTH) + 1
        val year = today.get(Calendar.YEAR)

        return listOf(day, month, year)
    }

    // Get the last date value in user_records
    fun getDateDB(cursor: Cursor): List<Int> {
        var dbDate: String
        var dbDay: Int = 0
        var dbMonth: Int = 0
        var dbYear: Int = 0
        var dateIndex = cursor.getColumnIndex("date_played")

        dbDate = cursor.getString(dateIndex)
        var dbDateParts: List<String> = dbDate.split("-")
        dbDay = dbDateParts[0].toInt()
        dbMonth = dbDateParts[1].toInt()
        dbYear = dbDateParts[2].toInt()


        return listOf(dbDay, dbMonth, dbYear)
    }

    // Get the previous date from the query
    fun getDateBefore(cursor: Cursor): List<Int> {
        var dbDate: String
        var dbDay: Int = 0
        var dbMonth: Int = 0
        var dbYear: Int = 0
        var dateIndex = cursor.getColumnIndex("date_played")

        dbDate = cursor.getString(dateIndex)
        var dbDateParts: List<String> = dbDate.split("-")
        dbDay = dbDateParts[0].toInt()
        dbMonth = dbDateParts[1].toInt()
        dbYear = dbDateParts[2].toInt()


        return listOf(dbDay, dbMonth, dbYear)
    }

    // ============================================================
    // Methods to update Sharp Shooter Achievement in achievements table
    // ============================================================

    // Update achievements table for SS achievement
    fun updAchSS(context: Context) {
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var colName = "score"
        var cursor = ldb.rawQuery("SELECT $colName FROM $tUserRecords WHERE score = 100.00", null)
        var totalCount = cursor.count
        var currLevel = 0

        if (totalCount == 0) {
            return
        }

        currLevel = when (totalCount) {
            1 -> 1
            in 2..3 -> 2
            in 4..5 -> 3
            in 6..7 -> 4
            in 10..8 -> 5
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
    fun updAchTS(context: Context) {
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        val colTime = "time_spent"
        val cursor = ldb.rawQuery("SELECT * FROM $tUserRecords", null)
        val dbCurrTime = cursor.getColumnIndex("$colTime")
        var currProg = 0
        var currLevel = 0

        cursor.moveToFirst()
        currProg += cursor.getInt(dbCurrTime)
        while (cursor.moveToNext()) {
            currProg += cursor.getInt(dbCurrTime)
        }

        if(currProg != 0){
            currProg = (currProg / 60).toInt()
        }

        currLevel = when (currProg) {
            in 0..100 -> 1
            in 101..200 -> 2
            in 201..300 -> 3
            in 301..400 -> 4
            else -> 4
        }

        var cv = ContentValues()
        cv.put("current_progress", currProg)
        cv.put("current_level", currLevel)
        ldb.update("$tAchievements", cv, "_id = 4", null)

        cv.clear()
        cursor.close()
        ldb.close()
    }

    // ============================================================
    // Method to update Sharp Shooter Achievement in achievements table
    // ============================================================

    // Update achievements table for TS achievement
    fun updAchCL(context: Context) {
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var levelList = listOf(1, 2, 3, 4, 5)
        var phaseList = listOf(1, 2, 3)
        var currPhase = 0
        var currLevel = 0

        for (i in levelList) {
            for (k in phaseList) {
                if (ifPassed(ldb, i, k)) {
                    currLevel = i
                    currPhase = k
                } else {
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
    fun getAchCLPassed(db: SQLiteDatabase, level: Int, phase: Int): Int {
        val dfLevel = 0
        var cursor = db.rawQuery(
            "SELECT * FROM $tQuestions WHERE level = ? AND phase = ? AND difficulty_level = ?",
            arrayOf(level.toString(), phase.toString(), dfLevel.toString())
        )
        return cursor.count
    }

    // Get the number of total items for that level and phase
    fun getAchCLItems(db: SQLiteDatabase, level: Int, phase: Int): Int {
        var cursor =
            db.rawQuery("SELECT * FROM $tQuestions WHERE level = $level AND phase = $phase ", null)
        return cursor.count
    }

    // Check if the user passed the level and/or phase
    fun ifPassed(db: SQLiteDatabase, level: Int, phase: Int): Boolean {
        var pass = getAchCLPassed(db, level, phase)
        var items = getAchCLItems(db, level, phase)

        if ((items != 0) && (pass != 0)) {
            var track = ((pass.toDouble() / items.toDouble()) * 100)
            return track >= 80.0
        } else {
            return false
        }
    }

    // ============================================================
    // Method to update Purchased Hearts Achievement in achievements table
    // ============================================================

    // Update achievements table for PH achievement
    fun updAchPH(context: Context) {
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var cursor = ldb.rawQuery("SELECT * FROM $tUserRecords", null)
        var colIndex = cursor.getColumnIndex("replenished")
        var rowIndex = 0
        var currPurchased = 0
        var currLevel = 0

        if (cursor.moveToLast()) {
            currPurchased = cursor.getInt(colIndex)
            rowIndex = cursor.getInt(0)
        }

        currPurchased += 1

        currLevel = when (currPurchased) {
            1 -> 1
            in 2..5 -> 2
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
