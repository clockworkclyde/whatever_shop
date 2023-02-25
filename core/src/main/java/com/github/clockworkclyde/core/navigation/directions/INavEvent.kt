package com.github.clockworkclyde.core.navigation.directions

import androidx.annotation.LayoutRes
import com.github.clockworkclyde.core.dto.IEvent

sealed interface INavEvent: IEvent {

   class ShowScreen(val id: Int): INavEvent

   class BackTo(@LayoutRes val layoutId: Int, val inclusive: Boolean = false): INavEvent

   object PopBackTo : INavEvent
}

