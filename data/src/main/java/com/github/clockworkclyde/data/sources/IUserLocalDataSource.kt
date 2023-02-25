package com.github.clockworkclyde.data.sources

import com.github.clockworkclyde.data.UserEntity

interface IUserLocalDataSource {
   suspend fun saveUser(user: UserEntity)
   suspend fun findExistingUser(email: String): UserEntity?
}