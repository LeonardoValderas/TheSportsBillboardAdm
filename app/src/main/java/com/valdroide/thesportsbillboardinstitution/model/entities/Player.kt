package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Player(@SerializedName("id")
                  @Expose
                  var ID_PLAYER_KEY: Int,
                  @SerializedName("name")
                  @Expose
                  var NAME: String,
                  @SerializedName("id_position")
                  @Expose
                  var ID_POSITION: Int,
                  @SerializedName("position")
                  @Expose
                  var POSITION: String,
                  @SerializedName("name_image")
                  @Expose
                  var NAME_IMAGE: String,
                  @SerializedName("url_image")
                  @Expose
                  var URL_IMAGE: String,
                  @SerializedName("id_submenu")
                  @Expose
                  var ID_SUB_MENU: Int)