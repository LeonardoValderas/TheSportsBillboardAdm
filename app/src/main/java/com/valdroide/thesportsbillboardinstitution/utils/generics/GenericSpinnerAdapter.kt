package com.valdroide.thesportsbillboardinstitution.utils.generics

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClick

abstract class GenericSpinnerAdapter<T>(var list :MutableList<T>): BaseAdapter() {

    var any: Any? = null

    override fun getView(int: Int, view: View?, viewGroup: ViewGroup?): View =
            setView(int, view, viewGroup)

    protected abstract fun setView(int:Int, view: View?, viewGroup: ViewGroup?): View

    override fun getItem(p0: Int): Any? = null

    override fun getItemId(p0: Int): Long = 0

    override fun getCount(): Int = list.size

    fun refresh(list: MutableList<T>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun addItem(t: T) {
        list.add(0, t)
        notifyDataSetChanged()
    }

    fun updateItem(i:Int, t: T) {
        list.removeAt(i)
        list.add(i, t)
        notifyDataSetChanged()
    }
}