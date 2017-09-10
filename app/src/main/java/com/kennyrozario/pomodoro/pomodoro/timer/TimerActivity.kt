package com.kennyrozario.pomodoro.pomodoro.timer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.kennyrozario.pomodoro.pomodoro.R
import com.kennyrozario.pomodoro.pomodoro.kotterknife.bindView
import com.kennyrozario.pomodoro.pomodoro.utils.convertToMinsAndSeconds

class TimerActivity : AppCompatActivity(), TimerContract.View, View.OnClickListener {

    val timer: TextView by bindView(R.id.time_left)
    val shortBreak: Button by bindView(R.id.short_button)
    val playPause: FloatingActionButton by bindView(R.id.play_pause_button)
    val longBreak: Button by bindView(R.id.long_button)

    private var countDownTimer: CountDownTimer? = null

    private val timerPresenter = TimerPresenter(this)

    private var timerState: TimerState = TimerState.INACTIVE
    private var timeLeft: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shortBreak.setOnClickListener(this)
        playPause.setOnClickListener(this)
        longBreak.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {

            when (it.id) {
                R.id.short_button -> timerPresenter.onShortBreakButtonPressed()

                R.id.play_pause_button -> {

                    when (timerState) {

                        TimerState.INACTIVE -> timerPresenter.onPlayPressedFromInactiveState()

                        TimerState.PAUSED -> timerPresenter.onPlayPressedFromPausedState()

                        TimerState.ACTIVE -> timerPresenter.onPausePressed()
                    }
                }

                R.id.long_button -> timerPresenter.onLongBreakButtonPressed()
            }
        }
    }

    override fun beginDefaultTimer() {
        timerState = TimerState.ACTIVE

        val defaultTime = "25:00"
        timer.text = defaultTime
        playPause.setImageDrawable(getDrawable(R.drawable.ic_pause))

        countDownTimer = object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                timer.text = convertToMinsAndSeconds(millisUntilFinished)
            }

            override fun onFinish() {
                val defaultFinishedTime = "0:00"
                timerState = TimerState.INACTIVE
                timer.text = defaultFinishedTime
                playPause.setImageDrawable(getDrawable(R.drawable.ic_play))
            }
        }

        countDownTimer?.start()
    }

    override fun pauseTimer() {
        countDownTimer?.cancel()
        timerState = TimerState.PAUSED
        playPause.setImageDrawable(getDrawable(R.drawable.ic_play))
        timer.text = convertToMinsAndSeconds(timeLeft)
    }

    override fun resumeTimer() {
        timerState = TimerState.ACTIVE
        playPause.setImageDrawable(getDrawable(R.drawable.ic_pause))

        countDownTimer = object : CountDownTimer(timeLeft, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                timer.text = convertToMinsAndSeconds(millisUntilFinished)
            }

            override fun onFinish() {
                val defaultFinishedTime = "0:00"
                timerState = TimerState.INACTIVE
                timer.text = defaultFinishedTime
                playPause.setImageDrawable(getDrawable(R.drawable.ic_play))
            }
        }

        countDownTimer?.start()
    }

    override fun onDestroy() {
        countDownTimer = null
        super.onDestroy()
    }
}
