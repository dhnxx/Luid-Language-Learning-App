package com.example.luid.classes

import android.content.ContentValues
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.luid.database.DBConnect
import org.junit.Assert.*
import org.junit.Before
import com.google.common.truth.Truth.assertThat
import com.example.luid.classes.ResultScreen
import org.mockito.Mockito.`when`

import org.junit.Test
import java.lang.IndexOutOfBoundsException

class SMLeitnerTest {

    lateinit var sm : SMLeitner
    lateinit var context : Context

    @Before
    fun setUp() {
        sm = SMLeitner()
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun getEFTest() {
        var prevEF = 1.4
        var quality = 4
        var newEF = sm.getEF(quality, prevEF)
        println(newEF)
        assertEquals(1.4, newEF, 0.0)
    }

    @Test
    fun getNewDifficultyLevelTest() {
        val EF = 1.3
        var newDifficultyLevel = sm.getNewDifficultyLevel(EF)
        println(newDifficultyLevel)
        assertEquals(2, newDifficultyLevel)
    }

    @Test
    fun getNewIntervalTest() {
        val EF = 1.3
        val difficultyLevel = 2
        val oldInterval = 1
        var newInterval = sm.getNewInterval(EF, difficultyLevel, oldInterval)
        println(newInterval)
        assertEquals(2, newInterval)
    }

    @Test
    fun getNewGameSessionTest(){
        val oldGameSession = 1
        val newInterval = 2
        var newGameSession = oldGameSession + newInterval
        println(newGameSession)
        assertEquals(3, newGameSession)
    }

    @Test
    fun smLeitnerCalc() {
        val oldQuality = 4
        val oldEF = 1.4
        val status = true
        val timeSpent = 34
        val oldGameSession = 1

        var newEF = sm.getEF(oldQuality, oldEF)
        assertThat(newEF).isEqualTo(1.4)

        var newQuality = sm.getNewQuality(status, timeSpent)
        assertThat(newQuality).isEqualTo(4)

        var newDifficultyLevel = sm.getNewDifficultyLevel(1.3)
        assertThat(newDifficultyLevel).isEqualTo(2)

        var newInterval = sm.getNewInterval(oldEF, newDifficultyLevel, 1)
        assertThat(newInterval).isEqualTo(2)

        var newGameSession = newInterval + oldGameSession
        assertThat(newGameSession).isEqualTo(3)

        println(newEF)
        println(newQuality)
        println(newDifficultyLevel)
        println(newInterval)
        println(newGameSession)


    }

    @Test
    fun scoreCalcTest() {
        var correctAnswer = 5
        var totalItems = 10
        var score = sm.scoreCalc(correctAnswer, totalItems)
        println(score)
        //use google truth library for more accurate assertions
        assertThat(score).isEqualTo(50.0)
    }

    @Test
    fun rewardCalcTest() {
        var score = 50.0
        var reward = sm.rewardCalc(score)
        println(reward)
        assertThat(reward).isEqualTo(5)
    }

    @Test
    fun currencyTest() {
        val db = DBConnect(ApplicationProvider.getApplicationContext()).writableDatabase
        var oldCurrency = 95

        var currencyEarned = sm.rewardCalc(50.0)
        println(oldCurrency)
        println(currencyEarned)

        var newCurrency = oldCurrency + currencyEarned
        assertThat(newCurrency).isEqualTo(100)
    }

    @Test
    //create a test case for sm.addSession() method if a new row is added in the database
    //if true, delete the inserted row
    fun addSessionTest(){
        var db = DBConnect(context).writableDatabase
        sm.addSession(context, 1, 1)
        //assert if a new row was added using google truth library
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)

        assertThat(cursor!!.moveToLast()).isTrue()
        //delete the inserted row
        cursor.moveToLast()
        var id = cursor.getInt(cursor.getColumnIndex("_id"))
        db.delete(DBConnect.user_records_tb, "_id = ?", arrayOf(id.toString()))

        cursor.close()
        db.close()
    }

