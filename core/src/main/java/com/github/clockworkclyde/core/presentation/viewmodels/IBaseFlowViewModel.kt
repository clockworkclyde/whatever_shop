package com.github.clockworkclyde.core.presentation.viewmodels

import com.github.clockworkclyde.core.dto.IEvent
import kotlinx.coroutines.flow.Flow

interface IBaseFlowViewModel {

   val eventsFlow: Flow<IEvent>
   val resultFlow: Flow<*>?
   val supportFlow: Flow<*>?
}