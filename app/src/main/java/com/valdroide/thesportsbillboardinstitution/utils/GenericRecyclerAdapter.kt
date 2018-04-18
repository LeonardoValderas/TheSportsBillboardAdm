package com.valdroide.thesportsbillboardinstitution.utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

abstract class GenericRecyclerAdapter<T>(val context: Context?,
                                         var listener: GenericOnItemClick<T>?):
                                         RecyclerView.Adapter<GenericRecyclerAdapter<T>.ViewHolder>() {

    private var items: MutableList<T>? = arrayListOf()

    constructor(context: Context) : this(context, null)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(createView(context!!, parent, viewType), listener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindView(getItem(position)!!, holder);
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var views: MutableMap<Int, View>? = null
        private var listener: GenericOnItemClick<T>? = null

        constructor(view: View, listener: GenericOnItemClick<T>?) : this(view) {
            this.listener = listener
            views = hashMapOf()
            views!!.put(0, view) // is de row inflated
            if (listener != null)
                view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            listener!!.onClick(view, adapterPosition, getItem(adapterPosition)!!)
        }

        fun initViewList(idList: IntArray) {
            for (id in idList)
                initViewById(id)
        }

        fun initViewById(id: Int) {
            val view = if (getViewAdapter() != null) getViewAdapter().findViewById<View>(id) else null

            if (view != null)
                views!!.put(id, view)
        }

        fun getViewAdapter(): View = getView(0)

        fun getView(id: Int): View {
            if (views!!.containsKey(id))
                return views!![id]!!
            else
                initViewById(id)

            return views!![id]!!
        }
    }

    protected abstract fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View

    protected abstract fun bindView(item: T, viewHolder: GenericRecyclerAdapter<T>.ViewHolder)

    fun getItem(index: Int): T? = if (items != null && index < items!!.size) items!![index] else null

    override fun getItemCount(): Int = items!!.size

    fun getList(): MutableList<T> = items!!

    fun addAll(list: List<T>) {
        reset()
        items!!.addAll(list)
        notifyDataSetChanged()
    }

    fun addAllMoreEntity(list: List<T>, t: T) {
        reset()
        items!!.addAll(list)

        notifyDataSetChanged()
    }

    fun reset() {
        items!!.clear()
    }

    fun delete(position: Int) {
        items!!.removeAt(position)
        notifyDataSetChanged()
    }

    fun setClickListener(listener: GenericOnItemClick<T>) {
        this.listener = listener
    }

    fun update(position: Int, t: T) {
        items!!.removeAt(position)
        items!!.add(position, t)
        notifyDataSetChanged()
    }
}
