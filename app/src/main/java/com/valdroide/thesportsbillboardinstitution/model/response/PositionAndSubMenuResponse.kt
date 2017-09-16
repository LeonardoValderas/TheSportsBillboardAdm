package com.valdroide.thesportsbillboardinstitution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.valdroide.thesportsbillboardinstitution.model.entities.Position
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse

data class PositionAndSubMenuResponse(@SerializedName("response")
                                      @Expose
                                      var wsResponse: WSResponse?,
                                      @SerializedName("submenus")
                                      @Expose
                                      var submunes: MutableList<SubMenuDrawer>?,
                                      @SerializedName("positions")
                                      @Expose
                                      var positions: MutableList<Position>?)