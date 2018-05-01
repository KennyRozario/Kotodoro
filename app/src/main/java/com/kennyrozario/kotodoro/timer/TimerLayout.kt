package com.kennyrozario.kotodoro.timer

import android.content.Context
import android.support.annotation.StringRes
import android.support.design.widget.FloatingActionButton
import android.view.Gravity.CENTER
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.kennyrozario.kotodoro.R
import com.kennyrozario.kotodoro.anko.materialMargin
import com.kennyrozario.kotodoro.dagger.ActivityScope
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton

@ActivityScope
open class TimerLayout : AnkoComponent<Context> {

	private lateinit var time: TextView

	private lateinit var shortBreak: Button
	val shortBreakClicks by lazy { RxView.clicks(shortBreak).share() }

	private lateinit var playPause: FloatingActionButton
	val playPauseClicks by lazy { RxView.clicks(playPause).share() }

	private lateinit var longBreak: Button
	val longBreakClicks by lazy { RxView.clicks(longBreak).share() }

	override fun createView(ui: AnkoContext<Context>): View = ui.verticalLayout {
		padding = materialMargin
		timer()
		actions()
	}

	fun setTime(timeValue: CharSequence) {
		time.text = timeValue
	}

	private fun _LinearLayout.timer() = textView {
		lparams(width = matchParent, height = dip(0), weight = 75f) { setGravity(CENTER) }
		text = "25:00"
		textSize = 48f
		backgroundResource = R.drawable.timer_outline
	}.let { time = it }

	private fun _LinearLayout.actions() = linearLayout {
		lparams(width = matchParent, height = dip(0), weight = 25f) { setGravity(CENTER) }
		shortBreak()
		playPause()
		longBreak()
	}

	private fun _LinearLayout.shortBreak() =
			breakButton(R.string.short_break_button).let { shortBreak = it }

	private fun _LinearLayout.playPause() = floatingActionButton {
		lparams(height = dip(64), width = dip(64))
		imageResource = R.drawable.ic_play
	}.let { playPause = it }

	private fun _LinearLayout.longBreak() =
			breakButton(R.string.long_break_button).let { longBreak = it }

	private fun _LinearLayout.breakButton(@StringRes textId: Int) = button {
		textResource = textId
		backgroundColor = android.R.color.transparent
	}
}