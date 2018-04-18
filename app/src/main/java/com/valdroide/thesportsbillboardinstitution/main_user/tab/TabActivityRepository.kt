package com.valdroide.thesportsbillboardinstitution.main_user.tab

import android.content.Context

interface TabActivityRepository {
    fun getTournamentoForIdSubmenu(context: Context, id_menu: Int)
}