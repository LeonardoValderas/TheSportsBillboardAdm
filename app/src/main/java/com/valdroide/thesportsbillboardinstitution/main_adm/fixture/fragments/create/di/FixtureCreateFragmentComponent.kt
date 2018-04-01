package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.FixtureCreateFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui.FixtureCreateFragment
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui.adapters.*
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapter
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(FixtureCreateFragmentModule::class, LibsModule::class))
interface FixtureCreateFragmentComponent {
    fun inject(fixtureCreateFragment: FixtureCreateFragment)
    /*
    fun getPresenter(): FixtureCreateFragmentPresenter
    @Named("spinner_menu")
    fun getAdapterSubMenus(): FixtureCreateFragmentSubMenuSpinnerAdapter
    @Named("spinner_time")
    fun getAdapterTimeMatch(): FixtureCreateFragmentTimesSpinnerAdapter
    @Named("spinner_field")
    fun getAdapterFieldMatch(): FixtureCreateFragmentFieldSpinnerAdapter
    @Named("spinner_tournament")
    fun getAdapterTournament(): FixtureCreateFragmentTournamentSpinnerAdapter
    @Named("spinner_team_local")
    fun getAdapterTeamLocal(): FixtureCreateFragmentTeamSpinnerAdapter
    @Named("spinner_team_visite")
    fun getAdapterTeamVisite(): FixtureCreateFragmentTeamSpinnerAdapter
    */
}