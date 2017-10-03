package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.*
import java.util.*

data class FixtureResponse(
        @SerializedName("response")
        @Expose
        var wsResponse: WSResponse?,
        @SerializedName("fixtures")
        @Expose
        var fixtures: MutableList<Fixture>?,
        @SerializedName("fixture")
        @Expose
        var fixture: Fixture?,
        @SerializedName("fields")
        @Expose
        var fieldMatchs: MutableList<FieldMatch>?,
        @SerializedName("times")
        @Expose
        var timeMatchs: MutableList<TimeMatch>?,
        @SerializedName("tournaments")
        @Expose
        var tournament: MutableList<Tournament>?,
        @SerializedName("submenus")
        @Expose
        var subMenus: MutableList<SubMenuDrawer>?,
        @SerializedName("teams")
        @Expose
        var teams: MutableList<Team>?)