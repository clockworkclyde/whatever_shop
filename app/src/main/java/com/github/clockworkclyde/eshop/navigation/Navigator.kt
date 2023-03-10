package com.github.clockworkclyde.eshop.navigation

import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.github.clockworkclyde.core.common.NavigationHandler
import com.github.clockworkclyde.core.navigation.INavigationEventHandler
import com.github.clockworkclyde.core.navigation.INavigator
import com.github.clockworkclyde.core.utils.safeNavigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class Navigator @Inject constructor(
   private val coroutineScope: CoroutineScope
) : INavigator, INavigationEventHandler {

   override val commands = MutableSharedFlow<NavigationHandler>(
      replay = 0,
      onBufferOverflow = BufferOverflow.DROP_OLDEST,
      extraBufferCapacity = 1
   )

   override fun navigateTo(direction: NavDirections, navOptions: NavOptions?) {
      emit { it.safeNavigate(direction, navOptions) }
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

   init {
      coroutineScope.launch {
         commands.collect { Timber.d("Navigation command was received to observers") }
      }
   }
}