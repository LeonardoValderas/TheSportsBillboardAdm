package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu

import android.content.Context

class NavigationActivityInteractorImpl(private val repository: NavigationActivityRepository) : NavigationActivityInteractor {

    override fun getMenus(context: Context) {
        repository.getMenus(context)
    }
}
