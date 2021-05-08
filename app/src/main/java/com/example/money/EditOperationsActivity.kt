package com.example.money

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import com.example.money.adapter.*
import com.example.money.database.DataBaseHelper
import com.example.money.model.OperationDate
import com.example.money.model.Operations
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_categories.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditOperationsActivity : AppCompatActivity() {


    @SuppressLint("SimpleDateFormat")
    var df = SimpleDateFormat("EEE, dd MMM y")

    @SuppressLint("SimpleDateFormat")
    val format = SimpleDateFormat("y-MM-dd")
    val c = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_operations)
        val db = DataBaseHelper(this)

        val operations = intent.getParcelableArrayListExtra<OperationDate?>("Operations")
        val position = getIntent().getIntExtra("position", 0)
        val pos = getIntent().getIntExtra("pos", 0)

        val view = layoutInflater.inflate(R.layout.fragment_categories, null)
        var textView  = ArrayList<TextView>(arrayListOf(view.findViewById(R.id.text_operations_null),view.findViewById(R.id.text_expence_select),view.findViewById(R.id.text_income_select),view.findViewById(R.id.text_balans)))

        val mToolbar = findViewById<Toolbar>(R.id.toolbar_edit_operations_actionbar)
        setSupportActionBar(mToolbar)
        title = "Редактирование операции"
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        mToolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        val linear = findViewById<LinearLayout>(R.id.linear_date)
        val date = findViewById<DatePicker>(R.id.date)
        val cost = findViewById<TextInputLayout>(R.id.cost)
        val textDate = findViewById<TextView>(R.id.text_date)

        textDate.text = df.format(format.parse(operations?.get(position)!!.expence[pos].Date))

        val btnAdd = findViewById<TextView>(R.id.btn_add_operations)
        val spinnerCategori = findViewById<Spinner>(R.id.spinner_income)
        val spinner = findViewById<Spinner>(R.id.spinner_invoice)
        val description = findViewById<TextInputLayout>(R.id.description)
        var isShow = false

        cost.editText!!.text = Editable.Factory.getInstance().newEditable(operations?.get(position)!!.expence[pos].Cost)
        if (operations?.get(position)!!.expence[pos].Description?.isNullOrEmpty() == true) description.editText!!.text =
            Editable.Factory.getInstance().newEditable(operations?.get(position)!!.expence[pos].Description)


        var savedate = operations?.get(position)!!.expence[pos].Date

        linear.setOnClickListener {
            if (!isShow) {
                date.visibility = View.VISIBLE
                isShow = true
            } else {
                date.visibility = View.GONE
                isShow = false
            }
        }

        date.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)) { _, year, month, day ->
            val c = Calendar.getInstance()
            c.set(year, month, day)
            textDate.text = df.format(c.time)
            savedate = format.format(c.time)
        }

        cost.getEditText()!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                if (text.isEmpty()) {
                    cost.error = "Введите число"
                    cost.isErrorEnabled = true
                    return
                }
                if (text.length >= 2) if ((text[after] == '0' || text[count] == '0') && text[0] == '0') {
                    cost.error = "Неправильный ввод"
                    cost.isErrorEnabled = true
                    return
                }
                cost.isErrorEnabled = false
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        val adapterIncome = IncomeSpinnerAdapter(this)
        val adapterExpence = ExpenceSpinnerAdapter(this)
        val adapter = InvoiceSpinnerAdapter(this)

        when (operations?.get(position)!!.expence[pos].Type_table) {
            2 -> {
                spinnerCategori.adapter = adapterIncome
                spinnerCategori.onItemSelectedListener = adapterIncome.test(spinnerCategori)
            }
            1 -> {
                spinnerCategori.adapter = adapterExpence
                spinnerCategori.onItemSelectedListener = adapterExpence.test(spinnerCategori)
            }
        }
        spinnerCategori.setSelection(operations?.get(position)!!.expence[pos].Catagory_ID - 1)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = adapter.select(spinner)
        spinner.setSelection(operations?.get(position)!!.expence[pos].Invoice_ID - 1)

        btnAdd.setOnClickListener {
            if (cost.editText!!.text.toString() == "0") {
                cost.also {
                    it.error = "Операция не может быть равна 0"
                    it.isErrorEnabled = true
                }
            }
            if (!cost.isErrorEnabled && spinner.size != 0) {
                operations?.get(position)!!.expence[pos].also {
                    it.Cost = cost.editText!!.text.toString()
                    it.Date = savedate
                    it.Description = description.editText!!.text.toString()
                    it.Invoice_ID = adapter.position
                }
                when (operations?.get(position)!!.expence[pos].Type_table) {
                    2 -> {
                        operations?.get(position)!!.expence[pos].Catagory_ID = adapterIncome.position
                        db.onUpdateIncome(operations?.get(position)!!.expence[pos])
                    }
                    1 -> {
                        operations?.get(position)!!.expence[pos].Catagory_ID = adapterExpence.position
                        db.onUpdateExpence(operations?.get(position)!!.expence[pos])
                    }
                }
                var intent = Intent(this,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Введите данные корректно", Toast.LENGTH_SHORT).show()
            }

        }

    }


}