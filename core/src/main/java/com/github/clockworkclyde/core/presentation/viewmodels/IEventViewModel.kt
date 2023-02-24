package com.github.clockworkclyde.core.presentation.viewmodels

import com.github.clockworkclyde.core.dto.IEvent

interface IEventViewModel {
   fun processEvent(event: IEvent)
}