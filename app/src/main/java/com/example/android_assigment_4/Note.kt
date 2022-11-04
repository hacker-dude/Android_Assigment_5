package com.example.android_assigment_4

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "time_created") val timeCreated: String?,
){
    @PrimaryKey(autoGenerate = true) val nid: Int=0
}
