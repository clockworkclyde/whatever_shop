package com.github.clockworkclyde.data

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

   override fun getFavorites(): List<String> {
      return provider.getFavorites()
   }
}