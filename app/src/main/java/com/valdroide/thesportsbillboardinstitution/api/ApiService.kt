package com.valdroide.thesportsbillboardinstitution.api

import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import com.valdroide.thesportsbillboardinstitution.model.response.AccountResponse
import com.valdroide.thesportsbillboardinstitution.model.response.DataResponse
import com.valdroide.thesportsbillboardinstitution.model.response.WSResponse
import io.reactivex.Observable
import retrofit2.Call

import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET



interface ApiService {
    //DATEDATA
//    @GET("splash/getDateData.php")
//    fun getDateData(): Call<DataResponse>

    @GET("splash/getDateData.php")
    fun getDateData(): Observable<DataResponse>
    //ACCOUNT
    @GET("account/getAccount.php")
    fun getAccount(): Observable<AccountResponse>

    //LOGIN
    @FormUrlEncoded
    @POST("login/validateLogin.php")
    fun validateLogin(@Field("user") user: String, @Field("pass") pass: String): Call<WSResponse>
}