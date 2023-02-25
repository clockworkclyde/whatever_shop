package com.github.clockworkclyde.core.navigation

import kotlinx.coroutines.flow.SharedFlow

interface INavigationEventHandler {
   val commands: SharedFlow<NavigationHandler>
}