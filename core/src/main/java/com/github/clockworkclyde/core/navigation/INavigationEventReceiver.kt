package com.github.clockworkclyde.core.navigation

import com.github.clockworkclyde.core.navigation.directions.INavEvent
import com.github.clockworkclyde.core.navigation.directions.INavDirections

interface INavigationEventReceiver {
   fun processNavEvent(event: INavEvent, navigator: INavigator)
}