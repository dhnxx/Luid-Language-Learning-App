package com.example.luid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Class to connect to LuidDB database
class DBConnect(context : Context) : SQLiteOpenHelper(
    context, "LuidDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        TODO("FOR EDIT")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}

