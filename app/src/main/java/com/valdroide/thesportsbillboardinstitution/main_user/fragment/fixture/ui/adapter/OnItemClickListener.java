package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter;

import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture;

public interface OnItemClickListener {
    void onClickFixture(int position, Fixture fixture);
    void onClickButtonAddMore();
}
