package com.kennyrozario.kotodoro.timer

import com.kennyrozario.kotodoro.R
import com.kennyrozario.kotodoro.base.DisposableFeature
import com.kennyrozario.kotodoro.dagger.ActivityScope
import com.kennyrozario.kotodoro.utils.StringProvider
import com.kennyrozario.kotodoro.utils.subscribeSafely
import javax.inject.Inject

private const val SHORT_BREAK_DURATION_IN_MILLIS = 5000L
private const val LONG_BREAK_DURATION_IN_MILLIS = 10000L

@ActivityScope
class ShortBreakFeature @Inject constructor(
		private val view: TimerLayout,
		private val timer: Timer,
		private val store: TimerStore,
		private val strings: StringProvider
) : DisposableFeature() {

	override fun start() {
		view.shortBreakClicks
				.subscribeSafely {
					timer.stopTimer()
					timer.startTimer(SHORT_BREAK_DURATION_IN_MILLIS)
					view.setInProgressText(String.format(strings.get(R.string.in_progress_state),
							strings.get(R.string.short_break)))
					store.setTimerType(TimerType.BREAK)
				}
				.let(subscription::add)
	}
}

@ActivityScope
class LongBreakFeature @Inject constructor(
		private val view: TimerLayout,
		private val timer: Timer,
		private val store: TimerStore,
		private val strings: StringProvider
) : DisposableFeature() {

	override fun start() {
		view.longBreakClicks
				.subscribeSafely {
					timer.stopTimer()
					timer.startTimer(LONG_BREAK_DURATION_IN_MILLIS)
					view.setInProgressText(String.format(strings.get(R.string.in_progress_state),
							strings.get(R.string.long_break)))
					store.setTimerType(TimerType.BREAK)
				}
				.let(subscription::add)
	}
}