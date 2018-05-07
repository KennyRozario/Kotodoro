package com.kennyrozario.kotodoro.anko

import android.content.Context
import android.util.TypedValue

val Context.selectableItemBackgroundResource: Int
	get() {
		val typedValue = TypedValue()
		theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
		return typedValue.resourceId
	}