package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.create.ui.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.TimeMatch
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapterDos
import org.jetbrains.anko.layoutInflater

class FixtureCreateFragmentTimesSpinnerAdapter(list: MutableList<TimeMatch>,
val context: Context) : GenericSpinnerAdapterDos<TimeMatch>(list) {
    var timeMatch = TimeMatch()

    override fun setView(int: Int, view: View?, viewGroup: ViewGroup?): View {
        val row = context.layoutInflater.inflate(R.layout.spinner_menu_item, viewGroup, false)
        timeMatch = list[int]
        textViewMenu = row!!.findViewById<TextView>(R.id.textViewMain)
        imageViewMenu = row.findViewById<ImageView>(R.id.imageViewMain)
        with(timeMatch) {
            textViewMenu.text = TIME_MATCH
            imageViewMenu.setColorFilter(ContextCompat.getColor(context, R.color.greenColor))
        }
        return row
    }
}