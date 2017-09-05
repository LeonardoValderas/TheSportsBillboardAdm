package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Account(
        @SerializedName("id")
        @Expose
        var ID_ACCOUNT_KEY: Int,
        @SerializedName("name")
        @Expose
        var NAME: String,
        @SerializedName("description")
        @Expose
        var DESCRIPTION: String,
        @SerializedName("address")
        @Expose
        var ADDRESS: String,
        @SerializedName("phone")
        @Expose
        var PHONE: String,
        @SerializedName("facebook")
        @Expose
        var FACEBOOK: String,
        @SerializedName("instagram")
        @Expose
        var INSTAGRAM: String,
        @SerializedName("web")
        @Expose
        var WEB: String,
        @SerializedName("email")
        @Expose
        var EMAIL: String,
        @SerializedName("url_image")
        @Expose
        var URL_IMAGE: String)