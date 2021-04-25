package com.example.money.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.money.OnSaveData
import com.example.money.R
import com.github.florent37.shapeofview.shapes.CircleView
import kotlinx.android.synthetic.main.appbar.*

class CategoriesFragmentExpense : Fragment(), OnSaveData {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_categories_expense, container, false)
        var btn: CircleView = view.findViewById(R.id.header)
        btn.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_item_categoriesFragmentExpense_to_categoriesFragmentIncome)
        }
        return view
    }

    fun select( id :Int,context: Context)
    {
        if(id == -1)
        {
            Toast.makeText(context, "Все счета", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, id.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDateText(data: String?) {
        text_date.text = data!!.toUpperCase()
    }

    override fun onIdInvoiceSave(id: Long) {
    }

}