package com.github.clockworkclyde.providers.remote

import com.github.clockworkclyde.data.models.dto.ProductDetailsDto
import com.github.clockworkclyde.data.sources.IProductDetailsRemoteDataSource
import com.github.clockworkclyde.providers.remote.api.EShopApi
import javax.inject.Inject

class ProductDetailsRemoteDataSource @Inject constructor(
   private val api: EShopApi
) : IProductDetailsRemoteDataSource {

   override suspend fun getProductDetails(): ProductDetailsDto {
      return api.getProductDetails()
   }
}