package com.github.clockworkclyde.domain.repository

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.domain.model.user.User
import com.github.clockworkclyde.domain.usecases.auth.LoginAttempt

interface IUserRepository {
   suspend fun tryCreateUserAccount(userDto: User): AnyResult
   suspend fun loginExistingAccount(loginAttempt: LoginAttempt): AnyResult
}