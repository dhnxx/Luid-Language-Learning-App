package com.example.luid.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.luid.classes.Review
import java.lang.Exception


class DBConnect(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_V) {

    companion object {

        const val DB_V = 1
        const val DB_NAME = "LuidDB"
        const val questions_tb = "questions"
        const val user_records_tb = "user_records"
        const val achievements_tb = "achievements"
        const val kapWord = "kapampangan"
        const val engWord = "english"
        const val tagWord = "tagalog"
        const val vsbty = "visibility"
        const val diffs = "difficulty_level"

    }

    override fun onCreate(db: SQLiteDatabase){

        // question table
        db.execSQL("CREATE TABLE IF NOT EXISTS $questions_tb (_id INTEGER PRIMARY KEY AUTOINCREMENT, level INTEGER, phase FLOAT, " +
                "question TEXT, $kapWord TEXT, $engWord TEXT, $tagWord TEXT, translation TEXT, game_session INTEGER DEFAULT 0, " +
                "easiness_factor FLOAT DEFAULT 0, interval INTEGER DEFAULT 0, $diffs INTEGER DEFAULT 2, times_viewed INTEGER DEFAULT 0, $vsbty int DEFAULT 0)" )


        //user_records table
        db.execSQL("CREATE TABLE IF NOT EXISTS $user_records_tb (_id INTEGER PRIMARY KEY AUTOINCREMENT, game_session_number INTEGER, date_played TEXT, " +
                "score FLOAT, time_spent INTEGER, replenished INTEGER)")


        //achievements table
        db.execSQL("CREATE TABLE IF NOT EXISTS $achievements_tb (_id INTEGER PRIMARY KEY AUTOINCREMENT, achievements_abbreviation TEXT, achievement_name TEXT, " +
                "description TEXT, current_progress INTEGER DEFAULT 0, current_level INTEGER DEFAULT 0, maximum_value INTEGER )")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS questions")
        db.execSQL("DROP TABLE IF EXISTS user_records")
        db.execSQL("DROP TABLE IF EXISTS achievements")
        onCreate(db)

    }
}
