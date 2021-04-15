package com.example.money.data_base

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.money.model.Invoice

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

    @SuppressLint("Recycle")
    fun Invoice(): ArrayList<Invoice> {
        db = openHelper.writableDatabase
        var arrayList = ArrayList<Invoice>()
        var cursor: Cursor = db!!.query("Invoice", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {

                var invoice: Invoice = com.example.money.model.Invoice(0,"",0,0,0)
                invoice.imageId  = cursor.getInt(2)
                arrayList.add(
                    Invoice(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        invoice.imageId
                    )
                )
            } while (cursor.moveToNext())
        } else {
            arrayList = ArrayList<Invoice>()
            Toast.makeText(context, "Нет не одного счета", Toast.LENGTH_SHORT).show()
        }

        db!!.close()

        return arrayList
    }

    fun InvoiceCostSum(): Int {
        db = openHelper.writableDatabase
        var cursor: Cursor = db!!.rawQuery("SELECT SUM(Cost) FROM Invoice", null)
        if (cursor.moveToFirst()) {
            do {
                return cursor.getInt(0)
            } while (cursor.moveToNext())
        } else {
            return 0
            Toast.makeText(context, "Нет не одного счета", Toast.LENGTH_SHORT).show()
        }

        db!!.close()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        context.deleteDatabase("money.db")
        onCreate(p0)
    }

}