package com.example.luid

import android.content.Context
import android.database.sqlite.*

class Helper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_V) {

    companion object {

        const val DB_V = 1
        const val DB_NAME = "LuidDB.db"
        //
    }

    override fun onCreate(db: SQLiteDatabase){

        // question table
        db.execSQL("CREATE TABLE IF NOT EXISTS questions (_id INTEGER PRIMARY KEY AUTOINCREMENT, level INTEGER, phase FLOAT, " +
                "question TEXT, tagalog TEXT, english TEXT, kapampangan TEXT, translation TEXT, game_session INTEGER, " +
                "easiness_factor FLOAT, interval INTEGER, difficulty_level INTEGER, times_viewed INTEGER, visibility BOOLEAN)" )


        //user_records table
        db.execSQL("CREATE TABLE IF NOT EXISTS user_records (_id INTEGER PRIMARY KEY AUTOINCREMENT, game_session_number INTEGER, date_played TEXT, " +
                "score FLOAT, time_spent INTEGER, replenished INTEGER)")


        //achievements table
        db.execSQL("CREATE TABLE IF NOT EXISTS achievements (_id INTEGER PRIMARY KEY AUTOINCREMENT, achievements_abbreviation TEXT, achievement_name TEXT, " +
                "description TEXT, current_progress INTEGER, current_level INTEGER, maximum_value INTEGER )")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS questions")
        db.execSQL("DROP TABLE IF EXISTS user_records")
        db.execSQL("DROP TABLE IF EXISTS achievements")
        onCreate(db)
    }

}
