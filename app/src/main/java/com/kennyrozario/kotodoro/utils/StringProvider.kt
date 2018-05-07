package com.kennyrozario.kotodoro.utils

import android.content.Context
import android.support.annotation.StringRes
import javax.inject.Inject

class StringProvider @Inject constructor(
		private val context: Context
) {

	fun get(@StringRes textId: Int): String = context.getString(textId)
}