package com.github.clockworkclyde.eshop.ui.categories

import androidx.lifecycle.viewModelScope
import com.github.clockworkclyde.core.common.FlowResultList
import com.github.clockworkclyde.core.navigation.INavigator
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.core.presentation.viewmodels.INavigationViewModel
import com.github.clockworkclyde.core.utils.loadingResult
import com.github.clockworkclyde.domain.model.product.BaseProductCard
import com.github.clockworkclyde.domain.model.product.CommonCategory
import com.github.clockworkclyde.domain.model.product.ProductCardHorizontalItem
import com.github.clockworkclyde.domain.usecases.products.GetCommonCategoriesHorizontalItemUseCase
import com.github.clockworkclyde.domain.usecases.products.GetProductCategoriesHorizontalUseCase
import com.github.clockworkclyde.eshop.navigation.directions.categories.ShopCategoriesDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class ShopCategoriesViewModel @Inject constructor(
   private val getProductCategoriesHorizontal: GetProductCategoriesHorizontalUseCase,
   private val getCommonCategories: GetCommonCategoriesHorizontalItemUseCase,
   private val navigator: INavigator
) : BaseFlowViewModel(), INavigationViewModel<ShopCategoriesDirections> {

   override val directions = ShopCategoriesDirections()

   override val resultFlow: FlowResultList<ProductCardHorizontalItem> by lazy {
      flow {
         emit(loadingResult())
         emitAll(getProductCategoriesHorizontal())
      }
   }

   override val supportFlow by lazy {
      getCommonCategories().shareIn(
         scope = viewModelScope,
         started = SharingStarted.Eagerly,
         replay = 1
      )
   }

   fun onCommonCategoryClicked(item: CommonCategory) = Unit

   fun onHorizontalItemExpandClicked(item: ProductCardHorizontalItem) {
      // navigation to screen with all items
   }

   fun onProductCardClicked(index: Int, item: BaseProductCard) {
      processNavEvent(directions.rootToDetails(), navigator)
   }

   fun onProductCardAddToCartClicked(index: Int, item: BaseProductCard) = Unit

   fun onProfilePictureClicked() {
      processNavEvent(directions.rootToProfile(), navigator)
   }

   fun onNavDrawerClicked() {
      // click
   }
}