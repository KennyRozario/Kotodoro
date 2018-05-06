package com.kennyrozario.kotodoro.timer

import android.os.CountDownTimer
import com.kennyrozario.kotodoro.dagger.ActivityScope
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

private const val INTERVAL_IN_MILLISECONDS = 1000L

@ActivityScope
class Timer @Inject constructor(
		private val store: TimerStore
) {

	private val timerCompletion = PublishSubject.create<Any>()
	val timerFinish get() = timerCompletion as Observable<Any>

	private var countDownTimer: CountDownTimer? = null

	fun startTimer(duration: Long) {
		countDownTimer = object : CountDownTimer(duration, INTERVAL_IN_MILLISECONDS) {

			override fun onTick(timeLeft: Long) = store.setTimeLeft(timeLeft)

			override fun onFinish() {
				store.setTimerState(TimerState.INACTIVE)
				store.setTimeLeft(0L)
			}
		}

		store.setTimerState(TimerState.ACTIVE)
		countDownTimer!!.start()
	}

	fun stopTimer() = countDownTimer?.cancel()
}