package com.valdroide.thesportsbillboardinstitution.utils

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.*
import java.util.*

class GenericSpinnerAdapter(var contextActivity: Activity?, var contextFragment: Fragment?, var list :MutableList<*>, var type: Int)
    : BaseAdapter() {

    var any: Any? = null
    lateinit var textViewMenu: TextView
    lateinit var imageViewMenu: ImageView

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var row = p1
        if (row == null) {
            var inflater: LayoutInflater? = null
            if (contextActivity != null)
                inflater = contextActivity!!.layoutInflater
            else
                inflater = contextFragment!!.activity.layoutInflater
            row = inflater.inflate(R.layout.spinner_menu_item, p2, false);
        }

        any = list[p0]

        textViewMenu = row!!.findViewById<TextView>(R.id.textViewMain)
        imageViewMenu = row.findViewById<ImageView>(R.id.imageViewMain)

        setEntity(type)
        return row
    }

    override fun getItem(p0: Int): Any? = null

    override fun getItemId(p0: Int): Long = 0

    override fun getCount(): Int = list.size

    private fun setEntity(type: Int) {
        when (type) {
            1 -> { //MENU
                val menu = any as SubMenuDrawer
                val text = menu.MENU + "-" + menu.SUBMENU
                textViewMenu.text = text
                setActiveImage(menu.IS_ACTIVE)
            }
            2 -> {
                val submenu = any as SubMenuDrawer
                val text = submenu.MENU + " - " + submenu.SUBMENU
                textViewMenu.text = text
                setActiveImage(submenu.IS_ACTIVE)
            }
            3 -> {
                val player = any as Player
                textViewMenu.text = player.NAME
                setActiveImage(player.IS_ACTIVE)
            }
            4 -> {
                val fieldMatch = any as FieldMatch
                textViewMenu.text = fieldMatch.FIELD_MATCH
                setActiveImage(1)
            }
            5 -> {
                val timeMatch = any as TimeMatch
                textViewMenu.text = timeMatch.TIME_MATCH
                setActiveImage(1)
            }
            6 -> {
                val tournament = any as Tournament
                textViewMenu.text = tournament.TOURNAMENT
                setActiveImage(1)
            }
            7 -> {
                val team = any as Team
                textViewMenu.text = team.NAME
                setActiveImage(team.IS_ACTIVE)
            }
        }
    }

    private fun setActiveImage(is_active: Int) {
        if (is_active == 0) {
            if (contextActivity != null)
                imageViewMenu.setColorFilter(ContextCompat.getColor(contextActivity, R.color.redColor))
            else
                imageViewMenu.setColorFilter(ContextCompat.getColor(contextFragment!!.activity, R.color.redColor))
        } else {
            if (contextActivity != null)
                imageViewMenu.setColorFilter(ContextCompat.getColor(contextActivity, R.color.greenColor))
            else
                imageViewMenu.setColorFilter(ContextCompat.getColor(contextFragment!!.activity, R.color.greenColor))
        }
    }


    fun refresh(list: MutableList<*>, type: Int) {
        this.list = list
        this.type = type
        notifyDataSetChanged()
    }
}