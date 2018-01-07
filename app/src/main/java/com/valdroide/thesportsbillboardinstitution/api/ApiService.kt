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
    fun getFixtureForDivision(@Field("id_submenu") id_submenu: Int,
                              @Field("quantity") quantity: Int): Observable<FixtureResponse>

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
    fun validateLogin(@Field("user") user: String,
                      @Field("pass") pass: String): Call<WSResponse>

    //ADM
    //LOGIN
    @FormUrlEncoded
    @POST("adm/login/getLogin.php")
    fun getLogin(@Field("id_login") id_login: Int): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("adm/login/saveLogin.php")
    fun saveLogin(@Field("user") user: String,
                  @Field("pass") pass: String,
                  @Field("type") type: Int,
                  @Field("user_work") user_work: Int,
                  @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/login/updateLogin.php")
    fun editLogin(@Field("id_login") id_login: Int,
                  @Field("user") user: String,
                  @Field("pass") pass: String,
                  @Field("type") type: Int,
                  @Field("is_active") is_active: Int,
                  @Field("user_work") user_work: Int,
                  @Field("date_update") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/login/deleteLogin.php")
    fun deleteLogin(@Field("id_login") id_team: Int,
                    @Field("user_work") user_work: Int,
                    @Field("date_delete") date_delete: String): Single<WSResponse>

    @GET("adm/login/getLogins.php")
    fun getLogins(): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("adm/login/activeOrUnActiveLogin.php")
    fun activeOrUnActiveLogin(@Field("id_login") id_login: Int,
                              @Field("is_active") is_active: Int,
                              @Field("user_work") user_work: Int,
                              @Field("date_update") date_create: String): Single<WSResponse>
    //MENUS - SUBMENUS
    @GET("adm/menu/getMenusSubMenus.php")
    fun getMenusSubMenus(): Observable<MenuSubMenuResponse>
    //MENU
    @FormUrlEncoded
    @POST("adm/menu/saveMenu.php")
    fun saveMenu(@Field("menu") user: String,
                 @Field("user_work") user_work: Int,
                 @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/menu/updateMenu.php")
    fun updateMenu(@Field("id_menu") id_menu: Int,
                   @Field("menu") menu: String,
                   @Field("is_active") is_active: Int,
                   @Field("user_work") user_work: Int,
                   @Field("date_update") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/menu/activeOrUnActiveMenu.php")
    fun activeOrUnActiveMenu(@Field("id_menu") id_menu: Int,
                             @Field("is_active") is_active: Int,
                             @Field("user_work") user_work: Int,
                             @Field("date_update") date_create: String): Single<WSResponse>
    @FormUrlEncoded
    @POST("adm/menu/deleteMenu.php")
    fun deleteMenu(@Field("id_menu") id_menu: Int,
                   @Field("user_work") user_work: Int,
                   @Field("date_delete") date_delete: String): Single<WSResponse>

    //SUBMENU
    @FormUrlEncoded
    @POST("adm/submenu/saveSubMenu.php")
    fun saveSubMenu(@Field("submenu") user: String,
                    @Field("id_menu") id_menu: Int,
                    @Field("user_work") user_work: Int,
                    @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/submenu/updateSubMenu.php")
    fun updateSubMenu(@Field("id_submenu") id_submenu: Int,
                      @Field("submenu") submenu: String,
                      @Field("id_menu") id_menu: Int,
                      @Field("is_active") is_active: Int,
                      @Field("user_work") user_work: Int,
                      @Field("date_update") date_create: String): Single<WSResponse>
    @FormUrlEncoded
    @POST("adm/submenu/activeOrUnActiveSubMenu.php")
    fun activeOrUnActiveSubMenu(@Field("id_submenu") id_submenu: Int,
                                @Field("is_active") is_active: Int,
                                @Field("user_work") user_work: Int,
                                @Field("date_update") date_create: String): Single<WSResponse>
    @FormUrlEncoded
    @POST("adm/submenu/deleteSubMenu.php")
    fun deleteSubMenu(@Field("id_submenu") id_submenu: Int,
                      @Field("user_work") user_work: Int,
                      @Field("date_delete") date_delete: String): Single<WSResponse>

    //ACCOUNT
    @FormUrlEncoded
    @POST("adm/account/saveAccount.php")
    fun saveAccount(@Field("id_account") id_account: Int,
                    @Field("description") description: String,
                    @Field("address") address: String,
                    @Field("phone") phone: String,
                    @Field("facebook") facebook: String,
                    @Field("instagram") instagram: String,
                    @Field("web") web: String,
                    @Field("url_image") url_image: String,
                    @Field("name_image") name_image: String,
                    @Field("email") email: String,
                    @Field("encode") encode: String,
                    @Field("before") before: String,
                    @Field("user_work") user_work: Int): Single<WSResponse>
    //TEAM
    @FormUrlEncoded
    @POST("adm/team/getTeam.php")
    fun getTeam(@Field("id_team") id_team: Int): Observable<TeamResponse>

    @FormUrlEncoded
    @POST("adm/team/saveTeam.php")
    fun saveTeam(@Field("name") name: String,
                 @Field("url_image") url_image: String,
                 @Field("name_image") name_image: String,
                 @Field("encode") encode: String,
                 @Field("user_work") user_work: Int,
                 @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/team/updateTeam.php")
    fun updateTeam(@Field("id_team") id_login: Int,
                   @Field("name") name: String,
                   @Field("url_image") url_image: String,
                   @Field("name_image") name_image: String,
                   @Field("encode") encode: String,
                   @Field("before") before: String,
                   @Field("is_active") is_active: Int,
                   @Field("user_work") user_work: Int,
                   @Field("date_update") date_update: String): Single<WSResponse>

    @GET("adm/team/getTeams.php")
    fun getTeams(): Observable<TeamResponse>

    @FormUrlEncoded
    @POST("adm/team/activeOrUnActiveTeam.php")
    fun activeOrUnActiveTeam(@Field("id_team") id_team: Int,
                             @Field("is_active") is_active: Int,
                             @Field("user_work") user_work: Int,
                             @Field("date_update") date_update: String): Single<WSResponse>
    @FormUrlEncoded
    @POST("adm/team/deleteTeam.php")
    fun deleteTeam(@Field("id_team") id_team: Int,
                   @Field("image_name") before: String,
                   @Field("user_work") user_work: Int,
                   @Field("date_delete") date_delete: String): Single<WSResponse>

    //POSITION
    @FormUrlEncoded
    @POST("adm/position/savePosition.php")
    fun savePosition(@Field("position") position: String,
                     @Field("user_work") user_work: Int,
                     @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/position/updatePosition.php")
    fun updatePosition(@Field("id_position") id_position: Int,
                       @Field("position") position: String,
                       @Field("user_work") user_work: Int,
                       @Field("date_update") date_update: String): Single<WSResponse>

    @GET("adm/position/getPositionsSubMenus.php")
    fun getPositionsSubMenus(): Observable<PositionAndSubMenuResponse>

    //PLAYER
    @FormUrlEncoded
    @POST("adm/player/getPlayer.php")
    fun getPlayer(@Field("id_player") id_player: Int): Observable<PlayerResponse>

    @FormUrlEncoded
    @POST("adm/player/savePlayer.php")
    fun savePlayer(@Field("name") name: String,
                   @Field("id_position") id_position : Int,
                   @Field("id_submenu") id_submenu : Int,
                   @Field("url_image") url_image: String,
                   @Field("name_image") name_image: String,
                   @Field("encode") encode: String,
                   @Field("user_work") user_work: Int,
                   @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/player/updatePlayer.php")
    fun updatePlayer(@Field("id_player") id_player: Int,
                     @Field("name") name: String,
                     @Field("id_position") id_position : Int,
                     @Field("id_submenu") id_submenu : Int,
                     @Field("url_image") url_image: String,
                     @Field("name_image") name_image: String,
                     @Field("encode") encode: String,
                     @Field("before") before: String,
                     @Field("is_active") is_active: Int,
                     @Field("user_work") user_work: Int,
                     @Field("date_update") date_update: String): Single<WSResponse>

    @GET("adm/player/getPlayers.php")
    fun getPlayers(): Observable<PlayerResponse>

    @FormUrlEncoded
    @POST("adm/player/activeOrUnActivePlayer.php")
    fun activeOrUnActivePlayer(@Field("id_player") id_team: Int,
                               @Field("is_active") is_active: Int,
                               @Field("user_work") user_work: Int,
                               @Field("date_update") date_update: String): Single<WSResponse>
    @FormUrlEncoded
    @POST("adm/player/deletePlayer.php")
    fun deletePlayer(@Field("id_player") id_player: Int,
                     @Field("user_work") user_work: Int,
                     @Field("date_delete") date_delete: String): Single<WSResponse>

    //NEWS
    @FormUrlEncoded
    @POST("adm/news/getNews.php")
    fun getNew(@Field("id_news") id_news: Int): Observable<NewsResponse>

    @FormUrlEncoded
    @POST("adm/news/saveNews.php")
    fun saveNews(@Field("title") title: String,
                 @Field("description") description: String,
                 @Field("id_submenu") id_submenu : Int,
                 @Field("url_image") url_image: String,
                 @Field("name_image") name_image: String,
                 @Field("encode") encode: String,
                 @Field("user_work") user_work: Int,
                 @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/news/updateNews.php")
    fun updateNews(@Field("id_news") id_news: Int,
                   @Field("title") title: String,
                   @Field("description") description: String,
                   @Field("id_submenu") id_submenu : Int,
                   @Field("url_image") url_image: String,
                   @Field("name_image") name_image: String,
                   @Field("encode") encode: String,
                   @Field("before") before: String,
                   @Field("is_active") is_active: Int,
                   @Field("user_work") user_work: Int,
                   @Field("date_update") date_update: String): Single<WSResponse>

    @GET("adm/news/getNewsList.php")
    fun getNewsList(): Observable<NewsResponse>

    @FormUrlEncoded
    @POST("adm/news/activeOrUnActiveNews.php")
    fun activeOrUnActiveNews(@Field("id_news") id_news: Int,
                             @Field("is_active") is_active: Int,
                             @Field("user_work") user_work: Int,
                             @Field("date_update") date_update: String): Single<WSResponse>
    @FormUrlEncoded
    @POST("adm/news/deleteNews.php")
    fun deleteNews(@Field("id_news") id_news: Int,
                   @Field("user_work") user_work: Int,
                   @Field("date_delete") date_delete: String): Single<WSResponse>

    //SANCTION
    @GET("adm/sanction/getSubMenusAndPlayers.php")
    fun getSubMenusAndPlayers(): Observable<SanctionResponse>

    @FormUrlEncoded
    @POST("adm/sanction/getSanction.php")
    fun getSanction(@Field("id_sanction") id_sanction: Int): Observable<SanctionResponse>

    @FormUrlEncoded
    @POST("adm/sanction/getPlayerForIdSubMenu.php")
    fun getPlayerForIdSubMenu(@Field("id_submenu") id_submenu: Int): Observable<SanctionResponse>


    @FormUrlEncoded
    @POST("adm/sanction/saveSanction.php")
    fun saveSanction(@Field("yellow") yellow: String,
                     @Field("red") red: String,
                     @Field("sanction") sanction: String,
                     @Field("observation") observation: String,
                     @Field("id_submenu") id_submenu : Int,
                     @Field("id_player") id_player: Int,
                     @Field("user_work") user_work: Int,
                     @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/sanction/updateSanction.php")
    fun updateSanction(@Field("id_sanction") id_sanction: Int,
                       @Field("yellow") yellow: String,
                       @Field("red") red: String,
                       @Field("sanction") sanction: String,
                       @Field("observation") observation: String,
                       @Field("id_submenu") id_submenu : Int,
                       @Field("id_player") id_player: Int,
                       @Field("user_work") user_work: Int,
                       @Field("date_update") date_update: String): Single<WSResponse>

    @GET("adm/sanction/getSanctions.php")
    fun getSanctions(): Observable<SanctionResponse>

    @FormUrlEncoded
    @POST("adm/sanction/deleteSanction.php")
    fun deleteSanction(@Field("id_sanction") id_sanction: Int,
                       @Field("user_work") user_work: Int,
                       @Field("date_delete") date_delete: String): Single<WSResponse>

    //FIXTURE
    @GET("adm/fixture/getSpinnersData.php")
    fun getSpinnersData(): Observable<FixtureResponse>

    @FormUrlEncoded
    @POST("adm/fixture/getFixture.php")
    fun getFixture(@Field("id_fixture") id_fixture: Int): Observable<FixtureResponse>

    @FormUrlEncoded
    @POST("adm/fixture/saveFixture.php")
    fun saveFixture(@Field("id_tournament") id_tournament: Int,
                    @Field("id_team_local") id_team_local: Int,
                    @Field("id_team_visite") id_team_visite: Int,
                    @Field("id_submenu") id_submenu: Int,
                    @Field("id_field") id_field: Int,
                    @Field("id_time") id_time: Int,
                    @Field("result_local") result_local: String,
                    @Field("result_visite") result_visite: String,
                    @Field("date_match") day: String,
                    @Field("hour_match") hour: String,
                    @Field("observation") observation: String,
                    @Field("user_work") user_work: Int,
                    @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/fixture/updateFixture.php")
    fun updateFixture(@Field("id_fixture") id_fixture: Int,
                      @Field("id_tournament") id_tournament: Int,
                      @Field("id_team_local") id_team_local: Int,
                      @Field("id_team_visite") id_team_visite: Int,
                      @Field("id_submenu") id_submenu: Int,
                      @Field("id_field") id_field: Int,
                      @Field("id_time") id_time: Int,
                      @Field("result_local") result_local: String,
                      @Field("result_visite") result_visite: String,
                      @Field("date_match") day: String,
                      @Field("hour_match") hour: String,
                      @Field("observation") observation: String,
                      @Field("user_work") user_work: Int,
                      @Field("date_update") date_update: String): Single<WSResponse>
    @FormUrlEncoded
    @POST("adm/fixture/updateResultFixture.php")
    fun updateResultFixture(@Field("id_fixture") id_fixture: Int,
                      @Field("result_local") result_local: String,
                      @Field("result_visite") result_visite: String,
                      @Field("id_times_match") id_time: Int,
                      @Field("user_work") user_work: Int,
                      @Field("date_update") date_update: String): Single<WSResponse>

    @GET("adm/fixture/getFixtures.php")
    fun getFixtures(): Observable<FixtureResponse>

    @FormUrlEncoded
    @POST("adm/fixture/deleteFixture.php")
    fun deleteFixture(@Field("id_fixture") id_fixture: Int, @Field("user_work") user_work: Int, @Field("date_delete") date_delete: String): Single<WSResponse>

    //TOURNAMENT
    @GET("adm/tournament/getSubMenusTournaments.php")
    fun getTournamentsAndSubMenus(): Observable<TournamentResponse>

    @FormUrlEncoded
    @POST("adm/tournament/getSubMenusTournament.php")
    fun getSubMenuForTournament(@Field("id_tournament") id_tournament: Int): Observable<TournamentResponse>

    @FormUrlEncoded
    @POST("adm/tournament/saveTournament.php")
    fun saveTournament(@Field("tournament") tournament: String,
                    @Field("user_work") user_work: Int,
                    @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/tournament/updateTournament.php")
    fun updateTournament(@Field("id_tournament") id_tournament: Int,
                         @Field("tournament") tournament: String,
                         @Field("is_active") is_active: Int,
                         @Field("user_work") user_work: Int,
                         @Field("date_update") date_update: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/tournament/activeOrUnActiveTournament.php")
    fun activeOrUnActiveTournament(@Field("id_tournament") id_news: Int,
                                   @Field("is_active") is_active: Int,
                                   @Field("user_work") user_work: Int,
                                   @Field("date_update") date_update: String): Single<WSResponse>

    @GET("adm/tournament/getTournament.php")
    fun getTournament(): Observable<TournamentResponse>

    @FormUrlEncoded
    @POST("adm/tournament/deleteTournament.php")
    fun deleteTournament(@Field("id_tournament") id_tournament: Int, @Field("user_work") user_work: Int, @Field("date_delete") date_delete: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/tournament/assignationUnassignation.php")
    fun assignationUnassignation(@Field("id_submenu") id_submenu: Int,
                                 @Field("id_tournament") tournament: Int,
                                 @Field("user_work") user_work: Int,
                                 @Field("date_create") date_create: String): Single<WSResponse>

    @FormUrlEncoded
    @POST("adm/tournament/getTournamentForSubMenu.php")
    fun getTournamentForSubMenu(@Field("id_submenu") id_submenu: Int): Observable<TournamentResponse>

    @FormUrlEncoded
    @POST("adm/tournament/getTournamentToSubMenu.php")
    fun getTournamentToSubMenu(@Field("id_submenu") id_submenu: Int): Single<WSResponse>

}