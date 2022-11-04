package com.example.android_assigment_4

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): NoteDao
}