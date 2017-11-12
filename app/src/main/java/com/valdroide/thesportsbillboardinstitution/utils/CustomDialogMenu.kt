package com.valdroide.thesportsbillboardinstitution.utils

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Button
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.TimeMatch
import kotlinx.android.synthetic.main.custom_dialog_menu.*

class CustomDialogMenu(private var context: Activity,
                       private var titles: Array<String>?,
                       private var listener: OnMenuItemClickListener) : Dialog(context), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_menu)
//        linearDialog.minimumWidth = 500
//        linearDialog.minimumHeight = 300


        val count = titles!!.size

        for (i in 1..count) {
            setVisibility(i)
        }
    }

    private fun setOnClickItem(btn: Button) {
        btn.setOnClickListener(this)
    }

    private fun setVisibility(i: Int) {
        when (i) {
            1 -> {
                btnUpdate.visibility = View.VISIBLE
                btnUpdate.text = titles!![0]
                setOnClickItem(btnUpdate)
            }
            2 -> {
                btnActive.visibility = View.VISIBLE
                btnActive.text = titles!![1]
                viewUpdate.visibility = View.VISIBLE
                setOnClickItem(btnActive)
            }
            3 -> {
                btnDelete.visibility = View.VISIBLE
                btnDelete.text = titles!![2]
                viewActive.visibility = View.VISIBLE
                setOnClickItem(btnDelete)
            }
            4 -> {
                btnOther.visibility = View.VISIBLE
                btnOther.text = titles!![3]
                viewDelete.visibility = View.VISIBLE
                setOnClickItem(btnOther)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btnUpdate -> {
                listener.onClick(1)
                dismiss()
            }
            btnActive -> {
                listener.onClick(2)
                dismiss()
            }
            btnDelete -> {
                listener.onClick(3)
                dismiss()
            }
            btnOther -> {
                listener.onClick(4)
                dismiss()
            }
        //  buttonCancel -> dismiss()
        }
    }

    class Builder(private var context: Activity) {

        private var listener: OnMenuItemClickListener? = null
        private var titles: Array<String>? = null


        fun withTitles(titles: Array<String>): Builder {
            this.titles = titles
            return this
        }

        fun setOnClick(listener: OnMenuItemClickListener): Builder {
            this.listener = listener
            return this
        }
        //original
        //fun show() = CustomDialog(context, isMenu, isUpdate, menu, menus, submenu, listener!!).show()

        fun getDialog(): CustomDialogMenu = CustomDialogMenu(context, titles, listener!!)
    }
}