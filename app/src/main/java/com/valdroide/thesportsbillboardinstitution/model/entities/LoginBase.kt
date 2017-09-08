package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel
import com.valdroide.thesportsbillboardinstitution.db.SportsDataBase

@Table(database = SportsDataBase::class)
open class LoginBase(@PrimaryKey
                 @Column
                 @Expose
                 @SerializedName("id")
                 var ID_LOGIN_KEY: Int =0,
                 @Column
                 @SerializedName("user")
                 @Expose
                 var USER: String ="",
                 @Column
                 @SerializedName("user")
                 @Expose
                 var PASS: String ="",
                 @Column
                 @SerializedName("type")
                 @Expose
                 var TYPE_ADM: Int=0) : BaseRXModel()