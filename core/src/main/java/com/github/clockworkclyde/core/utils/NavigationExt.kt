package com.github.clockworkclyde.core.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import timber.log.Timber

fun NavController.safeNavigate(
   directions: NavDirections, navOptions: NavOptions?
) =
   try {
      navigate(directions, navOptions)
   } catch (e: Exception) {
      Timber.e("got navigation error $e")
   }