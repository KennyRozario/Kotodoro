package com.kennyrozario.pomodoro.pomodoro.timer

interface TimerContract {

    interface View {

        fun beginDefaultTimer()

        fun pauseTimer()

        fun resumeTimer()
    }

    interface Presenter {

        fun onPlayPressedFromInactiveState()

        fun onPlayPressedFromPausedState()

        fun onPausePressed()

        fun onShortBreakButtonPressed()

        fun onLongBreakButtonPressed()
    }
}