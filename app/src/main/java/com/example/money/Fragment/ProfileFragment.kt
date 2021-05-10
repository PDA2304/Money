package com.example.money.Fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.money.R
import com.example.money.database.DataBaseHelper
import com.example.money.database.SaveDateFireBase
import com.example.money.database.User
import com.example.money.model.SaveCost
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var db = DataBaseHelper(view.context)

        delete_data.setOnClickListener{
            activity?.deleteDatabase("money.db")
        }

        val uid = FirebaseAuth.getInstance().uid
        if (uid != null) {
            name.text = User.name
            email.text = User.email
            linear_users.visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.text_put).text = "Выход"
        } else {
            view.findViewById<TextView>(R.id.text_put).text = "Вход"
        }
        //Выход из системы
        view.findViewById<LinearLayout>(R.id.linear_put).setOnClickListener {
            if (FirebaseAuth.getInstance().uid != null) {
                SaveCost.Expence = 0
                SaveCost.Income = 0
                CoroutineScope(Dispatchers.IO).launch {
                    var income = db.Income()
                    var invoice = db.Invoice()
                    var expence = db.Expence()
                    var te = SaveDateFireBase(income, expence, invoice)
                    FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().uid.toString()).set(te)
                    activity?.runOnUiThread {
                        Toast.makeText(view.context, "Загрузка закончена", Toast.LENGTH_SHORT).show()
                        linear_users.visibility = View.GONE
                    }
                    activity?.deleteDatabase("money.db")
                    FirebaseAuth.getInstance().signOut()
                }
                view.findViewById<TextView>(R.id.text_put).text = "Вход"
            } else {
                onInput(view, uid)
            }
        }

    }

    fun onInput(view: View, uid: String?) {

        var db = DataBaseHelper(view.context)
        if (uid != null) FirebaseAuth.getInstance().signOut()

        val build = AlertDialog.Builder(view.context)
        val view_alert = layoutInflater.inflate(R.layout.alert_dialog_authorization, null)
        val email = view_alert.findViewById<TextInputLayout>(R.id.email)
        val password = view_alert.findViewById<TextInputLayout>(R.id.password)

        var test = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (email.editText!!.text.toString() == "") {
                    email.error = "Поле пустое"
                    email.isErrorEnabled = true
                } else {
                    email.isErrorEnabled = false
                }
                if (password.editText!!.text.toString() == "") {
                    password.error = "Поле пустое"
                    password.isErrorEnabled = true
                } else {
                    password.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        email.editText!!.addTextChangedListener(test)
        password.editText!!.addTextChangedListener(test)

        build.setView(view_alert)
        var create = build.show()

        view_alert.findViewById<Button>(R.id.btn_signIn).setOnClickListener {
            if (!password.isErrorEnabled && !email.isErrorEnabled) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.editText!!.text.toString(), password.editText!!.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(view.context, "Вошел", Toast.LENGTH_SHORT).show()
                        view.findViewById<TextView>(R.id.text_put).text = "Выход"
                        CoroutineScope(Dispatchers.IO).launch {

                            val docRef = FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().uid.toString())
                            docRef.get().addOnSuccessListener { documentSnapshot ->
                                val saveDateFireBase = documentSnapshot.toObject(SaveDateFireBase::class.java)
                                db.FirebaseSave(saveDateFireBase!!)
                            }
                            FirebaseFirestore.getInstance().collection("user_date").document(FirebaseAuth.getInstance().uid.toString()).get().addOnSuccessListener { documentSnapshot ->
                                    val saveDateFireBase = documentSnapshot.toObject(User::class.java)
                                    User.name = saveDateFireBase?.name!!
                                    User.email = saveDateFireBase?.email!!

                                }

                            activity?.runOnUiThread {
                                view.findViewById<TextView>(R.id.name).text = User.name
                                view.findViewById<TextView>(R.id.email).text = User.email
                                Toast.makeText(view.context, "Загрузка закончена", Toast.LENGTH_SHORT).show()
                                linear_users.visibility = View.VISIBLE
                            }
                        }
                        create.dismiss()
                    } else {
                        Toast.makeText(view.context, "Такого пользователя нет", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            if (password.editText!!.text.toString().trim() == "" && email.editText!!.text.toString().trim() == "") {
                password.editText!!.text.clear()
                password.error = "Поле пустое"
                password.isErrorEnabled = true
                email.editText!!.text.clear()
                email.error = "Поле пустое"
                email.isErrorEnabled = true
            }
        }

        view_alert.findViewById<TextView>(R.id.text_registration).setOnClickListener {
            create.dismiss()
            onRegistration(view, uid)
        }
    }

    fun onRegistration(view: View, uid: String?) {
        val build = AlertDialog.Builder(view.context)
        val view_alert = layoutInflater.inflate(R.layout.alert_dialog_registration, null)
        val name = view_alert.findViewById<TextInputLayout>(R.id.name)
        val email = view_alert.findViewById<TextInputLayout>(R.id.email)
        val password = view_alert.findViewById<TextInputLayout>(R.id.password)

        name.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (name!!.editText!!.text.length == 0) {
                    name.error = "поле пустое"
                    name.isErrorEnabled = true
                } else {
                    name.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        email.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (email!!.editText!!.text.length == 0) {
                    email.error = "поле пустое"
                    email.isErrorEnabled = true
                } else {
                    email.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        password.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (password!!.editText!!.text.length == 0) {
                    password.error = "поле пустое"
                    password.isErrorEnabled = true
                } else {
                    password.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        build.setView(view_alert)

        var create = build.show()

        view_alert.findViewById<TextView>(R.id.text_cancel).setOnClickListener {
            create.dismiss()
            onInput(view, uid)
        }

        var db = DataBaseHelper(view.context)

        view_alert.findViewById<Button>(R.id.btn_signUp).setOnClickListener {
            if (password!!.editText!!.text.toString() == "" || email!!.editText!!.text.toString() == "" || name!!.editText!!.text.toString() == "") {
                password.error = "Поле пустое"
                password.isErrorEnabled = true
                email.error = "Поле пустое"
                email.isErrorEnabled = true
                name.error = "Поле пустое"
                name.isErrorEnabled = true
                return@setOnClickListener
            }
            if (!password.isErrorEnabled && !name.isErrorEnabled && !email.isErrorEnabled) FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email!!.editText!!.text.toString(), password!!.editText!!.text.toString()).addOnSuccessListener {
                CoroutineScope(Dispatchers.IO).launch {
                    var income = db.Income()
                    var invoice = db.Invoice()
                    var expence = db.Expence()
                    User.email = email!!.editText!!.text.toString()
                    User.name = name!!.editText!!.text.toString()
                    var te = SaveDateFireBase(income, expence, invoice)
                    val user = User(email!!.editText!!.text.toString(), name!!.editText!!.text.toString())
                    FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().uid.toString()).set(te)
                    FirebaseFirestore.getInstance().collection("user_date").document(FirebaseAuth.getInstance().uid.toString())
                        .set(User)
                    activity?.runOnUiThread {
                       create.dismiss()
                        Toast.makeText(view.context, "Загрузка закончена", Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }

    }

}