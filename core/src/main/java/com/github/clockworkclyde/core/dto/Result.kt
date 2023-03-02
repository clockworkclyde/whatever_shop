package com.github.clockworkclyde.core.dto

import com.github.clockworkclyde.core.common.InHandler
import com.github.clockworkclyde.core.common.UnitHandler

@Suppress("UNCHECKED_CAST")
abstract class Result<out T : Any> : IResult {

   open val data: T? = null

   open var isHandled: Boolean = false

   override fun isSuccess(): Boolean = this is Success

   override fun isError(): Boolean = this is ResultThrowable.Error

   override fun isLoading(): Boolean = this is Loading

   override fun isEmpty(): Boolean = this is Empty

   override fun flatMap(mapper: (IResult) -> IResult): IResult = mapper(this)

   override fun <R> applyOnSuccess(onSuccess: InHandler<R>): IResult {
      return this.also { result ->
         if (result is Success) (data as R)?.let { onSuccess(it) }
      }
   }

   fun applyOnError(onError: InHandler<ResultThrowable.Error>): IResult {
      return this.also {
         if (it is ResultThrowable.Error) onError.invoke(it)
      }
   }

   override fun applyOnEmpty(onEmpty: UnitHandler): IResult {
      return this.also {
         if (it is Empty) onEmpty.invoke()
      }
   }

   abstract class NothingResult : Result<Nothing>()

   open class Success<out T : Any>(
      override val data: T
   ) : Result<T>()

   object Loading : NothingResult()

   object Empty : NothingResult()

   open class EmptySuccess : Success<Boolean>(true)

   abstract class ResultThrowable : NothingResult() {
      open var code: Int = 0
      open var message: Any? = null
      open var exception: Throwable? = null

      open class Error(
         override var code: Int = 0,
         override var message: Any? = null,
         override var exception: Throwable? = null
      ) : ResultThrowable()

   }
}