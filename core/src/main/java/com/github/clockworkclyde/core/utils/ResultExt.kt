package com.github.clockworkclyde.core.utils

import com.github.clockworkclyde.core.dto.Result

fun Any.toError(
   code: Int? = null,
   message: String? = null,
   exception: Throwable? = null
) = Result.ResultThrowable.Error()

fun Any.toLoading() = Result.Loading

fun Any.toEmptySuccess() = Result.EmptySuccess()

inline fun <reified T : Any> Any.toSuccess(data: T) = Result.Success<T>(data = data)

inline fun <reified T : Any, reified R : Any> T.runToSuccess(block: T.() -> Result.Success<R>) = this.let(block)
