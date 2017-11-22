package com.valdroide.thesportsbillboardinstitution.main_adm.fixture.fragments.update.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.model.entities.Fixture
import com.valdroide.thesportsbillboardinstitution.model.entities.TimeMatch
import com.valdroide.thesportsbillboardinstitution.utils.GenericOnItemClickListener
import com.valdroide.thesportsbillboardinstitution.utils.GenericSpinnerAdapter
import com.valdroide.thesportsbillboardinstitution.utils.OnItemClickListenerFixture
import kotlinx.android.synthetic.main.custom_dialog_fixture.*


class CustomDialog(private var context: Activity,
                   private var fixture: Fixture?,
                   private var timesMatch: MutableList<TimeMatch>?,
                   private var listener: OnItemClickListenerFixture) : Dialog(context), View.OnClickListener {
    var time: TimeMatch? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_fixture)
        linearDialog.minimumWidth = 500
        linearDialog.minimumHeight = 300

        buttonSave.setOnClickListener(this)
        buttonCancel.setOnClickListener(this)
        with(fixture) {
            editTextRLocal.text = Editable.Factory.getInstance().newEditable(fixture!!.RESULT_LOCAL)
            editTextRVisit.text = Editable.Factory.getInstance().newEditable(fixture!!.RESULT_VISITE)
        }
        spinnerTimes.adapter = GenericSpinnerAdapter(context, null, timesMatch!!, 5)
        spinnerTimes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                time = timesMatch!![pos]
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {

            }
        }
        spinnerTimes.setSelection(getPositionSpinner(fixture!!.ID_TIMES_MATCH))
    }


    private fun getPositionSpinner(id_menu: Int): Int {
        var index = 0
        for (i in 0 until timesMatch!!.size) {
            if (timesMatch!![i].ID_TIME_MATCH_KEY == id_menu) {
                index = i
                return index
            }
        }
        return index
    }

    override fun onClick(v: View?) {
        when (v) {
            buttonSave -> {
                fixture!!.ID_TIMES_MATCH = time!!.ID_TIME_MATCH_KEY
                fixture!!.TIMES_MATCH = time!!.TIME_MATCH
                fixture!!.RESULT_LOCAL = editTextRLocal.text.toString()
                fixture!!.RESULT_VISITE = editTextRVisit.text.toString()
                listener.onClickSaveResult(fixture!!)
                dismiss()
            }
            buttonCancel -> dismiss()
        }
    }

    class Builder(private var context: Activity) {

        private var fixture: Fixture? = null
        private var listener: OnItemClickListenerFixture? = null
        private var times: MutableList<TimeMatch>? = null


        fun withFixture(fixture: Fixture): Builder {
            this.fixture = fixture
            return this
        }

        fun withTimes(times: MutableList<TimeMatch>): Builder {
            this.times = times
            return this
        }

        fun setOnClick(listener: OnItemClickListenerFixture): Builder {
            this.listener = listener
            return this
        }
        //original
        fun show() = CustomDialog(context, fixture, times, listener!!).show()
    }
}