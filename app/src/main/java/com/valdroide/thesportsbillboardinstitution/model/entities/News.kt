package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class News(@SerializedName("id")
                @Expose
                var ID_NEWS_KEY: Int,
                @SerializedName("title")
                @Expose
                var TITLE: String,
                @SerializedName("description")
                @Expose
                var DESCRIPTION: String,
                @SerializedName("date_news")
                @Expose
                var DATE_NEWS: String,
                @SerializedName("name_image")
                @Expose
                var NAME_IMAGE: String,
                @SerializedName("url_image")
                @Expose
                var URL_IMAGE: String,
                @SerializedName("id_submenu")
                @Expose
                var ID_SUB_MENU: Int)