package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.*

data class DataResponse(
        @SerializedName("response")
        @Expose
        var wsResponse: WSResponse,
        @SerializedName("date_data")
        @Expose
        var dateData: DateData,
        @SerializedName("account")
        @Expose
        var account: Account,
        @SerializedName("menu")
        @Expose
        var menu: List<MenuDrawer>,
        @SerializedName("submenu")
        @Expose
        var submenu: List<SubMenuDrawer>
)
