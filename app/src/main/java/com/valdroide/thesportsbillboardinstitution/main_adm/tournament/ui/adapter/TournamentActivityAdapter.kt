package com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.adapter

import android.app.Activity
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.CheckBox
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.tournament.ui.TournamentActivity
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer
import com.valdroide.thesportsbillboardinstitution.model.entities.Tournament
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.tournament_item.view.*

class TournamentActivityAdapter(private var subMenus: MutableList<SubMenuDrawer>?,
                                private var tournament: Tournament?,
                                private var listener: GenericOnItemClickListener.actualUnActual,
                                activity: Activity, var isActual: Boolean) : RecyclerView.Adapter<TournamentActivityAdapter.ViewHolder>() {

    private val activityTounament: TournamentActivity

    init {
        this.activityTounament = activity as TournamentActivity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View? = LayoutInflater.from(parent.context).inflate(R.layout.tournament_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(subMenus!![position], position, listener, tournament!!, activityTounament)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindData(subMenus: SubMenuDrawer, position: Int, listener: GenericOnItemClickListener.actualUnActual,
                     tournament: Tournament, activity: TournamentActivity) {
            //   YoYo.with(Techniques.FlipInX).playOn(holder.card_view);
            with(subMenus) {
                itemView.textViewTournament.text = SUBMENU
                if (IS_CHECKED == 1) {
                    itemView.checkBoxTournament.isChecked = true
                    activity.isActual = true
                } else
                    itemView.checkBoxTournament.isChecked = false
            }
            setOnItemClickListener(listener, position, subMenus, tournament, activity)
        }

        fun setOnItemClickListener(listener: GenericOnItemClickListener.actualUnActual, position: Int,
                                   subMenus: SubMenuDrawer, tournament: Tournament?, activity: TournamentActivity) {
            itemView.checkBoxTournament.setOnClickListener(View.OnClickListener { view ->
                if (activity.isActual && itemView.checkBoxTournament.isChecked) {
                    itemView.checkBoxTournament.isChecked = false
                    listener.snackBarIsActual()
                } else
                    listener.boxEvent(position, subMenus, tournament!!.ID_TOURNAMENT_KEY, itemView.checkBoxTournament.isChecked)
            })
        }
    }

    override fun getItemCount(): Int =
            subMenus!!.size

    fun setSubMenusForTournament(subMenus: MutableList<SubMenuDrawer>, tournament: Tournament) {
        this.subMenus = subMenus
        this.tournament = tournament
        notifyDataSetChanged()
    }

    fun updateTournament(position: Int, subMenu: SubMenuDrawer) {
        subMenus!!.removeAt(position)
        subMenus!!.add(position, subMenu)
        notifyDataSetChanged()
    }
}