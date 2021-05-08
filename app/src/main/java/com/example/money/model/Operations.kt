package com.example.money.model

import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.money.R
import com.example.money.enumitem.ItemExpenceSpinner
import com.example.money.enumitem.ItemIncomeSpinner
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*
@Parcelize
data class Operations(
  var ID:   @RawValue Int, var Date:  @RawValue String?, var Catagory_ID:   @RawValue Int, var Cost:   @RawValue String, var Description:  @RawValue String?, var Invoice_ID:  @RawValue Int, var Type_table:  @RawValue Int
) : Observable(), Parcelable {

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