package com.kennyrozario.kotodoro.timer

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import com.kennyrozario.kotodoro.base.Feature
import com.kennyrozario.kotodoro.dagger.ActivityScope
import javax.inject.Inject

@ActivityScope
class AlarmPlayer @Inject constructor(
		private val context: Context
) : Feature {

	private lateinit var alarm: Ringtone

	override fun start() {
		var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
		if (alarmUri == null) {
			alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
			if (alarmUri == null) {
				alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
			}
		}

		alarm = RingtoneManager.getRingtone(context, alarmUri)
		alarm.audioAttributes = AudioAttributes.Builder()
				.setUsage(AudioAttributes.USAGE_ALARM)
				.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
				.setLegacyStreamType(AudioManager.STREAM_ALARM)
				.build()
		alarm.play()
	}

	override fun stop() = alarm.stop()
}