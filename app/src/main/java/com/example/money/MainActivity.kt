package com.example.money

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.navigation)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            navController.navigate(
                item.itemId
            )
            var linear_prof = findViewById<LinearLayout>(R.id.linear_profile)
            var linear_baze = findViewById<LinearLayout>(R.id.linear_baze)

            when(item.itemId)
            {
                R.id.item_profile->
                {
                    linear_prof.visibility = View.VISIBLE
                    linear_baze.visibility = View.GONE
                    Log.i("ID_ID","TEST")
                }
                else ->
                {
                    linear_prof.visibility = View.GONE
                    linear_baze.visibility = View.VISIBLE
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

    }

    fun test(view: View) {
        Toast.makeText(this, "Клик", Toast.LENGTH_SHORT).show()
    }
}