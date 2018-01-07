package com.valdroide.thesportsbillboardinstitution

import android.app.Application
import com.facebook.stetho.Stetho
import com.raizlabs.android.dbflow.config.FlowManager
import com.valdroide.thesportsbillboardinstitution.main_user.splash.di.SplashActivityModule
import com.valdroide.thesportsbillboardinstitution.main_user.splash.di.DaggerSplashActivityComponent
import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.multidex.MultiDex
import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_user.splash.ui.SplashActivityView
import com.valdroide.thesportsbillboardinstitution.main_user.splash.di.SplashActivityComponent
import android.support.v4.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.valdroide.thesportsbillboardinstitution.main_adm.account.di.AccountAdmActivityComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.account.di.AccountAdmActivityModule
import com.valdroide.thesportsbillboardinstitution.main_adm.account.di.DaggerAccountAdmActivityComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.account.ui.AccountAdmActivityView
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.di.DaggerFixtureCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.di.FixtureCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.di.FixtureCreateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui.FixtureCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.di.DaggerFixtureUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.di.FixtureUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.di.FixtureUpdateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.FixtureUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.di.DaggerLoginCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.di.LoginCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.di.LoginCreateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.ui.LoginCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.di.DaggerLoginEditFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.di.LoginEditFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.di.LoginEditFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.LoginEditFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.di.DaggerMenuSubMenuActivityComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.di.MenuSubMenuActivityComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.di.MenuSubMenuActivityModule
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.MenuSubMenuActivityView
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.di.DaggerNewsCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.di.NewsCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.di.NewsCreateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.ui.NewsCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.di.DaggerNewsUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.di.NewsUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.di.NewsUpdateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.NewsUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.di.DaggerPlayerCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.di.PlayerCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.di.PlayerCreateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui.PlayerCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.di.DaggerPlayerUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.di.PlayerUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.di.PlayerUpdateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui.PlayerUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.di.DaggerSanctionCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.di.SanctionCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.di.SanctionCreateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create.ui.SanctionCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.di.DaggerSanctionUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.di.SanctionUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.di.SanctionUpdateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.ui.SanctionUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.di.DaggerTeamCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.di.TeamCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.di.TeamCreateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.ui.TeamCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.di.DaggerTeamUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.di.TeamUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.di.TeamUpdateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.TeamUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.di.DaggerTournamentActivityComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.di.TournamentActivityComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.di.TournamentActivityModule
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.TournamentActivityView
import com.valdroide.thesportsbillboardinstitution.main_user.account.di.AccountActivityComponent
import com.valdroide.thesportsbillboardinstitution.main_user.account.di.AccountActivityModule
import com.valdroide.thesportsbillboardinstitution.main_user.account.di.DaggerAccountActivityComponent
import com.valdroide.thesportsbillboardinstitution.main_user.account.ui.AccountActivityView
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.di.DaggerFixtureFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.di.FixtureFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.di.FixtureFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.FixtureFragmentView
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter.OnItemClickListener
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.di.DaggerLeaderBoardFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.di.LeaderBoardFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.di.LeaderBoardFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui.LeaderBoardFragmentView
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.di.DaggerNewsFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.di.NewsFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.di.NewsFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.news.ui.NewsFragmentView
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.di.DaggerPlayerFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.di.PlayerFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.di.PlayerFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui.PlayerFragmentView
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.di.DaggerSanctionFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.di.SanctionFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.di.SanctionFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.SanctionFragmentView
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.di.DaggerNavigationActivityComponent
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.di.NavigationActivityComponent
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.di.NavigationActivityModule
import com.valdroide.thesportsbillboardinstitution.main_user.navigation.ui.NavigationActivityView
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClick
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener_2


class TheSportsBillboardInstitutionApp : Application() {
    lateinit var libsModule: LibsModule

