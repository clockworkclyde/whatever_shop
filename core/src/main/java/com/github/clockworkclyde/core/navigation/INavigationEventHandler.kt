package com.github.clockworkclyde.core.navigation

import kotlinx.coroutines.flow.MutableSharedFlow

interface INavigationEventHandler {
   val commands: MutableSharedFlow<NavigationHandler>
}