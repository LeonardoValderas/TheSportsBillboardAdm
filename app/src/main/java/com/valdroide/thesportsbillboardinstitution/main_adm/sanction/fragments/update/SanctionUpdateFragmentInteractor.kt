package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction

interface SanctionUpdateFragmentInteractor {
    fun getSanction(context: Context)
    fun deleteSanction(context: Context, Sanction: Sanction)
 }