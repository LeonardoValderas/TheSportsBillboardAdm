package com.valdroide.thesportsbillboardinstitution.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.View
import com.valdroide.thesportsbillboardinstitution.R
import kotlinx.android.synthetic.main.custom_dialog.*

abstract class GenericDialogEditText<T>(context: Context,
                                        private val hint: String,
                                        private var isUpdate: Boolean,
                                        private var t: T?,
                                        private var listener: OnItemClickListenerDialog<T>) :
        Dialog(context), View.OnClickListener {

    protected var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_edit_text)
        buttonSave.setOnClickListener(this)
        buttonCancel.setOnClickListener(this)
        linearDialog.minimumWidth = 500
        linearDialog.minimumHeight = 250
        textInputLayoutMenu.hint = hint
        if (isUpdate)
            editTextMenu.text = Editable.Factory.getInstance().newEditable(getString())
    }

    override fun onClick(v: View?) {
        when (v) {
            buttonSave -> {
                if (editTextMenu.text.isEmpty()) {
                    editTextMenu.error = context.getString(R.string.add_menu_error_empty, "menu.")
                } else {
                    if (!isUpdate) {
                        listener.onClickSave(setEntity(editTextMenu.text.toString()))
                    } else {
                        listener.onClickUpdate(setEntity(editTextMenu.text.toString()))
                    }
                    dismiss()
                }
            }
            buttonCancel -> dismiss()
        }
    }


    protected abstract fun getString() : String
    protected abstract fun setEntity(text:String) : T

/*

    class Builder<T>(private var context: Activity) {

        private var isUpdate: Boolean = false
        private var t: T? = null
        private var listener: OnItemClickListenerDialog<T>? = null

        fun isUpdate(isUpdate: Boolean): Builder<T> {
            this.isUpdate = isUpdate
            return this
        }

        fun withEmtitty(t: T): Builder<T> {
            this.t = t
            return this
        }

        fun setOnClick(listener: OnItemClickListenerDialog<T>): Builder<T> {
            this.listener = listener
            return this
        }
        //original
        fun show() = GenericDialogEditText(context, isUpdate, t, listener!!).show()

    //    fun getDialog(): GenericDialogEditText = GenericDialogEditText(context, isMenu, isUpdate, menu, menus, submenu, listener!!)
    }
    */
}