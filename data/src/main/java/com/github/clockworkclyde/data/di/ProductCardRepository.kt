package com.github.clockworkclyde.data.di

import com.github.clockworkclyde.core.common.FlowResult
import com.github.clockworkclyde.core.common.FlowResultList
import com.github.clockworkclyde.core.utils.*
import com.github.clockworkclyde.data.sources.IProductCardRemoteDataSource
import com.github.clockworkclyde.domain.model.product.ProductCardHorizontalItem
import com.github.clockworkclyde.domain.repository.IProductCardRepository
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ProductCardRepository @Inject constructor(
   private val remoteSource: IProductCardRemoteDataSource,
) : IProductCardRepository {

   override fun getProductCategories(): FlowResultList<ProductCardHorizontalItem> {
      return getLatestProducts().combine(getDiscountProducts()) { latest, discount ->
         latest.flatMapIfSuccessSuspend { it1 ->
            discount.flatMapIfSuccessSuspend { it2 ->
               buildList {
                  add(it1)
                  add(it2)
               }.toSuccessResult()
            }
         }
      }
   }

   override fun getLatestProducts(): FlowResult<ProductCardHorizontalItem> {
      return asyncFlow {
         val items = remoteSource.getLatestItems().map { it.convertTo() }
         when {
            items.isEmpty() -> emit(emptyResult())
            else -> {
               emit(
                  ProductCardHorizontalItem(
                     title = items.first().category,
                     items = items
                  ).toSuccessResult()
               )
            }
         }
      }
   }

   override fun getDiscountProducts(): FlowResult<ProductCardHorizontalItem> {
      return asyncFlow {
         val items = remoteSource.getDiscountItems().map { it.convertTo() }
         when {
            items.isEmpty() -> emit(emptyResult())
            else -> {
               emit(
                  ProductCardHorizontalItem(
                     title = items.first().category,
                     items = items
                  ).toSuccessResult()
               )
            }
         }
      }
   }
}