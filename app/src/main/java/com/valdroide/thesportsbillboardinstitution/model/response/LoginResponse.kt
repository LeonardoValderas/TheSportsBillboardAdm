package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse


data class LoginResponse(@SerializedName("response")
                         @Expose
                         var wsResponse: WSResponse?,
                         @SerializedName("login")
                         @Expose
                         var login: Login?,
                         @SerializedName("logins")
                         @Expose
                         var logins: MutableList<Login>?)

