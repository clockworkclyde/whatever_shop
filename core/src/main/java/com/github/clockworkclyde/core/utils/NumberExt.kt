package com.github.clockworkclyde.core.utils

fun Int?.orDefault() = this ?: 0

fun Int?.orLessZero() = this ?: -1