package com.github.clockworkclyde.domain.usecases.profile

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.core.common.IResourcesProvider
import com.github.clockworkclyde.core.utils.errorResult
import com.github.clockworkclyde.core.utils.flatMapIfError
import com.github.clockworkclyde.core.utils.flatMapIfSuccess
import com.github.clockworkclyde.domain.R
import com.github.clockworkclyde.domain.repository.IPreferenceRepository
import com.github.clockworkclyde.domain.repository.IProfilePhotoRepository
import com.github.clockworkclyde.domain.repository.IUserRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import javax.inject.Inject

class LogOutUserUseCase @Inject constructor(
   private val userRepository: IUserRepository,
   private val preferenceRepository: IPreferenceRepository,
   private val profileRepository: IProfilePhotoRepository,
   private val resources: IResourcesProvider
) : ILogOutUserUseCase {

   override suspend fun invoke(): AnyResult {
      return preferenceRepository.getCurrentUserEmail().flatMapIfSuccess {
         userRepository.logoutCurrentUser(it).flatMapIfSuccess {
            preferenceRepository.saveCurrentUserEmail("")
            preferenceRepository.clearShoppingCart()
            profileRepository.removeCurrentPhoto()
         }.flatMapIfError {
            errorResult(message = resources.getString(R.string.error_logout_with_email))
         }
      }
   }
}

interface ILogOutUserUseCase : IUseCase.Out<AnyResult>