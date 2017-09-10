package com.kennyrozario.pomodoro.pomodoro.timer

import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
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

    private val presenter = TimerPresenter(this)
    private var countDownTimer: CountDownTimer? = null

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
                R.id.short_button -> presenter.onShortBreakButtonPressed()

                R.id.play_pause_button -> presenter.onPlayPausePressed()

                R.id.long_button -> presenter.onLongBreakButtonPressed()
            }
        }
    }

    override fun beginTimer(duration: Long, timeInterval: Long) {
        countDownTimer = object : CountDownTimer(duration, timeInterval) {

            override fun onTick(millisUntilFinished: Long) {
                presenter.onTick(millisUntilFinished)
            }

            override fun onFinish() {
                presenter.onCountDownTimerFinished()
            }
        }

        countDownTimer?.start()
    }

    override fun beginAlarm() {
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val alarm: Ringtone = RingtoneManager.getRingtone(this, alarmUri)

        alarm.play()
        val alarmTimer = object : CountDownTimer(5000, 1000) {

            override fun onTick(millisUntilFinish: Long) {
                // Do Nothing
            }

            override fun onFinish() {
                alarm.stop()
            }
        }

        alarmTimer.start()
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
