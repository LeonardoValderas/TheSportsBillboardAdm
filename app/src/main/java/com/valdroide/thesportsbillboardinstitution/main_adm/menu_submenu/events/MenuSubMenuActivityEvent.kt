package com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.events

import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

/**
 var b: String? = "abc" signific that accept null, can call NPE
 b = null // ok
 b?.lenght - > it means that validate if is null. It's same to if(b != null)
 b?.length ?: -1  -> short condition
 b!!.length doesn't realice a validation, so it can throw a NPE
 */

open class MenuSubMenuActivityEvent {
    var type: Int = 0
    var msg: String? = null
    var menuDrawer: MenuDrawer? = null
    var subMenuDrawer: SubMenuDrawer? = null
    var menuDrawers: MutableList<MenuDrawer>? = null
    var subMenuDrawers: MutableList<SubMenuDrawer>? = null
    companion object {
        const val GETMENUSUBMENU: Int = 0
        //const val SAVEMENU: Int = 1
        //const val UPDATEMENU: Int = 2
        const val EVENTSUCCESS: Int =  1
//        const val UPDATEMENU: Int = 2
//        const val UPDATESUBMENU: Int =  3
//        const val ACTIVEORUNACTIVEMENU: Int = 4
//        const val ACTIVEORUNACTIVESUBMENU: Int =  5
//        const val DELETEMENU: Int = 6
//        const val DELETESUBMENU: Int =  7
        const val ERROR: Int =  3

    }
}