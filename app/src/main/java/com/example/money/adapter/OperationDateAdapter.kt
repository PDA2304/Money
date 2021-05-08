package com.example.money.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.money.EditOperationsActivity
import com.example.money.R
import com.example.money.database.DataBaseHelper
import com.example.money.databinding.ItemOperationsDateBinding
import com.example.money.model.OperationDate
import com.example.money.model.Operations
import com.example.money.model.SaveCost
import com.example.money.model.SaveOperations
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlin.collections.ArrayList


class OperationDateAdapter(var context:  Context): RecyclerView.Adapter<OperationDateAdapter.ExpenceHolder>() {

    var textView =ArrayList<TextView>()
    var operations  :  ArrayList<OperationDate> = ArrayList<OperationDate>()
    val db = DataBaseHelper(context)

    inner class ExpenceHolder(val recyclerviewMovieBinding: ItemOperationsDateBinding) : RecyclerView.ViewHolder(recyclerviewMovieBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExpenceHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_operations_date, parent, false
        )
    )

    override fun onBindViewHolder(holder: ExpenceHolder, position: Int) {
        holder.recyclerviewMovieBinding.model = operations[position]

        val itemSwipe = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        if (operations[holder.adapterPosition].expence[viewHolder.adapterPosition].Type_table == 1) {
                            db.onDeleteExpence(operations[holder.adapterPosition].expence[viewHolder.adapterPosition].ID)
                        } else {
                            db.onDeleteIncome(operations[holder.adapterPosition].expence[viewHolder.adapterPosition].ID)
                        }
                        Log.i("Item", operations[holder.adapterPosition].expence[viewHolder.adapterPosition].Date + " " + operations[holder.adapterPosition].expence[viewHolder.adapterPosition].ID)
                        operations = db.OperationDateAll(SaveOperations.type, SaveOperations.date_from, SaveOperations.date_to, SaveOperations.Invoice_ID)
                        if (operations.size == 0) {
                            textView[0].visibility = View.VISIBLE
                            textView[1].text = "0 P"
                            textView[2].text = "0 P"
                            textView[3].text = "0 P"
                        } else {
                            textView[0].visibility = View.GONE
                            textView[1].text = "${SaveCost.Expence} P"
                            textView[2].text = "${SaveCost.Income} P"
                            textView[3].text = "${(SaveCost.Expence + SaveCost.Income)} P"
                        }
                        notifyDataSetChanged()
                    }
                    ItemTouchHelper.RIGHT -> {
                        val intent  = Intent(context,EditOperationsActivity::class.java)
                        intent.putExtra("Operations",operations)
                        intent.putExtra("position",holder.adapterPosition)
                        intent.putExtra("pos",viewHolder.adapterPosition)
                        notifyItemChanged(holder.adapterPosition)
                        context.startActivity(intent)
                    }
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                RecyclerViewSwipeDecorator.Builder(
                    c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                ).addSwipeLeftBackgroundColor(ContextCompat.getColor(context, R.color.red)).addSwipeLeftActionIcon(R.drawable.ic_delete).addSwipeRightBackgroundColor(
                    ContextCompat.getColor(
                        context, R.color.cash
                    )
                ).addSwipeRightActionIcon(R.drawable.ic_edit).create().decorate()

                super.onChildDraw(
                    c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                )
            }

        }
        val swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(holder.recyclerviewMovieBinding.recyclerviewOperations)
        holder.recyclerviewMovieBinding.recyclerviewOperations.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(true)
            it.adapter = OperationsAdapter(
                operations[position].expence
            )
        }
    }

    override fun getItemCount() = operations.size

    fun onUpdate(position: Int,pos:Int)
    {
      this.operations = db.OperationDateAll(SaveOperations.type, SaveOperations.date_from, SaveOperations.date_to, SaveOperations.Invoice_ID)
        if (operations.size == 0) {
            textView[0].visibility = View.VISIBLE
            textView[1].text = "0 P"
            textView[2].text = "0 P"
            textView[3].text = "0 P"
        } else {
            textView[0].visibility = View.GONE
            textView[1].text = "${SaveCost.Expence} P"
            textView[2].text = "${SaveCost.Income} P"
            textView[3].text = "${(SaveCost.Expence + SaveCost.Income)} P"
        }
        notifyItemChanged(position)
    }

    fun refresh(operations: ArrayList<OperationDate>) {
        this.operations.clear()
        this.operations.addAll(operations)
        notifyDataSetChanged()
    }

}