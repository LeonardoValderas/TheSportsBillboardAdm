package com.valdroide.thesportsbillboardinstitution.main_adm.menu_submenu.ui.adapter

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

class SubMenuActivityAdapter(var contextAdapter: Activity, var resource: Int, var subMenuDrawers: MutableList<SubMenuDrawer>)
    : BaseAdapter() {


    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var row = p1
        if (row == null) {
            val inflater = contextAdapter.layoutInflater
            row = inflater.inflate(R.layout.spinner_menu_item, p2, false);
        }

        val subMenuDrawer = subMenuDrawers[p0]

        val textViewMenu = row!!.findViewById<TextView>(R.id.textViewMenu)
        val imageViewMenu = row.findViewById<ImageView>(R.id.imageViewMenu)
        val text = subMenuDrawer.MENU + " - " + subMenuDrawer.SUBMENU
        textViewMenu!!.text = text
        if (subMenuDrawer.IS_ACTIVE == 0)
            imageViewMenu.setColorFilter(ContextCompat.getColor(contextAdapter, R.color.redColor))
        else
            imageViewMenu.setColorFilter(ContextCompat.getColor(contextAdapter, R.color.greenColor))
        return row
    }

    override fun getItem(p0: Int): Any? = null

    override fun getItemId(p0: Int): Long = 0

    override fun getCount(): Int = subMenuDrawers.size

    fun refresh(subMenuDrawers: MutableList<SubMenuDrawer>) {
        this.subMenuDrawers = subMenuDrawers
        notifyDataSetChanged()
    }
}