package com.kennyrozario.pomodoro.pomodoro

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kennyrozario.pomodoro.pomodoro.Timer.TimerPresenter

class MainActivity : AppCompatActivity() {

    private val timerPresenter = TimerPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}
