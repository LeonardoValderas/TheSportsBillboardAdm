package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import com.valdroide.thesportsbillboardinstitution.model.entities.DateData
import com.valdroide.thesportsbillboardinstitution.model.entities.Menu
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenu
import javax.annotation.Generated


/*
class DataResponse {
    @SerializedName("response")
    @Expose
    val wsResponse: WSResponse? = null
    @SerializedName("menu")
    @Expose
    lateinit var menu: Menu
    @SerializedName("submenu")
    @Expose
    lateinit var submenu: SubMenu
    @SerializedName("account")
    @Expose
    lateinit var account: Account
}
*/
//@Generated("org.jsonschema2pojo")

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
        var menu: List<Menu>,
        @SerializedName("submenu")
        @Expose
        var submenu: List<SubMenu>


)