    @Test
    fun updUserRecordTest(){
        var db = DBConnect(context).writableDatabase
        sm.addSession(context, 1, 1)

        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)
        var cv = ContentValues()

        cursor.moveToLast()
        cv.put("_id", 2)
        cv.put("game_session_number", 2)
        cv.put("level", 1)
        cv.put("phase", 1)
        cv.put("date_played", "6-12-2023")
        cv.put("time_spent", 0)
        cv.put("replenished", 0)
        cv.put("score", 0)
        cv.put("currency", 0)
        cv.put("lives", 5)

        //check the database if there is a row with the _id of 2
        cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb} WHERE _id = 2", null)
        if(cursor.moveToFirst()){
            db.update(DBConnect.user_records_tb, cv, "_id = 2", null)
        }else{
            db.insert(DBConnect.user_records_tb, null, cv)
        }


        sm.updUserRecords(context, 1, 1, 0.0, 0, 0)

        //assert that a row in the user_records table has a row that has the same values as the cv
        cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb} WHERE _id = 2 AND game_session_number = 2 " +
                "AND level = 1 AND phase = 1 AND replenished = 0 AND score = 0 AND currency = 0 " +
                "AND lives = 5",null)
        assertThat(cursor!!.moveToFirst()).isTrue()

        cursor.moveToLast()
        db.delete(DBConnect.user_records_tb, "_id = 2", null)

        cursor.close()
        db.close()
    }

    @Test
    fun lifeSpentTest(){
        //test sm.lifeSpent method
        var db = DBConnect(context).writableDatabase
        sm.lifeSpent(context)
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)

        cursor.moveToLast()
        var currLives = cursor.getInt(cursor.getColumnIndex("lives"))
        assertThat(currLives).isEqualTo(4)

        var cv = ContentValues()
        cv.put("lives", 5)

        cursor.moveToLast()
        var id = cursor.getInt(cursor.getColumnIndex("_id"))
        db.update(DBConnect.user_records_tb, cv, "_id = $id", null)

        cursor.close()
        db.close()
    }

    @Test
    fun lifeGainTest(){
        //test sm.lifeGain method
        var db = DBConnect(context).writableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)

        var cv = ContentValues()
        cv.put("lives", 4)

        cursor.moveToLast()
        var id = cursor.getInt(cursor.getColumnIndex("_id"))
        db.update(DBConnect.user_records_tb, cv, "_id = $id", null)

        sm.lifeGain(context)
        var currLives = cursor.getInt(cursor.getColumnIndex("lives"))
        println("CURR LIVES : $currLives")
        assertThat(currLives).isEqualTo(5)

        cv = ContentValues()
        cv.put("lives", 5)

        cursor.moveToLast()
        id = cursor.getInt(cursor.getColumnIndex("_id"))
        db.update(DBConnect.user_records_tb, cv, "_id = $id", null)

        cursor.close()
        db.close()
    }

    @Test
    fun displayLivesTest(){
        var db = DBConnect(context).writableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)

        cursor.moveToLast()
        var dbLives = cursor.getInt(cursor.getColumnIndex("lives"))

        var lives = sm.displayLives(context)

        assertThat(lives).isEqualTo(dbLives)
    }

    @Test
    fun validateQuestionBankTest(){
        var db = DBConnect(context).writableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.questions_tb} " +
                "WHERE phase = 1 AND level = 1", null)
        var cv = ContentValues()

        for (i in 1 .. 5){
            cv.put("game_session", 3)
            db.update(DBConnect.questions_tb, cv, "_id = $i", null)
        }

        for (i in 6 .. 10){
            cv.put("game_session", 5)
            db.update(DBConnect.questions_tb, cv, "_id = $i", null)
        }

        var count = validateQuestionBank(context, 1, 1)

        assertThat(count).isEqualTo(5)

        for (i in 1 .. 10){
            cv.put("game_session", 1)
            db.update(DBConnect.questions_tb, cv, "_id = $i", null)
        }

        cursor.close()
        db.close()

    }

    fun validateQuestionBank(context: Context, level: Int, phase: Int) : Int{
        var db = DBConnect(context).writableDatabase
        var currentGameSession = sm.getLowestGameSessionNumber(context, level, phase)
        var cursor = db.rawQuery(
            "SELECT * FROM ${DBConnect.questions_tb} WHERE level = $level AND phase = $phase AND game_session = $currentGameSession",
            null
        )
        var count = cursor.count
        var cv = ContentValues()

        println("COUNT : $count")


        while (count < 5) {
            cursor = db.rawQuery(
                "SELECT * FROM ${DBConnect.questions_tb} WHERE level = $level AND phase = $phase AND game_session = $currentGameSession",
                null
            )
            count = cursor.count
            if (!(count < 5)) {
                break
            }
            cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)

            cursor.moveToLast()
            var gameSessionCol = cursor.getColumnIndex("game_session_number")
            var idCol = cursor.getColumnIndex("_id")
            var gameSessionTempVal = cursor.getInt(gameSessionCol)
            var gameSessionValUpd = gameSessionTempVal
            var idVal = cursor.getInt(idCol)
            currentGameSession = gameSessionValUpd + 1

            cv.put("game_session_number", currentGameSession)
            db.update("${DBConnect.user_records_tb}", cv, "_id = $idVal", null)
            cv.clear()

            cv.put("game_session", currentGameSession)
//            db.update("$tTempQuestions", cv, "game_session = $gameSessionTempVal", null)
            db.update(
                "${DBConnect.questions_tb}", cv,
                "game_session = $gameSessionTempVal AND level = $level AND phase = $phase",
                null
            )
            cv.clear()
            count++
        }

        cv.clear()
        cursor.close()
        db.close()

        return count
    }


    @Test
    fun getLowestGameSessionNumber(){
        var db = DBConnect(context).writableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.questions_tb}", null)
        var cv = ContentValues()

        for (i in 1 .. 10){
            cv = ContentValues()
            cv.put("game_session", 7)
            db.update(DBConnect.questions_tb, cv, "_id = $i", null)
        }

        cv.put("game_session", 5)
        db.update(DBConnect.questions_tb, cv, "_id = 9", null)

        var lowestGameSession = sm.getLowestGameSessionNumber(context, 1, 1)
        assertThat(lowestGameSession).isEqualTo(5)

        for (i in 1 .. 10){
            cv = ContentValues()
            cv.put("game_session", 1)
            db.update(DBConnect.questions_tb, cv, "_id = $i", null)
        }

        cursor.close()
        db.close()
    }


    @Test
    fun compareEFTest(){
        var newEF = 1.4

        //copy all of questions table of level 1 and phase 1 into temp_questions table
        var db = DBConnect(context).writableDatabase
        db.execSQL("DROP TABLE IF EXISTS ${DBConnect.temp_qstion}")
        db.execSQL("CREATE TABLE IF NOT EXISTS ${DBConnect.temp_qstion} AS SELECT * FROM ${DBConnect.questions_tb} " +
                "WHERE level = 1 AND phase = 1")

        for (i in 1 .. 10){
            var cv = ContentValues()
            cv.put("easiness_factor", newEF)
            db.update("questiontable_tmp", cv, "_id = $i", null)
        }

        var statusList : ArrayList<ResultScreen> = sm.compareEF(context)

        //print all of status list
        for(i in statusList){
            println("DF : ${i.finalDF} | EF : ${i.finalDF} | Status : ${i.status}")
        }

        for(i in statusList){
            assertThat(i.status).isEqualTo(2)
        }

        db.execSQL("DROP TABLE IF EXISTS ${DBConnect.temp_qstion}")

        db.close()

    }

    @Test
    fun updAchWFTest(){
        var db = DBConnect(context).writableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)
        var cv = ContentValues()

        cv.put("_id", 2)
        cv.put("level", 1)
        cv.put("phase", 1)
        cv.put("game_session_number", 1)
        cv.put("lives", 5)
        cv.put("score", 0)
        cv.put("time_spent", 0)
        cv.put("date_played", "11-6-2023")
        cv.put("replenished", 0)
        cv.put("currency", 0)

        db.insert(DBConnect.user_records_tb, null, cv)

        var currLevel = updAchWF(context)
        assertThat(currLevel).isEqualTo(1)

        cv.clear()

        cursor.moveToLast()
        db.delete(DBConnect.user_records_tb, "_id = ${cursor.getInt(0)}", null)
        cursor.close()
        db.close()


    }

    fun updAchWF(context: Context): Int {
        val dbHelper = DBConnect(context)
        val ldb = dbHelper.writableDatabase
        val columnName = "date_played"
        var cv = ContentValues()

        val cursor = ldb.rawQuery("SELECT $columnName FROM ${DBConnect.user_records_tb}", null)
        var currProg = 0
        var tempCurrProg = 0
        var currLevel = 0
        var today = arrayListOf(12, 6, 2023)
        var latestDate: List<Int>
        var date1: List<Int>
        var date2: List<Int>

        if (cursor.count <= 1) {
            currProg = 0
        } else {
            cursor.moveToLast()
            latestDate = sm.getDateDB(cursor)
            date1 = today
            date2 = latestDate

            do{

                if (((date1[2] - date2[2]) == 0) && ((date1[1] - date2[1]) == 0) && ((date1[0] - date2[0]) == 1)) {
                    date1 = date2
                    date2 = sm.getDateBefore(cursor)
                    tempCurrProg += 1
                } else {
                    currProg = 0
                    break
                }
            }while (cursor.moveToPrevious())
        }


        if (tempCurrProg == 0){
            currProg = 0
            currLevel = 0
        }else{
            currProg = tempCurrProg

            when (currProg) {
                in 1..2 -> currLevel = 1
                in 3..4 -> currLevel = 2
                in 5..6 -> currLevel = 3
                else -> currLevel = 4
            }

        }


        cv.put("current_level", "$currLevel")
        cv.put("current_progress", "$currProg")
        ldb.update("achievements", cv, "_id = 1", null)

        cursor.close()
        cv.clear()
        ldb.close()
        return currLevel
    }

    @Test
    fun updAchSSTest(){
        var db = DBConnect(context).writableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)
        var cv = ContentValues()

        cv.put("_id", 2)
        cv.put("level", 1)
        cv.put("phase", 1)
        cv.put("game_session_number", 1)
        cv.put("lives", 5)
        cv.put("score", 100.0)
        cv.put("time_spent", 0)
        cv.put("date_played", "11-6-2023")
        cv.put("replenished", 0)
        cv.put("currency", 0)

        db.insert(DBConnect.user_records_tb, null, cv)

        cv.put("_id", 3)
        cv.put("level", 1)
        cv.put("phase", 1)
        cv.put("game_session_number", 1)
        cv.put("lives", 5)
        cv.put("score", 100.0)
        cv.put("time_spent", 0)
        cv.put("date_played", "11-6-2023")
        cv.put("replenished", 0)
        cv.put("currency", 0)

        db.insert(DBConnect.user_records_tb, null, cv)

        cv.put("_id", 4)
        cv.put("level", 1)
        cv.put("phase", 1)
        cv.put("game_session_number", 1)
        cv.put("lives", 5)
        cv.put("score", 100.0)
        cv.put("time_spent", 0)
        cv.put("date_played", "11-6-2023")
        cv.put("replenished", 0)
        cv.put("currency", 0)

        db.insert(DBConnect.user_records_tb, null, cv)

        var currLevel = updAchSS(context)
        assertThat(currLevel).isEqualTo(2)

        cv.clear()

        cursor.moveToLast()
        db.delete(DBConnect.user_records_tb, "_id = ${cursor.getInt(0)}", null)
        cursor.moveToPrevious()
        db.delete(DBConnect.user_records_tb, "_id = ${cursor.getInt(0)}", null)
        cursor.moveToPrevious()
        db.delete(DBConnect.user_records_tb, "_id = ${cursor.getInt(0)}", null)
        cursor.close()
        db.close()
    }

    fun updAchSS(context: Context) : Int {
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var colName = "score"
        var cursor = ldb.rawQuery("SELECT $colName FROM ${DBConnect.user_records_tb} WHERE score = 100.00", null)
        var totalCount = cursor.count
        var currLevel = 0

        if (totalCount == 0) {
            return 0
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
        ldb.update("${DBConnect.achievements_tb}", cv, "_id = 2", null)

        cv.clear()
        cursor.close()
        ldb.close()

        return currLevel
    }

    @Test
    fun updAchTSTest(){
        var db = DBConnect(context).writableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)
        var cv = ContentValues()

        cv.put("_id", 2)
        cv.put("level", 1)
        cv.put("phase", 1)
        cv.put("game_session_number", 1)
        cv.put("lives", 5)
        cv.put("score", 0)
        cv.put("time_spent", 500)
        cv.put("date_played", "11-6-2023")
        cv.put("replenished", 0)
        cv.put("currency", 0)

        db.insert(DBConnect.user_records_tb, null, cv)

        var currLevel = updAchTS(context)
        assertThat(currLevel).isEqualTo(1)

        cv.clear()

        cursor.moveToLast()
        db.delete(DBConnect.user_records_tb, "_id = ${cursor.getInt(0)}", null)
        cursor.close()
        db.close()
    }

    fun updAchTS(context: Context) : Int{
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        val colTime = "time_spent"
        val cursor = ldb.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)
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
        ldb.update("${DBConnect.achievements_tb}", cv, "_id = 4", null)

        cv.clear()
        cursor.close()
        ldb.close()

        return currLevel
    }

    @Test
    fun updAchCLTest(){
        var db = DBConnect(context).writableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)

        for(i in 1 .. 20){
            var cv = ContentValues()
            cv.put("difficulty_level", 0)
            db.update(DBConnect.questions_tb, cv, "_id = $i", null)
        }


        var currLevel = updAchCL(context)
        assertThat(currLevel).isEqualTo(1)

        for(i in 1 .. 20){
            var cv = ContentValues()
            cv.put("difficulty_level", 2)
            db.update(DBConnect.questions_tb, cv, "_id = $i", null)
        }

        cursor.close()
        db.close()
    }

    fun updAchCL(context: Context) : Int{
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var levelList = listOf(1, 2, 3, 4, 5)
        var phaseList = listOf(1, 2, 3)
        var currPhase = 0
        var currLevel = 0

        for (i in levelList) {
            for (k in phaseList) {
                if (sm.ifPassed(ldb, i, k)) {
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
        ldb.update("${DBConnect.achievements_tb}", cv, "_id = 3", null)

        cv.clear()
        ldb.close()

        return currLevel
    }

    @Test
    fun updAchPHTest(){
        var db = DBConnect(context).writableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)


        var cv = ContentValues()
        cv.put("replenished", 2)
        db.update(DBConnect.user_records_tb, cv, "_id = 1", null)


        var currLevel = updAchPH(context)
        assertThat(currLevel).isEqualTo(2)

        cv.clear()
        cv.put("replenished", 0)
        db.update(DBConnect.user_records_tb, cv, "_id = 1", null)

        cursor.close()
        db.close()
    }

    fun updAchPH(context: Context) : Int {
        var dbHelper = DBConnect(context)
        var ldb = dbHelper.writableDatabase
        var cursor = ldb.rawQuery("SELECT * FROM ${DBConnect.user_records_tb}", null)
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
        ldb.update("${DBConnect.user_records_tb}", cv, "_id = $rowIndex", null)
        cv.clear()
        cv.put("current_progress", currPurchased)
        cv.put("current_level", currLevel)
        ldb.update("${DBConnect.achievements_tb}", cv, "_id = 5", null)

        cv.clear()
        cursor.close()
        ldb.close()

        ldb.close()

        return currLevel
    }
}