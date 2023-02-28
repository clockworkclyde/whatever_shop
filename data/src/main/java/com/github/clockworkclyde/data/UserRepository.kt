package com.github.clockworkclyde.data

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.core.common.IResourcesProvider
import com.github.clockworkclyde.data.models.local.UserEntity.Companion.toDbEntity
import com.github.clockworkclyde.data.sources.IUserLocalDataSource
import com.github.clockworkclyde.domain.model.user.User
import com.github.clockworkclyde.domain.repository.IUserRepository
import com.github.clockworkclyde.domain.usecases.auth.LoginAttempt
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.utils.*
import javax.inject.Inject

class UserRepository @Inject constructor(
   private val resources: IResourcesProvider,
   private val localDataSource: IUserLocalDataSource,
) : IUserRepository {

   override suspend fun tryCreateUserAccount(userDto: User): AnyResult {
      return runResultSuspendCatch {
         localDataSource.findUserByEmail(userDto.email)
            ?.errorResult(
               message = resources.getString(R.string.error_user_exists)
            ) ?: localDataSource.saveUser(userDto.toDbEntity()).toEmptySuccess()
      }
   }

   override suspend fun loginExistingAccount(loginAttempt: LoginAttempt): AnyResult {
      return runResultSuspendCatch {
         findUserByEmail(loginAttempt.email)
            .flatMapIfEmpty {
               errorResult(message = resources.getString(R.string.error_user_email_not_exist))
            }
      }
   }

   override suspend fun findUserByEmail(email: String): Result<User> {
      return runResultSuspendCatch {
         localDataSource.findUserByEmail(email)?.convertTo()?.toSuccessResult() ?: emptyResult()
      }
   }

   override suspend fun logoutCurrentUser(email: String): AnyResult {
      return runResultSuspendCatch {
         localDataSource.removeUserByEmail(email).toEmptySuccess()
      }
   }
}