package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Tournament(
        @SerializedName("id")
        @Expose
        var ID_TOURNAMENT_KEY: Int = 0,
        @SerializedName("tournament")
        @Expose
        var TOURNAMENT: String = "") {
    override fun toString(): String = TOURNAMENT
}