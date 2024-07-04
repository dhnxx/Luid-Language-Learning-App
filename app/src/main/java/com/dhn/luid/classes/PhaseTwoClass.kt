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
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.temp_qstion} WHERE level = $level AND phase = $phase", null)
        var tagalog = ArrayList<String>()
        var index = cursor.getColumnIndex("tagalog")

        if(cursor.moveToFirst()){
            do{
                tagalog.add(cursor.getString(index))
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return tagalog
    }

    fun getEnglish(context: Context, level : Int, phase : Int) : ArrayList<String>{
        var db = DBConnect(context).readableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.temp_qstion} WHERE level = $level AND phase = $phase", null)
        var english = ArrayList<String>()
        var index = cursor.getColumnIndex("english")

        if(cursor.moveToFirst()){
            do{
                english.add(cursor.getString(index))
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return english
    }

    fun getKapampangan(context: Context, level : Int, phase : Int) : ArrayList<String>{
        var db = DBConnect(context).readableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.temp_qstion} WHERE level = $level AND phase = $phase", null)
        var kapampangan = ArrayList<String>()
        var index = cursor.getColumnIndex("kapampangan")

        println("COUNT IN KAP FUN : ${cursor.count}")

        cursor.moveToFirst()
        do{
            kapampangan.add(cursor.getString(index))
        }while (cursor.moveToNext())


        return kapampangan

        cursor.close()
        db.close()
    }

    fun getImg(context: Context, level : Int, phase : Int) : ArrayList<Int>{
        var db = DBConnect(context).readableDatabase
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.temp_qstion} WHERE level = $level AND phase = $phase", null)
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
        var cursor = db.rawQuery("SELECT * FROM ${DBConnect.temp_qstion} WHERE level = $level AND phase = $phase", null)
        var question = ArrayList<String>()
        var index = cursor.getColumnIndex("question")

        if(cursor.moveToFirst()){
            do{
                question.add(cursor.getString(index))
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return question
    }
}