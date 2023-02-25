package com.github.clockworkclyde.data

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.core.common.IResourcesProvider
import com.github.clockworkclyde.core.utils.toEmptySuccess
import com.github.clockworkclyde.core.utils.toError
import com.github.clockworkclyde.data.UserEntity.Companion.toDbEntity
import com.github.clockworkclyde.data.sources.IUserLocalDataSource
import com.github.clockworkclyde.domain.dto.UserDto
import com.github.clockworkclyde.domain.repository.IUserRepository
import com.github.clockworkclyde.domain.usecases.auth.LoginAttempt
import javax.inject.Inject

class UserRepository @Inject constructor(
   private val resources: IResourcesProvider,
   private val localDataSource: IUserLocalDataSource
) : IUserRepository {

   override suspend fun tryCreateUserAccount(userDto: UserDto): AnyResult {
      return localDataSource.findUserByEmail(userDto.email)
         ?.toError(
            message = resources.getString(R.string.error_user_exists)
         ) ?: localDataSource.saveUser(userDto.toDbEntity()).toEmptySuccess()
   }

   override suspend fun loginExistingAccount(loginAttempt: LoginAttempt): AnyResult {
      return localDataSource.findUserByName(loginAttempt.firstName)?.toEmptySuccess()
         ?: toError(
            message = resources.getString(R.string.error_user_not_exist)
         )
   }
}