package com.valdroide.thesportsbillboardinstitution.utils.helper

import android.content.Context
import android.content.SharedPreferences
import com.valdroide.thesportsbillboardinstitution.R
import com.google.gson.Gson

object SharedHelper {

    fun setUserWork(context: Context, id: Int) {
        val shared: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_id_user_work), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = shared.edit()
        editor.putInt(context.getString(R.string.shared_id_user), id)
        editor.apply()
    }

    fun getIdUserWork(context: Context): Int {
        val shared: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_id_user_work), Context.MODE_PRIVATE)
        return shared.getInt(context.getString(R.string.shared_id_user), 0)
    }

    fun setSubmenuIdTitle(context: Context, id: Int, title: String) {
        val shared: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_id_title_submenu), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = shared.edit()
        editor.putInt(context.getString(R.string.shared_id), id)
        editor.putString(context.getString(R.string.shared_title), title)
        editor.apply()
    }

    fun setIdTournament(context: Context, id: Int) {
        val shared: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_id_tournament_title), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = shared.edit()
        editor.putInt(context.getString(R.string.shared_id_tournament), id)
        editor.apply()
    }

    fun getSubmenuId(context: Context): Int {
        val shared: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_id_title_submenu), Context.MODE_PRIVATE)
        return shared.getInt(context.getString(R.string.shared_id), 0)
    }

    fun getSubmenuTitle(context: Context): String {
        val shared: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_id_title_submenu), Context.MODE_PRIVATE)
        return shared.getString(context.getString(R.string.shared_title), "")
    }

    fun convertJsonParceable(any: Any): String {
        val gson = Gson()
        val json = gson.toJson(any)
        return if (json.isEmpty()) "" else json
    }

    inline fun <reified T> Gson.fromJson(json: String) : T =
            this.fromJson<T>(json, T::class.java)

    fun setMenuJson(context: Context, menuJson: String) {
        val shared: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_menu_json), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = shared.edit()
        editor.putString(context.getString(R.string.shared_menu), menuJson)
        editor.apply()
    }

    fun getMenuJson(context: Context): String {
        val shared: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_menu_json), Context.MODE_PRIVATE)
        return shared.getString(context.getString(R.string.shared_menu), "")
    }
}