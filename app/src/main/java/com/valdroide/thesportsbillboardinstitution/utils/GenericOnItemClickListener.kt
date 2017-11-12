package com.valdroide.thesportsbillboardinstitution.utils

import android.view.View
import com.valdroide.thesportsbillboardinstitution.model.entities.SubMenuDrawer

interface GenericOnItemClickListener<T> {
    interface withActive {
        fun onClickActive(position: Int, any: Any)
        fun onClickUnActive(position: Int, any: Any)
        fun onClickSave(position: Int, any: Any)
        fun onClickUpdate(position: Int, any: Any)
        fun onClickDelete(position: Int, any: Any)
    }
    interface withoutActive {
        fun onClickSave(position: Int, any: Any)
        fun onClickUpdate(position: Int, any: Any)
        fun onClickDelete(position: Int, any: Any)
    }
    interface actualUnActual {
        fun boxEvent(position: Int, id_submenu: SubMenuDrawer, id_tournament: Int, isActual:Boolean)
        fun snackBarIsActual()
    }
    interface fixture {
        fun onClickResult(position: Int, any: Any)
        fun onSaveResult(any: Any)
        fun onClickUpdate(position: Int, any: Any)
        fun onClickDelete(position: Int, any: Any)
    }
}