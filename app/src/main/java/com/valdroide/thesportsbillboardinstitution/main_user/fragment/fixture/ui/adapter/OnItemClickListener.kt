package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter

import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture

interface OnItemClickListener {
    fun onClickFixture(position: Int, fixture: Fixture)
    fun onClickButtonAddMore()
}
