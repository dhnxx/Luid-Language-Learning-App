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
                "question TEXT, kapampangan TEXT, english TEXT, tagalog TEXT, translation TEXT, game_session INTEGER DEFAULT 0, " +
                "easiness_factor FLOAT DEFAULT 0, interval INTEGER DEFAULT 0, difficulty_level INTEGER DEFAULT 2, times_viewed INTEGER DEFAULT 0, visibility BOOLEAN DEFAULT 0)" )


        //user_records table
        db.execSQL("CREATE TABLE IF NOT EXISTS user_records (_id INTEGER PRIMARY KEY AUTOINCREMENT, game_session_number INTEGER, date_played TEXT, " +
                "score FLOAT, time_spent INTEGER, replenished INTEGER)")


        //achievements table
        db.execSQL("CREATE TABLE IF NOT EXISTS achievements (_id INTEGER PRIMARY KEY AUTOINCREMENT, achievements_abbreviation TEXT, achievement_name TEXT, " +
                "description TEXT, current_progress INTEGER DEFAULT 0, current_level INTEGER DEFAULT 0, maximum_value INTEGER )")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS questions")
        db.execSQL("DROP TABLE IF EXISTS user_records")
        db.execSQL("DROP TABLE IF EXISTS achievements")
        onCreate(db)
    }

}
