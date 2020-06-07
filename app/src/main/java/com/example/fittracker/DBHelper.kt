package com.example.fittracker

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx,"Workout.db", null, 1) {

    companion object {
        private var instance: DBHelper? = null
        @Synchronized

    fun getInstance(ctx: Context) : DBHelper {

            if (instance == null) {

                instance = DBHelper(ctx.applicationContext)
            }

            return instance as DBHelper
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(Workout.TABLE_WORKOUT, true,
        Workout.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
        Workout.NAME to TEXT,
        Workout.DATE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Workout.TABLE_WORKOUT, true)
    }
}

val Context.database : DBHelper
get() = DBHelper.getInstance(applicationContext)