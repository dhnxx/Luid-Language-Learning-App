package com.example.luid

import android.content.ContentValues
import android.content.Context
import android.database.Cursor


class SMLeitner(context : Context) {
    // Table name declarations
    private val TSmLeit = "questions"
    private val TGameSession = "user_records"
    private val TAchievements = "achievements"

    // Declaration of Database connection and cursors for each table
    var dbHelper = DBConnect(context)
    var db = dbHelper.writableDatabase
    var rsAchievements = db.rawQuery("SELECT * FROM $TAchievements", null)
    var rsGameSession = db.rawQuery("SELECT * FROM $TGameSession", null)
    var rsSmLeit = db.rawQuery("SELECT * FROM $TSmLeit", null)

    private var currentGameSession = 0

    init {
        // Initializing of total game sessions done by the user
        currentGameSession = rsGameSession.count
    }

    // Method to close all cursors when application is done running
    fun closeAllCursors(){
        rsAchievements.close()
        rsGameSession.close()
        rsSmLeit.close()
    }

    // =======================================
    // Getters for questions table
    // =======================================

    fun getId(cursor : Cursor) : Int {
        return cursor.getInt(0)
    }

    fun getLevel(cursor : Cursor) : Int {
        return cursor.getInt(1)
    }

    fun getPhase(cursor : Cursor) : Float {
        return cursor.getFloat(2)
    }

    fun getQuestion(cursor : Cursor) : String{
        return cursor.getString(3)
    }

    fun getTagalog(cursor : Cursor) : String{
        return cursor.getString(4)
    }

    fun getEnglish(cursor : Cursor) : String {
        return cursor.getString(5)
    }

    fun getKapampangan(cursor : Cursor) : String{
        return cursor.getString(6)
    }

    fun getTranslation(cursor: Cursor) : String{
        return cursor.getString(7)
    }

    fun getGameSession(cursor : Cursor) : Int{
        return cursor.getInt(8)
    }

    fun getEF(cursor : Cursor) : Float{
        return cursor.getFloat(9)
    }

    fun getInterval(cursor : Cursor) : Int{
        return cursor.getInt(10)
    }

    fun getDifficultyLevel(cursor : Cursor) : Int{
        return cursor.getInt(11)
    }

    fun getTimesViewed(cursor : Cursor) : Int {
        return cursor.getInt(12)
    }

    fun getVisibility(cursor : Cursor) : Boolean{
        var visibility : Boolean = cursor.getInt(13) > 0
        return visibility
    }

    // =======================================
    // Setters for questions table
    // =======================================

    fun setGameSession(cursor : Cursor, updatedGameSession : Int){
        var cv = ContentValues()
        cv.put("game_session", updatedGameSession)
        db.update("questions", cv, "id = ?", arrayOf((cursor.getInt(0)).toString()))
    }

    fun setEF(cursor : Cursor, updatedEF : Float){
        var cv = ContentValues()
        cv.put("easiness_factor", updatedEF)
        db.update("questions", cv, "id = ?", arrayOf((cursor.getInt(0)).toString()))
    }

    fun setInterval(cursor : Cursor, updatedInterval : Int){
        var cv = ContentValues()
        cv.put("interval", updatedInterval)
        db.update("questions", cv, "id = ?", arrayOf((cursor.getInt(0)).toString()))
    }

    fun setDifficultyLevel(cursor : Cursor, updatedDifficultyLevel : Int){
        var cv = ContentValues()
        cv.put("difficulty_level", updatedDifficultyLevel)
        db.update("questions", cv, "id = ?", arrayOf((cursor.getInt(0)).toString()))
    }

    fun setTimesViewed(cursor : Cursor, updatedTimesViewed : Int){
        var cv = ContentValues()
        cv.put("times_viewed", updatedTimesViewed)
        db.update("questions", cv, "id = ?", arrayOf((cursor.getInt(0)).toString()))
    }

    fun setVisibility(cursor : Cursor, updatedVisibility: Boolean){
        var cv = ContentValues()
        cv.put("visibility", updatedVisibility)
        db.update("questions", cv, "id = ?", arrayOf((cursor.getInt(0)).toString()))
    }

    // =======================================
    // Getters for user_records table
    // =======================================

    fun getGameSessionNumber(cursor : Cursor) : Int{
        return cursor.getInt(0)
    }

