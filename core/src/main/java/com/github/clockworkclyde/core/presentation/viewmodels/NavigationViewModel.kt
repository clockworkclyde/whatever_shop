package com.github.clockworkclyde.core.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.github.clockworkclyde.core.navigation.INavigationEventReceiver
import com.github.clockworkclyde.core.navigation.directions.INavEvent
import com.github.clockworkclyde.core.navigation.INavigator

abstract class NavigationViewModel : ViewModel(), INavigationEventReceiver {

   override fun processNavEvent(event: INavEvent, navigator: INavigator) {
      when (event) {
         is INavEvent.BackTo -> navigator.backTo(event.layoutId, event.inclusive)
         is INavEvent.ShowScreen -> navigator.navigateTo(getDirectionByActionId(event.id))
         is INavEvent.PopBackTo -> navigator.popBackStack()
      }
   }

   private fun getDirectionByActionId(actionId: Int): NavDirections =
      ActionOnlyNavDirections(actionId)

}