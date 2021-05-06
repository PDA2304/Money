package com.example.money.model

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.money.R
import com.example.money.enumitem.ItemExpenceSpinner
import com.example.money.enumitem.ItemIncomeSpinner
import java.util.*

data class Operations(
    var ID: Int, var Date: String?, var Catagory_ID: Int, var Cost: String, var Description: String?, var Invoice_ID: Int, var Type_table: Int
) : Observable() {

    var znak: String = ""

    fun onZnak(value: String) {
        if (value == "1") {
            this.znak = "-"
        } else {
            this.znak = "+"
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:color")
        fun color(view: View, value: Int) {
            val text = view as TextView
            if (value == 2) text.setTextColor(view.resources.getColor(R.color.green))
        }
        var expence = ItemExpenceSpinner.values()
        var income = ItemIncomeSpinner.values()

        @JvmStatic
        @BindingAdapter(value = ["app:srcImg", "android:CatagoryID"], requireAll = true)
        fun setImage(view: View, table: Int, CatagoryID: Int) {
            val image = view as ImageView
            when (table) {
                1 -> {
                    image.setImageDrawable(ContextCompat.getDrawable(view.context, expence[CatagoryID - 1].img))
                }
                2 -> {
                    image.setImageDrawable(ContextCompat.getDrawable(view.context, income[CatagoryID - 1].img))
                }
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["app:text", "android:CatagoryID"], requireAll = true)
        fun setText(view: View, table: Int, CatagoryID: Int) {
            val text = view as TextView
            when (table) {
                1 -> {
                    text.text = expence.get(CatagoryID - 1).Name
                }
                2 -> {
                    text.text = income.get(CatagoryID - 1).Name
                }
            }
        }
    }

}