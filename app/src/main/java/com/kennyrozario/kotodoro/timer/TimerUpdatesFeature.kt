package com.kennyrozario.kotodoro.timer

import com.kennyrozario.kotodoro.base.DisposableFeature
import com.kennyrozario.kotodoro.utils.subscribeSafely
import com.kennyrozario.kotodoro.utils.toReadableTime
import javax.inject.Inject

private const val DEFAULT_TIME_TEXT = "0:00"

class TimerUpdatesFeature @Inject constructor(
		private val view: TimerLayout,
		private val timer: Timer
) : DisposableFeature() {

	override fun start() {
		timer.timerTicks
				.subscribeSafely { view.setTime(it.toReadableTime()) }
				.let(subscription::add)

		timer.timerFinish
				.subscribeSafely {
					view.setTime(DEFAULT_TIME_TEXT)
					timer.stopTimer()
				}
				.let(subscription::add)
	}
}