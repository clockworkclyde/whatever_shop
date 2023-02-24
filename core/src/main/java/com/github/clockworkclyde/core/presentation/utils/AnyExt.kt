package com.github.clockworkclyde.core.presentation.utils

fun <T: Boolean?> T.orFalse(): Boolean = this ?: false

fun <T: Boolean?> T.orTrue(): Boolean = this ?: true