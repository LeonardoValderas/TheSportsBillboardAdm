package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import com.valdroide.thesportsbillboardinstitution.db.SportsDataBase

@Table(database = SportsDataBase::class)
open class DateData : BaseModel() {
    @PrimaryKey
    @Column
    @SerializedName("id")
    var ID_DATE_DATA_KEY: Int = 0
    @Column
    @SerializedName("account_date")
    var ACCOUNT_DATE: String = ""
    @Column
    @SerializedName("menu_date")
    var MENU_DATE: String = ""
    @Column
    @SerializedName("submenu_date")
    var SUBMENU_DATE: String = ""
    @Column
    @SerializedName("unique_date")
    var UNIQUE_DATE: String = ""
    /*
    @Column
    @SerializedName("description")
    var ACCOUNT_DATE: String = ""
    @Column
    @SerializedName("address")
    var ADDRESS: String = ""
    @Column
    @SerializedName("phone")
    var PHONE: String = ""
    @Column
    @SerializedName("facebook")
    var FACEBOOK: String = ""
    @Column
    @SerializedName("instagram")
    var INSTAGRAM: String = ""
    */
}