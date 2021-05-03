package com.example.money.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.money.R
import com.example.money.databinding.ItemInvoiceBinding
import com.example.money.model.Invoice
import java.util.concurrent.ThreadPoolExecutor


class InvoiceAdapter(var listener: ClickListener) : RecyclerView.Adapter<InvoiceAdapter.InvoiceHolder>() {

    class InvoiceHolder(val recyclerViewMovieBinding: ItemInvoiceBinding) : RecyclerView.ViewHolder(recyclerViewMovieBinding.root) {
        fun onClick(listener: (Invoice) -> Unit, invoice: Invoice) {
            listener(invoice)
        }

    }

    private val invoice = ArrayList<Invoice>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InvoiceHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_invoice, parent, false
        )
    )

    interface ClickListener {
        fun onItemClick(invoice: Invoice)
    }


    override fun onBindViewHolder(holder: InvoiceHolder, position: Int) {
        holder.recyclerViewMovieBinding.model = invoice[position]
        holder.itemView.setOnClickListener {
            listener.onItemClick(invoice[position])
        }
    }

    override fun getItemCount(): Int = invoice.size

    fun SelectData(viewModels: ArrayList<Invoice>) {
        invoice.addAll(viewModels)
    }

    fun deleteData(position: Int): Int {
        var id = invoice[position].ID_Invoice
        invoice.removeAt(position)
        notifyItemRemoved(position)
        return id
    }

    fun updateData(position: Int, Name: String, Type_ID_Invoice: Int) {
        invoice[position].Name = Name
        invoice[position].Type_ID_Invoice = Type_ID_Invoice
        invoice[position].imageId = Type_ID_Invoice
        invoice.sortBy { invoice -> invoice.Type_ID_Invoice }
        notifyDataSetChanged()
    }

    fun insertData(viewModels: ArrayList<Invoice>) {
        invoice.addAll(viewModels)
        invoice.sortBy { invoice -> invoice.Type_ID_Invoice }
        notifyDataSetChanged()
    }
}

