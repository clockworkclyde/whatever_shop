package com.github.clockworkclyde.data.sources

import com.github.clockworkclyde.core.common.FlowList
import com.github.clockworkclyde.data.models.local.ShoppingCartEntity

interface IPreferenceProvider {
   fun getCurrentUserEmail(): String?
   fun saveCurrentUserEmail(email: String)
   fun saveAsFavorite(name: String): Boolean
   fun removeFavorite(name: String): Boolean
   fun getFavoritesAsFlow(): FlowList<String>
   fun addToShoppingCart(item: ShoppingCartEntity): Boolean
   fun clearShoppingCart()
   fun getShoppingCartAsFlow(): FlowList<ShoppingCartEntity>
}