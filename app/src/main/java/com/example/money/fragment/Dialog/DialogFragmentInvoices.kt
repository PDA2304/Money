package com.example.money.fragment.Dialog

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money.InvoiceResultCallBacks
import com.example.money.R
import com.example.money.OnSaveData
import com.example.money.adapter.Invoice_Adapter
import com.example.money.factory.InvoiceViewModelFactory
import com.example.money.viewmodel.InvoiceViewModel
import kotlinx.android.synthetic.main.dialog_fragment_invoices.*


class DialogFragmentInvoices : DialogFragment(), InvoiceResultCallBacks {

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

    private var onSaveData: OnSaveData? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        onSaveData = activity as OnSaveData
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewmodel =
            ViewModelProvider(this, InvoiceViewModelFactory(this, view.context)).get(
                InvoiceViewModel::class.java
            )

        all_invoice.setOnClickListener {
            onSaveData!!.onIdInvoiceSave(1)
            dismiss()
        }

        if (viewmodel.Cost() < 0) {
            cost_all_invoice.setTextColor(resources.getColor(R.color.red))
        } else {
            cost_all_invoice.setTextColor(resources.getColor(R.color.green))
        }

        cost_all_invoice.text = viewmodel.Cost().toString() + " P"

        dialog_listview_invoice.also {
            it.layoutManager = LinearLayoutManager(view.context)
            it.setHasFixedSize(true)
            it.adapter = Invoice_Adapter(
                viewmodel.InvoiceShow(), view.context
            )
            {

            }
        }
    }

    override fun onError(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSucces(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

}