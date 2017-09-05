package com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction

import android.content.Context

interface SanctionFragmentInteractor {
    fun getSanctions(context: Context, id_submenu: Int)
}