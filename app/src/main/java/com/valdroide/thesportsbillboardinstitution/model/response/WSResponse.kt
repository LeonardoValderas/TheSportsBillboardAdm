package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

/*
class WSResponse {
    @SerializedName("id")
    @Expose
    var ID: Int = 0
    @SerializedName("success")
    @Expose
    var SUCCESS: String = ""
    @SerializedName("message")
    @Expose
    var MESSAGE: String = ""
}
*/
//@Generated("org.jsonschema2pojo")
data class WSResponse(
    @SerializedName("id")
    @Expose
    var ID: Int,
    @SerializedName("success")
    @Expose
    var SUCCESS: String,
    @SerializedName("message")
    @Expose
    var MESSAGE: String
)
