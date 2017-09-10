package com.kennyrozario.pomodoro.pomodoro.timer

class TimerPresenter constructor(private val view: TimerContract.View) : TimerContract.Presenter {

    override fun onPlayPressedFromInactiveState() {
        view.beginDefaultTimer()
    }

    override fun onPlayPressedFromPausedState() {
        view.resumeTimer()
    }

    override fun onPausePressed() {
        view.pauseTimer()
    }

    override fun onShortBreakButtonPressed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLongBreakButtonPressed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}