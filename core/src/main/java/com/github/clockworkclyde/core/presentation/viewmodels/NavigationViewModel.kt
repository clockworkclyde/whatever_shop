package com.github.clockworkclyde.core.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.github.clockworkclyde.core.navigation.INavigationEventReceiver
import com.github.clockworkclyde.core.navigation.directions.INavEvent
import com.github.clockworkclyde.core.navigation.INavigator

abstract class NavigationViewModel : ViewModel(), INavigationEventReceiver {

   override fun processNavEvent(event: INavEvent, navigator: INavigator) {
      when (event) {
         is INavEvent.BackTo -> {
            navigator.backTo(event.layoutId, event.inclusive)
         }
         is INavEvent.ShowScreen -> navigator.navigateTo(
            direction = getDirectionByActionId(event.id),
            navOptions = navOptions(event.popUpTo, event.inclusive)
         )
         is INavEvent.PopBackTo -> navigator.popBackStack()
      }
   }

   private fun getDirectionByActionId(actionId: Int): NavDirections =
      ActionOnlyNavDirections(actionId)

   private fun navOptions(popUpTo: Int?, inclusive: Boolean?) =
      if (popUpTo != null && inclusive != null) {
         NavOptions.Builder()
            .setPopUpTo(popUpTo, inclusive)
            .build()
      } else null
}