package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class News(@SerializedName("id")
                @Expose
                var ID_NEWS_KEY: Int = 0,
                @SerializedName("title")
                @Expose
                var TITLE: String = "",
                @SerializedName("description")
                @Expose
                var DESCRIPTION: String = "",
                @SerializedName("date_news")
                @Expose
                var DATE_NEWS: String = "",
                @SerializedName("name_image")
                @Expose
                var NAME_IMAGE: String = "",
                @SerializedName("url_image")
                @Expose
                var URL_IMAGE: String = "",
                @SerializedName("is_active")
                @Expose
                var IS_ACTIVE: Int = 0,
                @SerializedName("id_submenu")
                @Expose
                var ID_SUB_MENU: Int = 0,
                @SerializedName("submenu")
                @Expose
                var SUBMENU: String = "",
                @SerializedName("menu")
                @Expose
                var MENU: String = "",
                @SerializedName("encode")
                @Expose
                var ENCODE: String = "",
                @SerializedName("before")
                @Expose
                var BEFORE: String = "")