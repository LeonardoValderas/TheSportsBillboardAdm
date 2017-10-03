package com.valdroide.thesportsbillboardinstitution.main_adm.news.fragments.create.ui

import com.valdroide.thesportsbillboardinstitution.model.entities.News
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

interface NewsCreateFragmentView {
    fun onClickPhoto()
    fun onClickButtonSave()
    fun setNewsUpdate(news: News)
    fun setSubMenus(submenus: MutableList<SubMenuDrawer>)
    fun fillViewUpdate()
    fun saveSuccess()
    fun editSuccess()
    fun cleanViews()
    fun setVisibilityViews(isVisible: Int)
    fun setError(error: String)
    fun hideProgressDialog()
    fun showProgressDialog()
}