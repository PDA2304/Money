package com.example.money.Dialog

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.money.R
import com.example.money.viewmodel.OnSaveDateViewModel
import kotlinx.android.synthetic.main.dialog_select_date.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class DialogDateFragment : DialogFragment() {

    private val model  :  OnSaveDateViewModel by activityViewModels()

    private var df: DateFormat? = null

    //Переменная которая хранит в себе состояние даты
    private val c = Calendar.getInstance()

    private val Year = c.get(Calendar.YEAR) // Текущий год
    private val Month = c.get(Calendar.MONTH) // Текущий месяц
    private val Day = c.get(Calendar.DAY_OF_MONTH) // Текущий день


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_select_date, container, false)
        return view
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Выбор даты
        select_date.setOnClickListener {
            var test = ""

            @RequiresApi(Build.VERSION_CODES.N)
            fun clickDataPicker(view: View) {

                val dpd = DatePickerDialog(view.context, { _, year, monthOfYear, dayOfMonth ->
                    df = SimpleDateFormat("EEE, dd MMM y")
                    c.set(year, monthOfYear, dayOfMonth)
                    test = df!!.format(c.time)
                }, Year, Month, Day)
                dpd.setOnDismissListener {
                    model.onSaveDate(test)
                    model.onDate(c.time,1)
                }

                dpd.show()
            }
//
            model.onSaveDate(test)

            clickDataPicker(it)
            dismiss()
        }

        //Все время
        all_date.setOnClickListener {
            model.onSaveDate("Весь период")
            model.onDate(c.time,2)
            dismiss()
        }

        // сегодня
        today.setOnClickListener {
            df = SimpleDateFormat("EEE, dd MMM y")
            model.onSaveDate(df!!.format(c.time))
            model.onDate(c.time,3)
            dismiss()
        }

        //Неделя
        week.setOnClickListener {
            model.onSaveDate(getMondaySunday())
            dismiss()
        }

        // Месяц
        month.setOnClickListener {
            df = SimpleDateFormat("LLLL")
            model.onSaveDate(df!!.format(c.time).toString())
            model.onDate(c.time,5)
            dismiss()
        }


        // Выбор года
        year.setOnClickListener {
            df = SimpleDateFormat("y г.")
            model.onSaveDate(df!!.format(c.time))
            model.onDate(c.time,6)
            dismiss()
        }
    }

    // Функция которая возвращает Понедельник и Воскресенье текущиего дня
    @SuppressLint("SimpleDateFormat")
    fun getMondaySunday(): String {
        df = SimpleDateFormat("dd MMM")
        val date = Date()
        c.time = date
        c[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        val monday = c.time

        c[Calendar.DAY_OF_WEEK] = Calendar.SUNDAY
        val sunday = c.time
        return "${df!!.format(monday)} - ${df!!.format(sunday)}"
    }

}

