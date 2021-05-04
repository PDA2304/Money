package com.example.money.Fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Canvas
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.money.Dialog.DialogAddInvoiceFragment
import com.example.money.InvoiceResultCallBacks
import com.example.money.R
import com.example.money.adapter.InvoiceAdapter
import com.example.money.factory.InvoiceViewModelFactory
import com.example.money.model.Invoice
import com.example.money.viewmodel.InvoiceViewModel
import com.example.money.viewmodel.OnSaveInvoiceViewModel
import com.google.android.material.textfield.TextInputLayout
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.fragment_invoice.*

class InvoiceFragment : Fragment(), InvoiceResultCallBacks, InvoiceAdapter.ClickListener {

    private val model: OnSaveInvoiceViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_invoice, container, false)
        return view
    }


    var adapter = InvoiceAdapter(this)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewmodel = ViewModelProvider(this, InvoiceViewModelFactory(this, view)).get(InvoiceViewModel::class.java)
        VisibilityCost(viewmodel.Count())

        text_invoice_all.text = "${viewmodel.Cost()} P"

        val itemSwipe = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        showDeleteDialog(viewHolder)
                    }
                    ItemTouchHelper.RIGHT -> {
                        showUpdateDialog(viewHolder)
                    }
                }
            }

            private fun showUpdateDialog(viewHolder: RecyclerView.ViewHolder) {
                val position = viewHolder.adapterPosition
                var textview_name_invoice = viewHolder.itemView.findViewById<TextView>(R.id.name_invoice)
                val builder = AlertDialog.Builder(activity)
                val dialogLayout = layoutInflater.inflate(R.layout.alert_dialog_edit_invoice, null)
                val editText = dialogLayout.findViewById<TextInputLayout>(R.id.edit_name_invoice)
                val btn_cancel = dialogLayout.findViewById<Button>(R.id.btn_cancel)
                val spinner_type_ivnoice = dialogLayout.findViewById<Spinner>(R.id.edit_type_invoice)
                val btn_edit_invoice = dialogLayout.findViewById<Button>(R.id.btn_edit_invoice)

                editText.editText!!.text = Editable.Factory.getInstance().newEditable(textview_name_invoice.text.toString())
                editText.editText!!.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun afterTextChanged(p0: Editable?) {}

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        if (p0!!.length == 0) {
                            editText.error = "Поле пустое"
                            editText.isErrorEnabled = true
                        } else {
                            editText.isErrorEnabled = false
                        }
                    }
                })
                var type_id = viewmodel.InvoiceShow()[position].Type_ID_Invoice

                spinner_type_ivnoice.setSelection(type_id - 1)
                spinner_type_ivnoice.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, id: Int, p3: Long) {
                        type_id = id + 1
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                })

                builder.setView(dialogLayout)
                val create = builder.show()
                btn_edit_invoice.setOnClickListener {
                    if (!editText.isErrorEnabled) {
                        adapter.updateData(position, editText.editText!!.text.toString(), type_id)
                        viewmodel.Update(position + 1, editText.editText!!.text.toString(), type_id)
                        create.cancel()
                    }
                }
                btn_cancel.setOnClickListener {
                    create.cancel()
                }
                adapter.notifyItemChanged(position)
            }

            @SuppressLint("SetTextI18n")
            private fun showDeleteDialog(viewHolder: RecyclerView.ViewHolder) {
                val position = viewHolder.adapterPosition
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("Удаление счета")
                builder.setMessage("Вы уверены что хотите удалить это счет")
                builder.setPositiveButton("Да") { dialog, which ->
                    viewmodel.Delete(adapter.deleteData(position))
                    recyclerview_invoice.visibility = View.GONE
                    recyclerview_invoice.visibility = View.VISIBLE
                    VisibilityCost(viewmodel.Count())
                    text_invoice_all.text = "${viewmodel.Cost()} Р"
                }
                builder.setNegativeButton("Нет") { dialog, which ->
                    adapter.notifyItemChanged(position)
                }
                builder.show()
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                RecyclerViewSwipeDecorator.Builder(
                    c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                ).addSwipeLeftBackgroundColor(ContextCompat.getColor(view.context, R.color.red)).addSwipeLeftActionIcon(R.drawable.ic_delete).addSwipeRightBackgroundColor(
                    ContextCompat.getColor(
                        view.context, R.color.cash
                    )
                ).addSwipeRightActionIcon(R.drawable.ic_edit).create().decorate()

                super.onChildDraw(
                    c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                )
            }

        }
        val swap = ItemTouchHelper(itemSwipe)

        val recyclerview_invoice = view.findViewById<RecyclerView>(R.id.recyclerview_invoice)

        btn_add_invoice.setOnClickListener {
            var dialog = DialogAddInvoiceFragment()
            dialog.show(parentFragmentManager, "Test")
        }


        adapter.SelectData(viewmodel.InvoiceShow())


        recyclerview_invoice.also {
            it.layoutManager = LinearLayoutManager(view.context)
            it.setHasFixedSize(true)
            it.adapter = adapter
        }
        recyclerview_invoice.adapter = adapter
        swap.attachToRecyclerView(recyclerview_invoice)

        model.saveinvoice.observe(viewLifecycleOwner, Observer<ArrayList<Invoice>> { item ->
            if (viewmodel.Count() == 4) {
                btn_add_invoice.visibility = View.GONE
                text_information_invoice.visibility = View.VISIBLE
                text_information_invoice.text = "Макс. кол-во счетов"
            } else if (viewmodel.Count() == 3) {
                text_information_invoice.visibility = View.GONE
                btn_add_invoice.visibility = View.VISIBLE
            }
            if (viewmodel.Count() == 0) {
                text_information_invoice.visibility = View.GONE
                btn_add_invoice.visibility = View.VISIBLE
            }
            adapter.insertData(item)
        })
        model.сostinvoice.observe(viewLifecycleOwner, Observer<Int> { item ->
            view.findViewById<TextView>(R.id.text_invoice_all).text = item.toString()
        })
    }

    fun VisibilityCost(count: Int) {
        if (count == 5) {
            btn_add_invoice.visibility = View.GONE
            text_information_invoice.visibility = View.VISIBLE
        } else {
            text_information_invoice.visibility = View.GONE
            btn_add_invoice.visibility = View.VISIBLE
        }
        if (count == 0) {
            text_information_invoice.visibility = View.VISIBLE
            text_information_invoice.text = "Нет ни одного счета"
            btn_add_invoice.visibility = View.VISIBLE
        }
    }

    override fun onError(message: String) {
        Toast.makeText(view?.context, "Ошибка", Toast.LENGTH_SHORT).show()
    }

    override fun onSucces(message: String) {
        Toast.makeText(view?.context, "Все ок", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(invoice: Invoice) {
        Toast.makeText(context, invoice.ID_Invoice.toString(), Toast.LENGTH_SHORT).show()
    }

}