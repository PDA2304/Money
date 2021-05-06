package com.example.money.viewmodel

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize
import java.util.*

//import com.example.money.model.SaveDate

class OnSaveDateViewModel : ViewModel() {
    val savedate = MutableLiveData<String>()

    fun onSaveDate(item: String) {
        savedate.value = item
    }

    data class modelDate(var date_from:  Date,var date_to:  Date?,var Invoice_ID: Int,var type:Int): Observable()
    val Date = MutableLiveData<modelDate>()

    fun onDate(date_from: Date, date_to: Date?, Invoice_ID: Int, type: Int) {
        Date.value = modelDate(date_from, date_to, Invoice_ID, type)
    }
}