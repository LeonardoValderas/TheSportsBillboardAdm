package com.valdroide.thesportsbillboardinstitution.main_user.navigation.with_menu.ui.adapters

import android.content.Context
import android.view.*
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClick
import com.valdroide.thesportsbillboardinstitution.utils.GenericRecyclerAdapter

class NavigationActivityAdapter(context: Context, listener: GenericOnItemClick<MenuDrawer>) : GenericRecyclerAdapter<MenuDrawer>(context, listener) {
    override fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View  =
        LayoutInflater.from(context).inflate(R.layout.list_menu, viewGroup, false)

    override fun bindView(item: MenuDrawer, viewHolder: ViewHolder) {
        val tvMenu = viewHolder.getView(R.id.listTitle) as TextView
            tvMenu.text = item.MENU
    }
}