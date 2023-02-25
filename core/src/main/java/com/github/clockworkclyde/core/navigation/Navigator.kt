package com.github.clockworkclyde.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias NavigationHandler = (NavController) -> Unit

class Navigator @Inject constructor(
   private val coroutineScope: CoroutineScope
): INavigator, INavigationEventHandler {

   override val commands = MutableSharedFlow<NavigationHandler>()

   override fun navigateTo(direction: NavDirections) {
      emit { it.navigate(direction) }
   }

   override fun popBackStack(): Boolean {
      var popUp = false
      emit { popUp = it.popBackStack() }
      return popUp
   }

   override fun backTo(destinationId: Int, inclusive: Boolean): Boolean {
      var popUp = false
      emit { popUp = it.popBackStack(destinationId, inclusive) }
      return popUp
   }

   private fun emit(handler: NavigationHandler) {
      coroutineScope.launch { commands.emit(handler) }
   }
}