package com.valdroide.thesportsbillboardinstitution.api

import com.valdroide.thesportsbillboardinstitution.model.response.WSResponse
import retrofit2.Call

import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded



interface ApiService {
    //LOGIN
    @FormUrlEncoded
    @POST("login/validateLogin.php")
    fun validateLogin(@Field("user") user: String, @Field("pass") pass: String): Call<WSResponse>
}