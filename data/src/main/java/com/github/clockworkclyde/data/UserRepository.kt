package com.github.clockworkclyde.data

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.core.common.IResourcesProvider
import com.github.clockworkclyde.core.utils.toEmptySuccess
import com.github.clockworkclyde.core.utils.errorResult
import com.github.clockworkclyde.data.models.local.UserEntity.Companion.toDbEntity
import com.github.clockworkclyde.data.sources.IUserLocalDataSource
import com.github.clockworkclyde.domain.model.user.User
import com.github.clockworkclyde.domain.repository.IUserRepository
import com.github.clockworkclyde.domain.usecases.auth.LoginAttempt
import javax.inject.Inject

class UserRepository @Inject constructor(
   private val resources: IResourcesProvider,
   private val localDataSource: IUserLocalDataSource
) : IUserRepository {

   override suspend fun tryCreateUserAccount(userDto: User): AnyResult {
      return localDataSource.findUserByEmail(userDto.email)
         ?.errorResult(
            message = resources.getString(R.string.error_user_exists)
         ) ?: localDataSource.saveUser(userDto.toDbEntity()).toEmptySuccess()
   }

   override suspend fun loginExistingAccount(loginAttempt: LoginAttempt): AnyResult {
      return localDataSource.findUserByName(loginAttempt.firstName)?.toEmptySuccess()
         ?: errorResult(
            message = resources.getString(R.string.error_user_not_exist)
         )
   }
}