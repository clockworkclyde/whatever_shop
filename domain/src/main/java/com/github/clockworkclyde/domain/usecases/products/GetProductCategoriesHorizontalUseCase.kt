package com.github.clockworkclyde.domain.usecases.products

import com.github.clockworkclyde.core.common.ResultList
import com.github.clockworkclyde.domain.model.product.ProductCardHorizontalItem
import com.github.clockworkclyde.domain.repository.IProductCardRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductCategoriesHorizontalUseCase @Inject constructor(
   private val repository: IProductCardRepository
) : IGetProductCategoriesHorizontalUseCase {

   override fun invoke(): Flow<ResultList<ProductCardHorizontalItem>> {
      return repository.getProductCategories()
   }
}

interface IGetProductCategoriesHorizontalUseCase :
   IUseCase.FlowOut<ResultList<ProductCardHorizontalItem>>