package com.valdroide.thesportsbillboardinstitution.api

import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.model.response.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call

import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

interface ApiService {
    //USER
    //DATEDATA
    @GET("user/splash/getDateData.php")
    fun getDateData(): Observable<DataResponse>

    //MENUSSUBMENUS
    @GET("user/menu_submenu/getMenusSubMenus.php")
    fun getMenuSubMenu(): Observable<MenuSubMenuResponse>

    //ACCOUNT
    @GET("user/account/getAccount.php")
    fun getAccount(): Observable<AccountResponse>

    //FIXTURES
    @FormUrlEncoded
    @POST("user/fixture/getFixture.php")
    fun getFixtureForDivision(@Field("id_submenu") id_submenu: Int, @Field("quantity") quantity: Int): Observable<FixtureResponse>

    //LEADERBOARDS
    @FormUrlEncoded
    @POST("user/leader_board/getLeaderBoards.php")
    fun getLeaderBoards(@Field("id_submenu") id_submenu: Int): Observable<LeaderBoardResponse>

    //NEWS
    @FormUrlEncoded
    @POST("user/news/getNews.php")
    fun getNews(@Field("id_submenu") id_submenu: Int): Observable<NewsResponse>

    //PLAYES
    @FormUrlEncoded
    @POST("user/player/getPlayers.php")
    fun getPlayers(@Field("id_submenu") id_submenu: Int): Observable<PlayerResponse>

    //SANCTIONS
    @FormUrlEncoded
    @POST("user/sanction/getSanctions.php")
    fun getSanctions(@Field("id_submenu") id_submenu: Int): Observable<SanctionResponse>

    //LOGIN
    @FormUrlEncoded
    @POST("user/login/validateLogin.php")
    fun validateLogin(@Field("user") user: String, @Field("pass") pass: String): Call<WSResponse>

    //ADM
    //LOGIN
    @FormUrlEncoded
    @POST("adm/login/getLogin.php")
    fun getLogin(@Field("id_login") id_login: Int): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("adm/login/saveLogin.php")
    fun saveLogin(@Field("user") user: String, @Field("pass") pass: String, @Field("type") type: Int,
                  @Field("user_work") user_work: Int, @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/login/updateLogin.php")
    fun editLogin(@Field("id_login") id_login: Int, @Field("user") user: String, @Field("pass") pass: String,
                  @Field("type") type: Int, @Field("is_active") is_active: Int,
                  @Field("user_work") user_work: Int, @Field("date_update") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/login/deleteLogin.php")
    fun deleteLogin(@Field("id_login") id_team: Int, @Field("user_work") user_work: Int,
                    @Field("date_delete") date_delete: String): Single<WSResponse>

    @GET("adm/login/getLogins.php")
    fun getLogins(): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("adm/login/activeOrUnActiveLogin.php")
    fun activeOrUnActiveLogin(@Field("id_login") id_login: Int, @Field("is_active") is_active: Int,
                              @Field("user_work") user_work: Int, @Field("date_update") date_create: String): Single<WSResponse>
    //ACCOUNT
    @FormUrlEncoded
    @POST("adm/account/saveAccount.php")
    fun saveAccount(@Field("id_account") id_account: Int, @Field("description") description: String,
                    @Field("address") address: String, @Field("phone") phone: String,
                    @Field("facebook") facebook: String, @Field("instagram") instagram: String,
                    @Field("web") web: String, @Field("url_image") url_image: String,
                    @Field("name_image") name_image: String, @Field("email") email: String,
                    @Field("encode") encode: String, @Field("before") before: String,
                    @Field("user_work") user_work: Int): Single<WSResponse>
    //TEAM
    @FormUrlEncoded
    @POST("adm/team/getTeam.php")
    fun getTeam(@Field("id_team") id_team: Int): Observable<TeamResponse>

    @FormUrlEncoded
    @POST("adm/team/saveTeam.php")
    fun saveTeam(@Field("name") name: String, @Field("url_image") url_image: String,
                 @Field("name_image") name_image: String, @Field("encode") encode: String,
                 @Field("user_work") user_work: Int, @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/team/updateTeam.php")
    fun updateTeam(@Field("id_team") id_login: Int, @Field("name") name: String, @Field("url_image") url_image: String,
                   @Field("name_image") name_image: String, @Field("encode") encode: String,
                   @Field("before") before: String, @Field("is_active") is_active: Int, @Field("user_work") user_work: Int,
                   @Field("date_update") date_update: String): Single<WSResponse>

    @GET("adm/team/getTeams.php")
    fun getTeams(): Observable<TeamResponse>

    @FormUrlEncoded
    @POST("adm/team/activeOrUnActiveTeam.php")
    fun activeOrUnActiveTeam(@Field("id_team") id_team: Int, @Field("is_active") is_active: Int,
                              @Field("user_work") user_work: Int, @Field("date_update") date_update: String): Single<WSResponse>
    @FormUrlEncoded
    @POST("adm/team/deleteTeam.php")
    fun deleteTeam(@Field("id_team") id_team: Int, @Field("user_work") user_work: Int, @Field("date_delete") date_delete: String): Single<WSResponse>
    //POSITION
    @FormUrlEncoded
    @POST("adm/position/savePosition.php")
    fun savePosition(@Field("position") position: String, @Field("user_work") user_work: Int,
                   @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/position/updatePosition.php")
    fun updatePosition(@Field("id_position") id_position: Int, @Field("position") position: String,
                       @Field("user_work") user_work: Int, @Field("date_update") date_update: String): Single<WSResponse>

    @GET("adm/position/getPositionsSubMenus.php")
    fun getPositionsSubMenus(): Observable<PositionAndSubMenuResponse>
    //PLAYER
    @FormUrlEncoded
    @POST("adm/player/getPlayer.php")
    fun getPlayer(@Field("id_player") id_player: Int): Observable<PlayerResponse>

    @FormUrlEncoded
    @POST("adm/player/savePlayer.php")
    fun savePlayer(@Field("name") name: String, @Field("id_position") id_position : Int, @Field("id_submenu") id_submenu : Int,
                   @Field("url_image") url_image: String, @Field("name_image") name_image: String, @Field("encode") encode: String,
                   @Field("user_work") user_work: Int, @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/player/updatePlayer.php")
    fun updatePlayer(@Field("id_player") id_player: Int, @Field("name") name: String, @Field("id_position") id_position : Int, @Field("id_submenu") id_submenu : Int,
                     @Field("url_image") url_image: String, @Field("name_image") name_image: String, @Field("encode") encode: String,
                     @Field("before") before: String, @Field("is_active") is_active: Int, @Field("user_work") user_work: Int,
                     @Field("date_update") date_update: String): Single<WSResponse>

    @GET("adm/player/getPlayers.php")
    fun getPlayers(): Observable<PlayerResponse>

    @FormUrlEncoded
    @POST("adm/player/activeOrUnActivePlayer.php")
    fun activeOrUnActivePlayer(@Field("id_player") id_team: Int, @Field("is_active") is_active: Int,
                             @Field("user_work") user_work: Int, @Field("date_update") date_update: String): Single<WSResponse>
    @FormUrlEncoded
    @POST("adm/player/deletePlayer.php")
    fun deletePlayer(@Field("id_player") id_player: Int, @Field("user_work") user_work: Int, @Field("date_delete") date_delete: String): Single<WSResponse>

}