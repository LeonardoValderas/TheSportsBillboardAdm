package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.Position
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapterDos
import org.jetbrains.anko.layoutInflater

class PlayerCreateFragmentPositionSpinnerAdapter(list: MutableList<Position>,
                                                 val context: Context) : GenericSpinnerAdapterDos<Position>(list) {
    var position = Position()

    override fun setView(int: Int, view: View?, viewGroup: ViewGroup?): View {
        val row = context.layoutInflater.inflate(R.layout.spinner_menu_item, viewGroup, false)
        position = list[int]
        textViewMenu = row!!.findViewById<TextView>(R.id.textViewMain)
        imageViewMenu = row.findViewById<ImageView>(R.id.imageViewMain)
        with(position) {
            textViewMenu.text = POSITION
            imageViewMenu.setColorFilter(ContextCompat.getColor(context, R.color.greenColor))
        }
        return row
    }
}