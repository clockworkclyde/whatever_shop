package com.github.clockworkclyde.providers.local.pref

import com.github.clockworkclyde.data.sources.IPreferenceProvider
import com.github.clockworkclyde.providers.local.pref.product.ProductPref
import com.github.clockworkclyde.providers.local.pref.user.CurrentUserPref
import com.google.gson.Gson
import javax.inject.Inject

class PreferenceProvider @Inject constructor(
   private val gson: Gson
) : IPreferenceProvider {

   override fun getCurrentUserEmail(): String? = CurrentUserPref.email.takeIf { it.isNotEmpty() }

   override fun saveCurrentUserEmail(email: String) {
      CurrentUserPref.email = email
   }

   override fun saveAsFavorite(name: String): Boolean {
      return ProductPref.favorites.add(name)
   }

   override fun getFavorites(): List<String> {
      return ProductPref.favorites.toList()
   }
}