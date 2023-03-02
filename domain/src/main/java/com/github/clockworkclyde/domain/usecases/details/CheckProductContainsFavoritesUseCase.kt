package com.github.clockworkclyde.domain.usecases.details

import com.github.clockworkclyde.domain.repository.IPreferenceRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CheckProductContainsFavoritesUseCase @Inject constructor(
   private val preferences: IPreferenceRepository
) : ICheckProductContainsFavoritesUseCase {

   override fun invoke(param: String?): Flow<Boolean> {
      return preferences.getFavorites().map { it.contains(param) }
   }
}

interface ICheckProductContainsFavoritesUseCase : IUseCase.FlowSingleInOut<String?, Boolean>