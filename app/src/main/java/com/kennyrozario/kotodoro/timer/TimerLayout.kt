package com.kennyrozario.kotodoro.timer

import android.content.Context
import android.support.annotation.StringRes
import android.view.Gravity.CENTER
import android.view.View
import com.kennyrozario.kotodoro.R
import com.kennyrozario.kotodoro.anko.materialMargin
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton

class TimerLayout : AnkoComponent<Context> {

	override fun createView(ui: AnkoContext<Context>): View = ui.verticalLayout {
		padding = materialMargin
		timer()
		actions()
	}

	private fun _LinearLayout.timer() = textView {
		lparams(width = matchParent, height = dip(0), weight = 75f) { setGravity(CENTER) }
		text = "25:00"
		textSize = 48f
		backgroundResource = R.drawable.timer_outline
	}

	private fun _LinearLayout.actions() = linearLayout {
		lparams(width = matchParent, height = dip(0), weight = 25f) { setGravity(CENTER) }
		shortBreak()
		playPause()
		longBreak()
	}

	private fun _LinearLayout.shortBreak() = breakButton(R.string.short_break_button)

	private fun _LinearLayout.playPause() = floatingActionButton {
		lparams(height = dip(64), width = dip(64))
		imageResource = R.drawable.ic_play
	}

	private fun _LinearLayout.longBreak() = breakButton(R.string.long_break_button)

	private fun _LinearLayout.breakButton(@StringRes textId: Int) = button {
		textResource = textId
		backgroundColor = android.R.color.transparent
	}
}