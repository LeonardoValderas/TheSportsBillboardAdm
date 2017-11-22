package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.FieldMatch
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapterDos
import org.jetbrains.anko.layoutInflater

class FixtureCreateFragmentFieldSpinnerAdapter(list: MutableList<FieldMatch>,
                                                   val context: Context) : GenericSpinnerAdapterDos<FieldMatch>(list) {
    var fieldMatch = FieldMatch()

    override fun setView(int: Int, view: View?, viewGroup: ViewGroup?): View {
        val row = context.layoutInflater.inflate(R.layout.spinner_menu_item, viewGroup, false)
        fieldMatch = list[int]
        textViewMenu = row!!.findViewById<TextView>(R.id.textViewMain)
        imageViewMenu = row.findViewById<ImageView>(R.id.imageViewMain)
        with(fieldMatch) {
            textViewMenu.text = FIELD_MATCH
            imageViewMenu.setColorFilter(ContextCompat.getColor(context, R.color.greenColor))
        }
        return row
    }
}