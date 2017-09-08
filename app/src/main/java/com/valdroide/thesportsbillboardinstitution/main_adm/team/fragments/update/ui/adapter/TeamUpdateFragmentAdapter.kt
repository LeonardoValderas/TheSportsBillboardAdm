package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.TeamUpdateFragment
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.player_item.view.*

class TeamUpdateFragmentAdapter(private var teams: MutableList<Team>?, private var listener: OnItemClickListener,
                                fragment: Fragment) : RecyclerView.Adapter<TeamUpdateFragmentAdapter.ViewHolder>() {

    private val fragment: Fragment

    init {
        this.fragment = fragment as TeamUpdateFragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View? = LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(teams!!.get(position), position, listener, fragment)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindData(team: Team, position: Int, listener: OnItemClickListener, fragment: Fragment) {
            //   YoYo.with(Techniques.FlipInX).playOn(holder.card_view);
            with(team) {
                itemView.textViewName.text = NAME
                Utils.setPicasso(fragment.activity, URL_IMAGE, R.mipmap.ic_launcher, itemView.imageViewPlayer)
                itemView.textViewPosition.text = if (IS_ACTIVE == 0) "Inactivo" else "Activo"
            }
            setOnItemClickListener(listener, position, team, fragment)
        }

        fun setOnItemClickListener(listener: OnItemClickListener, position: Int, team: Team, fragment: Fragment) {
            itemView.setOnClickListener {
                 showPopupMenu(itemView.conteiner, fragment.activity, position, team, listener);
            }
        }

        private fun showPopupMenu(view: View, context: Context, position: Int, team: Team, listener: OnItemClickListener) {
            val popup = PopupMenu(context, view, Gravity.RIGHT)
            if (team.IS_ACTIVE == 0)
                popup.getMenu().add(Menu.NONE, 1, 1, "Activar Equipo")
            else
                popup.getMenu().add(Menu.NONE, 2, 2, "Desactivar Equipo")
            popup.getMenu().add(Menu.NONE, 3, 3, "Editar Equipo")
            popup.getMenu().add(Menu.NONE, 4, 4, "Eliminar Equipo")
            popup.setOnMenuItemClickListener(MenuItemClickListener(position, team, listener))
            popup.show()
        }

        private class MenuItemClickListener(internal var position: Int, internal var team: Team, internal var listener: OnItemClickListener) : PopupMenu.OnMenuItemClickListener {

            override fun onMenuItemClick(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    1 -> {
                        team.IS_ACTIVE = 1
                        listener.onClickActiveTeam(position, team)
                        return true
                    }
                    2 -> {
                        team.IS_ACTIVE = 0
                        listener.onClickUnActiveTeam(position, team)
                        return true
                    }
                    3 -> {
                        listener.onClickUpdateTeam(position, team)
                        return true
                    }
                    4 -> {
                        listener.onClickDeleteTeam(position, team)
                        return true
                    }
                }
                return false
            }
        }
    }

    override fun getItemCount(): Int =
            teams!!.size

    fun setTeams(teams: MutableList<Team>) {
        this.teams = teams
        notifyDataSetChanged()
    }
    fun deleteTeam(position: Int) {
        teams!!.removeAt(position)
        notifyDataSetChanged()
    }
}