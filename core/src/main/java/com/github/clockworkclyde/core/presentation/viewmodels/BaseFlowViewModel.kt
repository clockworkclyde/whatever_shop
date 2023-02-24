package com.github.clockworkclyde.core.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.github.clockworkclyde.core.dto.IEvent
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

open class BaseFlowViewModel : ViewModel(), IBaseFlowViewModel, IEventViewModel {

   override val eventsFlow: MutableSharedFlow<IEvent> by lazy {
      MutableSharedFlow(replay = 1, extraBufferCapacity = 0, BufferOverflow.DROP_OLDEST)
   }

   override val resultFlow: Flow<*>? = null
   override val supportFlow: Flow<*>? = null

   override fun processEvent(event: IEvent) {
      eventsFlow.tryEmit(event)
   }
}