package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class SubMenuDrawer(@SerializedName("id")
                   @Expose
                   var ID_SUBMENU_KEY: Int,
                   @SerializedName("sub_menu")
                   @Expose
                   var SUBMENU: String,
                   @SerializedName("id_menu")
                   @Expose
                   var ID_MENU_FOREIGN: Int,
                   @SerializedName("is_active")
                   @Expose
                   var IS_ACTIVE: Int)