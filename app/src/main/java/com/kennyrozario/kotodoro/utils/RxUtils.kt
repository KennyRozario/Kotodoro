package com.kennyrozario.kotodoro.utils

import android.util.Log
import io.reactivex.Observable

private const val TAG = "kotodoro"

fun <T> Observable<T>.subscribeSafely(onNext: (T) -> Unit) = subscribe(onNext, { Log.e(TAG, it.message) })!!