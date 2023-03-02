package com.github.clockworkclyde.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T> asyncFlow(
   dispatcher: CoroutineDispatcher = Dispatchers.IO,
   block: suspend FlowCollector<T>.() -> Unit,
) =
   flow<T>(block).flowOn(dispatcher).catch {  }