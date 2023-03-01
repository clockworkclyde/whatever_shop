package com.github.clockworkclyde.domain.repository

import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.domain.model.product.ProductDetails

interface IProductDetailsRepository {
   suspend fun getProductDetails(): Result<ProductDetails>
}