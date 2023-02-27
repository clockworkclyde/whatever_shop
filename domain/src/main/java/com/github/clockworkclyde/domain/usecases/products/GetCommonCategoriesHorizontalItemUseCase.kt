package com.github.clockworkclyde.domain.usecases.products

import com.github.clockworkclyde.core.common.IResourcesProvider
import com.github.clockworkclyde.core.common.ResultList
import com.github.clockworkclyde.core.utils.asyncFlow
import com.github.clockworkclyde.core.utils.toSuccessResult
import com.github.clockworkclyde.domain.model.product.CommonCategory
import com.github.clockworkclyde.domain.model.product.CommonCategoryEnum
import com.github.clockworkclyde.domain.model.product.CommonCategoryHorizontalItem
import com.github.clockworkclyde.domain.usecases.IUseCase
import kotlinx.coroutines.flow.Flow
import com.github.clockworkclyde.core.dto.Result
import javax.inject.Inject

class GetCommonCategoriesHorizontalItemUseCase @Inject constructor(
   private val resources: IResourcesProvider
) : IGetCommonCategoriesUseCase {

   override fun invoke(): Flow<ResultList<CommonCategory>> {
      return asyncFlow {
         CommonCategoryEnum.values().map {
            CommonCategory(
               title = resources.getString(it.stringResId),
               imageId = it.imageResId
            )
         }.toSuccessResult().let { emit(it) }
      }
   }
}

interface IGetCommonCategoriesUseCase : IUseCase.FlowOut<ResultList<CommonCategory>>