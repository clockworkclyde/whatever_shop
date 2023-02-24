package com.github.clockworkclyde.core.navigation

import kotlinx.coroutines.flow.MutableSharedFlow

interface INavigationHandler {
   val commands: MutableSharedFlow<NavigationHandler>
}