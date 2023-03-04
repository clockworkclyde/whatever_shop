package com.github.clockworkclyde.eshop.ui.bottomnav

import com.github.clockworkclyde.core.common.FlowResult
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.domain.usecases.cart.ObserveShoppingCartCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BottomNavigationViewModel @Inject constructor(
   private val observeCartItemsCount: ObserveShoppingCartCountUseCase
) : BaseFlowViewModel() {

   override val resultFlow: FlowResult<Int> by lazy {
      observeCartItemsCount()
   }
}