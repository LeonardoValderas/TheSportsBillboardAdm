package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

data class GenericResponse(
        @SerializedName("response")
        @Expose
        var wsResponse: WSResponse?,
        @SerializedName("object")
        @Expose
        var any: Any?,
        @SerializedName("objects")
        @Expose
        var anies: MutableList<Any>?,
        @SerializedName("objects_2")
        @Expose
        var anies_2: MutableList<Any>?
)