package com.github.clockworkclyde.domain.usecases.details

import com.github.clockworkclyde.domain.repository.IPreferenceRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import javax.inject.Inject

class AddProductToFavoritesUseCase @Inject constructor(
   private val preferences: IPreferenceRepository
): IAddToFavoritesUseCase {

   override suspend fun invoke(param: String): Boolean {
      return preferences.saveFavorite(param)
   }
}

interface IAddToFavoritesUseCase: IUseCase.SingleInOut<String, Boolean>