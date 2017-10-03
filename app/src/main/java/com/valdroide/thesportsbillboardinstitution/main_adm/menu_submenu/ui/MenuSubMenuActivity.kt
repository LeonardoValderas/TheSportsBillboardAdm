package com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.TheSportsBillboardInstitutionApp
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.MenuSubMenuActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.di.MenuSubMenuActivityComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.adapter.MenuActivityAdapter
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.adapter.SubMenuActivityAdapter
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.dialog.CustomDialog
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.dialog.OnItemClickListener
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.activity_menu_submenu.*
import kotlinx.android.synthetic.main.activity_menu_submenu_content.*
import kotlinx.android.synthetic.main.toolbar_layout.*


open class MenuSubMenuActivity : AppCompatActivity(), MenuSubMenuActivityView, View.OnClickListener, OnItemClickListener {

    private lateinit var presenter: MenuSubMenuActivityPresenter
    private lateinit var component: MenuSubMenuActivityComponent
    private var isRegister: Boolean = false
    private var menuDrawer = MenuDrawer()
    private var subMenuDrawer = SubMenuDrawer()
    private lateinit var menuDrawers: MutableList<MenuDrawer>
    private lateinit var subMenuDrawers: MutableList<SubMenuDrawer>
    private lateinit var adapterSpinnerMenus: MenuActivityAdapter
    private lateinit var adapterSpinnerSubMenus: SubMenuActivityAdapter
    private var alert: CustomDialog? = null
    private var msg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_submenu)
        setupInjection()
        initToobar()
        register()
        initSpinnerAdapter()
        getMenuSubMenu()
        setOnclickButtons()
    }

    private fun setOnclickButtons() {
        fabCreateMenu.setOnClickListener(this)
        fabUpdateMenu.setOnClickListener(this)
        fabActiveMenu.setOnClickListener(this)
        fabDeleteMenu.setOnClickListener(this)
        fabCreateSubMenu.setOnClickListener(this)
        fabUpdateSubMenu.setOnClickListener(this)
        fabActiveSubMenu.setOnClickListener(this)
        fabDeleteSubMenu.setOnClickListener(this)
    }

    private fun getMenuSubMenu() {
        showProgressBar()
        presenter.getMenuSubMenu(this)
    }

    private fun initToobar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun setupInjection() {
        val app = application as TheSportsBillboardInstitutionApp
        app.firebaseAnalyticsInstance().setCurrentScreen(this, javaClass.simpleName, null)
        component = app.getMenuSubMenuActivityComponent(this, this)
        presenter = getPresenter()
        adapterSpinnerMenus = getAdapterMenus()
        adapterSpinnerSubMenus = getAdapterSubMenus()
    }

    open fun getPresenter(): MenuSubMenuActivityPresenter =
            component.getPresenter()

    private fun getAdapterMenus(): MenuActivityAdapter =
            component.getAdapterMenus()

    private fun getAdapterSubMenus(): SubMenuActivityAdapter =
            component.getAdapterSubMenus()

    override fun setMenuSubMenu(menuDrawers: MutableList<MenuDrawer>, subMenuDrawers: MutableList<SubMenuDrawer>) {
        this.menuDrawers = menuDrawers
        this.subMenuDrawers = subMenuDrawers
        adapterSpinnerMenus.refresh(menuDrawers)
        adapterSpinnerSubMenus.refresh(subMenuDrawers)
    }

    override fun onClick(v: View?) {
        when (v) {
            fabCreateMenu ->
                saveMenuAlert()
            fabUpdateMenu ->
                updateMenuAlert()
            fabActiveMenu -> {
                if (menuDrawers.isNotEmpty()) {
                    if (menuDrawer.IS_ACTIVE == 0)
                        msg = getString(R.string.active_menu_alerte_msg, "activar", "menu")
                    else
                        msg = getString(R.string.active_menu_alerte_msg, "desactivar", "menu")
                    showAlertDialog(getString(R.string.alert_title), msg, menuDrawer, null, true, false)
                } else
                    setError(getString(R.string.menus_empty_submenu))
            }
            fabDeleteMenu -> {
                if (menuDrawers.isNotEmpty())
                    showAlertDialog(getString(R.string.alert_title), getString(R.string.delete_menu_alerte_msg, "menu"), menuDrawer, null, true, true)
                else
                    setError(getString(R.string.menus_empty_submenu))
            }
            fabCreateSubMenu ->
                saveSubMenuAlert()
            fabUpdateSubMenu ->
                updateSubMenuAlert()
            fabActiveSubMenu -> {
                if (subMenuDrawers.isNotEmpty()) {
                    if (subMenuDrawer.IS_ACTIVE == 0)
                        msg = getString(R.string.active_menu_alerte_msg, "activar", "submenu")
                    else
                        msg = getString(R.string.active_menu_alerte_msg, "desactivar", "submenu")
                    showAlertDialog(getString(R.string.alert_title), msg, null, subMenuDrawer, false, false)
                } else
                    setError(getString(R.string.submenu_empty))
            }
            fabDeleteSubMenu -> {
                if (subMenuDrawers.isNotEmpty())
                    showAlertDialog(getString(R.string.alert_title), getString(R.string.delete_menu_alerte_msg, "submenu"), null, subMenuDrawer, false, true)
                else
                    setError(getString(R.string.submenu_empty))
            }
        }
    }

    private fun initSpinnerAdapter() {
        spinnerMenu.adapter = adapterSpinnerMenus
        spinnerMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                menuDrawer = menuDrawers[pos]
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }

        spinnerSubMenu.adapter = adapterSpinnerSubMenus
        spinnerSubMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                subMenuDrawer = subMenuDrawers[pos]
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
            }
        }
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

    private fun saveMenuAlert() {
        alert = CustomDialog.Builder(this).isMenu(true).isUpdate(false).setOnClick(this).getDialog()
        alert!!.show()
    }

    private fun updateMenuAlert() {
        if (menuDrawers.isEmpty()) {
            setError(getString(R.string.menus_empty_submenu))
        } else {
            alert = CustomDialog.Builder(this).isMenu(true).isUpdate(true).withMenu(menuDrawer).setOnClick(this).getDialog()
            alert!!.show()
        }
    }

    override fun menuSaveSuccess() {
        Utils.showSnackBar(conteiner, getString(R.string.save_menu_success))
    }

    override fun eventSuccess(msg: String) {
        Utils.showSnackBar(conteiner, msg)
    }

    private fun saveSubMenuAlert() {
        if (menuDrawers.isEmpty())
            setError(getString(R.string.menus_empty_submenu))
        else {
            alert = CustomDialog.Builder(this).isMenu(false).isUpdate(false).withSubMenu(menuDrawers, subMenuDrawer).setOnClick(this).getDialog()
            alert!!.show()
        }
    }

    private fun updateSubMenuAlert() {
        if (menuDrawers.isEmpty())
            setError(getString(R.string.menus_empty_submenu))
        else if (subMenuDrawers.isEmpty())
            setError(getString(R.string.submenu_empty))
        else {
            alert = CustomDialog.Builder(this).isMenu(false).isUpdate(true).withSubMenu(menuDrawers, subMenuDrawer).setOnClick(this).getDialog()
            alert!!.show()
        }
    }

    fun showAlertDialog(title: String, msg: String, menu: MenuDrawer?, subMenu: SubMenuDrawer?, isMenu: Boolean, isDelete: Boolean) {
        val alertDilog = AlertDialog.Builder(this).create()
        alertDilog.setTitle(title)
        alertDilog.setMessage(msg)

        alertDilog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", { dialogInterface, i ->
            showProgressBar()
            if (isMenu) {
                if (!isDelete) {
                    menuDrawer.IS_ACTIVE = if (menuDrawer.IS_ACTIVE == 0) 1 else 0
                    presenter.activeOrUnActiveMenu(this, menuDrawer)
                } else
                    presenter.deleteMenu(this, menuDrawer)
            } else {
                if (!isDelete) {
                    subMenuDrawer.IS_ACTIVE = if (subMenuDrawer.IS_ACTIVE == 0) 1 else 0
                    presenter.activeOrUnActiveSubMenu(this, subMenuDrawer)
                } else
                    presenter.deleteSubMenu(this, subMenuDrawer)
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

    fun register() {
        if (!isRegister) {
            presenter.onCreate()
            isRegister = true
        }
    }

    fun unregister() {
        if (isRegister) {
            presenter.onDestroy()
            isRegister = false
        }
    }

    override fun onPause() {
        super.onPause()
        unregister()
//        mAd.pause(this)
//        if (mAdView != null) {
//            mAdView.pause()
//        }
    }

    override fun onResume() {
        super.onResume()
        register()
//        mAd.resume(this)
//        if (mAdView != null) {
//            mAdView.resume()
//        }
    }

    override fun onStart() {
        super.onStart()
        register()
    }

    override fun onStop() {
        super.onStop()
        unregister()
    }


    override fun onDestroy() {
        unregister()
        super.onDestroy()
    }
}