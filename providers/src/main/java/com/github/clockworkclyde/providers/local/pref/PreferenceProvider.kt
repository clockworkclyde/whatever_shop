package com.github.clockworkclyde.providers.local.pref

import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import com.chibatching.kotpref.livedata.asLiveData
import com.github.clockworkclyde.core.common.FlowList
import com.github.clockworkclyde.data.models.local.ShoppingCartEntity
import com.github.clockworkclyde.data.sources.IPreferenceProvider
import com.github.clockworkclyde.providers.local.pref.cart.ShoppingCartPref
import com.github.clockworkclyde.providers.local.pref.product.ProductPref
import com.github.clockworkclyde.providers.local.pref.user.CurrentUserPref
import com.google.gson.Gson
import kotlinx.coroutines.flow.map
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

   override fun getFavoritesAsFlow(): FlowList<String> {
      return ProductPref.asLiveData(ProductPref::favorites).map { it.toList() }.asFlow()
   }

   override fun addToShoppingCart(item: ShoppingCartEntity): Boolean {
      return ShoppingCartPref.items.add(gson.toJson(item))
   }

   override fun clearShoppingCart() {
      return ShoppingCartPref.items.clear()
   }

   override fun getShoppingCartAsFlow(): FlowList<ShoppingCartEntity> {
      return ShoppingCartPref.asLiveData(ShoppingCartPref::items)
         .asFlow()
         .map {
            it.map { item -> gson.fromJson(item, ShoppingCartEntity::class.java) }
         }
   }
}