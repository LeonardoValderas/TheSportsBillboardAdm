package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class SubMenuDrawer(@SerializedName("id")
                         @Expose
                         var ID_SUBMENU_KEY: Int = 0,
                         @SerializedName("sub_menu")
                         @Expose
                         var SUBMENU: String = "",
                         @SerializedName("id_menu")
                         @Expose
                         var ID_MENU_FOREIGN: Int = 0,
                         @SerializedName("menu")
                         @Expose
                         var MENU: String = "",
                         @SerializedName("is_active")
                         @Expose
                         var IS_ACTIVE: Int = 0) {
    override fun toString(): String = MENU.plus(" - ").plus(SUBMENU).plus(if(IS_ACTIVE == 0) " (No Activo)" else " (Activo)")
}