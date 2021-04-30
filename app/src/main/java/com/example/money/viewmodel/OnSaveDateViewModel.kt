package com.example.money.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.money.model.SaveDate

class OnSaveDateViewModel : ViewModel() {
    val savedate = MutableLiveData<String>()

    fun onSaveDate(item: String) {
        savedate.value = item
    }
}