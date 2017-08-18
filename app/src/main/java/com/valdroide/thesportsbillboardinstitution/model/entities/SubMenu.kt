package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import com.valdroide.thesportsbillboardinstitution.db.SportsDataBase

@Table(database = SportsDataBase::class)
open class SubMenu : BaseModel() {
    @PrimaryKey
    @Column
    @SerializedName("id")
    var ID_SUBMENU_KEY: Int = 0
    @Column
    @SerializedName("sub_menu")
    var SUBMENU: String = ""
    @Column
    @SerializedName("id_menu")
    var ID_MENU_FOREIGN: Int = 0
    @SerializedName("is_active")
    var IS_ACTIVE: Int = 0
}