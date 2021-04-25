package com.example.money.fragment.Dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.money.R
import com.example.money.OnSaveData
import kotlinx.android.synthetic.main.dialog_fragment_date.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DialogFragmentDate : DialogFragment() {
    //Переменная которая в себе хранит формат времени
    var df: DateFormat? = null

    //Переменная которая в себе хранит Interface
    private var onDataTextDate: OnSaveData? = null

    //Переменная которая хранит в себе состояние даты
    val c = Calendar.getInstance()

    val Year = c.get(Calendar.YEAR) // Текущий год
    val Month = c.get(Calendar.MONTH) // Текущий месяц
    val Day = c.get(Calendar.DAY_OF_MONTH) // Текущий день

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var view = inflater.inflate(R.layout.dialog_fragment_date, container, false)
        return view
    }


    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var any_date_relative = view.findViewById<RelativeLayout>(R.id.any_date)
        var year_relative = view.findViewById<RelativeLayout>(R.id.year)

        // Выбор даты
        any_date_relative.setOnClickListener {

            @RequiresApi(Build.VERSION_CODES.N)
            fun clickDataPicker(view: View) {
                df = SimpleDateFormat("EEE, d MMM y")
                val dpd = DatePickerDialog(
                    view.context,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        c.set(year, monthOfYear, dayOfMonth)
                        onDataTextDate!!.onDateText(df!!.format(c.time))
                    },
                    Year,
                    Month,
                    Day
                )
                dpd.show()
            }

            clickDataPicker(it)
            dismiss()
        }

        //Все время
        all_date.setOnClickListener {
            onDataTextDate!!.onDateText("Весь период")
            dismiss()
        }

        // сегодня
        today.setOnClickListener {
            df = SimpleDateFormat("EEE, d MMM y")
            onDataTextDate!!.onDateText(df!!.format(c.time))
            dismiss()
        }

        //Неделя
        week.setOnClickListener {
            onDataTextDate!!.onDateText(getMondaySunday())
            dismiss()
        }

        // Месяц
        month.setOnClickListener {
            df = SimpleDateFormat("LLLL")
            onDataTextDate!!.onDateText(df!!.format(c.time))
            dismiss()
        }


        // Выбор года
        year_relative.setOnClickListener {
            df = SimpleDateFormat("y")
            onDataTextDate!!.onDateText("${df!!.format(c.time)} год")
            dismiss()
        }
    }

    // Функция которая возвращает Понедельник и Воскресенье текущиего дня
    fun getMondaySunday(): String? {
        df = SimpleDateFormat("dd MMM")
        val date = Date()
        c.time = date
        c[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        var monday = c.time

        c[Calendar.DAY_OF_WEEK] = Calendar.SUNDAY
        var sunday = c.time

        return "${df!!.format(monday)} - ${df!!.format(sunday)}"
    }


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        onDataTextDate = activity as OnSaveData
    }
}

















