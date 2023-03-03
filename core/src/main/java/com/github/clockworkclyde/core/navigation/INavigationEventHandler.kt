package com.github.clockworkclyde.core.navigation

import com.github.clockworkclyde.core.common.NavigationHandler
import kotlinx.coroutines.flow.SharedFlow

interface INavigationEventHandler {
   val commands: SharedFlow<NavigationHandler>
}