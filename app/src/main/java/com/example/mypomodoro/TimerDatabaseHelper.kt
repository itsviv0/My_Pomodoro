package com.example.mypomodoro

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TimerDatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        const val TABLE_NAME = "Timers"
        const val DATABASE_NAME = "MyDatabase.db"
        const val DATABASE_VERSION = 1
        const val TIMER_NAME = "Name"
        const val FOCUS_TIME = "FocusTime"
        const val REST_TIME = "RestTime"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("create Table $TABLE_NAME ($TIMER_NAME TEXT PRIMARY KEY NOT NULL, $FOCUS_TIME INT NOT NULL, $REST_TIME INT NOT NULL);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertTimer(pomoTimer: PomoTimer) {
        val db=writableDatabase
        val values = ContentValues().apply {
            put(TIMER_NAME, pomoTimer.timerName)
            put(FOCUS_TIME, pomoTimer.focusTime)
            put(REST_TIME, pomoTimer.restTime)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllTimers(): List<PomoTimer> {
        val pomoTimerList = mutableListOf<PomoTimer>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(TIMER_NAME))
            val ftime = cursor.getInt(cursor.getColumnIndexOrThrow(FOCUS_TIME))
            val rtime = cursor.getInt(cursor.getColumnIndexOrThrow(REST_TIME))

            val pomoTimer = PomoTimer(name, ftime, rtime)
            pomoTimerList.add(pomoTimer)
        }
        cursor.close()
        db.close()
        return pomoTimerList
    }

    fun updateTimer(pomoTimer: PomoTimer){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(TIMER_NAME, pomoTimer.timerName)
            put(FOCUS_TIME, pomoTimer.focusTime)
            put(REST_TIME, pomoTimer.restTime)
        }
        val whereClause = "$TIMER_NAME = ?"
        val whereArgs = arrayOf(pomoTimer.timerName)
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getTimerByName(timerId: String?): PomoTimer {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $TIMER_NAME='$timerId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val curName = cursor.getString(cursor.getColumnIndexOrThrow(TIMER_NAME))
        val curFocus = cursor.getInt(cursor.getColumnIndexOrThrow(FOCUS_TIME))
        val curRest = cursor.getInt(cursor.getColumnIndexOrThrow(REST_TIME))

        cursor.close()
        db.close()
        return PomoTimer(curName, curFocus, curRest)
    }

    fun deleteTimer(timerid: String) {
        val db = writableDatabase
        val whereClause = "$TIMER_NAME = ?"
        val whereArgs = arrayOf(timerid)
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}