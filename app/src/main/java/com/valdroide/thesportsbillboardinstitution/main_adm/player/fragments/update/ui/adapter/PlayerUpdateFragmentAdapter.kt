package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui.adapter

import android.content.Context
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClick
import com.valdroide.thesportsbillboardinstitution.utils.GenericRecyclerAdapter
import com.valdroide.thesportsbillboardinstitution.utils.helper.ImageHelper

class PlayerUpdateFragmentAdapter(context: Context, listener: GenericOnItemClick<Player>) : GenericRecyclerAdapter<Player>(context, listener) {
    override fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View =
            LayoutInflater.from(context).inflate(R.layout.player_item, viewGroup, false)

    override fun bindView(item: Player, viewHolder: ViewHolder) {

        val tvName = viewHolder.getView(R.id.textViewName) as TextView
        val ivTeam = viewHolder.getView(R.id.imageViewPlayer) as ImageView
        val tvPosition = viewHolder.getView(R.id.textViewPosition) as TextView
        val tvMenuSubMenu = viewHolder.getView(R.id.textViewMenuSubMenu) as TextView
        val tvActive = viewHolder.getView(R.id.textViewActive) as TextView

        with(item) {
            tvName.text = NAME
            ImageHelper.setPicasso(context!!, URL_IMAGE, R.drawable.shield_icon, ivTeam)
            tvPosition.text = POSITION
            tvMenuSubMenu.visibility = View.VISIBLE
            tvMenuSubMenu.text = context.getString(R.string.menu_concatenate, MENU, SUBMENU)
            tvActive.visibility = View.VISIBLE
            tvActive.text = if (IS_ACTIVE == 0) "Inactivo" else "Activo"


        }
    }
}
