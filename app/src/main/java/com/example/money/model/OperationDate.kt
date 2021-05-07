package com.example.money.model

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OperationDate(var date: String,var Cost: Int, var expence: ArrayList<Operations>) : Observable() {
    var _date: String
        get() = this.date
        @SuppressLint("SimpleDateFormat") set(value) {
            val getdate = SimpleDateFormat("y-MM-dd")
            val setdate = SimpleDateFormat("EEEE, dd MMMM, y г.")
            val da = getdate.parse(value)
            this.date = setdate.format(da)
        }
}


