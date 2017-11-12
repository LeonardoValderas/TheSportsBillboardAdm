package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.update.ui.PlayerUpdateFragment
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.player_item.view.*

class PlayerUpdateFragmentAdapter(private var players: MutableList<Player>?,
                                  private var listener: GenericOnItemClickListener.withActive,
                                  fragment: Fragment) : RecyclerView.Adapter<PlayerUpdateFragmentAdapter.ViewHolder>() {

    private val fragment: Fragment

    init {
        this.fragment = fragment as PlayerUpdateFragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View? = LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(players!!.get(position), position, listener, fragment)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindData(player: Player,
                     position: Int,
                     listener: GenericOnItemClickListener.withActive,
                     fragment: Fragment) {
            //   YoYo.with(Techniques.FlipInX).playOn(holder.card_view);
            with(player) {
                itemView.textViewName.text = NAME
                Utils.setPicasso(fragment.activity, URL_IMAGE, R.drawable.players_icon, itemView.imageViewPlayer)
                itemView.textViewPosition.text = POSITION
                itemView.textViewMenuSubMenu.visibility = View.VISIBLE
                val conca = MENU + " - " + SUBMENU
                itemView.textViewMenuSubMenu.text = conca
                itemView.textViewActive.visibility = View.VISIBLE
                itemView.textViewActive.text = if (IS_ACTIVE == 0) "Inactivo" else "Activo"
            }
            setOnItemClickListener(listener, position, player, fragment)
        }

        fun setOnItemClickListener(listener: GenericOnItemClickListener.withActive,
                                   position: Int,
                                   player: Player,
                                   fragment: Fragment) {
            itemView.setOnClickListener {
                showPopupMenu(itemView.conteiner, fragment.activity, position, player, listener);
            }
        }

        private fun showPopupMenu(view: View, context: Context,
                                  position: Int,
                                  player: Player,
                                  listener: GenericOnItemClickListener.withActive) {
            val popup = PopupMenu(context, view, Gravity.END )
            if (player.IS_ACTIVE == 0)
                popup.getMenu().add(Menu.NONE, 1, 1, "Activar Jugador")
            else
                popup.getMenu().add(Menu.NONE, 2, 2, "Desactivar Jugador")
            popup.getMenu().add(Menu.NONE, 3, 3, "Editar Jugador")
            popup.getMenu().add(Menu.NONE, 4, 4, "Eliminar Jugador")
            popup.setOnMenuItemClickListener(MenuItemClickListener(position, player, listener))
            popup.show()
        }

        private class MenuItemClickListener(internal var position: Int,
                                            internal var player: Player,
                                            internal var listener: GenericOnItemClickListener.withActive) : PopupMenu.OnMenuItemClickListener {

            override fun onMenuItemClick(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    1 -> {
                        player.IS_ACTIVE = 1
                        listener.onClickActive(position, player)
                        return true
                    }
                    2 -> {
                        player.IS_ACTIVE = 0
                        listener.onClickUnActive(position, player)
                        return true
                    }
                    3 -> {
                        listener.onClickUpdate(position, player)
                        return true
                    }
                    4 -> {
                        listener.onClickDelete(position, player)
                        return true
                    }
                }
                return false
            }
        }
    }

    override fun getItemCount(): Int =
            players!!.size

    fun setPlayers(Players: MutableList<Player>) {
        this.players = Players
        notifyDataSetChanged()
    }

    fun deletePlayer(position: Int) {
        players!!.removeAt(position)
        notifyDataSetChanged()
    }
}