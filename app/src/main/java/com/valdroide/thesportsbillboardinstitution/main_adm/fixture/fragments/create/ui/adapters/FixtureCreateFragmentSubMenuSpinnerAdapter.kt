package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapterDos
import org.jetbrains.anko.layoutInflater

class FixtureCreateFragmentSubMenuSpinnerAdapter(list: MutableList<SubMenuDrawer>,
                                                  val context: Context) : GenericSpinnerAdapterDos<SubMenuDrawer>(list) {
    var subMenu = SubMenuDrawer()

    override fun setView(int: Int, view: View?, viewGroup: ViewGroup?): View {
        val row = context.layoutInflater.inflate(R.layout.spinner_menu_item, viewGroup, false)
        subMenu = list[int]
        textViewMenu = row!!.findViewById<TextView>(R.id.textViewMain)
        imageViewMenu = row.findViewById<ImageView>(R.id.imageViewMain)
        with(subMenu) {
            val text = MENU + "-" + SUBMENU
            textViewMenu.text = text
            if (IS_ACTIVE == 0)
                imageViewMenu.setColorFilter(ContextCompat.getColor(context, R.color.redColor))
            else
                imageViewMenu.setColorFilter(ContextCompat.getColor(context, R.color.greenColor))
        }
        return row
    }
}