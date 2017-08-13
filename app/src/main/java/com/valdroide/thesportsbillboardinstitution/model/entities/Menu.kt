package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import com.valdroide.thesportsbillboardinstitution.db.SportsDataBase

@Table(database = SportsDataBase::class)
open class Menu : BaseModel() {
    @PrimaryKey
    @Column
    @SerializedName("id")
    var ID_MENU_KEY: Int = 0
    @Column
    @SerializedName("menu")
    var MENU: String = ""
}