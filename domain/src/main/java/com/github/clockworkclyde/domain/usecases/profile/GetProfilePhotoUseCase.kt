package com.github.clockworkclyde.domain.usecases.profile

import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.domain.model.user.UserPhoto
import com.github.clockworkclyde.domain.repository.IProfilePhotoRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import javax.inject.Inject

class GetProfilePhotoUseCase @Inject constructor(
   private val repository: IProfilePhotoRepository
) : IGetCurrentUserPhotoUseCase {

   override suspend fun invoke(): Result<UserPhoto> {
      return repository.getCurrentPhoto()
   }
}

interface IGetCurrentUserPhotoUseCase : IUseCase.Out<Result<UserPhoto>>