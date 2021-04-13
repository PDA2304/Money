package com.example.money.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.money.ListView_Adapter_Invoice
import com.example.money.OnDataTextDate
import com.example.money.R
import com.example.money.model.Invoice
import kotlinx.android.synthetic.main.dialog_fragment_invoices.*


class DialogFragmentInvoices : DialogFragment() {

    var adapter: ListView_Adapter_Invoice? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var view = inflater.inflate(
            R.layout.dialog_fragment_invoices, container, false
        )

        return view
    }

    private var onDataTextDate: OnDataTextDate? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        onDataTextDate = activity as OnDataTextDate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var test = ArrayList<Invoice>()
        test.add(Invoice(1, "Наличные", 1, 1000))
        test.add(Invoice(2, "Сбербанк", 2, 10000))

        dialog_listview_invoice.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(view.context, "$id", Toast.LENGTH_SHORT).show()
            onDataTextDate!!.onIdInvoiceSave(id)
            dismiss()
        }

        adapter = ListView_Adapter_Invoice(view.context, test)
        dialog_listview_invoice.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

}