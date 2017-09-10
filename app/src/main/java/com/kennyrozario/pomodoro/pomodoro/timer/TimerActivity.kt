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

class TimerActivity : AppCompatActivity(), TimerContract.View, View.OnClickListener {

    val timer: TextView by bindView(R.id.time_left)
    val shortBreak: Button by bindView(R.id.short_button)
    val playPause: FloatingActionButton by bindView(R.id.play_pause_button)
    val longBreak: Button by bindView(R.id.long_button)

    private var countDownTimer: CountDownTimer? = null
    private val timerPresenter = TimerPresenter(this)


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

                R.id.play_pause_button -> timerPresenter.onPlayPausePressed()

                R.id.long_button -> timerPresenter.onLongBreakButtonPressed()
            }
        }
    }

    override fun beginTimer(duration: Long, timeInterval: Long) {
        countDownTimer = object : CountDownTimer(duration, timeInterval) {

            override fun onTick(millisUntilFinished: Long) {
                timerPresenter.onTick(millisUntilFinished)
            }

            override fun onFinish() {
                timerPresenter.onCountDownTimerFinished()
            }
        }

        countDownTimer?.start()
    }

    override fun pauseTimer() {
        countDownTimer?.cancel()
        playPause.setImageDrawable(getDrawable(R.drawable.ic_play))
    }

    override fun showPlayButton() {
        playPause.setImageDrawable(getDrawable(R.drawable.ic_play))
    }

    override fun showPauseButton() {
        playPause.setImageDrawable(getDrawable(R.drawable.ic_pause))
    }

    override fun updateTimerText(timeLeft: String) {
        timer.text = timeLeft
    }

    override fun onDestroy() {
        countDownTimer = null
        super.onDestroy()
    }
}
