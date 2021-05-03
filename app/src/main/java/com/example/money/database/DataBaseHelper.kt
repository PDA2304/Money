package com.example.money.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.money.model.Invoice


class DataBaseHelper(var context: Context) : SQLiteOpenHelper(context, NAME_FILE, null, DATABASE_VERSION) {

    private val openHelper: SQLiteOpenHelper
    private var db: SQLiteDatabase? = null
    var instances: DBConnectFile? = null

    companion object {
        private val NAME_FILE = "money.db"
        private val DATABASE_VERSION = 1
    }


    init {
        openHelper = DBConnectFile(context)
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        DataBaseHelper(this.context).getInstance(this.context)
    }

    fun getInstance(context: Context?): DBConnectFile? {
        if (instances == null) {
            instances = DBConnectFile(context!!)
        }
        return instances
    }

    @SuppressLint("Recycle")
    fun SelectInvoice(): ArrayList<Invoice> {
        db = openHelper.writableDatabase
        var arrayList = ArrayList<Invoice>()
        val cursor: Cursor = db!!.query("Invoice", null, null, null, null, null, "Type_ID_Invoice ASC")
        if (cursor.moveToFirst()) {
            do {

                var invoice: Invoice = Invoice(0, "", 0, 0, 0)
                invoice.imageId = cursor.getInt(2)
                arrayList.add(
                    Invoice(
                        cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), invoice.imageId
                    )
                )
            } while (cursor.moveToNext())
        } else {
            arrayList = ArrayList<Invoice>()
        }

        db!!.close()

        return arrayList
    }

    @SuppressLint("Recycle")
    fun InvoiceCostSum(): Int {
        db = openHelper.writableDatabase
        val cursor: Cursor = db!!.rawQuery("SELECT SUM(Cost) FROM Invoice", null)
        if (cursor.moveToFirst()) {
            do {
                return cursor.getInt(0)
            } while (cursor.moveToNext())
        } else {
            return 0
        }

        db!!.close()
    }

    fun SelectIDInvoice(): Int {
        db = openHelper.writableDatabase
        var cursor: Cursor = db!!.rawQuery(
            "SELECT ID_Invoice FROM Invoice ORDER BY Invoice.ID_Invoice DESC LIMIT 1", null
        )
        if (cursor.moveToFirst()) {
            do {
                return cursor.getInt(0) + 1
            } while (cursor.moveToNext())
        } else {
            return 1
        }

        db!!.close()
    }

    fun InvoiceCount(): Int {
        db = openHelper.writableDatabase
        var cursor: Cursor = db!!.rawQuery(
            "select count(*) from Invoice", null
        )
        if (cursor.moveToFirst()) {
            do {
                return cursor.getInt(0)
            } while (cursor.moveToNext())
        } else {
            return 0
        }

        db!!.close()
    }

    fun InsertInvoice(ID_Invoice: Int, Name_Invoice: String, Type_ID_Invoice: Int, Cost: Int) {
        db = openHelper.writableDatabase
        val newValues = ContentValues()
        newValues.put("ID_Invoice", ID_Invoice)
        newValues.put("Name", Name_Invoice)
        newValues.put("Type_ID_Invoice", Type_ID_Invoice)
        newValues.put("Cost", Cost)
        db!!.insert("Invoice", null, newValues)
        db!!.close()
    }

    fun DeleteInvoice(ID: Int) {
        db = openHelper.writableDatabase
        db!!.delete("Invoice", "ID_Invoice =$ID", null)
        db!!.close()
    }

    fun UpdateInvoice(ID: Int, Name_Invoice: String, Type_ID_Invoice: Int )
    {
        db = openHelper.writableDatabase
        val newValues = ContentValues()
        newValues.put("Name", Name_Invoice)
        newValues.put("Type_ID_Invoice", Type_ID_Invoice)
        db!!.update("Invoice", newValues, "ID_Invoice = $ID",null)
        db!!.close()
    }



    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        context.deleteDatabase("money.db")
        onCreate(p0)
    }
}

