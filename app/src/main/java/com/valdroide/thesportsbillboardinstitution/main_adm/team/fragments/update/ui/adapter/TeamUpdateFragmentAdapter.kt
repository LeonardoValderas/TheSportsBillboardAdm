package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import com.valdroide.thesportsbillboardinstitution.utils.GenericRecyclerAdapter
import com.valdroide.thesportsbillboardinstitution.utils.Utils

class TeamUpdateFragmentAdapter(context: Context) : GenericRecyclerAdapter<Team>(context, null) {

    override fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View {
        val view: View? = LayoutInflater.from(context).inflate(R.layout.player_item, viewGroup, false)
        return view!!
    }

    override fun bindView(item: Team, viewHolder: ViewHolder) {
        val tvName = viewHolder.getView(R.id.textViewName) as TextView
        val ivTeam = viewHolder.getView(R.id.imageViewPlayer) as ImageView
        val tvActive = viewHolder.getView(R.id.textViewPosition) as TextView
        with(item) {
            tvName.text = NAME
            Utils.setPicasso(context!!, URL_IMAGE, R.drawable.shield_icon, ivTeam)
            tvActive.text = if (IS_ACTIVE == 0) "Inactivo" else "Activo"
        }
    }
}