package com.example.android_assigment_4

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var input: EditText
    private lateinit var button: Button
    private lateinit var data: MutableList<Note>
    private lateinit var adapter: NoteAdapter
    private lateinit var db: DBHelper
    private var changed = false
    //private lateinit var noteDao:NoteDao

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview = findViewById(R.id.recycler)
        recyclerview.layoutManager = LinearLayoutManager(this)

        listeners()

//        db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "note-database"
//        ).build()
//
//        noteDao = db.userDao()

        db = DBHelper(this, null)
        db.addNote("asdasd","asdasd")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun listeners() {
        button = findViewById(R.id.buttonAdd)
        input = findViewById(R.id.input)

        button.setOnClickListener {
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                data.add(Note(text, LocalDateTime.now().toString()))
                adapter.notifyDataSetChanged()
                changed = true
            }
        }
    }

    private fun switchActivities(item: Note) {
        val switchActivityIntent = Intent(this, NoteDetailActivity::class.java)
        switchActivityIntent.putExtra("text", item.text).putExtra("time", item.timeCreated)
        startActivity(switchActivityIntent)
    }

    override fun onPause() {
        super.onPause()
        if (this.changed) {
            for (i in data) {
                db.addNote(i.text!!, i.timeCreated!!)
            }
        }
    }

    @SuppressLint("Range")
    override fun onResume() {
        super.onResume()
        changed = false

//        val notes: List<Note> = noteDao.getAll()
//        data = notes.toMutableList()
        data = mutableListOf()
        val cursor: Cursor? = db.getNote()
        cursor!!.moveToNext()
        data.add(
            Note(
                cursor.getString(cursor.getColumnIndex(DBHelper.TEXT_COl)),
                cursor.getString(cursor.getColumnIndex(DBHelper.TIME_COL))
            )
        )
        while (cursor.moveToNext()) {
            data.add(
                Note(
                    cursor.getString(cursor.getColumnIndex(DBHelper.TEXT_COl)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.TIME_COL))
                )
            )
        }
        cursor.close()

        adapter = NoteAdapter(data) {
            this.changed = true
            switchActivities(it)
        }
        recyclerview.adapter = adapter

        adapter.notifyDataSetChanged()

    }
}