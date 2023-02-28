package com.github.clockworkclyde.domain.usecases.profile

import com.github.clockworkclyde.domain.model.user.User
import com.github.clockworkclyde.domain.repository.IPreferenceRepository
import com.github.clockworkclyde.domain.repository.IUserRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.utils.emptyResult
import com.github.clockworkclyde.core.utils.flatMapIfSuccess
import com.github.clockworkclyde.core.utils.toSuccessResult
import com.github.clockworkclyde.domain.repository.IProfilePhotoRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
   private val userRepository: IUserRepository,
   private val photoRepository: IProfilePhotoRepository,
   private val preferences: IPreferenceRepository
) : IGetUserDataUseCase {

   override suspend fun invoke(): Result<User> {
      return preferences.getCurrentUserEmail()?.let { email ->
         userRepository.findUserByEmail(email)
            .flatMapIfSuccess { user ->
               when (val photoResult = photoRepository.getCurrentPhoto()) {
                  is Result.Success -> user.copy(pic = photoResult.data)
                  else -> user
               }.toSuccessResult()
            }
      } ?: emptyResult()
   }
}

interface IGetUserDataUseCase : IUseCase.Out<Result<User>>
