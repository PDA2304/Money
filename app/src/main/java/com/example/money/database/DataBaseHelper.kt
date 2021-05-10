package com.example.money.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.money.model.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class DataBaseHelper(var context: Context) : SQLiteOpenHelper(context, NAME_FILE, null, DATABASE_VERSION) {

    private val openHelper: SQLiteOpenHelper
    private var db: SQLiteDatabase? = null
    private var instances: DBConnectFile? = null

    init {
        openHelper = DBConnectFile(context)
    }

    companion object {
        private const val NAME_FILE = "money.db"
        private const val DATABASE_VERSION = 1
    }


    override fun onCreate(p0: SQLiteDatabase?) {
        DataBaseHelper(this.context).getInstance(this.context)
    }

    private fun getInstance(context: Context?): DBConnectFile? {
        if (instances == null) {
            instances = DBConnectFile(context!!)
        }
        return instances
    }

    @SuppressLint("Recycle")
    fun selectInvoice(): ArrayList<Invoice> {
        db = openHelper.writableDatabase
        var arrayList = ArrayList<Invoice>()
        val cursor: Cursor = db!!.query("Invoice", null, null, null, null, null, "Type_ID_Invoice ASC")
        if (cursor.moveToFirst()) {
            do {

                val invoice = Invoice(0, "", 0, 0, 0)
                invoice.imageId = cursor.getInt(2)
                arrayList.add(
                    Invoice(
                        cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getLong(3), invoice.imageId
                    )
                )
            } while (cursor.moveToNext())
        } else {
            arrayList = ArrayList()
        }

        db!!.close()

        return arrayList
    }


    @SuppressLint("Recycle")
    fun InvoiceCostSum(): Long {
        db = openHelper.writableDatabase
        val cursor: Cursor = db!!.rawQuery("SELECT SUM(Cost) FROM Invoice", null)
        if (cursor.moveToFirst()) {
            do {
                return cursor.getLong(0)
            } while (cursor.moveToNext())
        } else {
            return 0
        }

        db!!.close()
    }

    @SuppressLint("Recycle")
    fun SelectIDInvoice(): Int {
        db = openHelper.writableDatabase
        val cursor: Cursor = db!!.rawQuery(
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

    @SuppressLint("Recycle")
    fun InvoiceCount(): Int {
        db = openHelper.writableDatabase
        val cursor: Cursor = db!!.rawQuery(
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

    fun InsertInvoice(ID_Invoice: Int, Name_Invoice: String, Type_ID_Invoice: Int, Cost: Long) {
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
        db!!.execSQL("PRAGMA foreign_keys=ON");
        db!!.delete("Invoice", "ID_Invoice =$ID", null)
        db!!.close()
    }

    fun UpdateInvoice(ID: Int, Name_Invoice: String, Type_ID_Invoice: Int) {
        db = openHelper.writableDatabase
        val newValues = ContentValues()
        newValues.put("Name", Name_Invoice)
        newValues.put("Type_ID_Invoice", Type_ID_Invoice)
        db!!.update("Invoice", newValues, "ID_Invoice = $ID", null)
        db!!.close()
    }

    fun InsertIncome(Date: String, Category_Income_ID: Int, Cost: Long, Description: String, Invoice_ID: Int) {
        db = openHelper.writableDatabase
        val newValues = ContentValues()
        newValues.put("Date", Date)
        newValues.put("Category_Income_ID", Category_Income_ID)
        newValues.put("Cost", Cost)
        newValues.put("Description", Description)
        newValues.put("Invoice_ID", Invoice_ID)
        db!!.insert("Income", null, newValues)
        db!!.close()
    }

    fun InsertExpence(Date: String, Category_expence_ID: Int, Cost: Long, Description: String, Invoice_ID: Int) {
        db = openHelper.writableDatabase
        val newValues = ContentValues()
        newValues.put("Date", Date)
        newValues.put("Category_expence_ID", Category_expence_ID)
        newValues.put("Cost", Cost)
        newValues.put("Description", Description)
        newValues.put("Invoice_ID", Invoice_ID)
        db!!.insert("Expence", null, newValues)
        db!!.close()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        context.deleteDatabase("money.db")
        onCreate(p0)
    }

    @SuppressLint("Recycle", "SimpleDateFormat")
    private fun allDate(type: Int, date_from: Date, date_to: Date?): ArrayList<String> {
        db = openHelper.writableDatabase
        var date = ArrayList<String>()
        var c: Cursor? = null
        if (type == 1 || type == 3) {
            val format = SimpleDateFormat("y-MM-dd")
            Log.i("Date", format.format(date_from))
            c = db!!.query("View_Expence_Income", arrayOf("DISTINCT Date"), "Date  =  ?", arrayOf(format.format(date_from)), null, null, null)// День
        }
        if (type == 2) c = db!!.rawQuery("SELECT DISTINCT Date from View_Expence_Income", null) // Все даты
        if (type == 4) {
            val format = SimpleDateFormat("y-MM-dd")
            c = db!!.query("View_Expence_Income", arrayOf("DISTINCT Date"), "Date  BETWEEN  ? and ?", arrayOf(format.format(date_from), format.format(date_to)), null, null, null) /// Неделя
        }
        if (type == 5) {
            val format = SimpleDateFormat("y-MM-")
            Log.i("Date", format.format(date_from))
            c = db!!.query("View_Expence_Income", arrayOf("DISTINCT Date"), "Date Like ?", arrayOf("${format.format(date_from)}%"), null, null, null) // месяц
        }
        if (type == 6) {
            val format = SimpleDateFormat("y-")
            Log.i("Date", format.format(date_from))
            c = db!!.query("View_Expence_Income", arrayOf("DISTINCT Date"), "Date Like ? ", arrayOf("${format.format(date_from)}%"), null, null, null) //Год
        }

        if (c!!.moveToFirst()) {
            do {
                date.add(
                    c.getString(0)
                )
            } while (c.moveToNext());
        } else {
            date = ArrayList<String>()
        }

        db!!.close()
        return date
    }

    fun OperationDateAll(type: Int, date_from: Date, date_to: Date?, Invoice_ID: Int): ArrayList<OperationDate> {
        val arrayExpence = ArrayList<OperationDate>()
        val date = allDate(type, date_from, date_to)
        SaveCost.Expence = 0
        SaveCost.Income = 0

        if (date.isEmpty()) return ArrayList<OperationDate>()

        for (i in 0..date.size - 1) {
            val cursor = onSelectInvoice(date[i], Invoice_ID)
            val arrayList = ArrayList<Operations>()
            var cost: Long = 0

            if (cursor.moveToFirst()) {
                do {
                    val model = Operations(0, "", 1, "", "", 0, 0)
                    model.ID = cursor.getInt(0)
                    model.Date = cursor.getString(1)
                    model.Catagory_ID = cursor.getInt(2)
                    model.Cost = cursor.getString(3)
                    model.Description = cursor.getString(4)
                    model.Invoice_ID = cursor.getInt(5)
                    model.Type_table = cursor.getInt(6)
                    model.onZnak(cursor.getInt(6).toString())
                    arrayList.add(
                        model
                    )
                    if (cursor.getInt(6) == 1) {
                        cost -= cursor.getLong(3)
                        SaveCost.Expence -= cursor.getLong(3)
                    } else {
                        cost += cursor.getLong(3)
                        SaveCost.Income += cursor.getLong(3)
                    }
                } while (cursor.moveToNext())
            } else {
                continue
            }
            //cost,expence,income,
            val test = OperationDate(date[i], cost, arrayList)
            test._date = date[i]
            arrayExpence.add(
                test
            )
            db!!.close()
        }
        return arrayExpence
    }

    fun onSelectInvoice(date: String, Invoice_ID: Int): Cursor {
        db = openHelper.writableDatabase
        if (Invoice_ID == 0) return db!!.query(
            "View_Expence_Income", null, "Date = ?", arrayOf(date), null, null, null
        ) else {
            return db!!.query(
                "View_Expence_Income", null, "Date = ? AND Invoice_ID = ?", arrayOf(date, Invoice_ID.toString()), null, null, null
            )
        }
    }

    fun onDeleteExpence(ID: Int) {
        db = openHelper.writableDatabase
        db!!.execSQL("PRAGMA foreign_keys=ON");
        db!!.delete("Expence", "ID_Expence =$ID", null)
        db!!.close()
    }

    fun onDeleteIncome(ID: Int) {
        db = openHelper.writableDatabase
        db!!.execSQL("PRAGMA foreign_keys=ON");
        db!!.delete("Income", "ID_Income =$ID", null)
        db!!.close()
    }

    fun onUpdateExpence(operations: Operations) {
        db = openHelper.writableDatabase
        val newValues = ContentValues()
        newValues.put("Date", operations.Date)
        newValues.put("Category_Expence_ID", operations.Catagory_ID)
        newValues.put("Cost", operations.Cost)
        newValues.put("Description", operations.Description)
        newValues.put("Invoice_ID", operations.Invoice_ID)
        db!!.update("Expence", newValues, "ID_Expence = ${operations.ID}", null)
        db!!.close()
    }

    fun onUpdateIncome(operations: Operations) {
        db = openHelper.writableDatabase
        val newValues = ContentValues()
        newValues.put("Date", operations.Date)
        newValues.put("Category_Income_ID", operations.Catagory_ID)
        newValues.put("Cost", operations.Cost)
        newValues.put("Description", operations.Description)
        newValues.put("Invoice_ID", operations.Invoice_ID)
        db!!.update("Income", newValues, "ID_Income = ${operations.ID}", null)
        db!!.close()
    }

    fun Invoice(): ArrayList<Inv> {
        db = openHelper.writableDatabase
        var arrayList = ArrayList<Inv>()
        val cursor: Cursor = db!!.query("Invoice", null, null, null, null, null, "Type_ID_Invoice ASC")
        if (cursor.moveToFirst()) {
            do {

                val invoice = Invoice(0, "", 0, 0, 0)
                invoice.imageId = cursor.getInt(2)
                arrayList.add(
                    Inv(
                        cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getLong(3)
                    )
                )
            } while (cursor.moveToNext())
        } else {
            arrayList = ArrayList()
        }

        db!!.close()

        return arrayList
    }

    fun Expence(): ArrayList<Oper> {
        db = openHelper.writableDatabase
        var arrayList = ArrayList<Oper>()
        val cursor: Cursor = db!!.query("Expence", null, null, null, null, null, "ID_Expence ASC")
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(
                    Oper(
                        cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5)
                    )
                )
            } while (cursor.moveToNext())
        } else {
            arrayList = ArrayList()
        }

        db!!.close()

        return arrayList
    }

    fun Income(): ArrayList<Oper> {
        db = openHelper.writableDatabase
        var arrayList = ArrayList<Oper>()
        val cursor: Cursor = db!!.query("Income", null, null, null, null, null, "ID_Income ASC")
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(
                    Oper(
                        cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5)
                    )
                )
            } while (cursor.moveToNext())
        } else {
            arrayList = ArrayList()
        }

        db!!.close()

        return arrayList
    }

    fun FirebaseExpence(arrayexpence: ArrayList<HashMap<*, *>>) {

    }

    fun FirebaseIncome(arrayincome: ArrayList<HashMap<*, *>>) {


    }

    fun FirebaseSave(save: SaveDateFireBase) {
        db = openHelper.writableDatabase
        var newValues = ContentValues()
        for (i in save.expence!!) {
            newValues.put("Date", i.date)
            newValues.put("Category_Expence_ID", i.Category_ID)
            newValues.put("Cost", i.cost)
            newValues.put("Description", i.description)
            newValues.put("Invoice_ID", i.invoice_id)
            db!!.insert("Expence", null, newValues)
        }
        db!!.close()

        db = openHelper.writableDatabase
        newValues = ContentValues()
        for (i in save.income!!) {
            newValues.put("Date", i.date)
            newValues.put("Category_Income_ID", i.Category_ID)
            newValues.put("Cost", i.cost!!.toInt())
            newValues.put("Description", i.description)
            newValues.put("Invoice_ID", i.invoice_id)
            db!!.insert("Income", null, newValues)
        }
        db!!.close()

        db = openHelper.writableDatabase
        newValues = ContentValues()
        for (i in save.invoice!!) {
            newValues.put("ID_Invoice", i.ID_Invoice)
            newValues.put("Name", i.Name)
            newValues.put("Type_ID_Invoice", i.Type_ID_Invoice)
            newValues.put("Cost", i.Cost!!.toInt())
            db!!.insert("Invoice", null, newValues)
        }
        db!!.close()
    }

}

data class Oper(var ID: Int? = null, var date: String? = null, var Category_ID: Int? = null, var cost: String? = null, var description: String? = null, var invoice_id: Int? = null)

data class Inv(
    var ID_Invoice: Int? = null, var Name: String? = null, var Type_ID_Invoice: Int? = null, var Cost: Long? = null
)

data class SaveDateFireBase(
    var income: ArrayList<Oper>? = null, var expence: ArrayList<Oper>? = null, var invoice: ArrayList<Inv>? = null
)

data class User(var email: String? = null, var name: String? = null) {
    companion object {
        var email: String = ""
        var name: String = ""

    }
}