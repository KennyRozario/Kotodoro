package com.kennyrozario.pomodoro.pomodoro.timer

import com.kennyrozario.pomodoro.pomodoro.utils.convertToMinsAndSeconds

class TimerPresenter constructor(private val view: TimerContract.View) : TimerContract.Presenter {

    private val timeInterval = 1000L // milliseconds
    private val defaultFinishedTime = "0:00"
    private val defaultDuration = 10000L // milliseconds
    private val defaultShortBreakDuration = 50000L // milliseconds
    private val defaultLongBreakDuration = 15000L // milliseconds

    private var millisLeft: Long = 0L
    private var timerState: TimerState = TimerState.INACTIVE

    override fun onPlayPausePressed() {
        when (timerState) {
            TimerState.INACTIVE -> onPlayPressedFromInactiveState()

            TimerState.ACTIVE -> onPausePressed()

            TimerState.PAUSED -> onPlayPressedFromPausedState()
        }
    }

    private fun onPlayPressedFromInactiveState() {
        timerState = TimerState.ACTIVE
        view.beginTimer(defaultDuration, timeInterval)
        view.showPauseButton()
    }

    private fun onPlayPressedFromPausedState() {
        timerState = TimerState.ACTIVE
        view.beginTimer(millisLeft, timeInterval)
        view.showPauseButton()
    }

    private fun onPausePressed() {
        timerState = TimerState.PAUSED
        view.pauseTimer()
        view.updateTimerText(convertToMinsAndSeconds(millisLeft))
    }

    override fun onShortBreakButtonPressed() {
        view.pauseTimer()
        timerState = TimerState.ACTIVE
        view.beginTimer(defaultShortBreakDuration, timeInterval)
        view.showPauseButton()
    }

    override fun onLongBreakButtonPressed() {
        view.pauseTimer()
        timerState = TimerState.ACTIVE
        view.beginTimer(defaultLongBreakDuration, timeInterval)
        view.showPauseButton()

    }

    override fun onTick(millisLeft: Long) {
        this.millisLeft = millisLeft
        view.updateTimerText(convertToMinsAndSeconds(millisLeft))
    }

    override fun onCountDownTimerFinished() {
        timerState = TimerState.INACTIVE
        view.updateTimerText(defaultFinishedTime)
        view.showPlayButton()
        view.beginAlarm()
    }
}