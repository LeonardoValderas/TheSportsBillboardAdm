package com.valdroide.thesportsbillboardinstitution

import android.app.Application
import com.facebook.stetho.Stetho
import com.raizlabs.android.dbflow.config.FlowManager
import com.valdroide.thesportsbillboardinstitution.main_user.splash.di.SplashActivityModule
import com.valdroide.thesportsbillboardinstitution.main_user.splash.di.DaggerSplashActivityComponent
import android.app.Activity
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
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.di.DaggerLoginCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.di.LoginCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.di.LoginCreateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.create.ui.LoginCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.di.DaggerLoginEditFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.di.LoginEditFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.di.LoginEditFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.LoginEditFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.di.DaggerTeamCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.di.TeamCreateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.di.TeamCreateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.create.ui.TeamCreateFragmentView
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.di.DaggerTeamUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.di.TeamUpdateFragmentComponent
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.di.TeamUpdateFragmentModule
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.TeamUpdateFragmentView
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


class TheSportsBillboardInstitutionApp : Application() {
    lateinit var libsModule: LibsModule

    companion object {
        private lateinit var firebaseAnalytics: FirebaseAnalytics
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
        MultiDex.install(this)
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

    fun getTeamUpdateFragmentComponent(view: TeamUpdateFragmentView, listener: com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.adapter.OnItemClickListener, fragment: Fragment): TeamUpdateFragmentComponent {
        return DaggerTeamUpdateFragmentComponent
                .builder()
                .libsModule(LibsModule(fragment))
                .teamUpdateFragmentModule(TeamUpdateFragmentModule(view, listener))
                .build()

    }
}
