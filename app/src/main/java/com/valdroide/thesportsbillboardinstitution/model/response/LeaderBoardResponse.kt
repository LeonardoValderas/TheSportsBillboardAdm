package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.LeaderBoard
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

data class LeaderBoardResponse(
        @SerializedName("response")
        @Expose
        var wsResponse: WSResponse?,
        @SerializedName("leaderboard")
        @Expose
        var leaderboard: MutableList<LeaderBoard>?
)