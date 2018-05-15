package com.kennyrozario.kotodoro.timer

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.widget.FloatingActionButton
import android.view.Gravity.CENTER
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.kennyrozario.kotodoro.R
import com.kennyrozario.kotodoro.anko.materialMargin
import com.kennyrozario.kotodoro.anko.materialMarginSmall
import com.kennyrozario.kotodoro.anko.selectableItemBackgroundResource
import com.kennyrozario.kotodoro.dagger.ActivityScope
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton

@ActivityScope
open class TimerLayout : AnkoComponent<Context> {

	private lateinit var time: TextView
	private lateinit var progress: ImageView

	private lateinit var shortBreak: Button
	val shortBreakClicks by lazy { RxView.clicks(shortBreak).share() }

	private lateinit var playPause: FloatingActionButton
	val playPauseClicks by lazy { RxView.clicks(playPause).share() }

	private lateinit var longBreak: Button
	val longBreakClicks by lazy { RxView.clicks(longBreak).share() }

	private lateinit var stopAlarm: FloatingActionButton
	val stopAlarmClicks by lazy { RxView.clicks(stopAlarm).share() }

	override fun createView(ui: AnkoContext<Context>): View = ui.verticalLayout {
		padding = materialMargin
		progressState()
		timer()
		actions()
	}

	fun setTime(timeValue: CharSequence) {
		time.text = timeValue
	}

	fun setIcon(@DrawableRes iconId: Int) {
		playPause.setImageResource(iconId)
	}

	fun setAndShowNumOfPomodorosCompleted() = showProgress(R.drawable.red_tomato)

	fun showShortBreak() = showProgress(R.drawable.coffee)

	fun showLongBreak() = showProgress(R.drawable.vacation)

	fun hideProgress() {
		progress.visibility = View.INVISIBLE
	}

	fun showProgress(@DrawableRes background: Int) {
		progress.visibility = View.VISIBLE
		progress.backgroundResource = background
	}

	fun showStopAlarm(isVisible: Boolean) {
		stopAlarm.visibility = if (isVisible) View.VISIBLE else View.GONE
	}

	fun showPlayPause(isVisible: Boolean) {
		playPause.visibility = if (isVisible) View.VISIBLE else View.GONE
	}

	private fun _LinearLayout.timer() = textView {
		lparams(width = matchParent, height = dip(0), weight = 70f) { setGravity(CENTER) }
		textSize = 56f
		backgroundResource = R.drawable.timer_outline
	}.let { time = it }

	private fun _LinearLayout.progressState() =
			imageView().lparams {
				gravity = CENTER
				width = dip(64)
				height = dip(0)
				weight = 10f
			}.let { progress = it }

	private fun _LinearLayout.actions() = linearLayout {
		lparams(width = matchParent, height = dip(0), weight = 20f) { setGravity(CENTER) }
		shortBreak()
		playPause()
		stopAlarm()
		longBreak()
	}

	private fun _LinearLayout.shortBreak() =
			breakButton(R.string.short_break_button).let { shortBreak = it }

	private fun _LinearLayout.playPause() = floatingActionButton {
		lparams(height = dip(64), width = dip(64))
		imageResource = R.drawable.ic_play_arrow_white
		visibility = View.VISIBLE
	}.let { playPause = it }

	private fun _LinearLayout.longBreak() =
			breakButton(R.string.long_break_button).let { longBreak = it }

	private fun _LinearLayout.stopAlarm() = floatingActionButton {
		lparams(height = dip(64), width = dip(64))
		imageResource = R.drawable.ic_clear_white
		visibility = View.GONE
	}.let { stopAlarm = it }

	private fun _LinearLayout.breakButton(@StringRes textId: Int) = button {
		textResource = textId
		backgroundColor = android.R.color.transparent
		backgroundResource = context.selectableItemBackgroundResource
		isClickable = true
	}.lparams { horizontalMargin = materialMarginSmall }
}