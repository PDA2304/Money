package com.example.money.Dialog

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.money.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.dialog_add_invoice.*


class DialogAddInvoiceFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.dialog_add_invoice, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var name_invoice = view.findViewById<TextInputEditText>(R.id.name_invoice)

        btn_cancel.setOnClickListener {

        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        var builder = AlertDialog.Builder(activity)
        builder.setTitle("Внимание")
            .setMessage("Данные были не сохранены")
            .setPositiveButton(
                "Продолжить редактирование",
                DialogInterface.OnClickListener { dialog, id -> // Закрываем окно
                    dialog.cancel()
                    var dialog = DialogAddInvoiceFragment()
                    dialog.show(parentFragmentManager,"Test")
                })
            .setNegativeButton(
                "Удалить изменения",
                DialogInterface.OnClickListener { dialog, id -> // Закрываем окно
                    dialog.cancel()
                })
        builder.show()
    }

}