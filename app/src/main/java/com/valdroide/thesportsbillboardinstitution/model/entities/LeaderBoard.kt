package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LeaderBoard(@SerializedName("id")
                        @Expose
                        var ID_LEADERBOARD_KEY: Int,
                        @SerializedName("position")
                        @Expose
                        var POSITION: String,
                        @SerializedName("team")
                        @Expose
                        var TEAM: String,
                        @SerializedName("points")
                        @Expose
                        var POINTS: String,
                        @SerializedName("pj")
                        @Expose
                        var PJ: String,
                        @SerializedName("pg")
                        @Expose
                        var PG: String,
                        @SerializedName("pe")
                        @Expose
                        var PE: String,
                        @SerializedName("pp")
                        @Expose
                        var PP: String,
                        @SerializedName("gf")
                        @Expose
                        var GF: String,
                        @SerializedName("ge")
                        @Expose
                        var GE: String,
                        @SerializedName("df")
                        @Expose
                        var DF: String)
