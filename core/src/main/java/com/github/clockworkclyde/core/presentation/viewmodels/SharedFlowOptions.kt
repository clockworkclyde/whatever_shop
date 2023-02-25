package com.github.clockworkclyde.core.presentation.viewmodels

import kotlinx.coroutines.channels.BufferOverflow

data class SharedFlowOptions(
   val replay: Int = 0,
   val bufferCapacity: Int = 1,
   val bufferOverflow: BufferOverflow = BufferOverflow.DROP_OLDEST
)