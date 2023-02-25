package com.github.clockworkclyde.core.presentation.viewmodels

import com.github.clockworkclyde.core.dto.IEvent
import com.github.clockworkclyde.core.navigation.directions.INavDirections
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

open class BaseFlowViewModel: NavigationViewModel(), IBaseFlowViewModel, IEventViewModel {

   override val destinations: INavDirections? = null

   override val eventsFlow: MutableSharedFlow<IEvent> by lazy {
      MutableSharedFlow(replay = 1, extraBufferCapacity = 0, BufferOverflow.DROP_OLDEST)
   }

   override val resultFlow: Flow<*>? = null
   override val supportFlow: Flow<*>? = null

   override fun processEvent(event: IEvent) {
      eventsFlow.tryEmit(event)
   }
}