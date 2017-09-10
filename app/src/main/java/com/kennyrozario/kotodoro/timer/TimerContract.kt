package com.kennyrozario.kotodoro.timer

interface TimerContract {

    interface View {

        fun beginTimer(duration: Long, timeInterval: Long)

        fun pauseTimer()

        fun updateTimerText(timeLeft: String)

        fun showPlayButton()

        fun showPauseButton()

        fun beginAlarm()
    }

    interface Presenter {

        fun onPlayPausePressed()

        fun onShortBreakButtonPressed()

        fun onLongBreakButtonPressed()

        fun onTick(millisLeft: Long)

        fun onCountDownTimerFinished()
    }
}