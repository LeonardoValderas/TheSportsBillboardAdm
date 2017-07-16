package com.valdroide.thesportsbillboardadm.entities

import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import com.valdroide.thesportsbillboardadm.db.SportsDataBase

@Table(database = SportsDataBase::class)
class Login : BaseModel() {
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
}