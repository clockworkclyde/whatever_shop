package com.github.clockworkclyde.data

import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.utils.runResultSuspendCatch
import com.github.clockworkclyde.core.utils.toSuccessResult
import com.github.clockworkclyde.data.sources.IProductDetailsRemoteDataSource
import com.github.clockworkclyde.domain.model.product.ProductDetails
import com.github.clockworkclyde.domain.repository.IProductDetailsRepository
import javax.inject.Inject

class ProductDetailsRepository @Inject constructor(
   private val remoteDataSource: IProductDetailsRemoteDataSource
) : IProductDetailsRepository {

   override suspend fun getProductDetails(): Result<ProductDetails> {
      return runResultSuspendCatch {
         remoteDataSource.getProductDetails().convertTo().toSuccessResult()
      }
   }
}