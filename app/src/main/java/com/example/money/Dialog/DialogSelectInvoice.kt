package com.example.money.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.money.InvoiceResultCallBacks
import com.example.money.R
import com.example.money.adapter.InvoiceAdapter
import com.example.money.factory.InvoiceViewModelFactory
import com.example.money.model.Invoice
import com.example.money.model.SaveOperations
import com.example.money.viewmodel.InvoiceViewModel
import com.example.money.viewmodel.OnSaveDateViewModel

class DialogSelectInvoice : InvoiceAdapter.ClickListener,DialogFragment(),InvoiceResultCallBacks {

    private val model: OnSaveDateViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.alert_dialog_select_invoice, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewmodel = ViewModelProvider(this, InvoiceViewModelFactory(this, view)).get(InvoiceViewModel(view, this)::class.java)
        val adapter = InvoiceAdapter(this)
        adapter.SelectData(viewmodel.invoiceShowSelect())
        val recycler_invoice_select = view.findViewById<RecyclerView>(R.id.recycler_invoice_select)

        recycler_invoice_select.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
        view.findViewById<LinearLayout>(R.id.select_invoice_all).setOnClickListener {
            model.onSaveNameInvoice("Все счета")
            model.onDate(SaveOperations.date_from,SaveOperations.date_from,0,SaveOperations.type)
            dismiss()
        }

    }

    override fun onItemClick(invoice: Invoice) {
        model.onSaveNameInvoice(invoice.Name)
        model.onDate(SaveOperations.date_from,SaveOperations.date_from,invoice.ID_Invoice,SaveOperations.type)
        dismiss()
    }

    override fun onError(message: String) {}

    override fun onSucces(message: String) {}
}