package com.kennyrozario.kotodoro.timer

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import com.kennyrozario.kotodoro.base.Feature
import com.kennyrozario.kotodoro.dagger.ActivityScope
import javax.inject.Inject

@ActivityScope
class AlarmPlayer @Inject constructor(
		private val context: Context
) : Feature {

	private lateinit var mediaPlayer: MediaPlayer

	override fun start() {
		var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
		if (alarmUri == null) {
			alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
			if (alarmUri == null) {
				alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
			}
		}

		mediaPlayer = MediaPlayer.create(context, alarmUri)
		mediaPlayer.isLooping = true
		mediaPlayer.start()
	}

	override fun stop() = mediaPlayer.stop()
}