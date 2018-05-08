package com.kennyrozario.kotodoro.timer

import com.kennyrozario.kotodoro.base.DisposableFeature
import com.kennyrozario.kotodoro.dagger.ActivityScope
import com.kennyrozario.kotodoro.utils.subscribeSafely
import javax.inject.Inject

@ActivityScope
class TimerAlarmFeature @Inject constructor(
		private val timer: Timer,
		private val view: TimerLayout,
		private val alarmPlayer: AlarmPlayer
) : DisposableFeature() {

	override fun start() {
		timer.timerFinish
				.subscribeSafely {
					showCancelAlarm(true)
					alarmPlayer.start()
				}
				.let(subscription::add)

		view.stopAlarmClicks
				.subscribeSafely {
					showCancelAlarm(false)
					alarmPlayer.stop()
				}
				.let(subscription::add)
	}

	private fun showCancelAlarm(isVisible: Boolean) {
		view.showStopAlarm(isVisible)
		view.showPlayPause(!isVisible)
	}
}