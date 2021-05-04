package com.example.money.adapter

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.example.money.R
import com.example.money.database.DataBaseHelper
import com.example.money.model.Invoice


class InvoiceSpinnerAdapter(context: Context) : ArrayAdapter<Invoice>(context, 0, InvoiceSpinnerAdapter.setArrayList(context)) {

    companion object {
        fun setArrayList(context: Context): ArrayList<Invoice> {
            val data = DataBaseHelper(context)
            val array = data.SelectInvoice()
            if (array.size != 0) {
                return array
            } else {
                return array
            }
        }
    }

    var array = InvoiceSpinnerAdapter.setArrayList(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View = setInflate(position, convertView, parent)

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View = setInflate(position, convertView, parent)

    fun setInflate(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false)

        getItem(position).let {
            setItem(view, it!!)
        }
        return view
    }

    fun setItem(view: View, model: Invoice) {
        val tvCountry = view.findViewById<TextView>(R.id.tvCountry)
        val ivCountry = view.findViewById<ImageView>(R.id.ivCountry)
        if(model.Name.length > 10)
        {
            tvCountry.text  =  model.Name.substring(0, 10) + "..."
        }else
        {
            tvCountry.text  =  model.Name
        }

        ivCountry.setPadding(5,5,5,5)
        if (model.imageId == R.drawable.cash) {
            ivCountry.also {
                it.setBackgroundResource(R.color.cash);
            }
        } else {
            ivCountry.also {
                it.setBackgroundResource(R.color.card);
            }
        }

        ivCountry.setImageDrawable(ContextCompat.getDrawable(view.context, model.imageId))
    }

    var position = 1

    fun select(spinner: Spinner): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, id: Int, p3: Long) {
                Toast.makeText(spinner.context, id.toString(), Toast.LENGTH_SHORT).show()
                position = array.get(id).ID_Invoice
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }


}