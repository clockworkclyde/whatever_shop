package com.github.clockworkclyde.core.utils

import com.github.clockworkclyde.core.common.InHandler
import com.github.clockworkclyde.core.dto.Result

fun Any.toError(
   code: Int? = null,
   message: String? = null,
   exception: Throwable? = null
) = Result.ResultThrowable.Error(code = code.orDefault(), message = message, exception = exception)

fun Any.toLoading() = Result.Loading

fun Any.toEmptySuccess() = Result.EmptySuccess()

inline fun <reified T : Any> Any.toSuccess(data: T) = Result.Success<T>(data = data)

inline fun <reified T : Any, reified R : Any> T.runToSuccess(block: T.() -> Result.Success<R>) = this.let(block)

inline fun <reified T : Any> Result<T>.applyIfSuccess(block: InHandler<T>): Result<T> {
   if (this is Result.Success) block(this.data)
   return this
}

inline fun <reified T : Any> Result<T>.applyIfError(block: InHandler<Result.ResultThrowable>): Result<T> {
   if (this is Result.ResultThrowable) block(this)
   return this
}
