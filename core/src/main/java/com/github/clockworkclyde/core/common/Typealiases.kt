package com.github.clockworkclyde.core.common

import com.github.clockworkclyde.core.dto.Result
import kotlinx.coroutines.flow.Flow

typealias UnitHandler = () -> Unit
typealias SingleInHandler<T> = (T) -> Unit
typealias DoubleInHandler<T, R> = (T, R) -> Unit
typealias OutHandler<T> = () -> T
typealias InOutHandler<T> = (T) -> T

typealias FlowList<T> = Flow<List<T>>
typealias FlowResultList<T> = Flow<List<Result<T>>>
typealias FlowResult<T> = Flow<Result<T>>