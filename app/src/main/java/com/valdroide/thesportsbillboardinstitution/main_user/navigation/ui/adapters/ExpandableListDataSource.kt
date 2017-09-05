package com.valdroide.thesportsbillboardinstitution.main_user.navigation.ui.adapters



import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import java.util.ArrayList
import java.util.TreeMap

object ExpandableListDataSource {
    fun getData(menus: List<MenuDrawer>, submenus: List<SubMenuDrawer>): Map<String, List<SubMenuDrawer>> {
        val expandableListData = TreeMap<String, List<SubMenuDrawer>>()

        for (i in menus.indices) {
            val subMenuAux = ArrayList<SubMenuDrawer>()
            for (j in submenus.indices) {
                if (menus[i].ID_MENU_KEY == submenus[j].ID_MENU_FOREIGN)
                    subMenuAux.add(submenus[j])
            }
            expandableListData.put(menus[i].MENU, subMenuAux)
        }
        return expandableListData
    }
}
