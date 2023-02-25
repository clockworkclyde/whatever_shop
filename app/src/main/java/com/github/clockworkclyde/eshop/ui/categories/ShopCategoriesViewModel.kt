package com.github.clockworkclyde.eshop.ui.categories

import com.github.clockworkclyde.core.common.AnyResult
import com.github.clockworkclyde.core.navigation.INavigator
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.eshop.navigation.directions.categories.ShopCategoriesDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ShopCategoriesViewModel @Inject constructor(
   private val navigator: INavigator
): BaseFlowViewModel() {

   override val destinations = ShopCategoriesDirections()

   override val resultFlow: Flow<AnyResult> by lazy {
      flow {

      }
   }

}