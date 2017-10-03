package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Fixture(
        @SerializedName("id")
        @Expose
        var ID_FIXTURE_KEY: Int = 0,
        @SerializedName("id_submenu")
        @Expose
        var ID_SUBMENU_KEY: Int = 0,
        @SerializedName("id_local_team")
        @Expose
        var ID_LOCAL_TEAM: Int = 0,
        @SerializedName("name_local_team")
        @Expose
        var NAME_LOCAL_TEAM: String = "",
        @SerializedName("url_local_team")
        @Expose
        var URL_LOCAL_TEAM: String = "",
        @SerializedName("id_visit_team")
        @Expose
        var ID_VISITE_TEAM: Int = 0,
        @SerializedName("name_visit_team")
        @Expose
        var NAME_VISITA_TEAM: String = "",
        @SerializedName("url_visit_team")
        @Expose
        var URL_VISIT_TEAM: String = "",
        @SerializedName("date_match")
        @Expose
        var DATE_MATCH: String = "",
        @SerializedName("hour_match")
        @Expose
        var HOUR_MATCH: String  = "",
        @SerializedName("result_local")
        @Expose
        var RESULT_LOCAL: String = "",
        @SerializedName("result_visit")
        @Expose
        var RESULT_VISITE: String = "",
        @SerializedName("id_times_match")
        @Expose
        var ID_TIMES_MATCH: Int = 0,
        @SerializedName("times_match")
        @Expose
        var TIMES_MATCH: String = "",
        @SerializedName("id_field_match")
        @Expose
        var ID_FIELD_MATCH: Int = 0,
        @SerializedName("field_name")
        @Expose
        var NAME_FIELD: String = "",
        @SerializedName("field_address")
        @Expose
        var ADDRESS: String  = "",
        @SerializedName("observation")
        @Expose
        var OBSERVATION: String = "",
        @SerializedName("id_tournament")
        @Expose
        var ID_TOURNAMENT: Int = 0,
        @SerializedName("tournament")
        @Expose
        var TOURNAMENT: String = "")
