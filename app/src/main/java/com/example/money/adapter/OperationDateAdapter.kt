package com.example.money.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.money.R
import com.example.money.databinding.ItemOperationsDateBinding
import com.example.money.model.OperationDate

class OperationDateAdapter(var context: Context) : RecyclerView.Adapter<OperationDateAdapter.ExpenceHolder>() {

    var operations = ArrayList<OperationDate>()

    inner class ExpenceHolder(val recyclerviewMovieBinding: ItemOperationsDateBinding) : RecyclerView.ViewHolder(recyclerviewMovieBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExpenceHolder(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_operations_date, parent, false
        )
    )

    override fun onBindViewHolder(holder: ExpenceHolder, position: Int) {
        holder.recyclerviewMovieBinding.model = operations[position]
        holder.recyclerviewMovieBinding.recyclerviewOperations.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(true)
            it.adapter = OperationsAdapter(
                operations[position].expence
            )
        }
    }

    override fun getItemCount() = operations.size

    fun refresh(operations: ArrayList<OperationDate>) {
        this.operations.clear()
        this.operations.addAll(operations)
        notifyDataSetChanged()
    }

}