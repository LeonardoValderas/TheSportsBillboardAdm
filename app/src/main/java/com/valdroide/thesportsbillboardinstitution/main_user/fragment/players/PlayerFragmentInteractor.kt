package com.valdroide.thesportsbillboardinstitution.main_user.fragment.players

import android.content.Context

interface PlayerFragmentInteractor {
    fun getPlayers(context: Context, id_submenu: Int)
}