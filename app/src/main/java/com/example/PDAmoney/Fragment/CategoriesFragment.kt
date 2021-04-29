package com.example.PDAmoney.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.PDAmoney.Model.OnSaveDateModel
import com.example.PDAmoney.R


class CategoriesFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_categories, container, false)
        Toast.makeText(view.context, "!", Toast.LENGTH_SHORT).show()


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model: OnSaveDateModel by activityViewModels()
        model.savedate.observe(viewLifecycleOwner, Observer<String> { item ->
            view.findViewById<TextView>(R.id.text_categories_date).text = item
        })

    }

}