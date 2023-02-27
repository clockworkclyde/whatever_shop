package com.github.clockworkclyde.providers.local

import com.github.clockworkclyde.data.models.local.UserEntity
import com.github.clockworkclyde.data.sources.IUserLocalDataSource
import com.github.clockworkclyde.providers.local.database.user.UserDao
import timber.log.Timber
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
   private val api: UserDao
): IUserLocalDataSource {

   override suspend fun saveUser(user: UserEntity) {
      api.storeUser(user)
      Timber.d("Saving user to db : $user")
   }

   override suspend fun findUserByEmail(email: String): UserEntity? {
      return api.findUserByEmail(email)
   }

   override suspend fun findUserByName(firstName: String): UserEntity? {
      return api.findUserByName(firstName)
   }
}