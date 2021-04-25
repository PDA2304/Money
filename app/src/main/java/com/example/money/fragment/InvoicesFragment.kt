package com.example.money.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money.InvoiceResultCallBacks
import com.example.money.R
import com.example.money.adapter.Invoice_Adapter
import com.example.money.factory.InvoiceViewModelFactory
import com.example.money.fragment.Dialog.DialogFragmentAddInvoice
import com.example.money.viewmodel.InvoiceViewModel
import kotlinx.android.synthetic.main.fragment_invoices.*


class InvoicesFragment : Fragment(), InvoiceResultCallBacks {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_invoices, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var viewmodel =
            ViewModelProvider(this, InvoiceViewModelFactory(this, view.context)).get(
                InvoiceViewModel::class.java
            )

        if (viewmodel.Cost() < 0) {
            Invoice_Cost.setTextColor(resources.getColor(R.color.red))
        } else {
            Invoice_Cost.setTextColor(resources.getColor(R.color.green))
        }
        Invoice_Cost.text = viewmodel.Cost().toString() + " P"

        fragment_invoice_list.also {
            it.layoutManager = LinearLayoutManager(view.context)
            it.setHasFixedSize(true)
            it.adapter = Invoice_Adapter(
                viewmodel.InvoiceShow(), view.context
            )
            {


            }
        }

        add_invoice.setOnClickListener {
            var dialog = DialogFragmentAddInvoice()
            dialog.show(parentFragmentManager,"AddInvoices")
        }
    }

    override fun onError(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSucces(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

}