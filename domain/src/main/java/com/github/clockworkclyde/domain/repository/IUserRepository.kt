package com.github.clockworkclyde.domain.repository

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.domain.dto.UserDto

interface IUserRepository {
   suspend fun tryCreateUserAccount(userDto: UserDto): AnyResult
}