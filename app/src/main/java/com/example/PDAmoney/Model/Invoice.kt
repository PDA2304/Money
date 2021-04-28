package com.example.PDAmoney.Model

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.PDAmoney.R
import java.util.*


data class Invoice(
    var ID_Invoice: Int,
    var Name: String,
    var Type_ID_Invoice: Int,
    var Cost: Int,
    var image: Int
) : Observable() {

    var _costToString: String
        get() = Cost.toString() + " P"
        set(value) {
            this.Cost = value.toInt()
        }

    var imageId: Int
        get() = this.image
        set(value) {
            when (value) {
                1 -> {
                    this.image = R.drawable.cash
                }
                2 -> {
                    this.image = R.drawable.card
                }
            }
        }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageViewResource(view: View, resource: Int?): Unit {
            var image = view as ImageView
            image.setImageDrawable(ContextCompat.getDrawable(view.context, resource!!))
        }

        @JvmStatic
        @BindingAdapter("background")
        fun setBackgroundTintLinear(view: View, resource: Int?): Unit {
            var linear = view as LinearLayout
            if (resource == R.drawable.cash) {
                linear.setBackgroundResource(R.color.cash);

            } else {
                linear.setBackgroundResource(R.color.card);
            }
        }
    }

}
