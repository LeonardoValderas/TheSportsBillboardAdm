package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.inject.Named

data class Sanction(@SerializedName("id")
                    @Expose
                    var ID_SANCTION_KEY: Int = 0,
                    @SerializedName("id_player")
                    @Expose
                    var ID_PLAYER: Int = 0,
                    @SerializedName("name")
                    @Expose
                    var NAME: String = "",
                    @SerializedName("yellow")
                    @Expose
                    var YELLOW: String = "",
                    @SerializedName("red")
                    @Expose
                    var RED: String = "",
                    @SerializedName("sanction")
                    @Expose
                    var SANCTION: String = "",
                    @SerializedName("observation")
                    @Expose
                    var OBSERVATION: String = "",
                    @SerializedName("id_submenu")
                    @Expose
                    var ID_SUB_MENU: Int = 0,
                    @SerializedName("submenu")
                    @Expose
                    var SUBMENU: String = "",
                    @SerializedName("menu")
                    @Expose
                    var MENU: String = "")