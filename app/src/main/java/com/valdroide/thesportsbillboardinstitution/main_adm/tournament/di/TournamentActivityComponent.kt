package com.valdroide.thesportsbillboardinstitution.main_adm.tournament.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.adapter.SubMenuActivityAdapter
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.TournamentActivityPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.adapter.TournamentActivityAdapter
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapter
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TournamentActivityModule::class, LibsModule::class))
interface TournamentActivityComponent {
    fun getPresenter(): TournamentActivityPresenter
    fun getAdapterTournaments(): GenericSpinnerAdapter
    fun getAdapterSubMenusTournament(): TournamentActivityAdapter
}