    companion object {
        private lateinit var firebaseAnalytics: FirebaseAnalytics
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }
    override fun onCreate() {
        super.onCreate()
        initModules()
        initDB()
        FirebaseApp.initializeApp(this)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        if (!isRoboUnitTest()) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initModules() {
        libsModule = LibsModule()
    }

    override fun onTerminate() {
        super.onTerminate()
        DBTearDown()
    }

    private fun DBTearDown() {
        FlowManager.destroy()
    }

    private fun initDB() {
        FlowManager.init(this)
    }

    fun isRoboUnitTest(): Boolean =
            "robolectric" == Build.FINGERPRINT


    fun firebaseAnalyticsInstance(): FirebaseAnalytics =
            firebaseAnalytics;


    fun getNavigationActivityComponent(view: NavigationActivityView, activity: Activity): NavigationActivityComponent {
        return DaggerNavigationActivityComponent
                .builder()
                .libsModule(LibsModule(activity))
                .navigationActivityModule(NavigationActivityModule(activity, view))
                .build()
    }

    fun getSplashActivityComponent(view: SplashActivityView, activity: Activity): SplashActivityComponent {
        return DaggerSplashActivityComponent
                .builder()
                .libsModule(LibsModule(activity))
                .splashActivityModule(SplashActivityModule(view))
                .build()
    }

    fun getAccountActivityComponent(view: AccountActivityView, activity: Activity): AccountActivityComponent {
        return DaggerAccountActivityComponent
                .builder()
                .libsModule(LibsModule(activity))
                .accountActivityModule(AccountActivityModule(view))
                .build()
    }

    fun getFixtureFragmentComponent(view: FixtureFragmentView, fragment: Fragment, listener: OnItemClickListener): FixtureFragmentComponent {
        return DaggerFixtureFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .fixtureFragmentModule(FixtureFragmentModule(view, listener))
                .build()
    }

    fun getLeaderBoardFragmentComponent(view: LeaderBoardFragmentView, fragment: Fragment): LeaderBoardFragmentComponent {
        return DaggerLeaderBoardFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .leaderBoardFragmentModule(LeaderBoardFragmentModule(view, fragment.activity))
                .build()
    }

    fun getNewsFragmentComponent(view: NewsFragmentView, fragment: Fragment): NewsFragmentComponent {
        return DaggerNewsFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .newsFragmentModule(NewsFragmentModule(view, fragment.activity))
                .build()
    }

    fun getPlayerFragmentComponent(view: PlayerFragmentView, fragment: Fragment): PlayerFragmentComponent {
        return DaggerPlayerFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .playerFragmentModule(PlayerFragmentModule(view, fragment))
                .build()
    }

    fun getSanctionFragmentComponent(view: SanctionFragmentView, fragment: Fragment): SanctionFragmentComponent {
        return DaggerSanctionFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .sanctionFragmentModule(SanctionFragmentModule(view, fragment))
                .build()
    }

    //ADM

    fun getLoginCreateFragmentComponent(view: LoginCreateFragmentView, fragment: Fragment): LoginCreateFragmentComponent {
        return DaggerLoginCreateFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .loginCreateFragmentModule(LoginCreateFragmentModule(view))
                .build()
    }

    fun getLoginEditFragmentComponent(view: LoginEditFragmentView, onItemClickListener: com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.adapter.OnItemClickListener, fragment: Fragment): LoginEditFragmentComponent {
        return DaggerLoginEditFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .loginEditFragmentModule(LoginEditFragmentModule(view, onItemClickListener))
                .build()
    }

    fun getMenuSubMenuActivityComponent(view: MenuSubMenuActivityView, activity: Activity): MenuSubMenuActivityComponent {
        return DaggerMenuSubMenuActivityComponent
                .builder()
                .libsModule(LibsModule(activity))
                .menuSubMenuActivityModule(MenuSubMenuActivityModule(view, activity))
                .build()
    }

    fun getAccountAdmActivityComponent(view: AccountAdmActivityView, activity: Activity): AccountAdmActivityComponent {
        return DaggerAccountAdmActivityComponent
                .builder()
                .libsModule(LibsModule(activity))
                .accountAdmActivityModule(AccountAdmActivityModule(view))
                .build()

    }

    fun getTeamCreateFragmentComponent(view: TeamCreateFragmentView, fragment: Fragment): TeamCreateFragmentComponent {
        return DaggerTeamCreateFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .teamCreateFragmentModule(TeamCreateFragmentModule(view))
                .build()
    }

    fun getTeamUpdateFragmentComponent(view: TeamUpdateFragmentView, fragment: Fragment, listener: GenericOnItemClick<Team>): TeamUpdateFragmentComponent {
        return DaggerTeamUpdateFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .teamUpdateFragmentModule(TeamUpdateFragmentModule(view, fragment.activity, listener))
                .build()
    }

    fun getPlayerCreateFragmentComponent(view: PlayerCreateFragmentView, fragment: Fragment): PlayerCreateFragmentComponent {
        return DaggerPlayerCreateFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .playerCreateFragmentModule(PlayerCreateFragmentModule(view, fragment.activity))
                .build()

    }

    fun getPlayerUpdateFragmentComponent(view: PlayerUpdateFragmentView, fragment: Fragment, listener: GenericOnItemClick<Player>): PlayerUpdateFragmentComponent {
        return DaggerPlayerUpdateFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .playerUpdateFragmentModule(PlayerUpdateFragmentModule(view, listener))
                .build()
    }

    fun getNewsCreateFragmentComponent(view: NewsCreateFragmentView, fragment: Fragment): NewsCreateFragmentComponent {
        return DaggerNewsCreateFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .newsCreateFragmentModule(NewsCreateFragmentModule(view, fragment.activity))
                .build()
    }

    fun getNewsUpdateFragmentComponent(view: NewsUpdateFragmentView, listener: com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.update.ui.adapter.OnItemClickListener, fragment: Fragment): NewsUpdateFragmentComponent {
        return DaggerNewsUpdateFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .newsUpdateFragmentModule(NewsUpdateFragmentModule(view, listener))
                .build()
    }

    fun getSanctionCreateFragmentComponent(view: SanctionCreateFragmentView, fragment: Fragment): SanctionCreateFragmentComponent {
        return DaggerSanctionCreateFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .sanctionCreateFragmentModule(SanctionCreateFragmentModule(view, fragment.activity))
                .build()
    }

    fun getSanctionUpdateFragmentComponent(view: SanctionUpdateFragmentView, listener: GenericOnItemClickListener_2, fragment: Fragment): SanctionUpdateFragmentComponent {
        return DaggerSanctionUpdateFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .sanctionUpdateFragmentModule(SanctionUpdateFragmentModule(view, listener))
                .build()
    }

    fun getFixtureCreateFragmentComponent(view: FixtureCreateFragmentView, fragment: Fragment): FixtureCreateFragmentComponent {
        return DaggerFixtureCreateFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .fixtureCreateFragmentModule(FixtureCreateFragmentModule(view, fragment.activity))
                .build()
    }

    fun getFixtureUpdateFragmentComponent(view: FixtureUpdateFragmentView, fragment: Fragment): FixtureUpdateFragmentComponent {
        return DaggerFixtureUpdateFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .fixtureUpdateFragmentModule(FixtureUpdateFragmentModule(view))
                .build()
    }

    fun getTournamentActivityComponent(view: TournamentActivityView, activity: Activity): TournamentActivityComponent {
        return DaggerTournamentActivityComponent
                .builder()
                .libsModule(LibsModule(activity))
                .tournamentActivityModule(TournamentActivityModule(view))
                .build()
    }
}
