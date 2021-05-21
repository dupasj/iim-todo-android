package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(MIGRATION.UP)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(MIGRATION.DOWN)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"
        const val TABLE_NAME = "todos"

        object COLUMN {
            const val CONTENT = "content"
            const val ID = "id"
        }

        object MIGRATION {
            const val UP = "CREATE TABLE ${TABLE_NAME} (" +
                "${COLUMN.ID} INTEGER PRIMARY KEY," +
                "${COLUMN.CONTENT} varchar(120)" +
            ")"
            const val DOWN = "DROP TABLE IF EXISTS ${TABLE_NAME}"
        }
    }
}