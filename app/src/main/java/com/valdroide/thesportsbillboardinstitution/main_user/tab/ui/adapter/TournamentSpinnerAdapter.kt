package com.valdroide.thesportsbillboardinstitution.main_user.tab.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament
import com.valdroide.thesportsbillboardinstitution.utils.generics.GenericSpinnerAdapter
import org.jetbrains.anko.layoutInflater

class TournamentSpinnerAdapter(list: MutableList<Tournament>,
                               val context: Context) : GenericSpinnerAdapter<Tournament>(list) {
    var tournament = Tournament()

    override fun setView(int: Int, view: View?, viewGroup: ViewGroup?): View {
        val row = context.layoutInflater.inflate(R.layout.spinner_toolbar_item, viewGroup, false)

        tournament = list[int]

        val textView = row!!.findViewById<TextView>(R.id.tv_text)
        val actual = row.findViewById<TextView>(R.id.tv_text_actual)

        with(tournament) {
            textView.text = TOURNAMENT

            if (IS_ACTUAL == 0)
                actual.visibility = View.INVISIBLE
            else
                actual.visibility = View.INVISIBLE
        }
        return row
    }
}