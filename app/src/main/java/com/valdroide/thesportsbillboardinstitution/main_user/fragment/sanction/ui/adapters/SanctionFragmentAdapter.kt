package com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.adapters

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.reflect.TypeToken
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.sanction.ui.SanctionFragment
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import kotlinx.android.synthetic.main.sanction_item.view.*

class SanctionFragmentAdapter(private var sanctions: MutableList<Sanction>?, fragment: Fragment) : RecyclerView.Adapter<SanctionFragmentAdapter.ViewHolder>() {
    private val fragment: Fragment

    init {
        this.fragment = fragment as SanctionFragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View? = null
        view = LayoutInflater.from(parent.context).inflate(R.layout.sanction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindData(sanctions!![position], fragment)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindData(sanction: Sanction, fragment: Fragment) {
            //   YoYo.with(Techniques.FlipInX).playOn(holder.card_view);
            with(sanction) {
                itemView.textViewName.text = NAME
                itemView.textViewYellow.text = YELLOW
                itemView.textViewRed.text = RED
                itemView.textViewSanction.text = SANCTION
                itemView.textViewObsevation.text = OBSERVATION
            }
        }

    }

    override fun getItemCount(): Int =
            sanctions!!.size


    fun setSanctions(sanctions: MutableList<Sanction>) {
        this.sanctions = sanctions
        notifyDataSetChanged()
    }


}