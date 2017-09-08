package com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.main_adm.login.fragments.update.ui.LoginEditFragment
import com.valdroide.thesportsbillboardinstitution.model.entities.Login
import kotlinx.android.synthetic.main.login_item.view.*


class LoginEditFragmentAdapter(private var logins: MutableList<Login>?, private var listener: OnItemClickListener, fragment: Fragment) : RecyclerView.Adapter<LoginEditFragmentAdapter.ViewHolder>() {

    private val fragment: Fragment

    init {
        this.fragment = fragment as LoginEditFragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View? = LayoutInflater.from(parent.context).inflate(R.layout.login_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(logins!!.get(position), position, listener, fragment)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindData(login: Login, position: Int, listener: OnItemClickListener, fragment: Fragment) {
            //   YoYo.with(Techniques.FlipInX).playOn(holder.card_view);
            with(login) {
                itemView.textViewUser.text = USER
                itemView.textViewPass.text = PASS
                itemView.textViewActive.text = if(IS_ACTIVE == 0) "Inactivo" else "Activo"
            }
            setOnItemClickListener(listener, position, login, fragment)
        }

        fun setOnItemClickListener(listener: OnItemClickListener, position: Int, login: Login, fragment: Fragment) {
            itemView.setOnClickListener {
                showPopupMenu(itemView.relativeBody, fragment.activity, position, login, listener);
            }
        }

        private fun showPopupMenu(view: View, context: Context, position: Int, login: Login, listener: OnItemClickListener) {
            val popup = PopupMenu(context, view)
            if (login.IS_ACTIVE == 1)
                popup.getMenu().add(Menu.NONE, 1, 1, "Desactiva")
            else
                popup.getMenu().add(Menu.NONE, 2, 2, "Activar")

            popup.getMenu().add(Menu.NONE, 3, 3, "Editar Login")
            popup.setOnMenuItemClickListener(MenuItemClickListener(context, position, login, listener))
            popup.show()
        }

        private class MenuItemClickListener(internal var context: Context, internal var position: Int, internal var login: Login, internal var listener: OnItemClickListener) : PopupMenu.OnMenuItemClickListener {

            override fun onMenuItemClick(menuItem: MenuItem): Boolean {

                when (menuItem.getItemId()) {
                    1 -> {
                        login.IS_ACTIVE = 0
                        listener.onClickUnActiveLogin(position, login)
                        return true
                    }
                    2 -> {
                        login.IS_ACTIVE = 1
                        listener.onClickActiveLogin(position, login)
                        return true
                    }
                    3 -> {
                        listener.onClickEditLogin(position, login)
                        return true
                    }
                }
                return false
            }
        }

    }

    override fun getItemCount(): Int =
            logins!!.size

    fun setLogins(logins: MutableList<Login>) {
        this.logins = logins
        notifyDataSetChanged()
    }


}