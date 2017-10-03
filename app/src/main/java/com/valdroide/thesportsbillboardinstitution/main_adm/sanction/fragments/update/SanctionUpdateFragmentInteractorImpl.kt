package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction

class SanctionUpdateFragmentInteractorImpl(val repository: SanctionUpdateFragmentRepository) : SanctionUpdateFragmentInteractor {


    override fun getSanction(context: Context) {
        repository.getSanction(context)
    }

    override fun deleteSanction(context: Context, sanction: Sanction) {
        repository.deleteSanction(context, sanction)
    }
}