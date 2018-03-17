package com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.MenuSubMenuActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.dialog.CustomDialog
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.dialog.OnItemClickListener
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import com.valdroide.thesportsbillboardinstitution.utils.base.BaseActivity
import com.valdroide.thesportsbillboardinstitution.utils.generics.OnSpinerItemClick
import com.valdroide.thesportsbillboardinstitution.utils.generics.SpinnerDialog
import kotlinx.android.synthetic.main.activity_menu_submenu.*
import kotlinx.android.synthetic.main.activity_menu_submenu_content.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import javax.inject.Inject


open class MenuSubMenuActivity : BaseActivity(), MenuSubMenuActivityView, View.OnClickListener, OnItemClickListener {
    @Inject
    lateinit var presenter: MenuSubMenuActivityPresenter
    private var menuDrawer: MenuDrawer? = null
    private var subMenuDrawer: SubMenuDrawer? = null
    private lateinit var menuDrawers: MutableList<MenuDrawer>
    private lateinit var subMenuDrawers: MutableList<SubMenuDrawer>
    private var alert: CustomDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToobar()
        getMenuSubMenu()
        setOnclickButtons()
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_menu_submenu

    override fun validateEvenBusRegisterForLifeCycle(isRegister: Boolean) {
        if (isRegister)
            presenter.onCreate()
        else
            presenter.onDestroy()
    }

    private fun setOnclickButtons() {
        setOnClickView(btnMenu)
        setOnClickView(btnSubMenu)
        setOnClickView(fabCreateMenu)
        setOnClickView(fabUpdateMenu)
        setOnClickView(fabActiveMenu)
        setOnClickView(fabDeleteMenu)
        setOnClickView(fabCreateSubMenu)
        setOnClickView(fabUpdateSubMenu)
        setOnClickView(fabActiveSubMenu)
        setOnClickView(fabDeleteSubMenu)
    }

    private fun setOnClickView(view: View) {
        view.setOnClickListener(this)
    }

    private fun getMenuSubMenu() {
        presenter.getMenuSubMenu(this)
    }

    private fun initToobar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun setupInjection() {
        val app = application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(this, javaClass.simpleName, null)
        app.getMenuSubMenuActivityComponent(this, this).inject(this)
    }

    override fun setMenuSubMenu(menuDrawers: MutableList<MenuDrawer>, subMenuDrawers: MutableList<SubMenuDrawer>) {
        this.menuDrawers = menuDrawers
        this.subMenuDrawers = subMenuDrawers
        validateSpinners()
    }

    override fun onClick(v: View?) {
        if (v == fabCreateMenu) {
            saveMenuSubMenuOnClick(true)
            return
        } else
            if (!menuListAny()) {
                errorMenuSubMenuEmpty(true)
                return
            }
        when (v) {
            btnMenu -> onClickMenuSubmenu(true)
            fabCreateMenu -> saveMenuSubMenuOnClick(true)
            fabUpdateMenu -> updateMenuSubMenuOnClick(true)
            fabActiveMenu -> activeAndDeleteMenuOnClick(false)
            fabDeleteMenu -> activeAndDeleteMenuOnClick(true)

            btnSubMenu -> onClickMenuSubmenu(false)
            fabCreateSubMenu -> saveMenuSubMenuOnClick(false)
            fabUpdateSubMenu -> updateMenuSubMenuOnClick(false)
            fabActiveSubMenu -> activeAndDeleteSubMenuOnClick(false)
            fabDeleteSubMenu -> activeAndDeleteSubMenuOnClick(true)
        }
    }

    private fun onClickMenuSubmenu(isMenu: Boolean) {
        if (isMenu)
            dialogSpinnerMenu()
        else
            if (subMenuListAny()) dialogSpinnerSubMenu() else setError(getString(R.string.submenu_empty_error))
    }

    private fun menuListAny(): Boolean = menuDrawers.any()

    private fun subMenuListAny(): Boolean = subMenuDrawers.any()

    private fun activeAndDeleteMenuOnClick(isDelete: Boolean) {
        if (menuDrawer == null) {
            setError(getString(R.string.select_menu))
            return
        }
        if (isDelete)
            showAlertDialog(getString(R.string.alert_title), getString(R.string.delete_menu_alerte_msg, "menu"), menuDrawer, null, true, true)
        else
            showAlertDialog(getString(R.string.alert_title), returnMessangeActive(true), menuDrawer, null, true, false)
    }

    private fun activeAndDeleteSubMenuOnClick(isDelete: Boolean) {
        if (subMenuDrawer == null) {
            setError(getString(R.string.select_submenu))
            return
        }

        if (isDelete)
            showAlertDialog(getString(R.string.alert_title), getString(R.string.delete_menu_alerte_msg, "submenu"), null, subMenuDrawer, false, true)
        else
            showAlertDialog(getString(R.string.alert_title), returnMessangeActive(false), null, subMenuDrawer, false, false)
    }

    private fun returnMessangeActive(isMenu: Boolean): String {
        if (isMenu) {
            if (menuDrawer!!.IS_ACTIVE == 0)
                return getString(R.string.active_menu_alerte_msg, "activar", "menu")
            else
                return getString(R.string.active_menu_alerte_msg, "desactivar", "menu")
        } else {
            if (subMenuDrawer!!.IS_ACTIVE == 0)
                return getString(R.string.active_menu_alerte_msg, "activar", "sub menu")
            else
                return getString(R.string.active_menu_alerte_msg, "desactivar", "sub menu")
        }
    }

