package com.github.clockworkclyde.domain.usecases.auth

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.core.utils.applyIfEmpty
import com.github.clockworkclyde.core.utils.emptyResult
import com.github.clockworkclyde.core.utils.toEmptySuccess
import com.github.clockworkclyde.domain.repository.IPreferenceRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckUserLoggedInUseCase @Inject constructor(
   private val repository: IPreferenceRepository
) : ICheckUserLoggedInUseCase {

   override fun invoke(): Flow<AnyResult> {
      return flow {
         emit(repository.getCurrentUserEmail().applyIfEmpty {
            repository.clearShoppingCart()
         })
      }
   }
}

interface ICheckUserLoggedInUseCase : IUseCase.FlowOut<AnyResult>