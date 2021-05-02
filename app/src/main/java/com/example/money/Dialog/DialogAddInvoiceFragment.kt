package com.example.money.Dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.money.InvoiceResultCallBacks
import com.example.money.R
import com.example.money.database.DataBaseHelper
import com.example.money.model.Invoice
import com.example.money.viewmodel.OnSaveInvoiceViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.dialog_add_invoice.*


class DialogAddInvoiceFragment : DialogFragment(), InvoiceResultCallBacks {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_add_invoice, container, false)

        return view
    }

    val model: OnSaveInvoiceViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DataBaseHelper(view.context)

        val name_invoice = view.findViewById<TextInputLayout>(R.id.name_invoice)
        val type_invoice = view.findViewById<Spinner>(R.id.type_invoice)
        val cost = view.findViewById<TextInputLayout>(R.id.cost_invoice)

        cost.getEditText()!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                if (text.isEmpty()) {
                    cost.error = "Введите число"
                    cost.isErrorEnabled = true
                    return
                }
                if (text.length >= 2)
                    if ((text.get(after) == '0' || text.get(count) == '0') && text.get(0) == '0') {
                        cost.error = "Неправильный ввод"
                        cost.isErrorEnabled = true
                        return
                    }
                cost.isErrorEnabled = false
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


        name_invoice.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length == 0) {
                    name_invoice.error = "Поле пустое"
                    name_invoice.isErrorEnabled = true
                } else {
                    name_invoice.isErrorEnabled = false
                }
            }

        })


        var type_id = 1
        type_invoice.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, id: Int, p3: Long) {
                type_id = id + 1
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        })


        btn_add_invoice.setOnClickListener {

            if (name_invoice.editText!!.text.toString().trim() == "") {
                name_invoice.editText!!.text.clear()
                name_invoice.error = "Поле пустое"
                name_invoice.isErrorEnabled = true
            }

            if (!cost.isErrorEnabled && !name_invoice.isErrorEnabled) {
                val invoice =
                    Invoice(
                        db.SelectIDInvoice(),
                        name_invoice.editText!!.text.toString().trim(),
                        type_id,
                        cost.editText!!.text.toString().trim().toInt(),
                        0
                    )

                invoice.imageId = type_id
                val array = ArrayList<Invoice>()
                array.add(invoice)

                model.onSaveInvoice(array)
                db.AddInvoice(name_invoice.editText!!.text.toString().trim(), type_id, cost.editText!!.text.toString().toInt())
                array.clear()

                dismiss()
            } else {
                Toast.makeText(view.context, "Введите данные корректно", Toast.LENGTH_SHORT).show()
            }

        }

    }


//    override fun onDismiss(dialog: DialogInterface) {
////        var builder = AlertDialog.Builder(activity)
////        builder.setTitle("Внимание")
////            .setMessage("Данные были не сохранены")
////            .setPositiveButton(
////                "Продолжить редактирование",
////                DialogInterface.OnClickListener { dialog, id -> // Закрываем окно
////                    dialog.cancel()
////                    var dialog = DialogAddInvoiceFragment()
////                    dialog.show(parentFragmentManager, "Test")
////                })
////            .setNegativeButton(
////                "Удалить изменения",
////                DialogInterface.OnClickListener { dialog, id -> // Закрываем окно
////                    dialog.cancel()
////                })
////        builder.show()
//    }

    override fun onError(message: String) {
    }

    override fun onSucces(message: String) {
    }

}