    private fun validateSpinners() {
        menuDrawer = null
        subMenuDrawer = null

        if (!menuDrawers.any())
            btnMenu.text = "Menu"
        else
            btnMenu.text = getString(R.string.select_menu)

        if (!subMenuDrawers.any())
            btnSubMenu.text = "Sub Menu"
        else
            btnSubMenu.text = getString(R.string.select_submenu)
    }

    private fun dialogSpinnerMenu() {
        val spinnerDialog = SpinnerDialog(this@MenuSubMenuActivity, menuDrawers, "Seleccione un menu", R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                menuDrawer = menuDrawers[position]
                btnMenu.text = item
            }
        })

        spinnerDialog.showSpinerDialog()
    }

    private fun dialogSpinnerSubMenu() {
        val spinnerDialog = SpinnerDialog(this@MenuSubMenuActivity, subMenuDrawers, "Seleccione un sub menu", R.style.DialogAnimations_SmileWindow)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                subMenuDrawer = subMenuDrawers[position]
                btnSubMenu.text = subMenuDrawer.toString()
            }
        })

        spinnerDialog.showSpinerDialog()
    }

    override fun setError(error: String) {
        Utils.showSnackBar(conteiner, error)
    }

    override fun validateAlert() {
        if (alert != null)
            alert!!.dismiss()
    }

    override fun onClickSaveMenu(context: Context, menu: MenuDrawer) {
        presenter.saveMenu(context, menu)
    }

    override fun onClickUpdateMenu(context: Context, menu: MenuDrawer) {
        presenter.updateMenu(context, menu)
    }

    override fun onClickSaveSubMenu(context: Context, submenu: SubMenuDrawer) {
        presenter.saveSubMenu(context, submenu)
    }

    override fun onClickUpdateSubMenu(context: Context, submenu: SubMenuDrawer) {
        presenter.updateSubMenu(context, submenu)
    }

    private fun saveMenuSubMenuOnClick(isMenu: Boolean) {
        if (isMenu) {
            alert = CustomDialog.Builder(this).isMenu(true).isUpdate(false).setOnClick(this).getDialog()
            alert!!.show()
        } else {
            alert = CustomDialog.Builder(this).isMenu(false).isUpdate(false).withSubMenu(menuDrawers, subMenuDrawer).setOnClick(this).getDialog()
            alert!!.show()
        }
    }

    private fun updateMenuSubMenuOnClick(isMenu: Boolean) {
        if (isMenu) {
            if (menuDrawer == null)
                setError(getString(R.string.select_menu))
            else {
                alert = CustomDialog.Builder(this).isMenu(true).isUpdate(true).withMenu(menuDrawer!!).setOnClick(this).getDialog()
                alert!!.show()
            }
        } else {
            if (!subMenuListAny())
                errorMenuSubMenuEmpty(false)
            else if (subMenuDrawer == null)
                setError(getString(R.string.select_submenu))
            else {
                alert = CustomDialog.Builder(this).isMenu(false).isUpdate(true).withSubMenu(menuDrawers, subMenuDrawer!!).setOnClick(this).getDialog()
                alert!!.show()
            }
        }
    }

    private fun errorMenuSubMenuEmpty(isMenu: Boolean) {
        if (isMenu)
            setError(getString(R.string.spinner_empty, "un menu"))
        else
            setError(getString(R.string.spinner_empty, "un sub menu"))
    }

    override fun menuSaveSuccess() {
        Utils.showSnackBar(conteiner, getString(R.string.save_success, "Menu", "o"))
    }

    override fun eventSuccess(msg: String) {
        Utils.showSnackBar(conteiner, msg)
    }

    private fun showAlertDialog(title: String,
                                msg: String,
                                menu: MenuDrawer?,
                                subMenu: SubMenuDrawer?,
                                isMenu: Boolean,
                                isDelete: Boolean) {

        val alertDilog = AlertDialog.Builder(this).create()
        alertDilog.setTitle(title)
        alertDilog.setMessage(msg)

        alertDilog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", { dialogInterface, i ->
            if (isMenu) {
                if (!isDelete) {
                    menuDrawer!!.IS_ACTIVE = if (menuDrawer!!.IS_ACTIVE == 0) 1 else 0
                    presenter.activeOrUnActiveMenu(this, menu!!)
                } else
                    presenter.deleteMenu(this, menu!!)
            } else {
                if (!isDelete) {
                    subMenuDrawer!!.IS_ACTIVE = if (subMenuDrawer!!.IS_ACTIVE == 0) 1 else 0
                    presenter.activeOrUnActiveSubMenu(this, subMenu!!)
                } else
                    presenter.deleteSubMenu(this, subMenu!!)
            }
        })

        alertDilog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCELAR", { dialogInterface, j ->
            alertDilog.dismiss()
        })
        alertDilog.show()
    }

    override fun refreshSpinners() {
        getMenuSubMenu()
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
        conteinerContent.visibility = View.VISIBLE
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        conteinerContent.visibility = View.INVISIBLE
    }
}