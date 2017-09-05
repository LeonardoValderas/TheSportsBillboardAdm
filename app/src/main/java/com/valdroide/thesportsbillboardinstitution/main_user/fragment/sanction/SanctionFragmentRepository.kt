package com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction

import android.content.Context

interface SanctionFragmentRepository {
    fun getSanctions(context: Context, id_submenu: Int)
}