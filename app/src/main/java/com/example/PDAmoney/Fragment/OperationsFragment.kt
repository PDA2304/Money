package com.example.PDAmoney.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.PDAmoney.R
import com.example.PDAmoney.Model.OnSaveDateModel

class OperationsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_operations, container, false)
        Toast.makeText(view.context, "!", Toast.LENGTH_SHORT).show()
        var btn = view.findViewById<Button>(R.id.btn_to_statistic)
        btn.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_operationsFragment_to_statisticInvoiceFragment)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = OnSaveDateModel()

        view.findViewById<TextView>(R.id.text_operations_date).text = model.savedate
    }
}