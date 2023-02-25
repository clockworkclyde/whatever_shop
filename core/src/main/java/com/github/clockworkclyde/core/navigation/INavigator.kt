package com.github.clockworkclyde.core.navigation

import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

interface INavigator {
   fun navigateTo(direction: NavDirections, navOptions: NavOptions?)
   fun popBackStack(): Boolean
   fun backTo(destinationId: Int, inclusive: Boolean): Boolean
}