package com.example.money.viewmodel

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.money.database.DataBaseHelper
import com.example.money.model.OperationDate
import com.example.money.model.SaveCost
import java.util.*
import kotlin.collections.ArrayList

class OperationDateViewModel(val context: View) : ViewModel() {
    private val data = DataBaseHelper(context.context)

    @SuppressLint("SetTextI18n")
    fun onOperationDateAll(type: Int, date: Date, arrayTextView: ArrayList<TextView>): ArrayList<OperationDate> {
        val array = data.OperationDateAll(type, date)
        if (array.size == 0) {
            arrayTextView[0].visibility = View.VISIBLE
            arrayTextView[1].text = "0 P"
            arrayTextView[2].text = "0 P"
            arrayTextView[3].text = "0 P"
        } else {
            arrayTextView[0].visibility = View.GONE
            arrayTextView[1].text = "${SaveCost.Expence} P"
            arrayTextView[2].text = "${SaveCost.Income} P"
            arrayTextView[3].text = "${(SaveCost.Expence + SaveCost.Income)} P"
        }
        return array
    }


}