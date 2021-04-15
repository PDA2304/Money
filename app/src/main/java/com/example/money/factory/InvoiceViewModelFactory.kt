package com.example.money.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.money.InvoiceResultCallBacks
import com.example.money.viewmodel.InvoiceViewModel

class InvoiceViewModelFactory(private val listener: InvoiceResultCallBacks, var context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InvoiceViewModel(context,listener) as T
    }
}