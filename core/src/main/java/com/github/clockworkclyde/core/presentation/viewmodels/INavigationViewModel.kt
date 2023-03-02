package com.github.clockworkclyde.core.presentation.viewmodels

import com.github.clockworkclyde.core.navigation.directions.INavDirections

interface INavigationViewModel<T: INavDirections> {
   val directions: T
}