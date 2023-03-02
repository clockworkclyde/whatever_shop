package com.github.clockworkclyde.domain.usecases.details

import com.github.clockworkclyde.domain.repository.IPreferenceRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import javax.inject.Inject

class RemoveFavoriteProductUseCase @Inject constructor(
   private val preferences: IPreferenceRepository
): IRemoveFavoriteProductUseCase {

   override suspend fun invoke(param: String): Boolean {
      return preferences.removeFavorite(param)
   }
}

interface IRemoveFavoriteProductUseCase: IUseCase.SingleInOut<String, Boolean>