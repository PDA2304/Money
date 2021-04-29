package com.example.PDAmoney.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.PDAmoney.Interface.InvoiceResultCallBacks
import com.example.PDAmoney.R
import kotlinx.android.synthetic.main.fragment_invoice.*

class InvoiceFragment : Fragment(), InvoiceResultCallBacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view =  inflater.inflate(R.layout.fragment_invoice, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        var viewmodel =
//            ViewModelProvider(this, InvoiceViewModelFactory(this, view.context)).get(
//                InvoiceViewModel::class.java
//            )
//
//        if (viewmodel.Cost() < 0) {
//            text_invoice_all.setTextColor(resources.getColor(R.color.red))
//        } else {
//            text_invoice_all.setTextColor(resources.getColor(R.color.green))
//        }
//        text_invoice_all.text = viewmodel.Cost().toString() + " P"
//
//
//        recyclerview_invoice.also {
//            it.layoutManager = LinearLayoutManager(view.context)
//            it.setHasFixedSize(true)
//            it.adapter = AdapterRecyclerViewInvoice(
//                viewmodel.InvoiceShow(), view.context
//            )
//            {
//
//
//            }
//        }


    }

    override fun onError(message: String) {
        Toast.makeText(view?.context, "Ошибка", Toast.LENGTH_SHORT).show()
    }

    override fun onSucces(message: String) {
        Toast.makeText(view?.context, "Все ок", Toast.LENGTH_SHORT).show()
    }

}