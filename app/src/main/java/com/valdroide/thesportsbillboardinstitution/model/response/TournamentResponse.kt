package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

data class TournamentResponse(@SerializedName("response")
                              @Expose
                              var wsResponse: WSResponse?,
                              @SerializedName("subMenus")
                              @Expose
                              var subMenus: MutableList<SubMenuDrawer>?,
                              @SerializedName("tournament")
                              @Expose
                              var tournament: Tournament?,
                              @SerializedName("tournaments")
                              @Expose
                              var tournaments: MutableList<Tournament>?)