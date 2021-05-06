package com.example.money.enumitem

import com.example.money.R

enum class ItemIncomeSpinner(var Name: String, var img: Int, var ID: Int) {
    WORK("Зарплата", R.drawable.work, 1), PART_TIME_JOB("Подработка", R.drawable.part_time_job, 2), OTHER("Другое", R.drawable.other, 3)
}