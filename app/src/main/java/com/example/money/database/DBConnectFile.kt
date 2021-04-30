package com.example.money.database

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DBConnectFile(context: Context?) :
    SQLiteAssetHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "money.db"
        private val DATABASE_VERSION = 1
    }
}
