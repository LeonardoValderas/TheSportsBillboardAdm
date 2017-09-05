package com.valdroide.thesportsbillboardinstitution.main_user.fragment.players

import android.content.Context

class PlayerFragmentInteractorImpl(val repository: PlayerFragmentRepository) : PlayerFragmentInteractor {

    override fun getPlayers(context: Context, id_submenu: Int) {
        repository.getPlayers(context, id_submenu)
    }
}