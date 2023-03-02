package com.github.clockworkclyde.providers.local.pref

import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import com.chibatching.kotpref.livedata.asLiveData
import com.github.clockworkclyde.core.common.FlowList
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

   override fun removeFavorite(name: String): Boolean {
      return ProductPref.favorites.remove(name)
   }

   override fun getFavorites(): FlowList<String> {
      return ProductPref.asLiveData(ProductPref::favorites).map { it.toList() }.asFlow()
   }
}