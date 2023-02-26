package com.github.clockworkclyde.core.common

import com.github.clockworkclyde.core.dto.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

typealias UnitHandler = () -> Unit
typealias InHandler<T> = (T) -> Unit
typealias DoubleInHandler<T, R> = (T, R) -> Unit
typealias OutHandler<T> = () -> T
typealias InOutHandler<T> = (T) -> T

typealias FlowList<T> = Flow<List<T>>
typealias FlowResultList<T> = Flow<Result<List<T>>>
typealias FlowResult<T> = Flow<Result<T>>
typealias FlowAnyResult = Flow<Result<Any>>

typealias AnyResult = Result<Any>
typealias ResultList<T> = Result<List<T>>

typealias MutableStateFlowList<T> = MutableStateFlow<List<T>>
typealias StateFlowList<T> = StateFlow<List<T>>