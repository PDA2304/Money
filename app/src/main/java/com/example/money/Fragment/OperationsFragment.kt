package com.example.money.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.money.R

class OperationsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_operations, container, false)

        var btn = view.findViewById<Button>(R.id.btn_to_statistic)
        btn.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_operationsFragment_to_statisticExpenceFragment)
        }
        return view
    }
}