package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Sanction(@SerializedName("id")
                    @Expose
                    var ID_SANCTION_KEY: Int,
                    @SerializedName("id_player")
                    @Expose
                    var ID_PLAYER: Int,
                    @SerializedName("name")
                    @Expose
                    var NAME: String,
                    @SerializedName("yellow")
                    @Expose
                    var YELLOW: String,
                    @SerializedName("red")
                    @Expose
                    var RED: String,
                    @SerializedName("sanctions")
                    @Expose
                    var SANCTIONS: String,
                    @SerializedName("observation")
                    @Expose
                    var OBSERVATION: String,
                    @SerializedName("id_submenu")
                    @Expose
                    var ID_SUB_MENU: Int)