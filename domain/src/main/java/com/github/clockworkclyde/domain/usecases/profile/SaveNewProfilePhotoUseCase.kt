package com.github.clockworkclyde.domain.usecases.profile

import com.github.clockworkclyde.domain.model.user.UserPhoto
import com.github.clockworkclyde.domain.repository.IProfilePhotoRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.utils.applyIfSuccess
import timber.log.Timber
import javax.inject.Inject

class SaveNewUserPhotoUseCase @Inject constructor(
   private val repository: IProfilePhotoRepository
): ISaveNewUserPhotoUseCase {

   override suspend fun invoke(param: UserPhoto): Result<UserPhoto> {
      return repository.saveNewPhoto(param)
   }
}

interface ISaveNewUserPhotoUseCase: IUseCase.SingleInOut<UserPhoto, Result<UserPhoto>>