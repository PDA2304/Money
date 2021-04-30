package com.example.money.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.money.model.Invoice
import com.example.money.R
import com.example.money.databinding.ItemInvoiceBinding

class InvoiceAdapter(private val invoice: ArrayList<Invoice>, var context: Context, var listener: (Invoice) -> Unit) :
    RecyclerView.Adapter<InvoiceAdapter.InvoiceHolder>() {

    class InvoiceHolder(val recyclerViewMovieBinding: ItemInvoiceBinding) :
        RecyclerView.ViewHolder(recyclerViewMovieBinding.root) {
        fun onClick(listener: (Invoice) -> Unit, invoice: Invoice)
        {
            listener(invoice)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InvoiceHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_invoice,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: InvoiceHolder, position: Int) {
        holder.recyclerViewMovieBinding.model = invoice[position]
    }

    override fun getItemCount(): Int = invoice.size
}

