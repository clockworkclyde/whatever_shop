package com.github.clockworkclyde.domain.usecases.details

import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.domain.model.product.ProductDetails
import com.github.clockworkclyde.domain.repository.IProductDetailsRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
   private val repository: IProductDetailsRepository
) : IGetProductDetailsUseCase {

   override suspend fun invoke(): Result<ProductDetails> {
      return repository.getProductDetails()
   }
}

interface IGetProductDetailsUseCase : IUseCase.Out<Result<ProductDetails>>