package com.kennyrozario.kotodoro.app

import android.app.Application
import com.kennyrozario.kotodoro.dagger.AppScope
import com.kennyrozario.kotodoro.timer.TimerComponent
import dagger.Component
import dagger.MembersInjector

class App : Application() {

	private lateinit var component: AppComponent
	val appComponent get() = component

	override fun onCreate() {
		super.onCreate()

		component = DaggerAppComponent.builder().build()
		component.injectMembers(this)
	}
}

@AppScope
@Component
interface AppComponent : MembersInjector<App> {

	fun timer(): TimerComponent.Builder
}