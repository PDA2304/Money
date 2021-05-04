package com.example.money.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.money.enumitem.ItemIncomeSpinner
import com.example.money.R

class IncomeSpinnerAdapter(context:Context) : ArrayAdapter<ItemIncomeSpinner>(context, 0, ItemIncomeSpinner.values()) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View = setInflate(position, convertView, parent)

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View = setInflate(position, convertView, parent)


    fun setInflate(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false)

        getItem(position).let {
            setItem(view, it!!)
        }
        return view
    }

    var position = 1

    fun setItem(view: View, model: ItemIncomeSpinner) {
        val tvCountry = view.findViewById<TextView>(R.id.tvCountry)
        val ivCountry = view.findViewById<ImageView>(R.id.ivCountry)
        tvCountry.text = model.Name
        ivCountry.setBackgroundResource(model.img)
    }

    fun test(spinner: Spinner): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, id: Int, p3: Long) {
                Toast.makeText(spinner.context, id.toString(), Toast.LENGTH_SHORT).show()
                position = id +1
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
}

