package com.github.clockworkclyde.data.sources

import com.github.clockworkclyde.data.models.local.UserEntity

interface IUserLocalDataSource {
   suspend fun saveUser(user: UserEntity)
   suspend fun findUserByEmail(email: String): UserEntity?
   suspend fun findUserByName(firstName: String): UserEntity?
   suspend fun removeUserByEmail(email: String)
}