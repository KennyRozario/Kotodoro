package com.kennyrozario.kotodoro.timer

import android.os.CountDownTimer
import com.kennyrozario.kotodoro.dagger.ActivityScope
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

private const val INTERVAL_IN_MILLISECONDS = 1000L

@ActivityScope
class Timer @Inject constructor() {

	private val timerProgression = PublishSubject.create<Long>()
	private val timerCompletion = PublishSubject.create<Any>()

	val timerTicks get() = timerProgression as Observable<Long>
	val timerFinish get() = timerCompletion as Observable<Any>

	private lateinit var countDownTimer: CountDownTimer

	fun startTimer(duration: Long) {
		countDownTimer = object : CountDownTimer(duration, INTERVAL_IN_MILLISECONDS) {

			override fun onTick(timeLeft: Long) = timerProgression.onNext(timeLeft)

			override fun onFinish() = timerCompletion.onNext(Any())
		}

		countDownTimer.start()
	}

	fun stopTimer() = countDownTimer.cancel()
}