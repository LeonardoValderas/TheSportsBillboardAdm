package com.valdroide.thesportsbillboardinstitution.fixture

import android.support.v7.widget.RecyclerView
import org.robolectric.annotation.Implements
import org.robolectric.annotation.RealObject
import android.util.SparseArray
import android.view.View
import com.valdroide.thesportsbillboardinstitution.R.id.recyclerView
import org.robolectric.annotation.Implementation
import com.valdroide.thesportsbillboardinstitution.R.id.recyclerView




@Implements(RecyclerView.Adapter::class)
class ShadowRecyclerViewAdapter {
    @RealObject
    lateinit private var realObject: RecyclerView.Adapter<RecyclerView.ViewHolder>
    lateinit private var recyclerView: RecyclerView
    private val holders = SparseArray<RecyclerView.ViewHolder>()

    @Implementation
    fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    @Implementation
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        realObject.onBindViewHolder(holder, position)
        holders.put(position, holder)
    }

    fun getItemCount(): Int {
        return realObject.itemCount
    }

    fun performItemClick(position: Int): Boolean {
        val holderView = holders.get(position).itemView
        return holderView.performClick()
    }

    fun performItemClickOverViewInHolder(position: Int, viewId: Int): Boolean {
        var valueToReturn = false
        val holderView = holders.get(position).itemView
        val viewToClick = holderView.findViewById<View>(viewId)
        if (viewToClick != null) {
            valueToReturn = viewToClick.performClick()
        }

        return valueToReturn
    }

    fun itemVisible(position: Int) {
        val holder = realObject.createViewHolder(recyclerView,
                realObject.getItemViewType(position))
        onBindViewHolder(holder, position)
    }

    fun getViewForHolderPosition(position: Int): View =
         holders.get(position).itemView
}