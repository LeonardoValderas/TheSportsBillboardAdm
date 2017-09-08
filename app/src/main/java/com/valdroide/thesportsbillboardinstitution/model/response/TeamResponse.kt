package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

data class TeamResponse(@SerializedName("response")
                        @Expose
                        var wsResponse: WSResponse?,
                        @SerializedName("team")
                        @Expose
                        var team: Team?,
                        @SerializedName("teams")
                        @Expose
                        var teams: MutableList<Team>?)