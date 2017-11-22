package com.valdroide.thesportsbillboardinstitution.main_adm.player.fragments.create.ui.dialogs

import android.content.Context
import com.valdroide.thesportsbillboardinstitution.model.entities.Position
import com.valdroide.thesportsbillboardinstitution.utils.GenericDialogEditText
import com.valdroide.thesportsbillboardinstitution.utils.OnItemClickListenerDialog


class PlayerPositionDialog(context: Context,
                           private var isUpdate: Boolean,
                           private var hint: String,
                           private var position: Position?,
                           listener: OnItemClickListenerDialog<Position>) : GenericDialogEditText<Position>(context, hint, isUpdate, position, listener) {


    override fun setEntity(text: String): Position {
        position!!.POSITION = text
        return position!!
    }

    override fun getString(): String {
        if (isUpdate)
            return position!!.POSITION
        else
            return ""
    }
}