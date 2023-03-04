package com.github.clockworkclyde.data

import com.github.clockworkclyde.core.common.FlowList
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.utils.toSuccessResult
import com.github.clockworkclyde.data.models.local.ShoppingCartEntity.Companion.toCartEntity
import com.github.clockworkclyde.data.sources.IPreferenceProvider
import com.github.clockworkclyde.domain.model.order.OrderProduct
import com.github.clockworkclyde.domain.repository.IPreferenceRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceRepository @Inject constructor(
   private val provider: IPreferenceProvider
): IPreferenceRepository {

   override fun getCurrentUserEmail(): Result<String> {
      return provider.getCurrentUserEmail().toSuccessResult()
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
      return provider.getFavoritesAsFlow()
   }

   override fun addProductToShoppingCart(item: OrderProduct): Boolean {
      return provider.addToShoppingCart(item.toCartEntity())
   }

   override fun observeShoppingCart(): FlowList<OrderProduct> {
      return provider.getShoppingCartAsFlow().map {
         it.map { entity -> entity.convertTo() }
      }
   }

   override fun clearShoppingCart() {
      provider.clearShoppingCart()
   }
}