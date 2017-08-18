package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import com.valdroide.thesportsbillboardinstitution.model.entities.DateData
import com.valdroide.thesportsbillboardinstitution.model.entities.Menu
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenu

data class AccountResponse(
        @SerializedName("response")
        @Expose
        var wsResponse: WSResponse,
        @SerializedName("account")
        @Expose
        var account: Account
)