package com.github.clockworkclyde.domain.usecases.auth

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.domain.repository.IUserRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import javax.inject.Inject

class LoginAttemptUseCase @Inject constructor(
   private val repository: IUserRepository
) : ILoginAttemptUseCase {

   override suspend fun invoke(param: LoginAttempt): AnyResult {
      return repository.loginExistingAccount(param)
   }
}

interface ILoginAttemptUseCase : IUseCase.SingleInOut<LoginAttempt, AnyResult>

data class LoginAttempt(
   val firstName: String,
   val password: String
)