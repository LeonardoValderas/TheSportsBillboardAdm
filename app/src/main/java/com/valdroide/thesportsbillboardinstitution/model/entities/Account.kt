package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import com.valdroide.thesportsbillboardinstitution.db.SportsDataBase

@Table(database = SportsDataBase::class)
open class Account : BaseModel() {
    @PrimaryKey
    @Column
    @SerializedName("id")
    var ID_ACCOUNT_KEY: Int = 0
    @Column
    @SerializedName("name")
    var NAME: String = ""
    @Column
    @SerializedName("description")
    var DESCRIPTION: String = ""
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
    @Column
    @SerializedName("web")
    var WEB: String = ""
    @Column
    @SerializedName("email")
    var EMAIL: String = ""
    @Column
    @SerializedName("url_image")
    var URL_IMAGE: String = ""
}