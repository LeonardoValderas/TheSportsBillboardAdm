package com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.ui.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.sanction.fragments.update.ui.SanctionUpdateFragment
import com.valdroide.thesportsbillboardinstitution.model.entities.Sanction
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener_2
import kotlinx.android.synthetic.main.sanction_item.view.*

class SanctionUpdateFragmentAdapter(private var sanctions: MutableList<Sanction>?,
                                    private var listener: GenericOnItemClickListener_2,
                                    fragment: Fragment) : RecyclerView.Adapter<SanctionUpdateFragmentAdapter.ViewHolder>() {

    private val fragment: Fragment

    init {
        this.fragment = fragment as SanctionUpdateFragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View? = LayoutInflater.from(parent.context).inflate(R.layout.sanction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(sanctions!![position], position, listener, fragment)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindData(sanction: Sanction, position: Int, listener: GenericOnItemClickListener_2, fragment: Fragment) {
            //   YoYo.with(Techniques.FlipInX).playOn(holder.card_view);
            with(sanction) {
                itemView.textViewName.text = NAME
                itemView.textViewYellow.text = YELLOW
                itemView.textViewRed.text = RED
                itemView.textViewSanction.text = SANCTION
                itemView.textViewObsevation.text = OBSERVATION
                itemView.textViewSubMenu.visibility = View.VISIBLE
                val conca = MENU + " - " + SUBMENU
                itemView.textViewSubMenu.text = conca
            }
            setOnItemClickListener(listener, position, sanction, fragment)
        }

        fun setOnItemClickListener(listener: GenericOnItemClickListener_2, position: Int, Sanction: Sanction, fragment: Fragment) {
            itemView.setOnClickListener {
                showPopupMenu(itemView.conteiner, fragment.activity, position, Sanction, listener);
            }
        }

        private fun showPopupMenu(view: View, context: Context, position: Int, sanction: Sanction, listener: GenericOnItemClickListener_2) {
            val popup = PopupMenu(context, view, Gravity.END)
            popup.getMenu().add(Menu.NONE, 1, 1, "Editar Sanción")
            popup.getMenu().add(Menu.NONE, 2, 2, "Eliminar Sanción")
            popup.setOnMenuItemClickListener(MenuItemClickListener(position, sanction, listener))
            popup.show()
        }

        private class MenuItemClickListener(internal var position: Int,
                                            internal var sanction: Sanction,
                                            internal var listener: GenericOnItemClickListener_2) : PopupMenu.OnMenuItemClickListener {

            override fun onMenuItemClick(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    1 -> {
                        listener.onClickUpdate(position, sanction)
                        return true
                    }
                    2 -> {
                        listener.onClickDelete(position, sanction)
                        return true
                    }
                }
                return false
            }
        }
    }

    override fun getItemCount(): Int =
            sanctions!!.size

    fun setSanction(Sanctions: MutableList<Sanction>) {
        this.sanctions = Sanctions
        notifyDataSetChanged()
    }

    fun deleteSanction(position: Int) {
        sanctions!!.removeAt(position)
        notifyDataSetChanged()
    }
}