package com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction

import android.content.Context

class SanctionFragmentInteractorImpl(val repository: SanctionFragmentRepository) : SanctionFragmentInteractor {

    override fun getSanctions(context: Context, id_submenu: Int) {
        repository.getSanctions(context, id_submenu)
    }
}