package com.github.clockworkclyde.domain.repository

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.domain.dto.UserDto
import com.github.clockworkclyde.domain.usecases.auth.LoginAttempt

interface IUserRepository {
   suspend fun tryCreateUserAccount(userDto: UserDto): AnyResult
   suspend fun loginExistingAccount(loginAttempt: LoginAttempt): AnyResult
}