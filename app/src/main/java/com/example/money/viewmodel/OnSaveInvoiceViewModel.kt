package com.example.money.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.money.model.Invoice

class OnSaveInvoiceViewModel : ViewModel() {
    val saveinvoice = MutableLiveData<ArrayList<Invoice>>()

    fun onSaveInvoice(item: ArrayList<Invoice>) {
        saveinvoice.value = item
    }
}