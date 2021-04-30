package com.example.money.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money.InvoiceResultCallBacks
import com.example.money.R
import com.example.money.adapter.InvoiceAdapter
import com.example.money.factory.InvoiceViewModelFactory
import com.example.money.viewmodel.InvoiceViewModel
import kotlinx.android.synthetic.main.fragment_invoice.*

class InvoiceFragment : Fragment(),InvoiceResultCallBacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invoice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var viewmodel =
            ViewModelProvider(this, InvoiceViewModelFactory(this, view.context)).get(
                InvoiceViewModel::class.java
            )

        recyclerview_invoice.also {
            it.layoutManager = LinearLayoutManager(view.context)
            it.setHasFixedSize(true)
            it.adapter = InvoiceAdapter(
                viewmodel.InvoiceShow(), view.context
            )
            {


            }
        }

    }

    override fun onError(message: String) {
        Toast.makeText(view?.context, "Ошибка", Toast.LENGTH_SHORT).show()
    }

    override fun onSucces(message: String) {
        Toast.makeText(view?.context, "Все ок", Toast.LENGTH_SHORT).show()
    }

}