package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction

interface SanctionUpdateFragmentRepository {
    fun getSanction(context: Context)
    fun deleteSanction(context: Context, sanction: Sanction)
}