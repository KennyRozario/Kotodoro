package com.kennyrozario.kotodoro.timer

import com.groupon.grox.RxStores
import com.groupon.grox.Store
import com.kennyrozario.kotodoro.dagger.ActivityScope
import io.reactivex.Observable
import javax.inject.Inject

data class TimerInformation(
		val state: TimerState,
		val type: TimerType,
		val timeLeft: Long
)

@ActivityScope
class TimerStore @Inject constructor() {

	private val store: Store<TimerInformation> = Store(TimerInformation(
			TimerState.INACTIVE,
			TimerType.POMODORO,
			0L
	))

	fun observe(): Observable<TimerInformation> = RxStores.states(store).share().map { it }

	fun setTimerState(state: TimerState) {
		store.dispatch { it.copy(state = state) }
	}

	fun setTimerType(type: TimerType) {
		store.dispatch { it.copy(type = type) }
	}

	fun setTimeLeft(timeLeft: Long) {
		store.dispatch { it.copy(timeLeft = timeLeft) }
	}
}