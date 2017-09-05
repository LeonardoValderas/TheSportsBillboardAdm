package com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.events.SanctionFragmentEvent
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.SanctionFragmentView

interface SanctionFragmentPresenter {
    fun onCreate()
    fun onDestroy()
    fun getViewPresenter(): SanctionFragmentView
    fun setViewPresenter(view: SanctionFragmentView)
    fun getSanctions(context: Context, id_submenu: Int)
    fun onEventMainThread(event: SanctionFragmentEvent)
}