package com.example.luid.classes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import androidx.core.util.lruCache
import com.example.luid.database.DBConnect
import kotlinx.coroutines.currentCoroutineContext

class PhaseTwoClass {
    fun getTagalog(context: Context, level : Int, phase : Int) : ArrayList<String>{
        var db = DBConnect(context).readableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.questions_tb} WHERE level = $level AND phase = $phase", null)
        cursor = db.rawQuery("SELECT * FROM ${DBConnect.temp_qstion}", null)
        var tagalog = ArrayList<String>()
        var index = cursor.getColumnIndex("tagalog")

        if(cursor.moveToFirst()){
            do{
                tagalog.add(cursor.getString(index))
            }while (cursor.moveToNext())
        }

        return tagalog
    }

    fun getEnglish(context: Context, level : Int, phase : Int) : ArrayList<String>{
        var db = DBConnect(context).readableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.questions_tb} WHERE level = $level AND phase = $phase", null)
        cursor = db.rawQuery("SELECT * FROM ${DBConnect.temp_qstion}", null)
        var english = ArrayList<String>()
        var index = cursor.getColumnIndex("english")

        if(cursor.moveToFirst()){
            do{
                english.add(cursor.getString(index))
            }while (cursor.moveToNext())
        }

        return english
    }

    fun getKapampangan(context: Context, level : Int, phase : Int) : ArrayList<String>{
        var db = DBConnect(context).readableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.questions_tb} WHERE level = $level AND phase = $phase", null)
        cursor = db.rawQuery("SELECT * FROM ${DBConnect.temp_qstion}", null)
        var kapampangan = ArrayList<String>()
        var index = cursor.getColumnIndex("kapampangan")

        if(cursor.moveToFirst()){
            do{
                kapampangan.add(cursor.getString(index))
            }while (cursor.moveToNext())
        }

        return kapampangan
    }

    fun getImg(context: Context, level : Int, phase : Int) : ArrayList<Int>{
        var db = DBConnect(context).readableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.questions_tb} WHERE level = $level AND phase = $phase", null)
        cursor = db.rawQuery("SELECT * FROM ${DBConnect.temp_qstion}", null)
        var img = ArrayList<Int>()
        var index = cursor.getColumnIndex("drawable")

        if(cursor.moveToFirst()){
            do{
                img.add(cursor.getInt(index))
            }while (cursor.moveToNext())
        }

        return img
    }

    fun getQuestion(context: Context, level : Int, phase : Int) : ArrayList<String>{
        var db = DBConnect(context).readableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.questions_tb} WHERE level = $level AND phase = $phase", null)
        cursor = db.rawQuery("SELECT * FROM ${DBConnect.temp_qstion}", null)
        var question = ArrayList<String>()
        var index = cursor.getColumnIndex("question")

        if(cursor.moveToFirst()){
            do{
                question.add(cursor.getString(index))
            }while (cursor.moveToNext())
        }

        return question
    }
}