package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import com.valdroide.thesportsbillboardinstitution.db.SportsDataBase

@Table(database = SportsDataBase::class)
open class Login : BaseModel() {
    @PrimaryKey
    @Column
    @SerializedName("id")
    var ID_LOGIN_KEY: Int = 0
    @Column
    @SerializedName("user")
    var USER: String = ""
    @Column
    @SerializedName("user")
    var PASS: String = ""
    @Column
    @SerializedName("type")
    var TYPE_ADM: Int = 1
}