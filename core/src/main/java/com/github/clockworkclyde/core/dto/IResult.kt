package com.github.clockworkclyde.core.dto

import com.github.clockworkclyde.core.common.InOutHandler
import com.github.clockworkclyde.core.common.SingleInHandler
import com.github.clockworkclyde.core.common.UnitHandler

interface IResult {

   fun isSuccess(): Boolean
   fun isError(): Boolean
   fun isLoading(): Boolean
   fun isEmpty(): Boolean

   fun flatMap(mapper: InOutHandler<IResult>): IResult

   fun <R : Any?> applyIfSuccess(onSuccess: SingleInHandler<R>): IResult
   fun applyIfEmpty(onEmpty: UnitHandler): IResult
}