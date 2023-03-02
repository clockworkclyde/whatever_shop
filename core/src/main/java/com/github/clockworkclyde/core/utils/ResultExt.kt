@file:Suppress("UNCHECKED_CAST", "unused")

package com.github.clockworkclyde.core.utils

import com.github.clockworkclyde.core.common.*
import com.github.clockworkclyde.core.dto.Result
import kotlinx.coroutines.flow.map
import timber.log.Timber

fun Any?.errorResult(
   code: Int? = null,
   message: String? = null,
   exception: Throwable? = null
) = Result.ResultThrowable.Error(code = code.orDefault(), message = message, exception = exception)

fun Any?.loadingResult() = Result.Loading

fun Any?.toEmptySuccess() = Result.EmptySuccess()

fun Any?.emptyResult() = Result.Empty

fun <T : Any> Any.successResult(data: T) = Result.Success<T>(data = data)

fun <T : Any> Result<T>.get(): T? = this.data

inline fun <reified T : Any, reified R : Any> T.runToSuccess(block: T.() -> Result.Success<R>) =
   this.let(block)

inline fun <reified T : Any> Result<T>.applyIfSuccess(block: InHandler<T>): Result<T> {
   if (this is Result.Success) block(this.data)
   return this
}

inline fun <reified T : Any> Result<T>.applyIfError(block: InHandler<Result.ResultThrowable>): Result<T> {
   if (this is Result.ResultThrowable) block(this)
   return this
}

inline fun <reified T : Any> Result<T>.applyIfLoading(block: () -> Unit): Result<T> {
   if (this is Result.Loading) block()
   return this
}

inline fun <reified T : Any> Result<T>.applyIfEmpty(block: () -> Unit): Result<T> {
   if (this is Result.Empty) block()
   return this
}

suspend inline fun <reified T : Any> Any.runResultSuspendCatch(
   noinline catch: ((Throwable) -> Unit)? = null,
   crossinline action: suspend () -> Result<T>
): Result<T> =
   try {
      action()
   } catch (e: Exception) {
      Timber.e("error was catch in suspend block : $e")
      catch?.invoke(e).errorResult(message = e.message, exception = e)
   }


/* Transformations */

inline fun <T : Any, R : Any> Result<T>.transformIfSuccess(
   block: T.() -> R
): Result<R> {
   return if (this is Result.Success) Result.Success(block.invoke(this.data))
   else this as Result<R>
}

suspend inline fun <T : Any, R : Any> Result<T>.transformIfSuccessSuspended(
   crossinline block: suspend T.() -> R
): Result<R> {
   return if (this is Result.Success) Result.Success(block.invoke(this.data))
   else this as Result<R>
}

suspend inline fun <T : Any, R : Any> Result<T>.mapIfSuccessSuspended(
   crossinline block: suspend (T) -> R
): Result<R> {
   return if (this is Result.Success) Result.Success(block.invoke(this.data))
   else this as Result<R>
}

inline fun <reified T : Any, reified R : Any> Result<T>.flatMapIfSuccess(
   block: (T) -> Result<R>
): Result<R> {
   return if (this is Result.Success) block(this.data)
   else this as Result<R>
}

inline fun <reified T : Any, reified R : Any> Result<T>.flatMapIfEmpty(
   block: () -> Result<R>
): Result<R> {
   return if (this is Result.Empty) block()
   else this as Result<R>
}

inline fun <reified T : Any, reified R : Any> Result<T>.flatMapIfError(
   crossinline block: (Result.ResultThrowable.Error) -> Result<R>
): Result<R> {
   return if (this is Result.ResultThrowable.Error) block(this)
   else this as Result<R>
}

suspend inline fun <reified T : Any, reified R : Any> Result<T>.flatMapIfSuccessSuspend(
   crossinline block: suspend (T) -> Result<R>
): Result<R> {
   return if (this is Result.Success) block(this.data)
   else this as Result<R>
}

inline fun <reified T : Any, reified R : Any> FlowResult<T>.flatMapFlowIfError(
   crossinline block: (Result.ResultThrowable.Error) -> Result<R>
): FlowResult<R> {
   return this.map {
      it.flatMapIfError(block)
   }
}

inline fun <reified T : Any, reified R : Any> FlowResult<T>.flatMapFlowIfSuccess(
   crossinline block: (T) -> Result<R>
): FlowResult<R> {
   return this.map {
      it.flatMapIfSuccess(block)
   }
}

inline fun <reified T : Any, reified R : Any> ResultList<T>.mapListTo(
   block: (T) -> R
): ResultList<R> {
   return when (this) {
      is Result.Success -> {
         data.map { block(it) }.mapToSuccessResultOrEmpty()
      }
      else -> this as ResultList<R>
   }
}

inline fun <reified T : List<Any>> T.mapToSuccessResultOrEmpty(): Result<T> =
   this.takeIf { it.isNotEmpty() }?.toSuccessResult() ?: emptyResult()

inline fun <reified T : Any> T?.toSuccessResult(default: Result<T> = emptyResult()): Result<T> =
   this?.let {
      successResult(it)
   } ?: default

inline fun <reified T : Any, reified R : Any> FlowResultList<T>.mapFlowListTo(
   crossinline block: (T) -> R
): FlowResultList<R> {
   return this.map { it.mapListTo(block) }
}