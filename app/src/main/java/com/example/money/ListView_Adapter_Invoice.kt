package com.example.money

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.money.model.Invoice

class ListView_Adapter_Invoice(
    private var context: Context,
    private var arrayList: ArrayList<Invoice>
) : BaseAdapter() {

    private class ViewHolder(view: View?) {
        //var Image_Type_ID_Ivnoice: ImageView? = null
        var Name: TextView? = null
        var Cost: TextView? = null

        init {
            Name = view?.findViewById<TextView>(R.id.Name_Invoice)
            Cost = view?.findViewById<TextView>(R.id.Cost)
           // Image_Type_ID_Ivnoice = view?.findViewById<ImageView>(R.id.Image_Type_Invoice)
        }

    }


    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(i: Int): Any {
        return arrayList[i]
    }

    override fun getItemId(id: Int): Long {
        return id.toLong()
    }

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup): View {

        val view: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            val inflater =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.invoices_item, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var invoice = arrayList[position]

        viewHolder.Name!!.text = invoice.Name
        viewHolder.Cost!!.text = invoice.Cost.toString()

        return view as View
    }

}