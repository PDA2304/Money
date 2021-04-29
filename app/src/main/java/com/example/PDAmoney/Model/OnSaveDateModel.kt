package com.example.PDAmoney.Model

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class OnSaveDateModel() :ViewModel(){
    var savedate : String = ""

    fun onSaveDate(item: String) {
        savedate = item
    }
}