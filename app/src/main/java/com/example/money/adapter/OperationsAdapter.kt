package com.example.money.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.money.R
import com.example.money.databinding.ItemOperationsBinding
import com.example.money.model.Operations

class OperationsAdapter (private val expence: ArrayList<Operations>) :
    RecyclerView.Adapter<OperationsAdapter.ExpenceHolder>() {

    inner class ExpenceHolder(val recyclerviewMovieBinding: ItemOperationsBinding) :
        RecyclerView.ViewHolder(recyclerviewMovieBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ExpenceHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_operations,
                parent,
                false
            )

        )

    override fun onBindViewHolder(holder: ExpenceHolder, position: Int) {
        holder.recyclerviewMovieBinding.model = expence[position]
    }

    override fun getItemCount() =  expence.size
}