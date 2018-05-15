package com.kennyrozario.kotodoro.timer

import com.kennyrozario.kotodoro.base.DisposableFeature
import com.kennyrozario.kotodoro.dagger.ActivityScope
import com.kennyrozario.kotodoro.utils.subscribeSafely
import com.kennyrozario.kotodoro.utils.toReadableTime
import javax.inject.Inject

private const val DEFAULT_TIME_TEXT = "0:00"

@ActivityScope
class TimerUpdatesFeature @Inject constructor(
		private val view: TimerLayout,
		private val timer: Timer,
		private val store: TimerStore
) : DisposableFeature() {

	override fun start() {
		store.observe()
				.subscribeSafely { view.setTime(it.timeLeft.toReadableTime()) }
				.let(subscription::add)

		timer.timerFinish
				.subscribeSafely {
					view.setTime(DEFAULT_TIME_TEXT)
					view.hideProgress()
					timer.stopTimer()
				}
				.let(subscription::add)
	}
}