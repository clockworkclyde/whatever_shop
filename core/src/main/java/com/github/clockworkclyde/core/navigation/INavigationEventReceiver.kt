package com.github.clockworkclyde.core.navigation

import com.github.clockworkclyde.core.navigation.directions.INavEvent
import com.github.clockworkclyde.core.navigation.directions.INavDirections

interface INavigationEventReceiver {
   val destinations: INavDirections?
   fun processNavEvent(event: INavEvent, navigator: INavigator)
}