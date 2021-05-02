package com.example.money.Fragment

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money.Dialog.DialogAddInvoiceFragment
import com.example.money.InvoiceResultCallBacks
import com.example.money.R
import com.example.money.adapter.InvoiceAdapter
import com.example.money.factory.InvoiceViewModelFactory
import com.example.money.model.Invoice
import com.example.money.viewmodel.InvoiceViewModel
import com.example.money.viewmodel.OnSaveInvoiceViewModel
import kotlinx.android.synthetic.main.fragment_invoice.*

class InvoiceFragment : Fragment(), InvoiceResultCallBacks {

    private val model: OnSaveInvoiceViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_invoice, container, false)
        return view
    }

    var adapter = InvoiceAdapter() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewmodel =
            ViewModelProvider(this, InvoiceViewModelFactory(this, view.context)).get(
                InvoiceViewModel::class.java
            )

        VisibilityCost(viewmodel.Count())

        btn_add_invoice.setOnClickListener {
            var dialog = DialogAddInvoiceFragment()
            dialog.show(parentFragmentManager, "Test")
        }

        adapter.SelectData(viewmodel.InvoiceShow())

        recyclerview_invoice.also {
            it.layoutManager = LinearLayoutManager(view.context)
            it.setHasFixedSize(true)
            it.adapter = adapter
        }
        recyclerview_invoice.adapter = adapter

        model.saveinvoice.observe(viewLifecycleOwner, Observer<ArrayList<Invoice>> { item ->
            if (viewmodel.Count() == 4) {
                btn_add_invoice.visibility = View.GONE
                text_max_invoice.visibility = View.VISIBLE
            }
            adapter.updateData(item)
        })

    }

    fun VisibilityCost(count: Int) {
        if (count == 5) {
            btn_add_invoice.visibility = View.GONE
            text_max_invoice.visibility = View.VISIBLE
        } else {
            text_max_invoice.visibility = View.GONE
            btn_add_invoice.visibility = View.VISIBLE
            text_null_invoice.visibility  = View.GONE
        }
        if (count == 0) {
            text_max_invoice.visibility = View.GONE
            btn_add_invoice.visibility = View.VISIBLE
            text_null_invoice.visibility  = View.VISIBLE
        }
    }

    override fun onError(message: String) {
        Toast.makeText(view?.context, "Ошибка", Toast.LENGTH_SHORT).show()
    }

    override fun onSucces(message: String) {
        Toast.makeText(view?.context, "Все ок", Toast.LENGTH_SHORT).show()
    }

}