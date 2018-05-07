package com.kennyrozario.kotodoro.utils

import java.util.concurrent.TimeUnit

fun Long.toReadableTime() =
		String.format("%d:%02d", minutes(this),
				seconds(this) - TimeUnit.MINUTES.toSeconds(minutes(this)))

private fun minutes(millis: Long) = TimeUnit.MILLISECONDS.toMinutes(millis)

private fun seconds(millis: Long) = TimeUnit.MILLISECONDS.toSeconds(millis)
