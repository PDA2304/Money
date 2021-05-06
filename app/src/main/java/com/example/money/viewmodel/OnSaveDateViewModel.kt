package com.example.money.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

//import com.example.money.model.SaveDate

class OnSaveDateViewModel : ViewModel() {
    val savedate = MutableLiveData<String>()

    fun onSaveDate(item: String) {
        savedate.value = item
    }

    data class test(var date:  Date,var type:Int): Observable()
    val Date = MutableLiveData<test>()

    fun onDate(date: Date, type:Int)
    {
        Date.value = test(date,type)
    }
}