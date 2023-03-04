package com.github.clockworkclyde.core.dto

abstract class UEvent : IEvent {
   object Fetch : UEvent()
   object Retry : UEvent()
   object Refresh : UEvent()
   class Error(val error: Result.ResultThrowable) : UEvent()
   object Validate : UEvent()
}