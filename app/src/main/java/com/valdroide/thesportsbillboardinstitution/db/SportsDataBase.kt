package com.valdroide.thesportsbillboardinstitution.db

import com.raizlabs.android.dbflow.annotation.Database

@Database(name = SportsDataBase.NAME, version = SportsDataBase.VERSION)
object SportsDataBase {
    const val VERSION: Int = 1;
    const val NAME: String = "SportsDataBase";
}
