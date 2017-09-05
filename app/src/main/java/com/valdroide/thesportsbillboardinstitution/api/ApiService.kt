package com.valdroide.thesportsbillboardinstitution.api

import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.model.response.*
import io.reactivex.Observable
import retrofit2.Call

import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

interface ApiService {
    //DATEDATA
    @GET("splash/getDateData.php")
    fun getDateData(): Observable<DataResponse>
    //MENUSSUBMENUS
    @GET("menu_submenu/getMenusSubMenus.php")
    fun getMenuSubMenu(): Observable<MenuSubMenuResponse>
    //ACCOUNT
    @GET("account/getAccount.php")
    fun getAccount(): Observable<AccountResponse>
    //FIXTURES
    @FormUrlEncoded
    @POST("fixture/getFixture.php")
    fun getFixtureForDivision(@Field("id_submenu") id_submenu: Int, @Field("quantity") quantity: Int): Observable<FixtureResponse>
    //LEADERBOARDS
    @FormUrlEncoded
    @POST("leader_board/getLeaderBoards.php")
    fun getLeaderBoards(@Field("id_submenu") id_submenu: Int): Observable<LeaderBoardResponse>
    //NEWS
    @FormUrlEncoded
    @POST("news/getNews.php")
    fun getNews(@Field("id_submenu") id_submenu: Int): Observable<NewsResponse>
    //PLAYES
    @FormUrlEncoded
    @POST("player/getPlayers.php")
    fun getPlayers(@Field("id_submenu") id_submenu: Int): Observable<PlayerResponse>
    //SANCTIONS
    @FormUrlEncoded
    @POST("sanction/getSanctions.php")
    fun getSanctions(@Field("id_submenu") id_submenu: Int): Observable<SanctionResponse>
    //LOGIN
    @FormUrlEncoded
    @POST("login/validateLogin.php")
    fun validateLogin(@Field("user") user: String, @Field("pass") pass: String): Call<WSResponse>
}