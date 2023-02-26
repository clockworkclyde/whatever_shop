package com.github.clockworkclyde.domain.repository

import com.github.clockworkclyde.core.common.FlowResult
import com.github.clockworkclyde.core.common.FlowResultList
import com.github.clockworkclyde.domain.model.product.ProductCardHorizontalItem

interface IProductCardRepository {
   fun getProductCategories(): FlowResultList<ProductCardHorizontalItem>
   fun getLatestProducts(): FlowResult<ProductCardHorizontalItem>
   fun getDiscountProducts(): FlowResult<ProductCardHorizontalItem>
}