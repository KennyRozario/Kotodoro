package com.kennyrozario.kotodoro.timer

import com.kennyrozario.kotodoro.base.DisposableFeature
import com.kennyrozario.kotodoro.dagger.ActivityScope
import com.kennyrozario.kotodoro.utils.subscribeSafely
import javax.inject.Inject

private const val SHORT_BREAK_DURATION_IN_MILLIS = 300000L
private const val LONG_BREAK_DURATION_IN_MILLIS = 1200000L

@ActivityScope
class ShortBreakFeature @Inject constructor(
		private val view: TimerLayout,
		private val timer: Timer,
		private val store: TimerStore
) : DisposableFeature() {

	override fun start() {
		view.shortBreakClicks
				.subscribeSafely {
					timer.stopTimer()
					timer.startTimer(SHORT_BREAK_DURATION_IN_MILLIS)
					view.showShortBreak()
					store.setTimerType(TimerType.SHORT_BREAK)
				}
				.let(subscription::add)
	}
}

@ActivityScope
class LongBreakFeature @Inject constructor(
		private val view: TimerLayout,
		private val timer: Timer,
		private val store: TimerStore
) : DisposableFeature() {

	override fun start() {
		view.longBreakClicks
				.subscribeSafely {
					timer.stopTimer()
					timer.startTimer(LONG_BREAK_DURATION_IN_MILLIS)
					view.showLongBreak()
					store.setTimerType(TimerType.LONG_BREAK)
				}
				.let(subscription::add)
	}
}