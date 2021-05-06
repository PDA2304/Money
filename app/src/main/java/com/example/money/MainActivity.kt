package com.example.money

import android.app.Activity
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.money.Dialog.DialogDateFragment
import com.example.money.Fragment.CategoriesFragment
import com.example.money.adapter.InvoiceAdapter
import com.example.money.factory.InvoiceViewModelFactory
import com.example.money.model.Invoice
import com.example.money.viewmodel.InvoiceViewModel
import com.example.money.viewmodel.OnSaveDateViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_invoice.*
import java.util.*

class MainActivity : AppCompatActivity(), InvoiceAdapter.ClickListener, InvoiceResultCallBacks {

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
            val search = toolbar.findViewById<View>(R.id.search)


            when (item.itemId) {
                R.id.invoiceFragment -> {
                    search.visibility = View.GONE
                    invoice_select.visibility = View.GONE
                    date_select.visibility = View.GONE
                    title = "Счета"
                }
                R.id.categoriesFragment -> {
                    search.visibility = View.VISIBLE
                    invoice_select.visibility = View.VISIBLE
                    date_select.visibility = View.VISIBLE
                    title = "Категории"
                }
                R.id.profileFragment -> {
                    search.visibility = View.GONE
                    invoice_select.visibility = View.GONE
                    date_select.visibility = View.GONE
                    title = "Профиль"
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var test = menuInflater.inflate(R.menu.menu_item_bar, menu);
        var manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchItem = menu?.findItem(R.id.search)
        var searchView = searchItem?.actionView as SearchView
//
        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        return true
    }

    var create: AlertDialog? = null
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.date_select -> {
                var selecc_date = DialogDateFragment()
                selecc_date.show(supportFragmentManager, "Date")
            }
            R.id.invoice_select -> {
                val build = AlertDialog.Builder(this)
                val view = layoutInflater.inflate(R.layout.alert_dialog_select_invoice, null)
                val viewmodel = ViewModelProvider(this, InvoiceViewModelFactory(this, view)).get(InvoiceViewModel(view, this)::class.java)
                val adapter = InvoiceAdapter(this)
                adapter.SelectData(viewmodel.invoiceShowSelect())
                build.setView(view)
                create = build.show()
                val recycler_invoice_select = view.findViewById<RecyclerView>(R.id.recycler_invoice_select)
                recycler_invoice_select.also {
                    it.adapter = adapter
                    it.layoutManager = LinearLayoutManager(this)
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(invoice: Invoice) {
        create!!.dismiss()
    }


    override fun onError(message: String) {}

    override fun onSucces(message: String) {}

}