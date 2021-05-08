package com.example.money.model

import android.annotation.SuppressLint
import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
class OperationDate(
    var date: @RawValue String, var Cost: @RawValue Long, var expence: @RawValue ArrayList<Operations>
) : Observable(), Parcelable {
    var _date: String
        get() = this.date
        @SuppressLint("SimpleDateFormat") set(value) {
            val getdate = SimpleDateFormat("y-MM-dd")
            val setdate = SimpleDateFormat("EEEE, dd MMMM, y г.")
            val da = getdate.parse(value)
            this.date = setdate.format(da)
        }
}


