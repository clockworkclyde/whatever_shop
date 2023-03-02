package com.github.clockworkclyde.core.navigation.directions

import com.github.clockworkclyde.core.dto.IEvent

sealed interface INavEvent: IEvent {

   class ShowScreen(val id: Int, val popUpTo: Int? = null, val inclusive: Boolean = false): INavEvent

   class BackTo(val layoutId: Int, val inclusive: Boolean = false): INavEvent

   object PopBackTo : INavEvent
}

