package com.example.money

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.utils.Easing
import com.example.money.adapter.ExpenceSpinnerAdapter
import com.example.money.enumitem.ItemExpenceSpinner
import com.example.money.enumitem.ItemIncomeSpinner
import com.example.money.model.OperationDate
import com.example.money.model.Operations
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class DiagramActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagram)

        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        title = "Диаграмма"
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        mToolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        var operations = intent.getParcelableArrayListExtra<OperationDate>("operations")
        var type = intent.getIntExtra("type", 0)
        var alloperations = ArrayList<Operations>()

        for (i in 0 until operations!!.size) {
            alloperations.addAll(operations[i].expence)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                alloperations.removeIf { item -> item.Type_table == type }
            }
        }

        var pieChar = findViewById<PieChart>(R.id.pieChar)
        var pieshararray = ArrayList<PieEntry>()

        if (type == 2) {
            for (i in ItemExpenceSpinner.values()) {
                var save = alloperations.filter { item -> item.Catagory_ID == i.ID }
                var cost = save.sumOf { item -> item.Cost.toLong() }
                if (cost != 0L) pieshararray.add(PieEntry(cost.toFloat(), i.Name))
            }
        }
        if (type == 1) {
            for (i in ItemIncomeSpinner.values()) {
                var save = alloperations.filter { item -> item.Catagory_ID == i.ID }
                var cost = save.sumOf { item -> item.Cost.toLong() }
                if (cost != 0L) pieshararray.add(PieEntry(cost.toFloat(), i.Name))
            }
        }

        var colorlist: List<Int> = listOf(
            resources.getColor(R.color.purple_200),
            resources.getColor(R.color.teal_200),
            resources.getColor(R.color.teal_700),
            resources.getColor(R.color.card),
            resources.getColor(R.color.cash),
            resources.getColor(R.color.information),
            resources.getColor(R.color.blue),
            resources.getColor(R.color.green)
        )

        var label = ""
        if (type == 1) label = "Доход"
        else label = "Расход"

        var pieDataSet = PieDataSet(pieshararray, label)
        pieDataSet.valueTextSize = 16f
        pieDataSet.sliceSpace = 5f
        pieDataSet.selectionShift = 5f
        pieDataSet.colors = colorlist
        pieDataSet.valueTextColor = Color.BLACK

        var piedata = PieData(pieDataSet)
        pieChar.data = piedata
        pieChar.dragDecelerationFrictionCoef = 0.99F
        pieChar.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChar.isDrawHoleEnabled = false
        pieChar.description.isEnabled = false
        pieChar.centerText = label
        pieChar.animateY(1000, com.github.mikephil.charting.animation.Easing.EaseInCubic)
        pieChar.animate()


    }
}