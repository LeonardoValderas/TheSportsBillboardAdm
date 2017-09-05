package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

data class SanctionResponse(@SerializedName("response")
                            @Expose
                            var wsResponse: WSResponse?,
                            @SerializedName("sanctions")
                            @Expose
                            var sanctions: MutableList<Sanction>?)