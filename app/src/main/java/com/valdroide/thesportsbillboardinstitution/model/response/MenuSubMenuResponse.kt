package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

data class MenuSubMenuResponse(
        @SerializedName("response")
        @Expose
        var wsResponse: WSResponse?,
        @SerializedName("menu")
        @Expose
        var menus: MutableList<MenuDrawer>?,
        @SerializedName("submenu")
        @Expose
        var submenus: MutableList<SubMenuDrawer>?
)