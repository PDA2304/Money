package com.example.money.factory

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.money.viewmodel.OperationDateViewModel

class OperationsDateModelFactory(var view: View) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OperationDateViewModel(view) as T
    }
}