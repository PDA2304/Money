package com.example.money.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.money.InvoiceResultCallBacks
import com.example.money.data_base.DatabaseHelper
import com.example.money.model.Invoice

class InvoiceViewModel(private var context: Context, private val listener: InvoiceResultCallBacks) :
    ViewModel() {
    val data = DatabaseHelper(context)

    fun Cost(): Int = data.InvoiceCostSum()

    fun InvoiceShow(): ArrayList<Invoice> {
        var array = data.Invoice()
        if (array.size != 0) {
            return array
        } else {
            listener.onError("Нет не одного счета")
        }
        return array
    }


}