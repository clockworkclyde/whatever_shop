package com.github.clockworkclyde.domain.usecases.cart

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.core.utils.runResultSuspendCatch
import com.github.clockworkclyde.core.utils.toSuccessResult
import com.github.clockworkclyde.domain.model.order.OrderProduct
import com.github.clockworkclyde.domain.repository.IPreferenceRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import javax.inject.Inject

class AddProductToShoppingCartUseCase @Inject constructor(
   private val preferences: IPreferenceRepository,
) : IAddProductToShoppingCartUseCase {

   override suspend fun invoke(param: AddProductToShoppingCartUseCaseModel): AnyResult {
      return runResultSuspendCatch {
         preferences.addProductToShoppingCart(
            OrderProduct(
               name = param.name,
               color = param.color,
               addedTimeInMs = System.currentTimeMillis()
            )
         ).toSuccessResult()
      }
   }
}

data class AddProductToShoppingCartUseCaseModel(
   val name: String,
   val color: String
)

interface IAddProductToShoppingCartUseCase :
   IUseCase.SingleInOut<AddProductToShoppingCartUseCaseModel, AnyResult>