package com.example.money

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = Navigation.findNavController(this,R.id.navigation)
        NavigationUI.setupWithNavController(bottomNavigationView,navController)
    }
/*
    <item
    android:id="@+id/item_categories"
    android:title="Категории"/>
    <item
    android:id="@+id/item_operations"
    android:title="Операции"/>
    <item
    android:id="@+id/item_profile"
    android:title="Профиль"/>*/
}