package com.kennyrozario.kotodoro.base

import io.reactivex.disposables.CompositeDisposable

interface Feature {

	fun start()

	fun stop()
}

abstract class DisposableFeature : Feature {

	protected val subscription = CompositeDisposable()

	override fun stop() {
		subscription.clear()
	}
}