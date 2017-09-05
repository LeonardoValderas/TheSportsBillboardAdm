package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import java.util.*

data class FixtureResponse(
        @SerializedName("response")
        @Expose
        var wsResponse: WSResponse?,
        @SerializedName("fixture")
        @Expose
        var fixtures: MutableList<Fixture>?
)