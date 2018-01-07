package com.valdroide.thesportsbillboardinstitution.utils.generics

import android.app.Activity
import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R

import java.util.ArrayList

class SpinnerDialog<T> {
    internal var items: MutableList<T>
    internal var context: Activity
    internal var dTitle: String
    internal lateinit var onSpinerItemClick: OnSpinerItemClick
    internal lateinit var alertDialog: AlertDialog
    internal var pos: Int = 0
    internal var style: Int = 0

    constructor(activity: Activity, items: MutableList<T>, dialogTitle: String) {
        this.items = items
        this.context = activity
        this.dTitle = dialogTitle
    }

    constructor(activity: Activity, items: MutableList<T>, dialogTitle: String, style: Int) {
        this.items = items
        this.context = activity
        this.dTitle = dialogTitle
        this.style = style
    }

    fun bindOnSpinerListener(onSpinerItemClick1: OnSpinerItemClick) {
        this.onSpinerItemClick = onSpinerItemClick1
    }

    fun showSpinerDialog() {
        val adb = AlertDialog.Builder(context)
        val v = context.layoutInflater.inflate(R.layout.dialog_layout, null)

        val rippleViewClose = v.findViewById<TextView>(R.id.close)
        val title = v.findViewById<TextView>(R.id.spinerTitle)
        title.text = dTitle
        val listView = v.findViewById<ListView>(R.id.list)
        val searchBox = v.findViewById<EditText>(R.id.searchBox)
        val adapter = ArrayAdapter(context, R.layout.items_spinner_dialog, items)
        listView.adapter = adapter
        adb.setView(v)
        alertDialog = adb.create()
        alertDialog.window!!.attributes.windowAnimations = style//R.style.DialogAnimations_SmileWindow;
        alertDialog.setCancelable(false)

        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val t = view.findViewById<TextView>(R.id.text1)
            for (j in items.indices) {
                if (t.text.toString().equals(items[j].toString(), ignoreCase = true)) {
                    pos = j
                }
            }
            onSpinerItemClick.onClick(t.text.toString(), pos)
            alertDialog.dismiss()
        }

        searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                adapter.filter.filter(searchBox.text.toString())
            }
        })

        rippleViewClose.setOnClickListener { alertDialog.dismiss() }
        alertDialog.show()
    }
}