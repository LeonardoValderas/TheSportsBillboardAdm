package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

data class SanctionResponse(@SerializedName("response")
                            @Expose
                            var wsResponse: WSResponse?,
                            @SerializedName("sanction")
                            @Expose
                            var sanction: Sanction?,
                            @SerializedName("sanctions")
                            @Expose
                            var sanctions: MutableList<Sanction>?,
                            @SerializedName("submenus")
                            @Expose
                            var subMenuDrawer: MutableList<SubMenuDrawer>?,
                            @SerializedName("players")
                            @Expose
                            var players: MutableList<Player>?)