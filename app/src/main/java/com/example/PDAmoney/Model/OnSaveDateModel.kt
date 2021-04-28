package com.example.PDAmoney.Model

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnSaveDateModel : ViewModel() {
    val savedate = MutableLiveData<String>()

    fun onSaveDate(item: String) {
        savedate.value = item
    }
}