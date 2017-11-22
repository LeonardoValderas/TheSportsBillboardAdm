package com.valdroide.thesportsbillboardinstitution.utils

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

abstract class GenericSpinnerAdapterDos<T>(var list :MutableList<T>)
    : BaseAdapter() {

    var any: Any? = null
    lateinit var textViewMenu: TextView
    lateinit var imageViewMenu: ImageView

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