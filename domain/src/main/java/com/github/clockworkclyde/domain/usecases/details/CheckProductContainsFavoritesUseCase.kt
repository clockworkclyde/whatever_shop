package com.github.clockworkclyde.domain.usecases.details

import com.github.clockworkclyde.domain.repository.IPreferenceRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import javax.inject.Inject

class CheckProductContainsFavoritesUseCase @Inject constructor(
   private val preferences: IPreferenceRepository
) : ICheckProductContainsFavoritesUseCase {

   override suspend fun invoke(param: String): Boolean {
      return preferences.getFavorites().contains(param)
   }
}

interface ICheckProductContainsFavoritesUseCase : IUseCase.SingleInOut<String, Boolean>