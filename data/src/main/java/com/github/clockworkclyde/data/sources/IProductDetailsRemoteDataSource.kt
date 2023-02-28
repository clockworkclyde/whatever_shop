package com.github.clockworkclyde.data.sources

import com.github.clockworkclyde.data.models.dto.ProductDetailsDto

interface IProductDetailsRemoteDataSource {
   suspend fun getProductDetails(): ProductDetailsDto
}