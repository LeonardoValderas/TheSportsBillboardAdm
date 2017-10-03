package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FieldMatch(
        @SerializedName("id")
        @Expose
        var ID_FIELD_MATCH_KEY: Int = 0,
        @SerializedName("name")
        @Expose
        var FIELD_MATCH: String = "",
        @SerializedName("address")
        @Expose
        var ADDRESS_MATCH: String = "") {
    override fun toString(): String = FIELD_MATCH
}