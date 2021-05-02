package com.example.money.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.money.InvoiceResultCallBacks
import com.example.money.database.DataBaseHelper
import com.example.money.model.Invoice

class InvoiceViewModel(private var context: Context, private val listener: InvoiceResultCallBacks) :
    ViewModel() {
    val data = DataBaseHelper(context)

    fun Cost(): Int = data.InvoiceCostSum()

    fun Count() : Int = data.InvoiceCount()

    fun InvoiceShow(): ArrayList<Invoice> {

        var array = data.SelectInvoice()
        if (array.size != 0) {
            return array
        } else {
            listener.onError("Нет не одного счета")
        }



        return array
    }



}