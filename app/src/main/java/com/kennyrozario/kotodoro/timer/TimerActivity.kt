package com.kennyrozario.kotodoro.timer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kennyrozario.kotodoro.app.App
import com.kennyrozario.kotodoro.base.Feature
import com.kennyrozario.kotodoro.dagger.ActivityScope
import com.kennyrozario.kotodoro.dagger.ComponentBuilder
import dagger.*
import org.jetbrains.anko.setContentView
import javax.inject.Inject

class TimerActivity : AppCompatActivity() {

	@Inject
	lateinit var features: List<@JvmSuppressWildcards Feature>

	private lateinit var layout: TimerLayout

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setupLayout()
		injectMembers()
		startFeatures()
	}

	override fun onDestroy() {
		super.onDestroy()
		for (feature in features) {
			feature.stop()
		}
	}

	private fun setupLayout() {
		layout = TimerLayout()
		layout.setContentView(this)
	}

	private fun injectMembers() =
			(application as App).appComponent
					.timer()
					.layout(layout)
					.timer(Timer())
					.build()
					.injectMembers(this)

	private fun startFeatures() {
		for (feature in features) {
			feature.start()
		}
	}
}

@ActivityScope
@Subcomponent(modules = [TimerComponent.TimerModule::class])
interface TimerComponent : MembersInjector<TimerActivity> {

	@Subcomponent.Builder
	interface Builder : ComponentBuilder<TimerComponent> {

		@BindsInstance
		fun layout(timerLayout: TimerLayout): Builder

		@BindsInstance
		fun timer(timer: Timer): Builder
	}

	@Module
	class TimerModule {

		@Provides
		@ActivityScope
		fun features(
				timerUpdates: TimerUpdatesFeature,
				shortBreak: ShortBreakFeature,
				longBreak: LongBreakFeature
		): List<@JvmSuppressWildcards Feature> = listOf(timerUpdates, shortBreak, longBreak)
	}
}