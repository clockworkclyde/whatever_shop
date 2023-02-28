package com.github.clockworkclyde.domain.usecases.auth

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.core.utils.applyIfSuccess
import com.github.clockworkclyde.domain.repository.IPreferenceRepository
import com.github.clockworkclyde.domain.repository.IUserRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import javax.inject.Inject

class LoginAttemptUseCase @Inject constructor(
   private val repository: IUserRepository,
   private val preferences: IPreferenceRepository
) : ILoginAttemptUseCase {

   override suspend fun invoke(param: LoginAttempt): AnyResult {
      return repository.loginExistingAccount(param)
         .applyIfSuccess { preferences.saveCurrentUserEmail(param.email) }
   }
}

interface ILoginAttemptUseCase : IUseCase.SingleInOut<LoginAttempt, AnyResult>

data class LoginAttempt(
   val email: String,
   val password: String
)