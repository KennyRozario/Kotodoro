package com.kennyrozario.kotodoro.dagger

interface ComponentBuilder<out T> {

	fun build(): T
}