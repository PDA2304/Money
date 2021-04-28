package com.example.PDAmoney.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.PDAmoney.R

class StatisticIncomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment linear_invoice

        var view = inflater.inflate(R.layout.fragment_statistic_income, container, false)

        var btn = view.findViewById<Button>(R.id.btn_to_operations)
        btn.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_statisticIncomeFragment_to_operationsFragment)
        }

        var linear = view.findViewById<LinearLayout>(R.id.linear_invoice)
        linear.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_statisticIncomeFragment_to_statisticInvoiceFragment)
        }

        return view
    }

}