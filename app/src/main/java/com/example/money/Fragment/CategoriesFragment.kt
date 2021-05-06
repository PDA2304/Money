package com.example.money.Fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money.Animation
import com.example.money.InvoiceResultCallBacks
import com.example.money.R
import com.example.money.adapter.ExpenceSpinnerAdapter
import com.example.money.adapter.IncomeSpinnerAdapter
import com.example.money.adapter.InvoiceSpinnerAdapter
import com.example.money.adapter.OperationDateAdapter
import com.example.money.database.DataBaseHelper
import com.example.money.factory.OperationsDateModelFactory
import com.example.money.viewmodel.OnSaveDateViewModel
import com.example.money.viewmodel.OperationDateViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_categories.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CategoriesFragment : Fragment(), InvoiceResultCallBacks {

    val model: OnSaveDateViewModel by activityViewModels()
    var isRotate = false
    val c = Calendar.getInstance()

    @SuppressLint("SimpleDateFormat")
    var df = SimpleDateFormat("EEE, dd MMM y")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayTextView = ArrayList<TextView>()
        arrayTextView.add(text_operations_null)
        arrayTextView.add(text_expence_select)
        arrayTextView.add(text_income_select)
        arrayTextView.add(text_balans)
        val viewModel = ViewModelProvider(this, OperationsDateModelFactory(view)).get(OperationDateViewModel::class.java)

//        val adapter = OperationDateAdapter(viewModel.test, view.context)
        val adapter = OperationDateAdapter(view.context)
        adapter.operations = viewModel.onOperationDateAll(1, c.time, c.time, 0, arrayTextView)

        recyclerview_operations_date.also {
            it.layoutManager = LinearLayoutManager(view.context)
            it.setHasFixedSize(true)
            it.adapter = adapter
        }

        text_categories_date.text = df.format(c.time)

        Animation.init(add_expence)
        Animation.init(add_income)
        Animation.init(text_expence)
        Animation.init(text_income)

        model.savedate.observe(viewLifecycleOwner, { item ->
            view.findViewById<TextView>(R.id.text_categories_date).text = item
        })

        model.Date.observe(viewLifecycleOwner, { item ->
            adapter.refresh(viewModel.onOperationDateAll(item.type, item.date_from, item.date_to, item.Invoice_ID, arrayTextView))
        })

        btn_add_operations.setOnClickListener {
            isRotate = Animation.rotateFab(it, !isRotate);
            if (isRotate) {
                show()
            } else {
                hide()
            }
        }

        add_income.setOnClickListener {
            showAlertDialog(view, "Доход")
        }
        add_expence.setOnClickListener {
            showAlertDialog(view, "Расход")
        }

    }

    @SuppressLint("SimpleDateFormat", "InflateParams")
    fun showAlertDialog(view: View, operations: String) {
        isRotate = Animation.rotateFab(btn_add_operations, !isRotate);
        hide()
        val db = DataBaseHelper(view.context)
        val buid = AlertDialog.Builder(view.context)
        val view = layoutInflater.inflate(R.layout.alert_diaog_add_income, null)
        buid.setView(view)
        view.findViewById<TextView>(R.id.text_header).text = operations
        val linear = view.findViewById<LinearLayout>(R.id.linear_date)
        val date = view.findViewById<DatePicker>(R.id.date)
        val cost = view.findViewById<TextInputLayout>(R.id.cost)
        val textDate = view.findViewById<TextView>(R.id.text_date)
        textDate.text = df.format(c.time)
        val cancel = view.findViewById<TextView>(R.id.btn_cancel)
        val btnAdd = view.findViewById<TextView>(R.id.btn_add_operations)
        val spinnerIncome = view.findViewById<Spinner>(R.id.spinner_income)
        val spinner = view.findViewById<Spinner>(R.id.spinner_invoice)
        val description = view.findViewById<TextInputLayout>(R.id.description)
        var isShow = false
        val format = SimpleDateFormat("dd.MM.y")

        var savedate = format.format(c.time)

        linear.setOnClickListener {
            if (!isShow) {
                date.visibility = View.VISIBLE
                isShow = true
            } else {
                date.visibility = View.GONE
                isShow = false
            }
        }

        date.init(
            c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
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
        val adapterIncome = IncomeSpinnerAdapter(view.context)
        val adapterExpence = ExpenceSpinnerAdapter(view.context)
        when (operations) {
            "Доход" -> {
                spinnerIncome.adapter = adapterIncome
                spinnerIncome.onItemSelectedListener = adapterIncome.test(spinnerIncome)
            }
            "Расход" -> {
                spinnerIncome.adapter = adapterExpence
                spinnerIncome.onItemSelectedListener = adapterExpence.test(spinnerIncome)
            }
        }


        val adapter = InvoiceSpinnerAdapter(view.context)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = adapter.select(spinner)


        val create = buid.show()

        btnAdd.setOnClickListener {
            if (cost.editText!!.text.toString() == "0") {
                cost.also {
                    it.error = "Операция не может быть равна 0"
                    it.isErrorEnabled = true
                }
            }
            if (!cost.isErrorEnabled) {
                when (operations) {
                    "Доход" -> {
                        db.InsertIncome(savedate, adapterIncome.position, cost.editText!!.text.toString().toInt(), description.editText!!.text.toString(), adapter.position)
                    }
                    "Расход" -> {
                        db.InsertExpence(savedate, adapterExpence.position, cost.editText!!.text.toString().toInt(), description.editText!!.text.toString(), adapter.position)
                    }
                }
                create.dismiss()
            } else {
                Toast.makeText(view.context, "Введите данные корректно", Toast.LENGTH_SHORT).show()
            }
        }
        cancel.setOnClickListener { create.cancel() }

    }

    private fun show() {
        Animation.showIn(text_expence)
        Animation.showIn(text_income)
        Animation.showIn(add_income)
        Animation.showIn(add_expence)
    }

    private fun hide() {
        Animation.init(text_expence)
        Animation.init(text_income)
        Animation.showOut(add_income)
        Animation.showOut(add_expence)
    }

    override fun onError(message: String) {
    }

    override fun onSucces(message: String) {
    }

}