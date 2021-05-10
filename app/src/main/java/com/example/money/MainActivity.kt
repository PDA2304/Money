package com.example.money

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.money.Dialog.DialogDateFragment
import com.example.money.Dialog.DialogSelectInvoice
import com.example.money.database.DataBaseHelper
import com.example.money.database.SaveDateFireBase
import com.example.money.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_invoice.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Объявление toolbar и установка
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_actionbar)
        setSupportActionBar(toolbar)

        if (FirebaseAuth.getInstance().uid != null) {
            var db = DataBaseHelper(this)
            CoroutineScope(Dispatchers.IO).launch {
                val docRef = FirebaseFirestore.getInstance().collection("user_date").document(FirebaseAuth.getInstance().uid.toString())
                docRef.get().addOnSuccessListener { documentSnapshot ->
                    val saveDateFireBase = documentSnapshot.toObject(User::class.java)
                    if (saveDateFireBase != null) {

                        User.name = saveDateFireBase!!.name!!
                        User.email = saveDateFireBase!!.email!!
                    }
                }

                var income = db.Income()
                var invoice = db.Invoice()
                var expence = db.Expence()
                var te = SaveDateFireBase(income, expence, invoice)
                FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().uid.toString()).set(te)
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Загрузка закончена", Toast.LENGTH_SHORT).show()
                }
            }
        }

        title = "Категории"

        val navController = Navigation.findNavController(this, R.id.navigation)
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
        var test = menuInflater.inflate(R.menu.menu_item_bar, menu);
        var manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchItem = menu?.findItem(R.id.search)
        var searchView = searchItem?.actionView as SearchView
//
        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.date_select -> {
                var selecc_date = DialogDateFragment()
                selecc_date.show(supportFragmentManager, "Date")
            }
            R.id.invoice_select -> {
                val dialog = DialogSelectInvoice()
                dialog.show(supportFragmentManager, "INVOCIW");
            }
        }
        return super.onOptionsItemSelected(item)
    }

}