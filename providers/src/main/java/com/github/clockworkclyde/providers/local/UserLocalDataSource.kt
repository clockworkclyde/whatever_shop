package com.github.clockworkclyde.providers.local

import com.github.clockworkclyde.data.UserEntity
import com.github.clockworkclyde.data.sources.IUserLocalDataSource
import com.github.clockworkclyde.providers.local.database.user.UserDao
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
   private val api: UserDao
): IUserLocalDataSource {

   override suspend fun saveUser(user: UserEntity) {
      api.storeUser(user)
   }

   override suspend fun findExistingUser(email: String): UserEntity? {
      return api.findExistingUser(email)
   }
}