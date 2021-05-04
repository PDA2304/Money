package com.example.money.Fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.money.Animation
import com.example.money.InvoiceResultCallBacks
import com.example.money.R
import com.example.money.adapter.ExpenceSpinnerAdapter
import com.example.money.adapter.IncomeSpinnerAdapter
import com.example.money.adapter.InvoiceSpinnerAdapter
import com.example.money.database.DataBaseHelper
import com.example.money.viewmodel.OnSaveDateViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_categories.*
import java.text.SimpleDateFormat
import java.util.*


class CategoriesFragment : Fragment(), InvoiceResultCallBacks {

    val model: OnSaveDateViewModel by activityViewModels()
    var isRotate = false
    val c = Calendar.getInstance()
    var df = SimpleDateFormat("EEE, d MMM y")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_categories, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_categories_date.text = df.format(c.time)

        Animation.init(add_expence);
        Animation.init(add_income);
        Animation.init(text_expence);
        Animation.init(text_income);

        model.savedate.observe(viewLifecycleOwner, Observer<String> { item ->
            view.findViewById<TextView>(R.id.text_categories_date).text = item
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

    fun showAlertDialog(view: View, operations: String) {
        isRotate =Animation.rotateFab(btn_add_operations, !isRotate);
        hide()
        val db = DataBaseHelper(view.context)
        val buid = AlertDialog.Builder(view.context)
        val view = layoutInflater.inflate(R.layout.alert_diaog_add_income, null)
        buid.setView(view)
        view.findViewById<TextView>(R.id.text_header).text = operations
        val linear = view.findViewById<LinearLayout>(R.id.linear_date)
        val date = view.findViewById<DatePicker>(R.id.date)
        val cost = view.findViewById<TextInputLayout>(R.id.cost)
        val text_date = view.findViewById<TextView>(R.id.text_date)
        text_date.text = df.format(c.time)
        val cansel = view.findViewById<TextView>(R.id.btn_cancel)
        val btn_add = view.findViewById<TextView>(R.id.btn_add_operations)
        val spinner_income = view.findViewById<Spinner>(R.id.spinner_income)
        val spiner = view.findViewById<Spinner>(R.id.spinner_invoice)
        val descrition = view.findViewById<TextInputLayout>(R.id.description)
        var isShow = false
        var format = SimpleDateFormat("d.MM.y")

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
        ) { view, year, month, day ->
            val c = Calendar.getInstance()
            c.set(year, month, day)
            text_date.text = df.format(c.time)
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
                if (text.length >= 2) if ((text.get(after) == '0' || text.get(count) == '0') && text.get(0) == '0') {
                    cost.error = "Неправильный ввод"
                    cost.isErrorEnabled = true
                    return
                }
                cost.isErrorEnabled = false
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        var adapterIncome = IncomeSpinnerAdapter(view.context)
        var adapterExpence = ExpenceSpinnerAdapter(view.context)
        when (operations) {
            "Доход" -> {
                spinner_income.adapter = adapterIncome
                spinner_income.onItemSelectedListener = adapterIncome.test(spinner_income)
            }
            "Расход" -> {
                spinner_income.adapter = adapterExpence
                spinner_income.onItemSelectedListener = adapterExpence.test(spinner_income)
            }
        }


        val adapter = InvoiceSpinnerAdapter(view.context)
        spiner.adapter = adapter
        spiner.onItemSelectedListener = adapter.select(spiner)


        var create = buid.show()

        btn_add.setOnClickListener {
            if (cost.editText!!.text.toString() == "0") {
                cost.also {
                    it.error = "Операция не может быть равна 0"
                    it.isErrorEnabled = true
                }
            }
            if (!cost.isErrorEnabled) {
                when (operations) {
                    "Доход" -> {
                        db.InsertIncome(savedate,adapterIncome.position,cost.editText!!.text.toString().toInt(),descrition.editText!!.text.toString() ,adapter.position)
                    }
                    "Расход" -> {
                        db.InsertExpence(savedate,adapterExpence.position,cost.editText!!.text.toString().toInt(),descrition.editText!!.text.toString() ,adapter.position)
                    }
                }
                create.dismiss()
            } else {
                Toast.makeText(view.context, "Введите данные корректно", Toast.LENGTH_SHORT).show()
            }
        }
        cansel.setOnClickListener { create.cancel() }

    }

    fun show() {
        Animation.showIn(text_expence);
        Animation.showIn(text_income);
        Animation.showIn(add_income);
        Animation.showIn(add_expence);
    }

    fun hide() {
        Animation.init(text_expence);
        Animation.init(text_income);
        Animation.showOut(add_income);
        Animation.showOut(add_expence);
    }

    override fun onError(message: String) {
    }

    override fun onSucces(message: String) {
    }


}