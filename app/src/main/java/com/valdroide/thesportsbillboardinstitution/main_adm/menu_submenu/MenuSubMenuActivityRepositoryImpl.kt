package com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.events.MenuSubMenuActivityEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.utils.Utils

class MenuSubMenuActivityRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : MenuSubMenuActivityRepository {

    private var response: WSResponse? = null
    private var menus: MutableList<MenuDrawer>? = null
    private var subMenus: MutableList<SubMenuDrawer>? = null

    override fun getMenuSubMenu(context: Context) {
        try {
            apiService.getMenusSubMenus()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    menus = result.menus
                                    subMenus = result.submenus
                                    post(MenuSubMenuActivityEvent.GETMENUSUBMENU, menus, subMenus)
                                } else {
                                    post(MenuSubMenuActivityEvent.ERROR, response!!.MESSAGE)
                                }
                            } else {
                                post(MenuSubMenuActivityEvent.ERROR, context.getString(
                                        R.string.null_response))
                            }
                        } else {
                            post(MenuSubMenuActivityEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(MenuSubMenuActivityEvent.ERROR, e.message!!)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(MenuSubMenuActivityEvent.ERROR, e.message!!)
        }
    }

    override fun saveMenu(context: Context, menu: MenuDrawer) {
        try {
            apiService.saveMenu(menu.MENU, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        response = result
                        if (response != null) {
                            if (response?.SUCCESS.equals("0")) {
                                post(MenuSubMenuActivityEvent.EVENTSUCCESS, context.getString(R.string.save_menu_success, "Menu"))
                            } else {
                                post(MenuSubMenuActivityEvent.ERROR, response?.MESSAGE!!)
                            }
                        } else {
                            post(MenuSubMenuActivityEvent.ERROR, context.getString(R.string.null_response))
                        }
                    }, { e ->
                        post(MenuSubMenuActivityEvent.ERROR, e.message!!)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(MenuSubMenuActivityEvent.ERROR, e.message!!)
        }
    }

    override fun updateMenu(context: Context, menu: MenuDrawer) {
        try {
            apiService.updateMenu(menu.ID_MENU_KEY, menu.MENU, menu.IS_ACTIVE, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        response = result
                        if (response != null) {
                            if (response?.SUCCESS.equals("0")) {
                                post(MenuSubMenuActivityEvent.EVENTSUCCESS, context.getString(R.string.update_menu_success, "Menu"))
                            } else {
                                post(MenuSubMenuActivityEvent.ERROR, response?.MESSAGE!!)
                            }
                        } else {
                            post(MenuSubMenuActivityEvent.ERROR, context.getString(R.string.null_response))
                        }
                    }, { e ->
                        post(MenuSubMenuActivityEvent.ERROR, e.message!!)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(MenuSubMenuActivityEvent.ERROR, e.message!!)
        }
    }

    override fun activeOrUnActiveMenu(context: Context, menu: MenuDrawer) {
        try {
            apiService.activeOrUnActiveMenu(menu.ID_MENU_KEY, menu.IS_ACTIVE, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(MenuSubMenuActivityEvent.EVENTSUCCESS, context.getString(R.string.update_menu_success, "Menu"))
                                } else {
                                    post(MenuSubMenuActivityEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(MenuSubMenuActivityEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(MenuSubMenuActivityEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(MenuSubMenuActivityEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(MenuSubMenuActivityEvent.ERROR, e.message)
        }
    }

    override fun deleteMenu(context: Context, menu: MenuDrawer) {
        try {
            apiService.deleteMenu(menu.ID_MENU_KEY, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(MenuSubMenuActivityEvent.EVENTSUCCESS, context.getString(R.string.delete_menu_success, "Menu"))
                                } else {
                                    post(MenuSubMenuActivityEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(MenuSubMenuActivityEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(MenuSubMenuActivityEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(MenuSubMenuActivityEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(MenuSubMenuActivityEvent.ERROR, e.message)
        }
    }

    override fun saveSubMenu(context: Context, subMenu: SubMenuDrawer) {
        try {
            apiService.saveSubMenu(subMenu.SUBMENU, subMenu.ID_MENU_FOREIGN, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        response = result
                        if (response != null) {
                            if (response?.SUCCESS.equals("0")) {
                                post(MenuSubMenuActivityEvent.EVENTSUCCESS, context.getString(R.string.save_menu_success, "SubMenu"))
                            } else {
                                post(MenuSubMenuActivityEvent.ERROR, response?.MESSAGE!!)
                            }
                        } else {
                            post(MenuSubMenuActivityEvent.ERROR, context.getString(R.string.null_response))
                        }
                    }, { e ->
                        post(MenuSubMenuActivityEvent.ERROR, e.message!!)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(MenuSubMenuActivityEvent.ERROR, e.message!!)
        }
    }

    override fun updateSubMenu(context: Context, subMenu: SubMenuDrawer) {
        try {
            apiService.updateSubMenu(subMenu.ID_SUBMENU_KEY, subMenu.SUBMENU, subMenu.ID_MENU_FOREIGN,
                    subMenu.IS_ACTIVE, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        response = result
                        if (response != null) {
                            if (response?.SUCCESS.equals("0")) {
                                post(MenuSubMenuActivityEvent.EVENTSUCCESS, context.getString(R.string.update_menu_success, "SubMenu"))
                            } else {
                                post(MenuSubMenuActivityEvent.ERROR, response?.MESSAGE!!)
                            }
                        } else {
                            post(MenuSubMenuActivityEvent.ERROR, context.getString(R.string.null_response))
                        }
                    }, { e ->
                        post(MenuSubMenuActivityEvent.ERROR, e.message!!)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(MenuSubMenuActivityEvent.ERROR, e.message!!)
        }
    }

    override fun activeOrUnActiveSubMenu(context: Context, subMenu: SubMenuDrawer) {
        try {
            apiService.activeOrUnActiveSubMenu(subMenu.ID_SUBMENU_KEY, subMenu.IS_ACTIVE, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(MenuSubMenuActivityEvent.EVENTSUCCESS, context.getString(R.string.update_menu_success, "SubMenu"))
                                } else {
                                    post(MenuSubMenuActivityEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(MenuSubMenuActivityEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(MenuSubMenuActivityEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(MenuSubMenuActivityEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(MenuSubMenuActivityEvent.ERROR, e.message)
        }
    }

    override fun deleteSubMenu(context: Context, subMenu: SubMenuDrawer) {
        try {
            apiService.deleteSubMenu(subMenu.ID_SUBMENU_KEY, 1, Utils.getFechaOficialSeparate())
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        if (result != null) {
                            response = result
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    post(MenuSubMenuActivityEvent.EVENTSUCCESS, context.getString(R.string.delete_menu_success, "SubMenu"))
                                } else {
                                    post(MenuSubMenuActivityEvent.ERROR, response?.MESSAGE)
                                }
                            } else {
                                post(MenuSubMenuActivityEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(MenuSubMenuActivityEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(MenuSubMenuActivityEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })
        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(MenuSubMenuActivityEvent.ERROR, e.message)
        }
    }

    fun post(types: Int) {
        post(types, null, null)
    }

    fun post(types: Int, msg: String?) {
        post(types, null, null, msg)
    }

    fun post(types: Int, memuDrawers: MutableList<MenuDrawer>?, subMenuDrawers: MutableList<SubMenuDrawer>?) {
        post(types, memuDrawers, subMenuDrawers, null)
    }

    fun post(types: Int, memuDrawers: MutableList<MenuDrawer>?, subMenuDrawers: MutableList<SubMenuDrawer>?, msg: String?) {
        val event = MenuSubMenuActivityEvent()
        event.type = types
        event.menuDrawers = memuDrawers
        event.subMenuDrawers = subMenuDrawers
        event.msg = msg
        eventBus.post(event)
    }
}