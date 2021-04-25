package com.example.money.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.money.R
import kotlinx.android.synthetic.main.dialog_add_invocie.*
import kotlinx.android.synthetic.main.fragment_invoices.*


class DialogFragmentAddInvoice : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.dialog_add_invocie, container)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ArrayAdapter<String>(view.context,
           android.R.layout.simple_spinner_item,  arrayOf("asd","asd"));
        type_invoice.adapter = adapter
    }

}