    fun getDatePlayed(cursor : Cursor) : IntArray {
        var date = cursor.getString(1)
        var parts = date.split("-")
        var day = parts[0].toInt()
        var month = parts[1].toInt()
        var year = parts[2].toInt()
        return intArrayOf(day, month, year)
    }

    fun getScore(cursor: Cursor) : Float{
        return cursor.getFloat(2)
    }

    fun getTimeSpent(cursor : Cursor) : Long{
        return cursor.getLong(3)
    }

    fun getReplenished(cursor : Cursor) : Int{
        return cursor.getInt(4)
    }

    // =======================================
    // Setters for user_records table
    // =======================================

    fun setGameSessionNumber(cursor : Cursor, updatedGameSessionNumber: Int){
        var cv = ContentValues()
        cv.put("game_session_number", updatedGameSessionNumber)
        db.insert("user_records", null, cv)
    }

    fun setDatePlayed(cursor : Cursor, updatedDatePlayed : IntArray) {
        var cv = ContentValues()
        var datePlayedString = "${updatedDatePlayed[0]}-${updatedDatePlayed[1]}-${updatedDatePlayed[2]}}"
        cv.put("date_played", datePlayedString)
        db.insert("user_records", null, cv)
    }

    fun setScore(cursor: Cursor, updatedScore : Float){
        var cv = ContentValues()
        cv.put("score", updatedScore)
        db.insert("user_records", null, cv)
    }
    fun setTimeSpent(cursor : Cursor, updatedTimeSpent : Long){
        var cv = ContentValues()
        cv.put("time_spent", updatedTimeSpent)
        db.insert("user_records", null, cv)
    }
    fun setLivesPurchased(cursor : Cursor, updatedLivesPurchased : Int){
        var cv = ContentValues()
        cv.put("replenished", updatedLivesPurchased)
        db.insert("user_records", null, cv)
    }

    // =======================================
    // Getters for achievements table
    // =======================================

    fun getAchAbbr(cursor : Cursor) : String{
        return cursor.getString(0)
    }

    fun getAchName(cursor : Cursor) : String{
        return cursor.getString(1)
    }

    fun getDescription(cursor : Cursor) : String{
        return cursor.getString(2)
    }

    fun getCurrProg(cursor: Cursor) : Int{
        return cursor.getInt(3)
    }

    fun getCurrLev(cursor : Cursor) : Int{
        return cursor.getInt(4)
    }

    fun getMaximumValue(cursor : Cursor) : Int{
        return cursor.getInt(5)
    }

    // =======================================
    // Setters for achievements table
    // =======================================


    fun setCurrProg(cursor: Cursor, updatedCurrentProgress : Int){
        var cv = ContentValues()
        cv.put("current_progress", updatedCurrentProgress)
        db.update("achievements", cv, "id = ?", arrayOf((cursor.getInt(0)).toString()))
    }

    fun setCurrLev(cursor : Cursor, updatedCurrentLevel : Int){
        var cv = ContentValues()
        cv.put("current_level", updatedCurrentLevel)
        db.update("achievements", cv, "id = ?", arrayOf((cursor.getInt(0)).toString()))
    }

    // =======================================
    //SMLeitner Processing Methods
    // =======================================

    //not updated by Gab, Left for Charles to update
    fun startGame(level:Int, phase : Float){
        val tempTableName = "tempSMLeit"
        val cursor = db.query("TSMLeit", arrayOf("gameSession", "$level", "$phase"), null, null, null, null, null, null)
        var count = 0

        while (count < 10){
            while (cursor.moveToNext()){
                if(cursor.getInt(8) == currentGameSession){
                    count++
                }
            }
            if (count < 10){
                currentGameSession += 1
            }
        }

        // Creation of in game starting table
        db.execSQL("CREATE TABLE IF NOT EXISTS $tempTableName AS SELECT * FROM $TSmLeit WHERE 0 = 1 ")
        // Copying of questions into temp table
//        NOTE: Fix Loop -> must go through all user_records prior
        db.execSQL("INSERT INTO $tempTableName SELECT * FROM $TSmLeit WHERE gameSession = $currentGameSession")
    }

    // Copies the values of the tempSMLeit and
    // updates values of the questions table after gameSession
    fun updateSMLeitnerTable(){
        val tempTableName = "temp_questions"
        db.execSQL("INSERT INTO $TSmLeit SELECT * FROM $tempTableName")

        db.execSQL("DROP TABLE $tempTableName")
    }

//    fun checkAnswer() : Boolean{
//        return getTagalog()
//    }



}