package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Position(
        @SerializedName("id")
        @Expose
        var ID_POSITION_KEY: Int = 0,
        @SerializedName("position")
        @Expose
        var POSITION: String = "") {
    override fun toString(): String = POSITION
}