package com.valdroide.thesportsbillboardinstitution.main_adm.team.fragments.update.ui.adapter

import com.valdroide.thesportsbillboardinstitution.model.entities.Team

interface OnItemClickListener {
    fun onClickActiveTeam(position: Int, team: Team)
    fun onClickUnActiveTeam(position: Int, team: Team)
    fun onClickUpdateTeam(position: Int, team: Team)
    fun onClickDeleteTeam(position: Int, team: Team)
}