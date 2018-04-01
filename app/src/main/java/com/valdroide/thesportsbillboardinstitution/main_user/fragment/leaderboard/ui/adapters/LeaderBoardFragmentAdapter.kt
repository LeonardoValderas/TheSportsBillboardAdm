package com.valdroide.thesportsbillboardinstitution.main_user.fragment.leaderboard.ui.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.model.entities.LeaderBoard
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.valdroide.thesportsbillboardinstitution.R

open class LeaderBoardFragmentAdapter(internal var context: Context,
                                      internal var layoutResourceId: Int,
                                      internal var leaderBoards: MutableList<LeaderBoard>) : ArrayAdapter<LeaderBoard>(context, layoutResourceId, leaderBoards) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var row: View? = convertView
        var holder: RecordHolder? = null
        if (row == null) {
            val inflater = (context as Activity).layoutInflater
            row = inflater.inflate(layoutResourceId, parent, false)
            holder = RecordHolder()

            holder.pos = row.findViewById(R.id.pos)
            holder.equipo = row.findViewById(R.id.equipo)
            holder.puntos = row.findViewById(R.id.puntos)
            holder.pj = row.findViewById(R.id.PJ)
            holder.pg = row.findViewById(R.id.PG)
            holder.pe = row.findViewById(R.id.PE)
            holder.pp = row.findViewById(R.id.PP)
            holder.gf = row.findViewById(R.id.GF)
            holder.ge = row.findViewById(R.id.GE)
            holder.df = row.findViewById(R.id.DF)
            row.setTag(holder)

        } else
            holder = row.tag as RecordHolder

        val leaderBoard = leaderBoards[position]
        holder.pos!!.setText(leaderBoard.POSITION)
        holder.equipo!!.setText(leaderBoard.TEAM)
        holder.puntos!!.setText(leaderBoard.POINTS)
        holder.pj!!.text = leaderBoard.PJ
        holder.pg!!.text = leaderBoard.PG
        holder.pe!!.text = leaderBoard.PE
        holder.pp!!.text = leaderBoard.PP
        holder.gf!!.text = leaderBoard.GF
        holder.ge!!.text = leaderBoard.GE
//        leaderBoard.DF = (leaderBoard.GF.min leaderBoard.GE)
        holder.df!!.text = leaderBoard.DF

        return row!!
    }

    internal class RecordHolder {
        var pos: TextView? = null
        var equipo: TextView? = null
        var puntos: TextView? = null
        var pj: TextView? = null
        var pg: TextView? = null
        var pe: TextView? = null
        var pp: TextView? = null
        var gf: TextView? = null
        var ge: TextView? = null
        var df: TextView? = null
    }

    override fun getCount(): kotlin.Int {
        return leaderBoards.size
    }

    fun setLeaderBoarders(leaderBoards: MutableList<LeaderBoard>){
        this.leaderBoards = leaderBoards
        this.notifyDataSetChanged()
    }
}
