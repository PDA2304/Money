package com.example.money

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.money.fragment.Dialog.DialogFragmentDate
import com.example.money.fragment.Dialog.DialogFragmentInvoices
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.dialog_fragment_invoices.*
import kotlinx.android.synthetic.main.fragment_invoices.*


class MainActivity : AppCompatActivity(), OnSaveData{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var linear_date = findViewById<LinearLayout>(R.id.linear_date)

        val navController = Navigation.findNavController(this, R.id.navigation)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            navController.navigate(
                item.itemId
            )
            var linear_prof = findViewById<LinearLayout>(R.id.linear_profile)
            var linear_baze = findViewById<LinearLayout>(R.id.linear_baze)



            when (item.itemId) {
                R.id.item_profile -> {
                    linear_prof.visibility = View.VISIBLE
                    linear_baze.visibility = View.GONE
                    text_profile_invoice.text = "Профиль"
                }
                R.id.item_categoriesFragmentExpense -> {
                    linear_date.visibility = View.VISIBLE
                    linear_prof.visibility = View.GONE
                    linear_baze.visibility = View.VISIBLE
                }
                R.id.item_invoices -> {
                    linear_prof.visibility = View.VISIBLE
                    linear_baze.visibility = View.GONE
                    text_profile_invoice.text = "Счета"
                }
                else -> {
                    linear_prof.visibility = View.GONE
                    linear_baze.visibility = View.VISIBLE
                    linear_date.visibility = View.VISIBLE
                }
            }
            return@setOnNavigationItemSelectedListener true
        }


        linear_invoices.setOnClickListener { view ->

            var DialogFragmentInvoices = DialogFragmentInvoices()
            DialogFragmentInvoices.show(supportFragmentManager, "Invoices")


        }
        linear_date.setOnClickListener { view ->
            var DialogFragmentDate = DialogFragmentDate()
            DialogFragmentDate.show(supportFragmentManager, "Date")
        }
    }


    public fun test(view: View) {
        Toast.makeText(this, "Клик", Toast.LENGTH_SHORT).show()
    }

    override fun onDateText(data: String?) {
        text_date.text = data!!.toUpperCase()
    }

    override fun onIdInvoiceSave(id: Long) {
    }
}
