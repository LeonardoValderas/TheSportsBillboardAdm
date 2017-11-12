package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.FixtureUpdateFragment
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener_2
import com.valdroide.thesportsbillboardinstitution.utils.Utils
import kotlinx.android.synthetic.main.fixture_item.view.*

class FixtureUpdateFragmentAdapter(private var fixtures: MutableList<Fixture>?, private var listener: GenericOnItemClickListener.fixture,
                                   fragment: Fragment) : RecyclerView.Adapter<FixtureUpdateFragmentAdapter.ViewHolder>() {

    private val fragment: Fragment

    init {
        this.fragment = fragment as FixtureUpdateFragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View? = LayoutInflater.from(parent.context).inflate(R.layout.fixture_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(fixtures!![position], position, listener, fragment)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindData(fixture: Fixture, position: Int, listener: GenericOnItemClickListener.fixture, fragment: Fragment) {
            //   YoYo.with(Techniques.FlipInX).playOn(holder.card_view);
            with(fixture) {
                val date_hour = DATE_MATCH + " - " + HOUR_MATCH + " hs."
                itemView.textViewDateHour.text = date_hour
                Utils.setPicasso(fragment.activity, URL_LOCAL_TEAM, R.drawable.shield_icon, itemView.imageViewLocalTeam)
                Utils.setPicasso(fragment.activity, URL_VISIT_TEAM, R.drawable.shield_icon, itemView.imageViewVisitTeam)
                itemView.textViewLocalResult.text = RESULT_LOCAL
                itemView.textViewVisitResult.text = RESULT_VISITE
                itemView.textViewTimes.text = TIMES_MATCH
                itemView.textViewLocalName.text = NAME_LOCAL_TEAM
                itemView.textViewVisitName.text = NAME_VISITA_TEAM
                itemView.textViewFieldMatch.text = NAME_FIELD
                itemView.textViewAddress.text = ADDRESS
                if (OBSERVATION != null && !OBSERVATION.isEmpty()) {
                    itemView.textViewObservation.visibility = View.VISIBLE
                    itemView.textViewObservation.text = OBSERVATION
                } else {
                    itemView.textViewObservation.visibility = View.GONE
                }
            }
            setOnItemClickListener(listener, position, fixture, fragment)
        }

        fun setOnItemClickListener(listener: GenericOnItemClickListener.fixture, position: Int, Fixture: Fixture, fragment: Fragment) {
            itemView.setOnClickListener {
                showPopupMenu(itemView.conteiner, fragment.activity, position, Fixture, listener)
            }
        }

        private fun showPopupMenu(view: View, context: Context, position: Int, Fixture: Fixture, listener: GenericOnItemClickListener.fixture) {
            val popup = PopupMenu(context, view, Gravity.END)
            popup.getMenu().add(Menu.NONE, 1, 1, "Editar Resultado")
            popup.getMenu().add(Menu.NONE, 2, 2, "Editar Fixture")
            popup.getMenu().add(Menu.NONE, 3, 3, "Eliminar Fixture")
            popup.setOnMenuItemClickListener(MenuItemClickListener(position, Fixture, listener))
            popup.show()
        }

        private class MenuItemClickListener(internal var position: Int, internal var Fixture: Fixture, internal var listener: GenericOnItemClickListener.fixture) : PopupMenu.OnMenuItemClickListener {

            override fun onMenuItemClick(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    1 -> {
                        listener.onClickResult(position, Fixture)
                        return true
                    }
                    2 -> {
                        listener.onClickUpdate(position, Fixture)
                        return true
                    }
                    3 -> {
                        listener.onClickDelete(position, Fixture)
                        return true
                    }
                }
                return false
            }
        }
    }

    override fun getItemCount(): Int =
            fixtures!!.size

    fun setFixture(fixtures: MutableList<Fixture>) {
        this.fixtures = fixtures
        notifyDataSetChanged()
    }

    fun updateFixture(position: Int, fixture: Fixture) {
        fixtures!!.removeAt(position)
        fixtures!!.add(position, fixture)
        notifyDataSetChanged()
    }

    fun deleteFixture(position: Int) {
        fixtures!!.removeAt(position)
        notifyDataSetChanged()
    }
}