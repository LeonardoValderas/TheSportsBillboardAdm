package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TimeMatch(
        @SerializedName("id")
        @Expose
        var ID_TIME_MATCH_KEY: Int = 0,
        @SerializedName("times")
        @Expose
        var TIME_MATCH: String = "") {
    override fun toString(): String = TIME_MATCH
}