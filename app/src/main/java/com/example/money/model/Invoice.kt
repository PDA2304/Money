package com.example.money.model

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.money.R
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
        get() = image
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
            var card = view as CardView
            if (resource == R.drawable.cash) {
                card.also {
                    it.setBackgroundResource(R.color.cash);
                    it.radius = 10.toDp(view.context).toFloat()
                }
            } else {
                card.also {
                    it.setBackgroundResource(R.color.card);
                    it.radius = 10.toDp(view.context).toFloat()
                }
            }
        }

        fun Int.toDp(context: Context):Int = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),context.resources.displayMetrics
        ).toInt()

    }


}
