package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Team(
        @SerializedName("id")
        @Expose
        var ID_TEAM_KEY: Int = 0,
        @SerializedName("name")
        @Expose
        var NAME: String = "",
        @SerializedName("is_active")
        @Expose
        var IS_ACTIVE: Int = 0,
        @SerializedName("url_image")
        @Expose
        var URL_IMAGE: String = "",
        @SerializedName("name_image")
        @Expose
        var NAME_IMAGE: String = "",
        @SerializedName("encode")
        @Expose
        var ENCODE: String = "",
        @SerializedName("before")
        @Expose
        var BEFORE: String = "")