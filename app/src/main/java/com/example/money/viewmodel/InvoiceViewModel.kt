package com.example.money.viewmodel

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import com.example.money.InvoiceResultCallBacks
import com.example.money.R
import com.example.money.database.DataBaseHelper
import com.example.money.model.Invoice

class InvoiceViewModel(private val context: View, private val listener: InvoiceResultCallBacks) : ViewModel() {

    val data = DataBaseHelper(context.context)

    fun Cost(): Long = data.InvoiceCostSum()

    fun Count(): Int = data.InvoiceCount()

    fun Delete(position: Int) {
        data.DeleteInvoice(position)
    }

    fun Update(position: Int, Name_Invoice: String, Type_ID_Invoice: Int) {
        data.UpdateInvoice(position, Name_Invoice, Type_ID_Invoice)
    }

    fun InvoiceShow(): ArrayList<Invoice> {
        val text: TextView = context.findViewById(R.id.text_information_invoice)

        val array = data.selectInvoice()
        if (array.size != 0) {
            return array
        } else {
            text.visibility = View.VISIBLE
            text.text = "Нет ни одного счета"
        }

        return array
    }

    fun invoiceShowSelect(): ArrayList<Invoice> {
        val text_null_invoice: TextView = context.findViewById(R.id.text_null_invoice)
        val card_recycler_view_invoice: CardView = context.findViewById(R.id.card_recycler_view_invoice)
        val invoice_all: LinearLayout = context.findViewById(R.id.select_invoice_all)
        val array = data.selectInvoice()
        if (array.size != 0) {
            card_recycler_view_invoice.visibility = View.VISIBLE
            invoice_all.visibility = View.VISIBLE
            text_null_invoice.visibility = View.GONE
            return array
        } else {
            text_null_invoice.visibility = View.VISIBLE
            text_null_invoice.text = "Нет ни одного счета"
            card_recycler_view_invoice.visibility = View.GONE
            invoice_all.visibility = View.GONE
            return ArrayList<Invoice>()
        }
    }

}