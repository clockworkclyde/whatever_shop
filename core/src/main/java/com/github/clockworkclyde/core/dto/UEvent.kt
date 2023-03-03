package com.github.clockworkclyde.core.dto

abstract class UEvent : IEvent {
   object Fetch : UEvent()
   object Retry : UEvent()
   object Refresh: UEvent()
   object Validate: UEvent()
}