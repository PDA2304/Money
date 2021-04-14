package com.example.money.data_base

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(var context: Context) :
    SQLiteOpenHelper(context, NAME_FILE, null, DATABASE_VERSION) {

    private val openHelper: SQLiteOpenHelper
    private var db: SQLiteDatabase? = null
    var instances: DataBaseConectFile? = null

    companion object {
        private val NAME_FILE = "money.db"
        private val DATABASE_VERSION = 1
    }


    init {
        openHelper = DataBaseConectFile(context)
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        DatabaseHelper(this.context).getInstance(this.context)
    }

    fun getInstance(context: Context?): DataBaseConectFile? {
        if (instances == null) {
            instances = DataBaseConectFile(context!!)
        }
        return instances
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        context.deleteDatabase("money.db")
        onCreate(p0)
    }

}