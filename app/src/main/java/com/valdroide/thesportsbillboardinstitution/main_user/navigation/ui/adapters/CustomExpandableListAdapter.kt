package com.valdroide.thesportsbillboardinstitution.main_user.navigation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView

import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.MenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

open class CustomExpandableListAdapter(var context: Context, var expandableListTitle: List<String>,
                                  var mExpandableListMenusAndSubMenu: Map<String, List<SubMenuDrawer>>) : BaseExpandableListAdapter() {

    private var mExpandableListMenus: List<MenuDrawer>? = null
    private val mLayoutInflater: LayoutInflater

    init {
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any =
            mExpandableListMenusAndSubMenu[expandableListTitle[listPosition]]!!
                .get(expandedListPosition)


    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long =
        expandedListPosition.toLong()


    override fun getGroupView(listPosition: Int, isExpanded: Boolean,
                              convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val categoty = getGroup(listPosition) as String
        //    int isUpdate = getUpdateCategory(categoty);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_menu, null)
        }
        val textViewCategory = convertView!!
                .findViewById<View>(R.id.listTitle) as TextView
        textViewCategory.text = categoty
        val imageView = convertView.findViewById<View>(R.id.imageViewIcon) as ImageView
        //        if (isUpdate == 1)
        //            imageView.setVisibility(View.VISIBLE);
        //        else
        //            imageView.setVisibility(View.GONE);
        return convertView
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int,
                              isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as SubMenuDrawer
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_sub_menu, null)
        }
        val textViewSubCategory = convertView!!
                .findViewById<View>(R.id.expandedListItem) as TextView
        textViewSubCategory.text = expandedListText.SUBMENU

        val imageView = convertView.findViewById<View>(R.id.imageViewIcon) as ImageView
        //        if (expandedListText.getIS_UPDATE() == 1)
        //            imageView.setVisibility(View.VISIBLE);
        //        else
        //            imageView.setVisibility(View.GONE);
        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int =
            mExpandableListMenusAndSubMenu[expandableListTitle[listPosition]]!!.size


    override fun getGroup(listPosition: Int): Any =
            expandableListTitle[listPosition]


    override fun getGroupCount(): Int =
            expandableListTitle.size


    override fun getGroupId(listPosition: Int): Long =
        listPosition.toLong()


    override fun hasStableIds(): Boolean =
        false


    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean =
        true


    fun setList(expandableListCategories: List<MenuDrawer>, expandableListTitle: List<String>, expandableListDetail: Map<String, List<SubMenuDrawer>>) {
        mExpandableListMenus = expandableListCategories
        this.expandableListTitle = expandableListTitle
        mExpandableListMenusAndSubMenu = expandableListDetail
        notifyDataSetChanged()
    }

    fun updateAdapter() {
        notifyDataSetChanged()
    }

    //    private int getUpdateCategory(String name) {
    //        for (int i = 0; i < mExpandableListMenus.size(); i++) {
    //            if (mExpandableListMenus.get(i).getMENU().equalsIgnoreCase(name)) {
    //                return mExpandableListMenus.get(i).getIS_UPDATE();
    //            }
    //        }
    //        return 0;
    //    }
}