package com.kennyrozario.kotodoro.timer

import com.kennyrozario.kotodoro.R
import com.kennyrozario.kotodoro.base.DisposableFeature
import com.kennyrozario.kotodoro.utils.subscribeSafely
import com.kennyrozario.kotodoro.utils.toReadableTime
import javax.inject.Inject

private const val DEFAULT_POMODORO_TIME_IN_MILLIS = 15000L

class PlayPauseFeature @Inject constructor(
		private val view: TimerLayout,
		private val timer: Timer,
		private val store: TimerStore
) : DisposableFeature() {

	override fun start() {
		store.observe()
				.subscribeSafely {
					when (it.state) {
						TimerState.ACTIVE -> view.setIcon(R.drawable.ic_pause_white)
						else -> view.setIcon(R.drawable.ic_play_arrow_white)
					}
				}
				.let(subscription::add)

		view.playPauseClicks
				.flatMap { store.observe().take(1) }
				.subscribeSafely(::updateTimerAndStore)
				.let(subscription::add)
	}

	private fun updateTimerAndStore(information: TimerInformation) {
		if (information.timeLeft == 0L) {
			startPomodoro()
		} else if (information.timeLeft != 0L && information.state == TimerState.PAUSED) {
			timer.startTimer(information.timeLeft)
		} else if (information.state == TimerState.ACTIVE) {
			timer.stopTimer()
			store.setTimerState(TimerState.PAUSED)
		}
	}

	private fun startPomodoro() {
		timer.stopTimer()
		store.setTimerType(TimerType.POMODORO)
		view.setTime(DEFAULT_POMODORO_TIME_IN_MILLIS.toReadableTime())
		timer.startTimer(DEFAULT_POMODORO_TIME_IN_MILLIS)
	}
}