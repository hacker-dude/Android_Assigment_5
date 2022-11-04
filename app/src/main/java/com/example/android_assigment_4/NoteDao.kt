package com.example.android_assigment_4

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<Note>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Note>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Note

    @Insert
    fun insertAll(vararg notes: Note)

    @Delete
    fun delete(user: Note)
}