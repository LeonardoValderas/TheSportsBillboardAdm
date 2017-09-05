package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MenuDrawer(@SerializedName("id")
                      @Expose
                      var ID_MENU_KEY: Int,
                      @SerializedName("menu")
                      @Expose
                      var MENU: String,
                      @SerializedName("is_active")
                      @Expose
                      var IS_ACTIVE: Int)