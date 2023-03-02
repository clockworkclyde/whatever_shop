package com.github.clockworkclyde.domain.repository

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.domain.model.user.UserPhoto

interface IProfilePhotoRepository {
   suspend fun saveNewPhoto(photo: UserPhoto): Result<UserPhoto>
   suspend fun getCurrentPhoto(): Result<UserPhoto>
   suspend fun removeCurrentPhoto(): AnyResult
}