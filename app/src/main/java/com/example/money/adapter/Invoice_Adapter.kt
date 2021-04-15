package com.example.money.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.money.R
import com.example.money.databinding.InvoicesItemBinding
import com.example.money.model.Invoice
import kotlinx.android.synthetic.main.appbar.*

class Invoice_Adapter(private val invoice: ArrayList<Invoice>, var context: Context, var listener: (Invoice) -> Unit) :
    RecyclerView.Adapter<Invoice_Adapter.InvoiceHolder>() {

    class InvoiceHolder(val recyclerViewMovieBinding: InvoicesItemBinding) :
        RecyclerView.ViewHolder(recyclerViewMovieBinding.root) {
            fun onClick(listener: (Invoice) -> Unit, invoice:Invoice)
            {
                listener(invoice)
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InvoiceHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.invoices_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: InvoiceHolder, position: Int) {
        holder.recyclerViewMovieBinding.viewmodel = invoice[position]
    }

    override fun getItemCount(): Int = invoice.size
}