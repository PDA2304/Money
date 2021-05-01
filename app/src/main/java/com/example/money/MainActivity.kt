package com.example.money

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.money.Dialog.DialogDateFragment
import com.example.money.viewmodel.OnSaveDateViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_categories.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Объявление toolbar и установка
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_actionbar)
        setSupportActionBar(toolbar)

        title = "Категории"

        var navController = Navigation.findNavController(this, R.id.navigation)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            navController.navigate(
                item.itemId
            )

            val invoice_select = toolbar.findViewById<View>(R.id.invoice_select)
            val date_select = toolbar.findViewById<View>(R.id.date_select)


            when (item.itemId) {
                R.id.invoiceFragment -> {
                    invoice_select.visibility = View.GONE
                    date_select.visibility = View.GONE
                    title = "Счета"
                }
                R.id.categoriesFragment -> {
                    invoice_select.visibility = View.VISIBLE
                    date_select.visibility = View.VISIBLE
                    title = "Категории"
                }
                R.id.profileFragment -> {
                    invoice_select.visibility = View.GONE
                    date_select.visibility = View.GONE
                    title = "Профиль"
                }
            }
            return@setOnNavigationItemSelectedListener true
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_item_bar, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.date_select -> {
                var selecc_date = DialogDateFragment()

                selecc_date.show(supportFragmentManager, "Date")
            }
            R.id.invoice_select -> {
                Toast.makeText(this, "Счета", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}