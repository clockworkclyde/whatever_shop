package com.github.clockworkclyde.providers.remote

import com.github.clockworkclyde.data.models.dto.ProductCardDiscountDto
import com.github.clockworkclyde.data.models.dto.ProductCardDto
import com.github.clockworkclyde.data.sources.IProductCardRemoteDataSource
import com.github.clockworkclyde.providers.remote.api.EShopApi
import javax.inject.Inject

class ProductCardRemoteDataSource @Inject constructor(
   private val api: EShopApi
): IProductCardRemoteDataSource {

   override suspend fun getDiscountItems(): List<ProductCardDiscountDto> {
      return api.getDiscountProducts().items
   }

   override suspend fun getLatestItems(): List<ProductCardDto> {
      return api.getLatestProducts().items
   }
}