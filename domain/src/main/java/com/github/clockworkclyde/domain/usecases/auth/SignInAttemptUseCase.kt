package com.github.clockworkclyde.domain.usecases.auth

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.core.utils.applyIfSuccess
import com.github.clockworkclyde.domain.model.user.User
import com.github.clockworkclyde.domain.repository.IPreferenceRepository
import com.github.clockworkclyde.domain.repository.IUserRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import javax.inject.Inject

class SignInAttemptUseCase @Inject constructor(
   private val repository: IUserRepository,
   private val preferences: IPreferenceRepository
) : ISignInAttemptUseCase {

   override suspend fun invoke(param: SignInAttempt): AnyResult {
      return repository.tryCreateUserAccount(
         User(
            firstName = param.firstName,
            lastName = param.lastName,
            email = param.email
         )
      ).applyIfSuccess { preferences.saveCurrentUserEmail(param.email) }
   }
}

interface ISignInAttemptUseCase : IUseCase.SingleInOut<SignInAttempt, AnyResult>

data class SignInAttempt(
   val firstName: String,
   val lastName: String,
   val email: String
)