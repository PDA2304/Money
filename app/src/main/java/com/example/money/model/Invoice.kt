package com.example.money.model

import android.view.View
import android.widget.ImageView
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
) :
    Observable() {
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
                    this.image = R.mipmap.ic_cash_foreground
                }
                2 -> {
                    this.image = R.mipmap.ic_credit_card_foreground
                }
            }
        }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageViewResource(view: View, resource: Int?): Unit {
            var image = view as ImageView
            image.setImageDrawable(ContextCompat.getDrawable(view.context, resource!!))
            if (resource == R.mipmap.ic_cash_foreground) {
                image.setBackgroundResource(R.color.green);

            } else {
                image.setBackgroundResource(R.color.red);
            }
        }
    }

}

