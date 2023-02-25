package com.github.clockworkclyde.domain

import kotlinx.coroutines.flow.Flow

interface IUseCase {

   interface FlowSingleInOut<in Input, out Output> {
      operator fun invoke(param: Input): Flow<Output>
   }

   interface FlowDoubleInOut<in FirstInput, in SecondInput, out Output> {
      operator fun invoke(firstParam: FirstInput, secondParam: SecondInput): Flow<Output>
   }

   interface FlowOut<out Output> {
      operator fun invoke(): Flow<Output>
   }

   interface SingleInOut<in Input, out Output> {
      suspend operator fun invoke(param: Input): Output
   }

   interface DoubleInOut<in FirstInput, in SecondInput, out Output> {
      suspend operator fun invoke(firstParam: FirstInput, secondParam: SecondInput): Output
   }

   interface SingleIn<in Input> {
      suspend operator fun invoke(param: Input)
   }

   interface DoubleIn<in FirstInput, in SecondInput> {
      suspend operator fun invoke(firstParam: FirstInput, secondParam: SecondInput)
   }

   interface Out<out Output> {
      suspend operator fun invoke(): Output
   }
}