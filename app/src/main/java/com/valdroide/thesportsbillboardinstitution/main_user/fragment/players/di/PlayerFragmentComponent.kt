package com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.di

import com.valdroide.thesportsbillboardinstitution.lib.di.LibsModule
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.PlayerFragmentPresenter
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui.PlayerFragment
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui.adapters.PlayerFragmentAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(PlayerFragmentModule::class, LibsModule::class))
interface PlayerFragmentComponent {
    fun inject(fragment: PlayerFragment)
}