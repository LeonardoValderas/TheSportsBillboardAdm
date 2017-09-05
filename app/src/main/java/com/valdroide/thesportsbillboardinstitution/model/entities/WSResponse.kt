package com.valdroide.thesportsbillboardinstitution.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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
data class WSResponse(@SerializedName("id")
                      @Expose val ID: Int,
                      @SerializedName("success")
                      @Expose val SUCCESS: String,
                      @SerializedName("message")
                      @Expose val MESSAGE: String)