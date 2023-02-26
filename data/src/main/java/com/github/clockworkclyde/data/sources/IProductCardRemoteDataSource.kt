package com.github.clockworkclyde.data.sources

import com.github.clockworkclyde.data.models.dto.ProductCardDiscountDto
import com.github.clockworkclyde.data.models.dto.ProductCardDto

interface IProductCardRemoteDataSource {
   suspend fun getDiscountItems(): List<ProductCardDiscountDto>
   suspend fun getLatestItems(): List<ProductCardDto>
}