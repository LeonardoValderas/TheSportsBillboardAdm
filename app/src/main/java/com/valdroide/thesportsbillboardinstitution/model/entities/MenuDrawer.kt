package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MenuDrawer(@SerializedName("id")
                      @Expose
                      var ID_MENU_KEY: Int = 0,
                      @SerializedName("menu")
                      @Expose
                      var MENU: String = "",
                      @SerializedName("is_active")
                      @Expose
                      var IS_ACTIVE: Int = 0) {
    override fun toString(): String = MENU
}
