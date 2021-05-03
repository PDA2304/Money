package com.example.money.viewmodel

import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.money.InvoiceResultCallBacks
import com.example.money.R
import com.example.money.database.DataBaseHelper
import com.example.money.model.Invoice
import com.google.android.material.floatingactionbutton.FloatingActionButton

class InvoiceViewModel(private val context: View, private val listener: InvoiceResultCallBacks) : ViewModel() {

    var text = context.findViewById<TextView>(R.id.text_information_invoice)
    var btn_add = context.findViewById<FloatingActionButton>(R.id.btn_add_invoice)
    val data = DataBaseHelper(context.context)

    fun Cost(): Int = data.InvoiceCostSum()

    fun Count(): Int = data.InvoiceCount()

    fun Delete(position: Int) {
        data.DeleteInvoice(position)
    }

    fun Update(position: Int, Name_Invoice: String, Type_ID_Invoice: Int) {
        data.UpdateInvoice(position, Name_Invoice, Type_ID_Invoice)
    }

    fun InvoiceShow(): ArrayList<Invoice> {

        var array = data.SelectInvoice()
        if (array.size != 0) {
            return array
        } else {
            text.visibility = View.VISIBLE
            text.text = "Нет ни одного счета"
        }

        return array
    }

}