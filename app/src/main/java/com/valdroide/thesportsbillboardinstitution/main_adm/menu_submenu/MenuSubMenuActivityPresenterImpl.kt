package com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.events.MenuSubMenuActivityEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.MenuSubMenuActivityView
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import org.greenrobot.eventbus.Subscribe

class MenuSubMenuActivityPresenterImpl(val view: MenuSubMenuActivityView, val event: EventBus, val interactor: MenuSubMenuActivityInteractor) : MenuSubMenuActivityPresenter {
    

    override fun onCreate() {
        event.register(this)
    }

    override fun onDestroy() {
        event.unregister(this)
    }

    override fun getMenuSubMenu(context: Context) {
        view.showProgressBar()
        interactor.getMenuSubMenu(context)
    }

    override fun saveMenu(context: Context, menu: MenuDrawer) {
        view.showProgressBar()
        interactor.saveMenu(context, menu)
    }

    override fun updateMenu(context: Context, menu: MenuDrawer) {
        view.showProgressBar()
        interactor.updateMenu(context, menu)
    }

    override fun activeOrUnActiveMenu(context: Context, menu: MenuDrawer) {
        view.showProgressBar()
        interactor.activeOrUnActiveMenu(context, menu)
    }

    override fun deleteMenu(context: Context, menu: MenuDrawer) {
        view.showProgressBar()
        interactor.deleteMenu(context, menu)
    }
    override fun saveSubMenu(context: Context, subMenu: SubMenuDrawer) {
        view.showProgressBar()
        interactor.saveSubMenu(context, subMenu)
    }

    override fun updateSubMenu(context: Context, subMenu: SubMenuDrawer) {
        view.showProgressBar()
        interactor.updateSubMenu(context, subMenu)
    }

    override fun activeOrUnActiveSubMenu(context: Context, subMenu: SubMenuDrawer) {
        view.showProgressBar()
        interactor.activeOrUnActiveSubMenu(context, subMenu)
    }

    override fun deleteSubMenu(context: Context, subMenu: SubMenuDrawer) {
        view.showProgressBar()
        interactor.deleteSubMenu(context, subMenu)
    }
    
    @Subscribe
    override fun onEventMainThread(event: MenuSubMenuActivityEvent) {
        when (event.type) {
            MenuSubMenuActivityEvent.GETMENUSUBMENU-> {
                view.setMenuSubMenu(event.menuDrawers!!, event.subMenuDrawers!!)
                view.hideProgressBar()
            }
            MenuSubMenuActivityEvent.EVENTSUCCESS -> {
                view.hideProgressBar()
                view.validateAlert()
                view.eventSuccess(event.msg!!)
                view.refreshSpinners()
            }
//            MenuSubMenuActivityEvent.UPDATEMENU-> {
//                view.hideProgressBar()
//                view.validateAlert()
//                view.eventSuccess(event.msg!!)
//                view.refreshSpinners()
//            }

            MenuSubMenuActivityEvent.ERROR-> {
                view.hideProgressBar()
                view.validateAlert()
                view.setError(event.msg!!)
            }
        }
    }
}