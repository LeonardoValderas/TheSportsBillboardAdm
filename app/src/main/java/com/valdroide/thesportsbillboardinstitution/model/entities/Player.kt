package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Player(@SerializedName("id")
                  @Expose
                  var ID_PLAYER_KEY: Int = 0,
                  @SerializedName("name")
                  @Expose
                  var NAME: String = "",
                  @SerializedName("id_position")
                  @Expose
                  var ID_POSITION: Int = 0,
                  @SerializedName("position")
                  @Expose
                  var POSITION: String = "",
                  @SerializedName("name_image")
                  @Expose
                  var NAME_IMAGE: String = "",
                  @SerializedName("url_image")
                  @Expose
                  var URL_IMAGE: String = "",
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
                  var BEFORE: String = "",
                  @SerializedName("is_active")
                  @Expose
                  var IS_ACTIVE: Int = 0)