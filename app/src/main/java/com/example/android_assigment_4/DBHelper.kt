package com.example.android_assigment_4

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                TEXT_COl + " TEXT," +
                TIME_COL + " TEXT" + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addNote(name : String, age : String ){

        val values = ContentValues()

        values.put(TEXT_COl, name)
        values.put(TIME_COL, age)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun getNote(): Cursor? {

        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }

    companion object{

        private val DATABASE_NAME = "note-database"

        private val DATABASE_VERSION = 1

        val TABLE_NAME = "notes"

        val ID_COL = "nid"

        val TEXT_COl = "text"

        val TIME_COL = "time_created"
    }
}