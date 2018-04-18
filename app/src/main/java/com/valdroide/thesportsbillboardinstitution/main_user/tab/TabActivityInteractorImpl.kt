package com.valdroide.thesportsbillboardinstitution.main_user.tab

import android.content.Context

class TabActivityInteractorImpl(val repository: TabActivityRepository) : TabActivityInteractor {
    override fun getTournamentoForIdSubmenu(context: Context, id_menu: Int) {
        repository.getTournamentoForIdSubmenu(context, id_menu)
    }
}