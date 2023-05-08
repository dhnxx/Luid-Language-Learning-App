package com.example.luid

import android.content.Context
import android.database.Cursor

class smLeitner(context : Context) {
    // Table name declarations
    private val TSmLeit = "smleit"
    private val TGameSession = "gamesession"
    private val TAchievements = "achievements"

    // Declaration of Database connection and cursors for each table
    private var dbHelper = DBConnect(context)
    private var db = dbHelper.writableDatabase
    private var rsAchievements = db.rawQuery("SELECT * FROM $TAchievements", null)
    private var rsGameSession = db.rawQuery("SELECT * FROM $TGameSession", null)
    private var rsSmLeit = db.rawQuery("SELECT * FROM $TSmLeit", null)

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

    // Getters for smleit table
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

    fun getEnglish(cursor : Cursor) : String{
        return cursor.getString(5)
    }

    fun getKapampangan(cursor : Cursor) : String{
        return cursor.getString(6)
    }

    fun getMeaning(cursor: Cursor) : String{
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
    // Setters for smleit table
    // =======================================

    // Getters for gamesession table
    fun getGameSessionNumber(cursor : Cursor){}
    fun getDatePlayed(cursor : Cursor){}
    fun getTimeSpent(cursor : Cursor){}
    fun getLivesPurchased(cursor : Cursor){}

    // =======================================
    // Setters for gamesession table
    // =======================================

    // Getters for achievements table
    fun getAchievementsId(cursor : Cursor){}
    fun getAchivementName(cursor : Cursor){}
    fun getCurrentProgress(cursor : Cursor){}
    fun getCurrentLevel(cursor : Cursor){}
    fun getMaximumValue(cursor : Cursor){}


    // =======================================
    // Setters for achievements table
    // =======================================

    //smLeitner Processing Methods

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
//        NOTE: Fix Loop -> must go through all gamesessions prior
        db.execSQL("INSERT INTO $tempTableName SELECT * FROM $TSmLeit WHERE gameSession = $currentGameSession")
    }

    // Copies the values of the tempSMLeit and
    // updates values of the smLeit table after gameSession
    fun updateSMLeitnerTable(){
        val tempTableName = "tempSMLeit"
        db.execSQL("INSERT INTO $TSmLeit SELECT * FROM $tempTableName")

        db.execSQL("DROP TABLE $tempTableName")
    }

}