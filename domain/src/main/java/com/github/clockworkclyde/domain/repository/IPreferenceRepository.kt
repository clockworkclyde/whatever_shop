package com.github.clockworkclyde.domain.repository

import com.github.clockworkclyde.core.common.FlowList
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.domain.model.order.OrderProduct

interface IPreferenceRepository {
   fun getCurrentUserEmail(): Result<String>
   fun saveCurrentUserEmail(email: String)
   fun saveFavorite(name: String): Boolean
   fun removeFavorite(name: String): Boolean
   fun getFavorites(): FlowList<String>
   fun addProductToShoppingCart(item: OrderProduct): Boolean
   fun clearShoppingCart()
   fun observeShoppingCart(): FlowList<OrderProduct>
}