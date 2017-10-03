package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.create

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction

class SanctionCreateFragmentInteractorImpl(val repository: SanctionCreateFragmentRepository) : SanctionCreateFragmentInteractor {


    override fun getPlayerForIdSubMenu(context: Context, id_submenu: Int){
        repository.getPlayerForIdSubMenu(context, id_submenu)
    }

    override fun getSanction(context: Context, id_sanction: Int) {
        repository.getSanction(context, id_sanction)
    }

    override fun saveSanction(context: Context, sanction: Sanction) {
        repository.saveSanction(context, sanction)
    }

    override fun updateSanction(context: Context, sanction: Sanction) {
        repository.updateSanction(context, sanction)
    }

    override fun getSubMenusAndPlayers(context: Context) {
        repository.getSubMenusAndPlayers(context)
    }
}