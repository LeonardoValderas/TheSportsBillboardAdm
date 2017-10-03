package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.News
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

data class NewsResponse(@SerializedName("response")
                        @Expose
                        var wsResponse: WSResponse?,
                        @SerializedName("new")
                        @Expose
                        var new: News?,
                        @SerializedName("news")
                        @Expose
                        var news: MutableList<News>?)