package com.valdroide.thesportsbillboardinstitution.main_user.fragment.fixture.ui.adapter

import android.content.Context
import android.view.*
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClick
import com.valdroide.thesportsbillboardinstitution.utils.GenericRecyclerAdapter

class FixtureAdapter(context: Context, listener: GenericOnItemClick<Fixture>) : GenericRecyclerAdapter<Fixture>(context, listener) {

    companion object {
        private val FIXTURE = 0
        private val BUTTON_MORE = 1
    }

    private var isFixture: Boolean = true

    override fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View {
        if (viewType == FIXTURE) {
            isFixture = true
            return LayoutInflater.from(context).inflate(R.layout.fixture_item, viewGroup, false)
        } else {
            isFixture = false
            return LayoutInflater.from(context).inflate(R.layout.item_button_add_more, viewGroup, false)
        }
    }


    override fun bindView(item: Fixture, viewHolder: ViewHolder) {
        if (isFixture){
            val tvMenu = viewHolder.getView(R.id.listTitle) as TextView
        } else {}


          //  tvMenu.text = item.MENU
    }
}