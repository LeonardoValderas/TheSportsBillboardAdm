package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.FixtureCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapter
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(FixtureCreateFragmentModule::class, LibsModule::class))
interface FixtureCreateFragmentComponent {
    fun getPresenter(): FixtureCreateFragmentPresenter
    @Named("spinner_menu")
    fun getAdapterSubMenus(): GenericSpinnerAdapter
    @Named("spinner_time")
    fun getAdapterTimeMatch(): GenericSpinnerAdapter
    @Named("spinner_field")
    fun getAdapterFieldMatch(): GenericSpinnerAdapter
    @Named("spinner_tournament")
    fun getAdapterTournament(): GenericSpinnerAdapter
    @Named("spinner_team_local")
    fun getAdapterTeamLocal(): GenericSpinnerAdapter
    @Named("spinner_team_visite")
    fun getAdapterTeamVisite(): GenericSpinnerAdapter
}