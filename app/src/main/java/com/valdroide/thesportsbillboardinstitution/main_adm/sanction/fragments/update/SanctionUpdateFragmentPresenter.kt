package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.events.SanctionUpdateFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.ui.SanctionUpdateFragmentView
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction

interface SanctionUpdateFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): SanctionUpdateFragmentView
    fun setViewPresenter(view: SanctionUpdateFragmentView)
    fun getSanction(context: Context)
    fun deleteSanction(context: Context, sanction: Sanction)
    fun onEventMainThread(event: SanctionUpdateFragmentEvent)
}