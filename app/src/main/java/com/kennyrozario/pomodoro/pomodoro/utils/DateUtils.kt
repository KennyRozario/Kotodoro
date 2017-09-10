package com.kennyrozario.pomodoro.pomodoro.utils

import java.util.concurrent.TimeUnit

fun convertToMinsAndSeconds(millis: Long): String {
    return String.format("%d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
}
