package com.valdroide.thesportsbillboardinstitution.main_user.tab

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.main_user.tab.events.TabActivityEvent

interface TabActivityPresenter {
    fun onCreate()
    fun onDestroy()
    fun getTournamentoForIdSubmenu(context: Context, id_menu: Int)
    fun onEventMainThread(event: TabActivityEvent)
}