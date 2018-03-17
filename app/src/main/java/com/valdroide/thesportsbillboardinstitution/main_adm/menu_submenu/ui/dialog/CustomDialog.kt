package com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.View
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.generics.OnSpinerItemClick
import com.valdroide.thesportsbillboardinstitution.utils.generics.SpinnerDialog
import kotlinx.android.synthetic.main.custom_dialog.*


class CustomDialog(private var context: Activity, private var isMenu: Boolean, private var isUpdate: Boolean,
                   private var menu: MenuDrawer?, private var menus: MutableList<MenuDrawer>?,
                   private var submenu: SubMenuDrawer?, private var listener: OnItemClickListener) : Dialog(context), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog)
        buttonSave.setOnClickListener(this)
        buttonCancel.setOnClickListener(this)

        if (isMenu) {
            linearDialog.minimumWidth = 500
            linearDialog.minimumHeight = 250
            conteinerMenu.visibility = View.VISIBLE
            if (isUpdate)
                editTextMenu.text = Editable.Factory.getInstance().newEditable(menu!!.MENU)
        } else {
            linearDialog.minimumWidth = 800
            linearDialog.minimumHeight = 350
            conteinerSubMenu.visibility = View.VISIBLE
            btnSpinnerMenu.text = "Menu"
            btnSpinnerMenu.setOnClickListener { dialogSpinnerMenu()}

            if (isUpdate) {
                menu = getMenuPositionSpinner(submenu!!.ID_MENU_FOREIGN)
                if(menu == null)
                    return
                else
                btnSpinnerMenu.text = menu.toString()
                editTextSubMenu.text = Editable.Factory.getInstance().newEditable(submenu!!.SUBMENU)
            }
        }
    }

    private fun dialogSpinnerMenu() {
        val spinnerDialog = SpinnerDialog(context, menus!!, "Seleccione un menu", R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                menu = menus!![position]
                btnSpinnerMenu.text = item
                tv_error_menu.visibility = View.GONE
            }
        })

        spinnerDialog.showSpinerDialog()
    }
    private fun getMenuPositionSpinner(id_menu: Int): MenuDrawer? {
        for (i in 0 until menus!!.size) {
            if (menus!![i].ID_MENU_KEY == id_menu) {
                return menus!![i]
            }
        }
        return null
    }

    override fun onClick(v: View?) {
        when (v) {
            buttonSave -> {
                //MENU
                if (isMenu) {
                    if (editTextMenu.text.isEmpty()) {
                        editTextMenu.error = context.getString(R.string.add_menu_error_empty, "menu.")
                    } else {
                        if (!isUpdate) {
                            val menuSave = MenuDrawer()
                            menuSave.MENU = editTextMenu.text.toString()
                            listener.onClickSaveMenu(context, menuSave)
                        } else {
                            menu!!.MENU = editTextMenu.text.toString()
                            listener.onClickUpdateMenu(context, menu!!)
                        }
                    }
                } else {
//                //SUBMENU
                    if (editTextSubMenu.text.isEmpty())
                        editTextSubMenu.error = context.getString(R.string.add_menu_error_empty, "submenu.")
                    else if(menu == null)
                        tv_error_menu.visibility = View.VISIBLE
                     else {
                        if (!isUpdate) {
                            val submenuAux = SubMenuDrawer()
                            with(submenuAux) {
                                ID_MENU_FOREIGN = menu!!.ID_MENU_KEY
                                SUBMENU = editTextSubMenu.text.toString()
                            }
                            listener.onClickSaveSubMenu(context, submenuAux)
                        } else {
                            with(submenu!!){
                                ID_MENU_FOREIGN = menu!!.ID_MENU_KEY
                                SUBMENU = editTextSubMenu.text.toString()
                            }
                            listener.onClickUpdateSubMenu(context, submenu!!)
                        }
                    }
                }
            }
            buttonCancel -> dismiss()
        }
    }

    class Builder(private var context: Activity) {

        private var isMenu: Boolean = true
        private var isUpdate: Boolean = false
        private var menu: MenuDrawer? = null
        private var submenu: SubMenuDrawer? = null
        private var listener: OnItemClickListener? = null
        private var menus: MutableList<MenuDrawer>? = null

        fun isMenu(isMenu: Boolean): Builder {
            this.isMenu = isMenu
            return this
        }

        fun isUpdate(isUpdate: Boolean): Builder {
            this.isUpdate = isUpdate
            return this
        }

        fun withMenu(menu: MenuDrawer): Builder {
            this.menu = menu
            return this
        }

        fun withSubMenu(menus: MutableList<MenuDrawer>, submenu: SubMenuDrawer?): Builder {
            this.menus = menus
            this.submenu = submenu
            return this
        }

        fun setOnClick(listener: OnItemClickListener): Builder {
            this.listener = listener
            return this
        }
        //original
        //fun show() = CustomDialog(context, isMenu, isUpdate, menu, menus, submenu, listener!!).show()

        fun getDialog(): CustomDialog = CustomDialog(context, isMenu, isUpdate, menu, menus, submenu, listener!!)
    }
}