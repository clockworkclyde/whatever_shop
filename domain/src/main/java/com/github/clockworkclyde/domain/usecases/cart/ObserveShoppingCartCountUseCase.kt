package com.github.clockworkclyde.domain.usecases.cart

import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.utils.toSuccessResult
import com.github.clockworkclyde.domain.repository.IPreferenceRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveShoppingCartCountUseCase @Inject constructor(
   private val preferences: IPreferenceRepository
) : IObserveShoppingCartCountUseCase {

   override fun invoke(): Flow<Result<Int>> {
      return preferences.observeShoppingCart().map { it.count().toSuccessResult() }
   }
}

interface IObserveShoppingCartCountUseCase : IUseCase.FlowOut<Result<Int>>