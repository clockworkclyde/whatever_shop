package com.github.clockworkclyde.core.navigation

import androidx.navigation.NavDirections

interface INavigator {
   fun navigateTo(direction: NavDirections)
   fun popBackStack(): Boolean
   fun backTo(destinationId: Int, inclusive: Boolean): Boolean
}