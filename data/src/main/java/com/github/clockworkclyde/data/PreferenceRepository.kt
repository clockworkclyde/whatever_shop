package com.github.clockworkclyde.data

import com.github.clockworkclyde.core.common.FlowList
import com.github.clockworkclyde.data.sources.IPreferenceProvider
import com.github.clockworkclyde.domain.repository.IPreferenceRepository
import javax.inject.Inject

class PreferenceRepository @Inject constructor(
   private val provider: IPreferenceProvider
): IPreferenceRepository {

   override fun getCurrentUserEmail(): String? {
      return provider.getCurrentUserEmail()
   }

   override fun saveCurrentUserEmail(email: String) {
      return provider.saveCurrentUserEmail(email)
   }

   override fun saveFavorite(name: String): Boolean {
      return provider.saveAsFavorite(name)
   }

   override fun removeFavorite(name: String): Boolean {
      return provider.removeFavorite(name)
   }

   override fun getFavorites(): FlowList<String> {
      return provider.getFavorites()
   }
}