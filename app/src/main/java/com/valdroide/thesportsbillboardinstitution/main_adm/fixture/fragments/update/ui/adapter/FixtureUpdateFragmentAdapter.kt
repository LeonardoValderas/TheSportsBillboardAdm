package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.Team
import com.valdroide.thesportsbillboardinstitution.utils.GenericRecyclerAdapter
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fixture_item.view.*

class FixtureUpdateFragmentAdapter(context: Context) : GenericRecyclerAdapter<Fixture>(context, null) {

    override fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View {
        val view: View? = LayoutInflater.from(context).inflate(R.layout.fixture_item, viewGroup, false)
        return view!!
    }

    override fun bindView(fixture: Fixture, itemView: ViewHolder) {
        val tvDateHour = itemView.getView(R.id.textViewDateHour) as TextView
        val tvLocalResult = itemView.getView(R.id.textViewLocalResult) as TextView
        val tvVisitResult = itemView.getView(R.id.textViewVisitResult) as TextView
        val tvTimes = itemView.getView(R.id.textViewTimes) as TextView
        val tvLocalName = itemView.getView(R.id.textViewLocalName) as TextView
        val tvVisitName = itemView.getView(R.id.textViewVisitName) as TextView
        val tvFieldMatch = itemView.getView(R.id.textViewFieldMatch) as TextView
        val tvAddress = itemView.getView(R.id.textViewAddress) as TextView
        val tvObservation = itemView.getView(R.id.textViewObservation) as TextView
        val ivLocalTeam = itemView.getView(R.id.imageViewLocalTeam) as ImageView
        val ivVisitTeam = itemView.getView(R.id.imageViewVisitTeam) as ImageView
        with(fixture) {
//            DATE_MATCH + " - " + HOUR_MATCH + " hs."
            val date_hour = context!!.getString(R.string.fixture_concatenate, DATE_MATCH ,HOUR_MATCH)
            tvDateHour.text = date_hour
            com.valdroide.thesportsbillboardinstitution.utils.Utils.setPicasso(context, URL_LOCAL_TEAM, R.drawable.shield_icon, ivLocalTeam)
            com.valdroide.thesportsbillboardinstitution.utils.Utils.setPicasso(context, URL_VISIT_TEAM, R.drawable.shield_icon, ivVisitTeam)
            tvLocalResult.text = RESULT_LOCAL
            tvVisitResult.text = RESULT_VISITE
            tvTimes.text = TIMES_MATCH
            tvLocalName.text = NAME_LOCAL_TEAM
            tvVisitName.text = NAME_VISITA_TEAM
            tvFieldMatch.text = NAME_FIELD
            tvAddress.text = ADDRESS
            if (OBSERVATION != null && !OBSERVATION.isEmpty()) {
                tvObservation.visibility = android.view.View.VISIBLE
                tvObservation.text = OBSERVATION
            } else {
                tvObservation.visibility = android.view.View.GONE
            }
        }

    }
}