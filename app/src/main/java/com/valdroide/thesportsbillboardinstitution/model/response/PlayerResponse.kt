package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

data class PlayerResponse(@SerializedName("response")
                          @Expose
                          var wsResponse: WSResponse?,
                          @SerializedName("player")
                          @Expose
                          var player: Player?,
                          @SerializedName("players")
                          @Expose
                          var players: MutableList<Player>?)