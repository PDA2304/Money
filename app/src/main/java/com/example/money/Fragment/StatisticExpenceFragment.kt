package com.example.money.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.navigation.Navigation
import com.example.money.R

class StatisticExpenceFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_statistic_expence, container, false)

        var btn = view.findViewById<Button>(R.id.btn_to_operations)
        btn.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_statisticExpenceFragment_to_operationsFragment)
        }

        var linear = view.findViewById<LinearLayout>(R.id.linear_income)
        linear.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_statisticExpenceFragment_to_statisticIncomeFragment)
        }

        return view
    }

}