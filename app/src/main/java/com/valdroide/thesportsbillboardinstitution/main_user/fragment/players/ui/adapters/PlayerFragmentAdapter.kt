package com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui.adapters

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_user.fragment.players.ui.PlayerFragment
import com.valdroide.thesportsbillboardinstitution.model.entities.Player
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.player_item.view.*

class PlayerFragmentAdapter(private var players: MutableList<Player>?, fragment: Fragment) : RecyclerView.Adapter<PlayerFragmentAdapter.ViewHolder>() {
    private val fragment: Fragment

    init {
        this.fragment = fragment as PlayerFragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View? = null
        view = LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(players!!.get(position), position, fragment)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindData(player: Player, position: Int, fragment: Fragment) {
            //   YoYo.with(Techniques.FlipInX).playOn(holder.card_view);
            with(player) {
                itemView.textViewName.text = NAME
                Utils.setPicasso(fragment.activity, URL_IMAGE, R.mipmap.ic_launcher, itemView.imageViewPlayer)
                itemView.textViewPosition.text = POSITION
            }
        }
    }

    override fun getItemCount(): Int =
            players!!.size

    fun setPlayers(players: MutableList<Player>) {
        this.players = players
        notifyDataSetChanged()
    }
}